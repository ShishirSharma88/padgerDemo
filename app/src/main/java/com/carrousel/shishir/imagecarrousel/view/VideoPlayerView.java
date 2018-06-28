package com.carrousel.shishir.imagecarrousel.view;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;

public interface VideoPlayerView {
    void showProgress();
    void hideProgress();
    void setUrl(String[] urls);
    void setMargins(int l, int t, int r, int b);
    void setPadding(int left, int top, int right, int bottom);
    int getScreenWidth();
    boolean checkViewIsNull();
    void initializePlayer();
    void setGuidline(float percentage);
    void checkNPlay();
    void storePosition();
    void setExtraSettings();
    Picasso getPicasso();
}
