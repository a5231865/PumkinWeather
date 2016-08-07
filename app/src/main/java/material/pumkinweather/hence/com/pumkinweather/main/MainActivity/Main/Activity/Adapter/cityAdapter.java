package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import material.pumkinweather.hence.com.pumkinweather.R;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Action.SwipeDetector;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.CityWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.DailyWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.weatherDatabase;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DetailsActivity;

/**
 * Created by Hence on 2016/7/31.
 */
public class cityAdapter extends RecyclerView.Adapter<cityAdapter.cityViewholder> {

    private LayoutInflater layoutInflater;
    private ArrayList<CityWeather> weatherArrayList = new ArrayList<>();
    private Context context;
    private final Activity activity;
    private int color;
    private static int[] colorlist;
    private weatherDatabase weatherDb;
    private String cityname;
    private cityViewholder holder;

    public static final String LOG_TAG = "Log";
    @Override
    public cityViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.citycardlayout, parent,false);
        cityViewholder viewholder= new cityViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(cityViewholder holder, int position) {

     //   Log.d(LOG_TAG,"rebuild Recycler View n"+ position);
            CityWeather currCity=weatherArrayList.get(position);
        holder.lowTemp.setText(String.valueOf(""+currCity.getNowtemp())+"℃");
        DailyWeather dailyWeather=currCity.getDailyWeather().get(0);
        holder.highTemp.setText(dailyWeather.getLowtemp()+"°～ "+dailyWeather.getHightemp()+"°" );
        holder.qlty.setText("空气质量："+currCity.getQlty());
        holder.pm25.setText("PM 2.5: "+String.valueOf(currCity.getPm25()));
        holder.cityName.setText(currCity.getCityname());
        holder.condtxt.setText(currCity.getCondtxt());
        Log.d(LOG_TAG,"return code"+ currCity.getCondcd());
        switch (currCity.getCondcd()){
            case 100: //晴
                holder.imageView.setImageResource(R.drawable.sun);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_sun);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 101: //多云
                holder.imageView.setImageResource(R.drawable.partycloud);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_partyclod);
                holder.cardView.setCardBackgroundColor(color);
       //         Log.d(LOG_TAG,"color"+ color);
       //         holder.cardView.setCardBackgroundColor(color.t);
                break;
            case 103:
                holder.imageView.setImageResource(R.drawable.partycloud);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_cloud);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 104:
                holder.imageView.setImageResource(R.drawable.cloud);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_cloud);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 300: //阵雨
                holder.imageView.setImageResource(R.drawable.rain);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_rain);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 305: //阵雨
                holder.imageView.setImageResource(R.drawable.rain);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_rain);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 306: //阵雨
                holder.imageView.setImageResource(R.drawable.rain);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_rain);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 307: //阵雨
                holder.imageView.setImageResource(R.drawable.rain);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_rain);
                holder.cardView.setCardBackgroundColor(color);
                break;
            case 302://雷阵雨
                holder.imageView.setImageResource(R.drawable.storm);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_storm);
                holder.cardView.setCardBackgroundColor(color);
            case 303://雷阵雨
                holder.imageView.setImageResource(R.drawable.storm);
                color=ContextCompat.getColor(context,R.color.cardview_backgour_storm);
                holder.cardView.setCardBackgroundColor(color);


        }

        Log.d(LOG_TAG,"Color adapter second"+position+color);
        colorlist[position]=color;
        weatherArrayList.get(position).setCondcolor(color);

        Log.d(LOG_TAG,"Color adapter th"+position+" "+colorlist[position]);
    }

    public void addData(CityWeather cityWeather){

        CityWeather currCity=cityWeather;
        weatherArrayList.add(currCity);
        int befcnt= weatherArrayList.size();

        notifyItemInserted(weatherArrayList.size());

    }

    @Override
    public int getItemCount() {

        colorlist=new int[weatherArrayList.size()];

        return weatherArrayList.size();

    }


    public cityAdapter(Context context, Activity activity,ArrayList<CityWeather> data){
   //     Log.d(LOG_TAG,"Color adapter first"+data.size());
        this.context = context;
        this.activity=activity;
        layoutInflater=LayoutInflater.from(context);
        this.weatherArrayList=data;


  //      colorlist=new int[weatherArrayList.size()];
    }

  //  public ArrayList<CityWeather> getWeatherArrayList() {
 //       return weatherArrayList;
  //  }

   // public CityWeather getCityWeather(int postion){
  //      return weatherArrayList.get(postion);
 //   }

    public class cityViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView cityName;
        private TextView highTemp;
        private TextView lowTemp;
        private CardView cardView;
        private ImageView imageView;
        private TextView condtxt;
        private TextView qlty;
        private TextView pm25;


        public cityViewholder(View itemView) {
            super(itemView);
            cityName= (TextView) itemView.findViewById(R.id.city_card);
            highTemp= (TextView) itemView.findViewById(R.id.hightemp_card);
            lowTemp= (TextView) itemView.findViewById(R.id.lowtemp_card);
            imageView= (ImageView) itemView.findViewById(R.id.weatherimg_card);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            condtxt= (TextView) itemView.findViewById(R.id.cond_text);
            qlty= (TextView) itemView.findViewById(R.id.qlty);
            pm25= (TextView) itemView.findViewById(R.id.pm25);

            SwipeDetector swipeDetector=new SwipeDetector();
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {



            Intent intent=new Intent(v.getContext(), DetailsActivity.class);
            Log.d(LOG_TAG,"On Click"+getPosition());
            ArrayList<String> hourlydate=new ArrayList<>();
            ArrayList<String> hourlytemp=new ArrayList<>();
            ArrayList<String> hourlypop=new ArrayList<>();
            ArrayList<String> hourlywinddir=new ArrayList<>();
            ArrayList<String> hourlywindsc=new ArrayList<>();
            ArrayList<String> suggestiontype=new ArrayList<>();
            ArrayList<String> suggestionbrf=new ArrayList<>();
            ArrayList<String> suggestiontxt=new ArrayList<>();
            ArrayList<String> futuredate=new ArrayList<>();
            ArrayList<String> futureltemp=new ArrayList<>();
            ArrayList<String> futurehtemp=new ArrayList<>();
            ArrayList<String> futuress=new ArrayList<>();
            ArrayList<String> futuresr=new ArrayList<>();
            ArrayList<String> futurecondcdd=new ArrayList<>();
            ArrayList<String> futurecondcdn=new ArrayList<>();

            DailyWeather dailyWeather;

            CityWeather currcity= weatherArrayList.get(getAdapterPosition());
            Log.d(LOG_TAG,"On Click"+currcity.getCityname());
//            Log.d(LOG_TAG,"On Click2"+currcity.getHourlyWeathers().get(0).getHourdate());

            for (int i=0;i<currcity.getHourlyWeathers().size();i++){
                hourlydate.add(currcity.getHourlyWeathers().get(i).getHourdate());
                hourlytemp.add(currcity.getHourlyWeathers().get(i).getHourtemp());
                hourlypop.add(currcity.getHourlyWeathers().get(i).getPop());
                hourlywinddir.add(currcity.getHourlyWeathers().get(i).getWinddir());
                hourlywindsc.add(currcity.getHourlyWeathers().get(i).getWindsc());
            }
            for (int i=0;i<currcity.getDailyWeather().size();i++){
                dailyWeather=currcity.getDailyWeather().get(i);
                futuredate.add(dailyWeather.getWeatherdate());
                futureltemp.add(dailyWeather.getLowtemp());
                futurehtemp.add(dailyWeather.getHightemp());
                futuress.add(dailyWeather.getSunset());
                futuresr.add(dailyWeather.getSunraise());
                futurecondcdd.add(String.valueOf(dailyWeather.getCondcdd()));
                futurecondcdn.add(String .valueOf(dailyWeather.getCondcdn()));
            }
            intent.putExtra("FUTU_DATE",futuredate);
            intent.putExtra("FUTU_LTMP",futureltemp);
            intent.putExtra("FUTU_HTMP",futurehtemp);
            intent.putExtra("FUTU_SS",futuress);
            intent.putExtra("FUTU_SR",futuresr);
            intent.putExtra("FUTU_CDD",futurecondcdd);
            intent.putExtra("FUTU_CDN",futurecondcdn);
       //     hourlydate=weatherArrayList.get(getPosition()).getHourdetaillist(1);
        //    hourlytemp=weatherArrayList.get(getPosition()).getHourdetaillist(2);
         //   hourlypop=weatherArrayList.get(getPosition()).getHourdetaillist(3);


//            Log.d(LOG_TAG,"On Click3"+hourlydate.get(0));
            intent.putExtra("HOUR_DATE",hourlydate);
            intent.putExtra("HOUR_TEMP",hourlytemp);
            intent.putExtra("HOUR_POP",hourlypop);
            intent.putExtra("HOUR_DIR",hourlywinddir);
            intent.putExtra("HOUR_SC",hourlywindsc);
            CityWeather.Suggstion suggstion;
            for (int i=0;i<currcity.getSuggstionsList().size();i++){
                suggstion=currcity.getSuggstionsList().get(i);

                Log.d(LOG_TAG,"sugg "+suggstion.getSuggestion().get(0));
                suggestiontype.add(suggstion.getSuggestion().get(0));
                suggestionbrf.add(suggstion.getSuggestion().get(1));
                suggestiontxt.add(suggstion.getSuggestion().get(2));

            }

            intent.putExtra("SUGGS_TYPE",suggestiontype);
            intent.putExtra("SUGGS_BRF",suggestionbrf);
            intent.putExtra("SUGGS_TXT",suggestiontxt);

            intent.putExtra("BACK_COLO",currcity.getCondcolor());


            String title=currcity.getCityname()+"  "+currcity.getCondtxt()+" "+currcity.getNowtemp()+"°";

            intent.putExtra("CITY_NAME",title);



      //      ActivityOptionsCompat compat= ActivityOptionsCompat.makeSceneTransitionAnimation(activity,null);

      //      context.startActivity(intent, compat.toBundle());

            context.startActivity(intent);




        }


        @Override
        public boolean onLongClick(View view) {
            CityWeather currcity = weatherArrayList.get(getPosition());
            weatherDb=weatherDb=new weatherDatabase (context);
            cityname=currcity.getCityname();
            holder = (cityViewholder) view.getTag();
            Log.d(LOG_TAG,"holder position"+getAdapterPosition());
            new MaterialDialog.Builder(context).title("不再关注"+currcity.getCityname()+"了吗？")
                    .positiveText("决定了")
                    .negativeText("再想想")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO

                            Log.d(LOG_TAG,"Long Click");
                            weatherDb.deleteData(cityname);

                            weatherArrayList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    }).show();
            return false;
        }
    }


}
