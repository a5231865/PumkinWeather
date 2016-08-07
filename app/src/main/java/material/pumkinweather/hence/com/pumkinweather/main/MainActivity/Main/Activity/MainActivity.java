package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import material.pumkinweather.hence.com.pumkinweather.R;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter.cityAdapter;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.CityWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.DailyWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.HourlyWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.weatherDatabase;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Network.VolleySingleton;

import static material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.Heweather.weatherKey.*;


/**
 * Created by Hence on 2016/7/30.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG_ADD_CITI = "add city";
    public static final String LOG_TAG = "Log";
    private boolean addcity = false;



    //String
    private static final String HEFENGAPI = "https://api.heweather.com/x3/weather?";
    private static final String APIKEY = "ed50ab68e15a46f198a7bfb34ebb4a56";

    private CityWeather cityWeather;
    private boolean resepflag=false;

    private VolleySingleton volleySignleton;
    private RequestQueue requestQueue;

    private RecyclerView recyclerView;
    private cityAdapter cityAdapter;
    private ViewPager viewPager;



    private ArrayList<CityWeather> cityList= new ArrayList<>();

    private View mainview;

    //SQL

    private static final String DATABASE_NAME="pumkinweather";
    private static final String TABLE_NAME="weather_table";
    private static final String CITY_NAME="city";
    private static final String UPDATE_DATE="date_update";
    private static final int DATE_BASE_VER=1;
    private weatherDatabase weatherDb;
    private Context context;
    private ArrayList<String> cityArrayList=new ArrayList<>();
    private boolean refreshflag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this.context;
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.mainlayout);

        refreshflag=true;



        mainview=findViewById(R.id.citylistrecyclerview);

        weatherDb=new weatherDatabase (this);


        //Network

        //Network
        volleySignleton = VolleySingleton.getInstance();
        requestQueue = volleySignleton.getmRequestQueue();


        //FAB
        ImageView fabicon = new ImageView(this); // Create an icon
        fabicon.setImageResource(R.drawable.plus);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(fabicon)
                .build();

        fabicon.setOnClickListener(this);

        fabicon.setTag(TAG_ADD_CITI);

        //SQL load data
        Log.d(LOG_TAG,"get all data ");
        cityArrayList=weatherDb.getAlldata();

        for (int i =0; i<cityArrayList.size();i++){

            Log.d(LOG_TAG,"first time send "+i);
            sendJsonrequest(cityArrayList.get(i),0);
        }


        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.citylistrecyclerview);
        cityAdapter = new cityAdapter(this,this, cityList);



        recyclerView.setBackground(getDrawable(R.drawable.mainbackground1));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);

        SimpleDateFormat df= new SimpleDateFormat("HH");

        int hour=Integer.valueOf(df.format(Calendar.getInstance().getTime()));

        Log.d(LOG_TAG,"Now : "+hour);
        if (hour <5 || hour > 19){
            recyclerView.setBackground(getResources().getDrawable(R.drawable.mainbackground3));
        }else if (hour<13){
            recyclerView.setBackground(getResources().getDrawable(R.drawable.mainbackground1));
        }else if (hour<20){
            recyclerView.setBackground(getResources().getDrawable(R.drawable.mainbackground2));
        }


    }

    private void setupWindowAnimations() {

        TransitionInflater transitionInflater= TransitionInflater.from(this);
        Transition transition=transitionInflater.inflateTransition(R.transition.transition_main);
        getWindow().setExitTransition(transition);
    }


    @Override
    public void onClick(View v) {
        final View view= v;
        if (v.getTag().equals(TAG_ADD_CITI)) {
            refreshflag=false;
            //Material Dialog handle
            new MaterialDialog.Builder(this)
                    .title(R.string.input)
                    .inputRangeRes(2, 20, R.color.material_red_50)
                    .input(null, null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            String City =   input.toString();
                            sendJsonrequest(City,1);
                            Log.d(LOG_TAG,"after send json");

                        }
                    }).show();


        }

    }

    public void sendJsonrequest(final String cityname, final  int flag){

        Log.d(LOG_TAG,"send"+cityname);
        String url= HEFENGAPI+"city="+cityname+"&key="+APIKEY;
        weatherDb=new weatherDatabase (this);

        Log.d(LOG_TAG,"sendurl"+url);


        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                CityWeather cityWeatheraft;
                Log.d(LOG_TAG, "sucess resp");
                cityWeatheraft=parseJSONResponse(response);
                Log.d(LOG_TAG,"sucess getresp");
                if (cityWeatheraft!=null){
                    Log.d(LOG_TAG,"flag true");
                    resepflag=true;
                }else{
                    Log.d(LOG_TAG,"flag false");
                    resepflag=false;
                }

                if (addcity){
                Log.d(LOG_TAG,"add data");

                int befcnt = cityList.size();

       //         cityList.add(cityWeather);
                boolean newData = false;
                if (flag==1){

                    if (!weatherDb.querryData(cityWeatheraft.getCityname())){

                        Log.d(LOG_TAG, "new data1" );
                        newData=true;
                    }else {
                        Snackbar.make(mainview,"已经在你的关注列表里咧@_@", Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (flag==0||newData) {
                    int aftcnt = cityList.size();

                    Log.d(LOG_TAG, "add City Adapter" );
                    cityAdapter.addData(cityWeatheraft);
                    Log.d(LOG_TAG,"after adapter "+ cityWeatheraft.getCityname());
//                    Log.d(LOG_TAG,"after adapter hour"+ cityWeatheraft.getHourlyWeathers().get(0).getHourdate());
//                    Log.d(LOG_TAG,"after adapter hour"+ cityWeatheraft.getHourlyWeathers().get(0).getHourtemp());
//                    Log.d(LOG_TAG,"after adapter hour"+ cityWeatheraft.getHourlyWeathers().get(0).getPop());
                    if (newData) {
                        Log.d(LOG_TAG, "new data2" );
                        weatherDb.insertData(cityWeatheraft.getCityname());
                    }
                    Log.d(LOG_TAG, "rebuild Recycler View" + befcnt + " " + aftcnt);
                    cityAdapter.notifyItemRangeChanged(befcnt, aftcnt);

                }}else
                {
                    Snackbar.make(mainview,"瓜瓜目前抓取不到火星地址哦>_<",Snackbar.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(LOG_TAG,"error resp");
                Snackbar.make(mainview,"瓜瓜目前抓取不到火星地址哦>_<",Snackbar.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(request);

    }

    public CityWeather parseJSONResponse(JSONObject response){
  //       cityWeather=new CityWeather();

        Log.d(LOG_TAG,"parse1");

        JSONObject resp= response;
        JSONObject detailJo;
        JSONObject cityJo;
        JSONObject aqiJo;
        String aqi="是啥";
        String city;
        String nowtemp;
        String condcd;
        String pm25="是啥";
        String condtxt;
        String cityqlty="天晓得";
        try {
            Log.d(LOG_TAG, "start parse");
            //    Log.d(LOG_TAG,resp.toString());

            JSONArray weatherarrary = resp.getJSONArray(KEY_API);
            //get Details
            detailJo = weatherarrary.getJSONObject(0);


            Log.d(LOG_TAG, "start parse2");


            Log.d(LOG_TAG, "start parse3");

            //getAQI
            //Log.d(LOG_TAG,detailJo.toString());
            try {
                if (detailJo.getJSONObject(KEY_AQI) != null) {
                    cityJo = detailJo.getJSONObject(KEY_AQI);

                    Log.d(LOG_TAG, "after send json true");

                    //when city not found
                    if (cityJo == null) {

                        Log.d(LOG_TAG, "parse fail");
                        return cityWeather;
                    }
                    try {

                        aqiJo = cityJo.getJSONObject(KEY_CITY);
                        Log.d(LOG_TAG, aqiJo.toString());
                        if (aqiJo.getString(KEY_AQI) != null) {
                            aqi = aqiJo.getString(KEY_AQI);
                        } else {
                            aqi = "不知道呢";
                        }
                        if (aqiJo.getString(KEY_PM25) != null) {
                            pm25 = aqiJo.getString(KEY_PM25);
                        } else {

                        }

                        if (aqiJo.getString(KEY_QLTY) != null) {
                            cityqlty = aqiJo.getString(KEY_QLTY);
                        } else {
                            cityqlty = "天晓得";
                        }

                    } catch (JSONException je) {
                        cityqlty = "天晓得";
                        pm25 = "是啥？";
                        aqi = "不知道呢";

                    }


                    Log.d(LOG_TAG, "parse succss1" + aqi + " " + pm25);
                }
            } catch (JSONException je) {
                Log.d(LOG_TAG, "json faile" + je.toString());


            }
            //get City
            cityJo = detailJo.getJSONObject(KEY_BASIC);
            city = cityJo.getString(KEY_CITY);


            if (refreshflag) {
                Snackbar.make(mainview,"已经为了刷新最新信息喽~", Snackbar.LENGTH_SHORT).show();

            } else {
                Snackbar.make(mainview, city + "添加成功", Snackbar.LENGTH_SHORT).show();
            }


            //get now temp
            cityJo=detailJo.getJSONObject(KEY_NOW);
            nowtemp=cityJo.getString(KEY_TMP);
            Log.d(LOG_TAG,"parse succss2"+city+" "+nowtemp);

            //get condition
            cityJo=cityJo.getJSONObject(KEY_COND);
            condcd=cityJo.getString(KEY_CODE);
            Log.d(LOG_TAG,"parse succss3"+condcd);

            //get condition txt
            condtxt=cityJo.getString(KEY_TXT);

            //get hourly
            JSONArray jsonArray=detailJo.getJSONArray(KEY_HOURLY_FCORCAST);
            ArrayList<HourlyWeather> hourlyList = new ArrayList<HourlyWeather>();
            HourlyWeather hourlyWeather;
            for (int i=0; i<jsonArray.length();i++){

                cityJo=jsonArray.getJSONObject(i);
                Log.d(LOG_TAG,"hourly added"+cityJo.getString(KEY_DATE));
                hourlyWeather=new HourlyWeather(cityJo.getString(KEY_DATE),cityJo.getString(KEY_TMP),cityJo.getString(KEY_POP));
                cityJo=cityJo.getJSONObject(KEY_WIND);

                hourlyWeather.setWinddir(cityJo.getString(KEY_DIR));
                hourlyWeather.setWindsc(cityJo.getString(KEY_SC));
                hourlyList.add(hourlyWeather);
                Log.d(LOG_TAG,"check after add "+hourlyList.get(i).getHourdate());

            }

            cityWeather=new CityWeather(city,aqi,pm25,Integer.parseInt(nowtemp),Integer.parseInt(condcd),hourlyList);
            cityWeather.setCondtxt(condtxt);
//            Log.d(LOG_TAG,"check after add 2"+cityWeather.getHourlyWeathers().get(0).getHourdate());

            cityWeather.setQlty(cityqlty);

            //Set daily weather
            jsonArray=detailJo.getJSONArray(KEY_DAILY_FORCAST);
            JSONObject astro;
            DailyWeather dailyWeather;
            for (int i=0;i<jsonArray.length();i++) {
                dailyWeather=new DailyWeather();
                cityJo = jsonArray.getJSONObject(i);
                astro=cityJo.getJSONObject(KEY_ASTRO);
                dailyWeather.setSunset(astro.getString(KEY_SS));
                dailyWeather.setSunraise(astro.getString(KEY_SR));
                dailyWeather.setWeatherdate(cityJo.getString(KEY_DATE));
                astro=cityJo.getJSONObject(KEY_TMP);
                dailyWeather.setHightemp(astro.getString(KEY_MAX));
                dailyWeather.setLowtemp(astro.getString(KEY_MIN));
                astro=cityJo.getJSONObject(KEY_COND);
                dailyWeather.setCondcdd(Integer.valueOf(astro.getString(KEY_CONDD)));
                dailyWeather.setCondcdn(Integer.valueOf(astro.getString(KEY_CONDN)));
                cityWeather.addDailyWeather(dailyWeather);
            }


            //getSuggestion

            String brf;
            String txt;

            try {
                cityJo = detailJo.getJSONObject(KEY_SUGGESTION);

                JSONObject suggJo = cityJo.getJSONObject(KEY_COMF);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.comf), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_FLU);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.flu), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_CW);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.cw), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_DRSG);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.drgs), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_SPORT);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.sport), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_TRAV);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.trav), brf, txt);

                suggJo = cityJo.getJSONObject(KEY_UV);

                brf = suggJo.getString(KEY_BRF);
                txt = suggJo.getString(KEY_TXT);

                cityWeather.addSuggesion(getString(R.string.uv), brf, txt);
            }catch (JSONException je){
                cityWeather.addSuggesion(getString(R.string.comf),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.flu),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.cw),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.drgs),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.sport),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.trav),"暂无","无能为力>_<||");
                cityWeather.addSuggesion(getString(R.string.uv),"暂无","无能为力>_<||");
            }

           Log.d(LOG_TAG,"hourly added compl"+cityWeather.getHourlyWeathers().size());

            addcity=true;





        }catch (JSONException je){
            Log.d(LOG_TAG,"JSONexp"+je.toString());
            addcity=false;
        }

        Log.d(LOG_TAG,"parse succss4");
        return cityWeather;

    }

}
