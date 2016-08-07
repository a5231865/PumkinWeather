package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

import material.pumkinweather.hence.com.pumkinweather.R;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.DailyWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.HourlyWeather;

/**
 * Created by Hence on 2016/8/1.
 */
public class hourlyAdapter extends RecyclerView.Adapter<hourlyAdapter.custViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<HourlyWeather> hourList = new ArrayList<>();
    public static final String LOG_TAG = "Log";
    ArrayList<String> suggestiontype = new ArrayList<>();
    ArrayList<String> suggestionbrf = new ArrayList<>();
    ArrayList<String> suggestiontxt = new ArrayList<>();
    private ArrayList<DailyWeather> dailyWeathers = new ArrayList<>();

    private static final int HOURLY_VIEW = 1;
    private static final int SUGGE_VIEW = 2;
    private static final int FUTURE_VIEW = 3;
    private int [] mDataSetTypes=new int[]{0,1};
    private int datasize=0;
    private int hourlycnt=0;
    private int dailycnt=0;
    int dayOfWeek;

    private int color ;
    private int futurecolor;

    @Override
    public custViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType==HOURLY_VIEW) {

            view = layoutInflater.inflate(R.layout.hourlydetails, parent, false);
            hourlyViewHolder hourlyViewHolder = new hourlyViewHolder(view);
            return hourlyViewHolder;
        }else if (viewType==SUGGE_VIEW){

            view = layoutInflater.inflate(R.layout.suggestionlayout, parent, false);
            suggViewHolder suggViewHolder = new suggViewHolder(view);
            return suggViewHolder;
        }else{
            view = layoutInflater.inflate(R.layout.forcastdetail, parent, false);
            futureViewholder futureViewholder = new futureViewholder(view);
            return futureViewholder;
        }

    }

    @Override
    public int getItemViewType(int position) {

        return mDataSetTypes[position];
    }


    @Override
    public void onBindViewHolder(custViewHolder holder, int position) {
        Log.d(LOG_TAG, "bind view" + position);



        switch (holder.getItemViewType()) {
            case HOURLY_VIEW:
                Log.d(LOG_TAG,"Color2"+color);
                hourlyViewHolder hourlyViewHolder= (hourlyViewHolder) holder;
                HourlyWeather hourlyWeather = hourList.get(position-dailycnt);
                Log.d(LOG_TAG, "hourdate" + hourlyWeather.getHourdate());
                hourlyViewHolder.hourdate.setText(hourlyWeather.getHourdate().substring(10,16));
                hourlyViewHolder.hourtemp.setText(hourlyWeather.getHourtemp() + "°");
                hourlyViewHolder.hourpop.setText(hourlyWeather.getPop() + "%");
                hourlyViewHolder.hourwindd.setText(hourlyWeather.getWinddir()+" "+hourlyWeather.getWindsc()+"级");
                hourlyViewHolder.cardView.setCardBackgroundColor(color);
                break;
            case SUGGE_VIEW:
                Log.d(LOG_TAG, "sugg view cnt" + position+" hourlycnt "+hourlycnt);
                suggViewHolder suggViewHolder=(suggViewHolder) holder;
                suggViewHolder.suggtype.setText(suggestiontype.get(position-hourlycnt));
                suggViewHolder.suggbrf.setText(suggestionbrf.get(position-hourlycnt));
                suggViewHolder.suggtxt.setText(suggestiontxt.get(position-hourlycnt));
                switch (suggestiontype.get(position-hourlycnt)){
                    case "舒适指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.comf);
                        break;
                    case "穿衣指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.drsg);
                        break;
                    case "洗车指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.cw);
                        break;
                    case "运动指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.sport);
                        break;
                    case "防晒指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.uv);
                        break;
                    case "旅游指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.tv);
                        break;
                    case "流感指数":
                        suggViewHolder.suggimg.setImageResource(R.drawable.flu);
                        break;
                }
                break;

            case FUTURE_VIEW:
                DailyWeather dailyWeather = dailyWeathers.get(position);
                String date=dailyWeather.getWeatherdate();

                Calendar calendar = Calendar.getInstance();
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                futureViewholder futureViewholder=(futureViewholder) holder;
                Log.d(LOG_TAG,"Dayof week"+position +" "+ dayOfWeek);
                int day = dayOfWeek + position ;
                if (day > 7) {
                    day = day - 7;
                }
                switch (day) {
                    case 2:
                        futureViewholder.futureDate.setText(R.string.Monday);
                        break;
                    case 3:
                        futureViewholder.futureDate.setText(R.string.Tuesday);
                        break;
                    case 4:
                        futureViewholder.futureDate.setText(R.string.Wednesday);
                        break;
                    case 5:
                        futureViewholder.futureDate.setText(R.string.Thursday);
                        break;
                    case 6:
                        futureViewholder.futureDate.setText(R.string.Friday);
                        break;
                    case 7:
                        futureViewholder.futureDate.setText(R.string.Saturday);
                        break;
                    case 1:
                        futureViewholder.futureDate.setText(R.string.Sunday);
                        break;

                }
                if (position == 0) {
                    futureViewholder.futureDate.setText(R.string.Today);
                }

                futureViewholder.futureTemp.setText(dailyWeather.getLowtemp() + "°/ " + dailyWeather.getHightemp() + "°");
                futureViewholder.futureSS.setText(dailyWeather.getSunset());
                futureViewholder.futureSR.setText(dailyWeather.getSunraise());

                futureViewholder.futureNimg.setImageResource(getResourseidImg(dailyWeather.getCondcdn()));
                futureViewholder.futureDimg.setImageResource(getResourseidImg(dailyWeather.getCondcdd()));
                Log.d(LOG_TAG,"background condcd"+dailyWeather.getCondcdd());

                futureViewholder.futureCardview.setCardBackgroundColor(ContextCompat.getColor(context,getResoursecolor(dailyWeather.getCondcdd())));
                Log.d(LOG_TAG,"background color"+(dailyWeather.getCondcdd()));
                break;

        }

    }
    public int getResourseidImg(int condcd) {
        int resourceid = R.drawable.sun;

        if (condcd == 100) {
            return resourceid;
        }
        if (condcd == 101) {
            resourceid = R.drawable.cloud;
        }
        if (condcd == 103) {
            resourceid = R.drawable.partycloud;
        }
        if (condcd == 300 ||(condcd>=305&&condcd<=312)) {
            resourceid = R.drawable.rain;
        }
        if (condcd == 302) {
            resourceid = R.drawable.cloud;
        }
        if (condcd == 303) {
            resourceid = R.drawable.cloud;
        }
        return resourceid;
    }

    public int getResoursecolor (int condcd) {
        int resourceid = R.color.card_background;

        if (condcd == 100) {
            return R.color.cardview_backgour_sun;
        }
        if (condcd == 101) {
            resourceid = R.color.cardview_backgour_cloud;
        }
        if (condcd == 103) {
            resourceid = R.color.cardview_backgour_partyclod;
        }
        if (condcd == 300) {
            resourceid = R.color.cardview_backgour_rain;
        }
        if (condcd == 302) {
            resourceid = R.color.cardview_backgour_cloud;
        }
        if (condcd == 303) {
            resourceid = R.color.cardview_backgour_cloud;
        }
        return resourceid;
    }

    @Override
    public int getItemCount() {

        Log.d(LOG_TAG, "hour size" + datasize);
        return datasize;
    }

    public hourlyAdapter(Context context, ArrayList<HourlyWeather> hourList) {
        this.context = context;
        this.hourList = hourList;
        Log.d(LOG_TAG, "hour size create" + hourList.size());

//        Log.d(LOG_TAG,"hour display"+hourList.get(0).getHourdate());
        layoutInflater = layoutInflater.from(context);

    }

    public hourlyAdapter(Context context,ArrayList<HourlyWeather> hourList, ArrayList<String> suggestiontype, ArrayList<String> suggestionbrf, ArrayList<String> suggestiontxt, int color,ArrayList<DailyWeather> dailyWeathers) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.hourList=hourList;
        this.suggestiontxt = suggestiontxt;
        this.suggestionbrf = suggestionbrf;
        this.suggestiontype = suggestiontype;
        this.dailyWeathers=dailyWeathers;
        datasize=hourList.size()+suggestionbrf.size()+dailyWeathers.size();
        mDataSetTypes=new int[datasize];
        int i =0;
        for (;i<dailyWeathers.size();i++){
            Log.d(LOG_TAG, "daily adapter " + i);
            mDataSetTypes[i]=FUTURE_VIEW;
        }
        dailycnt=i;

        for (;i<hourList.size()+dailyWeathers.size();i++){
            Log.d(LOG_TAG, "hour adapter hourly" + i);
            mDataSetTypes[i]=HOURLY_VIEW;
        }
        hourlycnt=i;
        for (;i<suggestiontype.size()+hourList.size()+dailyWeathers.size();i++){
            Log.d(LOG_TAG, "hour adapter sugg" + i);
            mDataSetTypes[i]=SUGGE_VIEW;
        }
        Log.d(LOG_TAG,"Color3"+color);
        this.color=color;

    }

    public class hourlyViewHolder extends custViewHolder{

        private TextView hourdate;
        private TextView hourtemp;
        private TextView hourpop;
        private CardView cardView;
        private TextView hourwindd;
        private TextView hourwindsc;

        public hourlyViewHolder(View itemView) {
            super(itemView);

            hourdate = (TextView) itemView.findViewById(R.id.hourdate);
            hourtemp = (TextView) itemView.findViewById(R.id.temp_card);
            hourpop = (TextView) itemView.findViewById(R.id.rain_rate);
            cardView = (CardView) itemView.findViewById(R.id.hourlycard_view);
            hourwindd= (TextView) itemView.findViewById(R.id.wind_detail);
        }
    }

    public class suggViewHolder extends custViewHolder {
        private TextView suggtype;
        private TextView suggbrf;
        private TextView suggtxt;
        private CardView cardView;
        private ImageView suggimg;

        public suggViewHolder(View itemView) {
            super(itemView);
            suggtype = (TextView) itemView.findViewById(R.id.suggtype);
            suggbrf = (TextView) itemView.findViewById(R.id.suggbrf);
            suggtxt = (TextView) itemView.findViewById(R.id.suggtext);
            suggimg= (ImageView) itemView.findViewById(R.id.suggimg);
        }
    }
    public class futureViewholder extends custViewHolder {

        private TextView futureDate;
        private TextView futureTemp;
        private TextView futureSS;
        private TextView futureSR;
        private ImageView futureDimg;
        private ImageView futureNimg;
        private CardView futureCardview;

        public futureViewholder(View itemView) {
            super(itemView);
            futureDate = (TextView) itemView.findViewById(R.id.future_date);
            futureTemp = (TextView) itemView.findViewById(R.id.future_temp);
            futureSR = (TextView) itemView.findViewById(R.id.future_sr);
            futureSS = (TextView) itemView.findViewById(R.id.future_ss);
            futureDimg = (ImageView) itemView.findViewById(R.id.future_dimg);
            futureNimg = (ImageView) itemView.findViewById(R.id.future_nimg);
            futureCardview= (CardView) itemView.findViewById(R.id.future_cardview);
        }
    }


    public class custViewHolder extends RecyclerView.ViewHolder{

        public custViewHolder(View itemView) {
            super(itemView);
        }
    }


}
