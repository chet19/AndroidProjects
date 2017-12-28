package com.example.chetan.myapplication;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.ref.ReferenceQueue;

public class UploadSinglonton {
    private static UploadSinglonton singlonton;
    private RequestQueue requestQueue;
    private static Context ctx;

    private UploadSinglonton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }


    private RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized UploadSinglonton getInstance(Context context) {
        if (singlonton == null) {
            singlonton = new UploadSinglonton(context);
        }
        return singlonton;
    }

    public <T> void addToRequestque(Request<T> request) {

        getRequestQueue().add(request);
    }

}






