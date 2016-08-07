package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import material.pumkinweather.hence.com.pumkinweather.R;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.CityWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.DailyWeather;

/**
 * Created by Hence on 2016/8/6.
 */

public class futureAdapter extends RecyclerView.Adapter<futureAdapter.futureViewholder> {

    private LayoutInflater layoutInflater;
    private ArrayList<DailyWeather> dailyWeathers = new ArrayList<>();
    private Context context;

    int dayOfWeek;

    @Override
    public futureViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.forcastdetail, parent, false);
        futureAdapter.futureViewholder viewholder = new futureAdapter.futureViewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(futureViewholder holder, int position) {
        DailyWeather dailyWeather = dailyWeathers.get(position);
        int day = dayOfWeek + position;
        if (day > 7) {
            day = day - 7;
        }
        switch (day) {
            case 1:
                holder.futureDate.setText(R.string.Monday);
            case 2:
                holder.futureDate.setText(R.string.Tuesday);
            case 3:
                holder.futureDate.setText(R.string.Wednesday);
            case 4:
                holder.futureDate.setText(R.string.Thursday);
            case 5:
                holder.futureDate.setText(R.string.Friday);
            case 6:
                holder.futureDate.setText(R.string.Saturday);
            case 7:
                holder.futureDate.setText(R.string.Sunday);

        }
        if (position == 0) {
            holder.futureDate.setText(R.string.Today);
        }

        holder.futureTemp.setText(dailyWeather.getLowtemp() + "°/ " + dailyWeather.getHightemp() + "°");
        holder.futureSS.setText(dailyWeather.getSunset());
        holder.futureSR.setText(dailyWeather.getSunraise());

        holder.futureNimg.setImageResource(getResourseidImg(dailyWeather.getCondcdn()));
        holder.futureDimg.setImageResource(getResourseidImg(dailyWeather.getCondcdd()));

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
        if (condcd == 300) {
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

    @Override
    public int getItemCount() {
        return dailyWeathers.size();
    }

    public void addData(DailyWeather dailyWeather){
        dailyWeathers.add(dailyWeather);
    }

    public futureAdapter(Context context,ArrayList<DailyWeather> dailyWeathers){
        this.context=context;
        this.dailyWeathers=dailyWeathers;
        layoutInflater=LayoutInflater.from(context);
    }


    public class futureViewholder extends RecyclerView.ViewHolder {

        private TextView futureDate;
        private TextView futureTemp;
        private TextView futureSS;
        private TextView futureSR;
        private ImageView futureDimg;
        private ImageView futureNimg;

        public futureViewholder(View itemView) {
            super(itemView);
            futureDate = (TextView) itemView.findViewById(R.id.future_date);
            futureTemp = (TextView) itemView.findViewById(R.id.future_temp);
            futureSR = (TextView) itemView.findViewById(R.id.future_sr);
            futureSS = (TextView) itemView.findViewById(R.id.future_ss);
            futureDimg = (ImageView) itemView.findViewById(R.id.future_dimg);
            futureNimg = (ImageView) itemView.findViewById(R.id.future_nimg);
        }
    }
}
