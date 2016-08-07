package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase;

/**
 * Created by Hence on 2016/8/1.
 */
public class HourlyWeather {
    private String hourdate;
    private String hourtemp;
    private String pop;

    private String winddir;
    private String windsc;

    public HourlyWeather(){

    }

    public HourlyWeather(String hourdate,String hourtemp, String pop){
        this.hourdate=hourdate;
        this.hourtemp=hourtemp;
        this.pop=pop;
    }

    public String getHourdate(){
        return hourdate;
    }

    public String getHourtemp() {
        return hourtemp;
    }

    public String getPop() {
        return pop;
    }

    public void setWinddir(String winddir) {
        this.winddir = winddir;
    }

    public void setWindsc(String windsc) {
        this.windsc = windsc;
    }

    public String getWinddir() {
        return winddir;
    }

    public String getWindsc() {
        return windsc;
    }
}
