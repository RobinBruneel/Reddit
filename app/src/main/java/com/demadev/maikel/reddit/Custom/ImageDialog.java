package com.demadev.maikel.reddit.Custom;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demadev.maikel.reddit.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Maikel on 26/12/2014.
 */
public class ImageDialog extends Dialog{

    @InjectView(R.id.image_dialog_content)
    ImageView imageView;
    @InjectView(R.id.image_dialog_loader)
    ProgressBar progressBar;
    private Context context;

    public ImageDialog(Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.context = context;
        init();
    }

    private void init() {
        setContentView(R.layout.image_dialog);
        ButterKnife.inject(this);
        new PhotoViewAttacher(imageView);
    }

    public void showWithURL(String url) {
        Picasso.with(context).load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Kan de afbeelding niet weergegeven", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        show();
    }
}
