package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase;

import android.provider.ContactsContract;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Hence on 2016/7/31.
 */
public class DailyWeather {
    String sunraise;//日出
    String sunset; //日落
    String weatherdate; //日期
    String hightemp ;//最高温度
    String lowtemp ;// 最低温度
    int condcdd;
    int condcdn;

    public DailyWeather(){

    }

    public  DailyWeather(String sunraise, String sunset, String weatherdate, String hightemp, String lowtemp, int condcdd, int condcdn){
        this.sunraise=sunraise;
        this.sunset=sunset;
        this.weatherdate=weatherdate;
        this.hightemp=hightemp;
        this.lowtemp=lowtemp;
        this.condcdd=condcdd;
        this.condcdn=condcdn;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public void setSunraise(String sunraise) {
        this.sunraise = sunraise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setWeatherdate(String weatherdate) {
        this.weatherdate = weatherdate;
    }

    public void setCondcdd(int condcdd) {
        this.condcdd = condcdd;
    }

    public void setCondcdn(int condcdn) {
        this.condcdn = condcdn;
    }

    public String getsunraise(){
        return sunraise;
    }

    public String getHightemp() {
        return hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public String getSunraise() {
        return sunraise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getWeatherdate() {
        return weatherdate;
    }

    public int getCondcdd() {
        return condcdd;
    }

    public int getCondcdn() {
        return condcdn;
    }
}
