package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wingjay.blurimageviewlib.BlurImageView;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import material.pumkinweather.hence.com.pumkinweather.R;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter.cityAdapter;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter.suggesstionAdapter;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.CityWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Adapter.hourlyAdapter;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.DailyWeather;
import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.HourlyWeather;


import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import static material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.Heweather.weatherKey.KEY_DIR;
import static material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.DataBase.Heweather.weatherKey.KEY_WIND;


/**
 * Created by Hence on 2016/8/1.
 */
public class DetailsActivity extends AppCompatActivity {

    private CityWeather cityWeather;
    private Context context;
    public static final String LOG_TAG = "Log";
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private hourlyAdapter hourlyAdapter;
    private HourlyWeather hourlyWeather;
    private suggesstionAdapter suggAdapter;
    private ArrayList<HourlyWeather> hourList = new ArrayList<>();
    private ArrayList<DailyWeather> dailyWeathers = new ArrayList<>();
    private String cityName;
    private ViewPager viewPager;

    private ImageView imageviewhead;
    private ImageView imageviewblur;
    private Bitmap bitmap;
    private int color;
    private Toolbar mToobar;
    private TabLayout mTablayout;


    ArrayList<String> hourlydate = new ArrayList<>();
    ArrayList<String> hourlytemp = new ArrayList<>();
    ArrayList<String> hourlypop = new ArrayList<>();
    ArrayList<String> suggestiontype = new ArrayList<>();
    ArrayList<String> suggestionbrf = new ArrayList<>();
    ArrayList<String> suggestiontxt = new ArrayList<>();
    ArrayList<String> hourlywinddir = new ArrayList<>();
    ArrayList<String> hourlywindsc = new ArrayList<>();

    ArrayList<String> futuredate = new ArrayList<>();
    ArrayList<String> futureltemp = new ArrayList<>();
    ArrayList<String> futurehtemp = new ArrayList<>();
    ArrayList<String> futuress = new ArrayList<>();
    ArrayList<String> futuresr = new ArrayList<>();
    ArrayList<String> futurecondcdd = new ArrayList<>();
    ArrayList<String> futurecondcdn = new ArrayList<>();

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setupWindowAnimations();
        setContentView(R.layout.dailydetails);

        Log.d(LOG_TAG, "start second activity ");
        context = this.context;
        Intent intent = getIntent();
        hourlydate = intent.getStringArrayListExtra("HOUR_DATE");

        //tab layout
        mToobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToobar);
        mTablayout = (TabLayout) findViewById(R.id.tab_bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        title = intent.getStringExtra("CITY_NAME");
        mToobar.setTitle(title);


        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctlLayout);
        mCollapsingToolbarLayout.setTitle(title);

        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色


//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_bar);

//        tabLayout.addTab(tabLayout.newTab().setText("了解当下"));
//        tabLayout.addTab(tabLayout.newTab().setText("预测未来"));
 //       tabLayout.setupWithViewPager(viewPager);

  //      viewPager = (ViewPager) findViewById(R.id.viewpager_tab);

        imageviewhead = (ImageView) findViewById(R.id.headerblur);
        //       imageviewblur = (ImageView) findViewById(R.id.headerblur);
        //       Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.header1);
        //      Bitmap blurred = blurRenderScript(this,bitmap, 25);
        //     imageviewblur.setImageBitmap(bitmap);


//        Log.d(LOG_TAG,"first hour date"+hourlydate.get(0));
        hourlytemp = intent.getStringArrayListExtra("HOUR_TEMP");
        hourlypop = intent.getStringArrayListExtra("HOUR_POP");
        hourlywinddir = intent.getStringArrayListExtra("HOUR_DIR");
        hourlywindsc = intent.getStringArrayListExtra("HOUR_SC");


        futuredate = intent.getStringArrayListExtra("FUTU_DATE");
        futureltemp = intent.getStringArrayListExtra("FUTU_LTMP");
        futurehtemp = intent.getStringArrayListExtra("FUTU_HTMP");
        futuress = intent.getStringArrayListExtra("FUTU_SS");
        futuresr = intent.getStringArrayListExtra("FUTU_SR");
        futurecondcdd = intent.getStringArrayListExtra("FUTU_CDD");
        futurecondcdn = intent.getStringArrayListExtra("FUTU_CDN");
        for (int i=0;i<futuredate.size();i++) {
            DailyWeather dailyWeather = new DailyWeather(futuresr.get(i),futuress.get(i),futuredate.get(i),futurehtemp.get(i),futureltemp.get(i)
            ,Integer.valueOf(futurecondcdd.get(i)),Integer.valueOf(futurecondcdn.get(i)));
            dailyWeathers.add(dailyWeather);

        }

        cityName = intent.getStringExtra("CITY_NAME");

        Log.d(LOG_TAG, "START Details " + cityName);

        for (int i = 0; i < hourlydate.size() && i < hourlytemp.size() && i < hourlypop.size(); i++) {

            hourlyWeather = new HourlyWeather(hourlydate.get(i).toString(), hourlytemp.get(i).toString(), hourlypop.get(i).toString());
            hourlyWeather.setWinddir(hourlywinddir.get(i).toString());
            hourlyWeather.setWindsc(hourlywindsc.get(i).toString());
            Log.d(LOG_TAG, "during add hour date" + hourlyWeather.getHourdate());
            hourList.add(hourlyWeather);

        }


        //Suggesstion
        suggestiontype = intent.getStringArrayListExtra("SUGGS_TYPE");
        suggestionbrf = intent.getStringArrayListExtra("SUGGS_BRF");
        suggestiontxt = intent.getStringArrayListExtra("SUGGS_TXT");

        //Viewpager

        color = intent.getIntExtra("BACK_COLO", 0);
        Log.d(LOG_TAG, "Color1" + color);


        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.hourlydetailrecycler);
        hourlyAdapter = new hourlyAdapter(this, hourList, suggestiontype, suggestionbrf, suggestiontxt, color, dailyWeathers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(hourlyAdapter);

        SimpleDateFormat df = new SimpleDateFormat("HH");

        int hour = Integer.valueOf(df.format(Calendar.getInstance().getTime()));

        Calendar c = Calendar.getInstance();

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        Log.d(LOG_TAG, "Now 2: " + hour);
        if (hour > 4 && hour < 11) {
            imageviewhead.setImageDrawable(getResources().getDrawable(R.drawable.header2));
        } else if (hour > 10 && hour < 16) {
            imageviewhead.setImageDrawable(getResources().getDrawable(R.drawable.header3));
        } else if (hour > 15 && hour < 20) {
            imageviewhead.setImageDrawable(getResources().getDrawable(R.drawable.header4));
        } else if (hour > 19) {
            imageviewhead.setImageDrawable(getResources().getDrawable(R.drawable.header5));
        } else {
            imageviewhead.setImageDrawable(getResources().getDrawable(R.drawable.header1));
        }


    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(300);
        getWindow().setEnterTransition(slide);
    }


    class MypagerAdapter extends FragmentStatePagerAdapter {

        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = hourlyFragment.newInstance("", "");
                case 1:
                    fragment = futureFragment.newInstance("", "");
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }


    }

    public static class hourlyFragment extends Fragment {

        private Context context;
        private LayoutInflater layoutInflater;
        private ArrayList<HourlyWeather> hourList = new ArrayList<>();
        public static final String LOG_TAG = "Log";
        ArrayList<String> suggestiontype = new ArrayList<>();
        ArrayList<String> suggestionbrf = new ArrayList<>();
        ArrayList<String> suggestiontxt = new ArrayList<>();

        private static final int HOURLY_VIEW = 1;
        private static final int SUGGE_VIEW = 2;
        private int[] mDataSetTypes = new int[]{0, 1};
        private int datasize = 0;
        private int hourlycnt = 0;

        private int color;

        public hourlyFragment() {

        }

        public static hourlyFragment newInstance(String param1, String param2) {
            hourlyFragment fragment = new hourlyFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            super.onCreateView(inflater, container, savedInstanceState);
            return null;
        }

    }

    public static class futureFragment extends Fragment {


        public futureFragment() {

        }

        public static futureFragment newInstance(String param1, String param2) {
            futureFragment fragment = new futureFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            super.onCreateView(inflater, container, savedInstanceState);
            return null;
        }

    }

    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}


