package com.demadev.maikel.reddit.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demadev.maikel.reddit.Domain.Post;
import com.demadev.maikel.reddit.Network.GsonRequest;
import com.demadev.maikel.reddit.Network.JsonCollector;
import com.demadev.maikel.reddit.Network.NetworkConnection;
import com.demadev.maikel.reddit.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SplashFragment extends Fragment implements JsonCollector {

    @InjectView(R.id.progress_spinner) ProgressBar spinner;
    private LoadingListener loadingListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.inject(this, view);
        startLoading();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public String retrieveProperJson(String rawJson) throws JSONException {
        JSONObject mainObject = new JSONObject(rawJson);
        JSONObject dataObject = mainObject.getJSONObject("data");
        JSONArray childrenArray = dataObject.getJSONArray("children");
        return childrenArray.toString();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            loadingListener = (LoadingListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement LoadingListener");
        }
    }

    private void startLoading(){
        Type collectionType = new TypeToken<List<Post>>(){}.getType();
        GsonRequest<List<Post>> request = new GsonRequest<List<Post>>(Request.Method.GET, NetworkConnection.base_url + ".json", collectionType, SplashFragment.this, null, new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> posts) {
                loadingListener.onLoadingComplete(posts);
                spinner.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                spinner.setVisibility(View.GONE);
                volleyError.printStackTrace();
            }
        });

        NetworkConnection.getInstance(getActivity().getApplicationContext()).addRequest(request);
        spinner.setVisibility(View.VISIBLE);
    }

    public interface LoadingListener{
        public <T> void onLoadingComplete(List<T> items);
    }
}
