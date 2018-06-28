package com.carrousel.shishir.imagecarrousel.view;

import android.app.Activity;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.carrousel.shishir.imagecarrousel.R;
import com.squareup.picasso.Picasso;

/**
 * This is main view or activity which is responsible as base for others
 */

public class VideoPlayerActivity extends Activity implements MediaPlayer.OnInfoListener, View.OnClickListener, VideoPlayerView, MediaPlayer.OnErrorListener {

    private VideoView mVideoView;
    private MediaController mMediaController;
    private TextView mProgressText;
    private ViewPager photoGallery;
    private ImageAdapter mImageAdapter;
    private Guideline mGuideline;
    private TextView mPrevious;
    private TextView mNext;

    private VideoViewPresenterImpl videoViewPresenterImpl;

    private int mCurrentPosition = 0;
    private int mCurrentPage = 1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPage", photoGallery.getCurrentItem());
        outState.putInt("currentPosition", mCurrentPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPage = savedInstanceState.getInt("currentPage");
        mCurrentPosition = savedInstanceState.getInt("currentPosition");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mVideoView = (VideoView) findViewById(R.id.view_video);

        mProgressText = (TextView) findViewById(R.id.progressBar_video_buffer);
        photoGallery = (ViewPager) findViewById(R.id.pager_picture_gallery);
        mGuideline = (Guideline) findViewById(R.id.guideline);
        mPrevious = (TextView) findViewById(R.id.textview_previous);
        mNext = (TextView) findViewById(R.id.textview_next);

        mPrevious.setOnClickListener(this);
        mNext.setOnClickListener(this);

        videoViewPresenterImpl = new VideoViewPresenterImpl(this);
        mVideoView.setOnErrorListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mVideoView.setOnInfoListener(this);
        }

        videoViewPresenterImpl.onCreate(this);
    }

    @Override
    public int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public boolean checkViewIsNull() {
        return mVideoView == null
                || mMediaController == null;
    }

    @Override
    protected void onResume() {
        videoViewPresenterImpl.onResume();
        super.onResume();
    }

    @Override
    public void checkNPlay() {
        if (!mVideoView.isPlaying()) {
            showProgress();
            mVideoView.start();
            mVideoView.seekTo(mCurrentPosition);
        }
    }

    @Override
    public void storePosition() {

        // we are saving current position here on rotation for video
        if (mVideoView != null && photoGallery != null) {
            if (mVideoView.getCurrentPosition() > 0) {
                mCurrentPosition = mVideoView.getCurrentPosition();
            }

            mCurrentPage = photoGallery.getCurrentItem();
        }
    }

    @Override
    public void setExtraSettings() {
        photoGallery.setClipToPadding(false);
        photoGallery.setOffscreenPageLimit(3);
        photoGallery.setCurrentItem(mCurrentPage);
    }

    @Override
    public Picasso getPicasso() {
        Picasso.Builder picasso = new Picasso.Builder(this);
        Picasso picasso1 = picasso.build();
        return picasso1;
    }

    @Override
    public void initializePlayer() {
        mMediaController = new MediaController(this);
        mMediaController.setAnchorView(mVideoView);

        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(Uri.parse(videoViewPresenterImpl.getVideoUrl()));
    }

    @Override
    public void setGuidline(float percntage) {
        mGuideline.setGuidelinePercent(percntage);
    }

    @Override
    protected void onPause() {
        videoViewPresenterImpl.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        videoViewPresenterImpl.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView = null;
        mMediaController = null;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        videoViewPresenterImpl.onInfo(mp, what, extra);
        return false;
    }

    @Override
    public void setMargins(int l, int t, int r, int b) {
        if (photoGallery.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) photoGallery.getLayoutParams();
            p.setMargins(l, t, r, b);
            photoGallery.requestLayout();
        }
    }

    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()) {
            case R.id.textview_previous:
                position = photoGallery.getCurrentItem();
                photoGallery.setCurrentItem(position == 0 ? 0 : position - 1);
                break;
            case R.id.textview_next:
                position = photoGallery.getCurrentItem();
                photoGallery.setCurrentItem(position + 1);
                break;

        }
    }

    @Override
    public void showProgress() {
        mProgressText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressText.setVisibility(View.GONE);
    }

    @Override
    public void setUrl(String[] urls) {
        mImageAdapter = new ImageAdapter(this, Integer.MAX_VALUE, videoViewPresenterImpl);
        photoGallery.setAdapter(mImageAdapter);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        photoGallery.setPadding(left, top, right, bottom);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hideProgress();
        return false;
    }
}
