package com.demadev.maikel.reddit.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.demadev.maikel.reddit.Adapter.RedditAdapter;
import com.demadev.maikel.reddit.Custom.ImageDialog;
import com.demadev.maikel.reddit.Domain.Post;
import com.demadev.maikel.reddit.Domain.RedditPost;
import com.demadev.maikel.reddit.Fragment.HotFragment;
import com.demadev.maikel.reddit.Fragment.SplashFragment;
import com.demadev.maikel.reddit.Fragment.WebFragment;
import com.demadev.maikel.reddit.R;

import java.util.List;


public class MainActivity extends Activity implements SplashFragment.LoadingListener, RedditAdapter.RedditPostClickListener{

    private SplashFragment splashFragment;
    private HotFragment hotFragment;
    private WebFragment webFragment;

    private long exitLastPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            if (splashFragment == null) {
                splashFragment = new SplashFragment();
            }
            displayFragment(splashFragment, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() < 1){
            Toast.makeText(this, "Druk nogmaals om af te sluiten", Toast.LENGTH_SHORT).show();
            long justPressed = System.currentTimeMillis();
            if (justPressed - exitLastPressed < 2000){  //LENGTH_SHORT
                this.finish();
            }
            exitLastPressed = justPressed;
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void displayFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment);
        if(addToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public <T> void onLoadingComplete(List<T> items) {
        if(hotFragment == null){
            hotFragment = new HotFragment();
        }
        hotFragment.setup((List<Post>) items);
        displayFragment(hotFragment, false);
    }

    @Override
    public void onThumbnailClick(RedditPost post) {
        ImageDialog imageDialog = new ImageDialog(this);
        imageDialog.showWithURL(post.getUrl());
    }

    @Override
    public void onTitleClick(RedditPost post) {
        if(webFragment == null){
            webFragment = new WebFragment();
        }
        webFragment.setUrl(post.getUrl());
        displayFragment(webFragment, true);
    }

    @Override
    public void onCommentClick(RedditPost post) {
        if(webFragment == null){
            webFragment = new WebFragment();
        }
        webFragment.setUrl("http://www.reddit.com/"+post.getPermalink());
        displayFragment(webFragment, true);
    }
}
