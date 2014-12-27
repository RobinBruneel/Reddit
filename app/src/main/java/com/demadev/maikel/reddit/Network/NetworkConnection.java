package com.demadev.maikel.reddit.Network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Maikel on 26/12/2014.
 */
public class NetworkConnection {

    public static final String base_url = "http://www.reddit.com/hot/";

    private static NetworkConnection instance;
    private static RequestQueue requestQueue;

    public static synchronized NetworkConnection getInstance(Context context){
        if(instance == null){
            instance = new NetworkConnection();
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return instance;
    }

    public <T> void addRequest(GsonRequest<T> gsonRequest){
        requestQueue.add(gsonRequest);
    }
}
