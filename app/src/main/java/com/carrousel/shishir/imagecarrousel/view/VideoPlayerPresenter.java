package com.carrousel.shishir.imagecarrousel.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ImageView;

public interface VideoPlayerPresenter {
    void onResume();
    void onStop();
    void onPause();
    void onCreate(Context context);
    void onInfo(MediaPlayer mp, int what, int extra);
    void setImage(ImageView imageView, int position);
}
