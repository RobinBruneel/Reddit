package com.demadev.maikel.reddit.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.demadev.maikel.reddit.Custom.LoadingWebViewClient;
import com.demadev.maikel.reddit.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebFragment extends Fragment {

    @InjectView(R.id.web_view)
    public WebView webView;
    @InjectView(R.id.web_view_loading)
    public ProgressBar progressBar;

    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.inject(this, view);
        webView.setWebViewClient(new LoadingWebViewClient(progressBar));
        if(url != null) {
            webView.loadUrl(url);
        }
        return view;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
