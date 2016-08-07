package material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import material.pumkinweather.hence.com.pumkinweather.main.MainActivity.Main.Activity.MyApplication;

/**
 * Created by Hence on 2016/7/29.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;
    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader=new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            //get maxMemory / 1024 / 8 for cache
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024/8/10/10));


            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);

            }
        });
    }

    public static VolleySingleton getInstance (){
        if (mInstance==null){
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
