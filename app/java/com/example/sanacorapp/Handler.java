package com.example.sanacorapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Handler
{

    public static Handler mInstance;
    private RequestQueue requesQueue;
    private static Context mCtx;

    private Handler(Context context)
    {
        mCtx = context;
        requesQueue = getRequesQueue();
    }

    public RequestQueue getRequesQueue()
    {
        if(requesQueue==null)
        {
            requesQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requesQueue;
    }

    public static synchronized Handler getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new Handler(context);
        }
        return mInstance;
    }

    public <T>void addToRequestQueue(Request<T> request)
    {
        requesQueue.add(request);
    }

}
