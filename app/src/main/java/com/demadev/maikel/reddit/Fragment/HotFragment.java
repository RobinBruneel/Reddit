package com.demadev.maikel.reddit.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demadev.maikel.reddit.Adapter.RedditAdapter;
import com.demadev.maikel.reddit.Domain.Post;
import com.demadev.maikel.reddit.Network.GsonRequest;
import com.demadev.maikel.reddit.Network.JsonCollector;
import com.demadev.maikel.reddit.Network.NetworkConnection;
import com.demadev.maikel.reddit.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HotFragment extends Fragment implements JsonCollector, SwipeRefreshLayout.OnRefreshListener {

    private final String key = "posts";

    @InjectView(R.id.hot_list)
    public ListView listView;
    @InjectView(R.id.swipe_container)
    public SwipeRefreshLayout swipeRefreshLayout;

    private RedditAdapter adapter;
    private List<Post> posts;

    private Response.Listener<List<Post>> succesListener;
    private Response.ErrorListener errorListener;

    private RedditAdapter.RedditPostClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        if (savedInstanceState != null) {
            posts = (List<Post>) savedInstanceState.get(key);
        }

        ButterKnife.inject(this, view);

        initListeners();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.white, android.R.color.holo_blue_light, android.R.color.white);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new RedditAdapter(getActivity(), R.layout.hot_item, posts, listener);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public String retrieveProperJson(String rawJson) throws JSONException {
        JSONObject mainObject = new JSONObject(rawJson);
        JSONObject dataObject = mainObject.getJSONObject("data");
        JSONArray childrenArray = dataObject.getJSONArray("children");
        return childrenArray.toString();
    }

    @Override
    public void onRefresh() {
        Type collectionType = new TypeToken<List<Post>>() {
        }.getType();
        GsonRequest<List<Post>> request = new GsonRequest<List<Post>>(Request.Method.GET, NetworkConnection.base_url + ".json", collectionType, HotFragment.this, null, succesListener, errorListener);
        NetworkConnection.getInstance(getActivity().getApplicationContext()).addRequest(request);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (RedditAdapter.RedditPostClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement LoadingListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(key, (Serializable) posts);
    }

    private void initListeners() {
        succesListener = new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> posts) {
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        };
    }

    public void setup(List<Post> posts) {
        this.posts = posts;
    }
}
