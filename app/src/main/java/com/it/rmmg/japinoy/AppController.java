package com.it.rmmg.japinoy;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by bern on 5/5/16.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    public RequestQueue mRequestQueue;

    private static AppController mInstance;


    private static Context mContext;



    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }




}