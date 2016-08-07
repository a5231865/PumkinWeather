package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hence on 2016/7/31.
 */
public class CityWeather {

    String cityname;
    String aqi;  //空气质量
    String pm25; //pm2.5
    int nowtemp; //当天天气
    int condcd; //当天天气情况
    String condtxt;
    String qlty;
    int condcolor;

    ArrayList<DailyWeather> dailyWeather; //天气预报
    ArrayList<HourlyWeather> hourlyWeathers; //每小时情况
    ArrayList<Suggstion> suggstionsList;//建议

    public static final String LOG_TAG = "Log";


    public String getCityname() {
        return cityname;
    }

    public String getAqi() {
        return aqi;
    }

    public String getPm25() {
        return pm25;
    }

    public int getCondcd() {
        return condcd;

    }

    public String getCondtxt() {
        return condtxt;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public void setCondtxt(String condtxt) {
        this.condtxt = condtxt;
    }

    public void setSuggstionsList(ArrayList<Suggstion> suggstionsList) {
        this.suggstionsList = suggstionsList;
    }


    public ArrayList<Suggstion> getSuggstionsList() {
        return suggstionsList;
    }

    public void addSuggesion(String type, String bref, String txtdetail) {
        Log.d(LOG_TAG, "Sugg called" + type);
        Suggstion suggstion = new Suggstion(type, bref, txtdetail);
        this.suggstionsList.add(suggstion);
    }

    public int getNowtemp() {
        return nowtemp;
    }

    public CityWeather() {
        hourlyWeathers = new ArrayList<HourlyWeather>();
        dailyWeather = new ArrayList<DailyWeather>();

    }

    public void setCondcolor(int condcolor) {
        this.condcolor = condcolor;
    }

    public int getCondcolor() {
        return condcolor;
    }

    public CityWeather(String cityname, String aqi, String pm25, int nowtemp, int condcd, ArrayList<HourlyWeather> hourlyWeathers) {
        this.cityname = cityname;
        this.aqi = aqi;
        this.nowtemp = nowtemp;
        this.pm25 = pm25;
        this.condcd = condcd;

        this.hourlyWeathers = hourlyWeathers;
        this.dailyWeather = new ArrayList<DailyWeather>();
        this.suggstionsList = new ArrayList<Suggstion>();

    }


    public ArrayList<String> getHourdetaillist(int flag) {
        ArrayList<String> hourdetails = new ArrayList<>();
        Log.d(LOG_TAG, "add string array flag" + flag);
        for (int i = 0; i < hourlyWeathers.size(); i++) {
            String detail = "";
            switch (flag) {
                case 1:
                    detail = hourlyWeathers.get(i).getHourdate();
                case 2:
                    detail = hourlyWeathers.get(i).getHourtemp();
                case 3:
                    detail = hourlyWeathers.get(i).getPop();
            }
            Log.d(LOG_TAG, "add string array" + detail);
            hourdetails.add(detail);
        }
        return hourdetails;
    }

    public void setHourlyweather(String hourdate, String hourtemp, String pop) {
        HourlyWeather hourlyWeather = new HourlyWeather(hourdate, hourtemp, pop);
        hourlyWeathers.add(hourlyWeather);
    }

    public ArrayList<DailyWeather> getDailyWeather() {
        return dailyWeather;
    }

    public ArrayList<HourlyWeather> getHourlyWeathers() {
        return hourlyWeathers;
    }

    public void addDailyWeather(DailyWeather dailyw) {
        dailyWeather.add(dailyw);
    }

    public class Suggstion {
        String type;
        String bref;
        String txtdetail;

        public Suggstion(String type, String bref, String txtdetail) {
            this.bref = bref;
            this.type = type;
            this.txtdetail = txtdetail;
        }

        public Suggstion() {

        }

        public ArrayList<String> getSuggestion() {
            ArrayList<String> sugesstion = new ArrayList<>();
            sugesstion.add(type);
            sugesstion.add(bref);
            sugesstion.add(txtdetail);
            return sugesstion;
        }
    }

}
