package com.carrousel.shishir.imagecarrousel.view;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.widget.ImageView;

public class VideoViewPresenterImpl implements VideoPlayerPresenter {

    private VideoPlayerView mVideoPlayerView;

    private String[] mImgUrl = {"https://picsum.photos/300/250",
            "https://picsum.photos/300/251",
            "https://picsum.photos/300/252",
            "https://picsum.photos/300/253",
            "https://picsum.photos/300/254",
            "https://picsum.photos/300/255",
            "https://picsum.photos/300/256",
            "https://picsum.photos/300/257"};

    private final String mVideoUrl = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8";


    VideoViewPresenterImpl(VideoPlayerView videoPlayerView) {
        this.mVideoPlayerView = videoPlayerView;
    }

    protected String getVideoUrl() {
        return mVideoUrl;
    }

    @Override
    public void onResume() {
        if (mVideoPlayerView.checkViewIsNull()) {
            mVideoPlayerView.initializePlayer();
        }
        mVideoPlayerView.checkNPlay();
    }

    @Override
    public void onStop() {
        mVideoPlayerView.storePosition();
    }

    @Override
    public void onPause() {
        mVideoPlayerView.storePosition();
    }

    @Override
    public void onCreate(Context context) {
        defaultConfig(context);
    }

    @Override
    public void onInfo(MediaPlayer mp, int what, int extra) {
        mp.setVolume(0, 0);

        if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
            mVideoPlayerView.hideProgress();
        }
        if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
            mVideoPlayerView.showProgress();
        }
        if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
            mVideoPlayerView.hideProgress();
        }
    }

    @Override
    public void setImage(ImageView imageView, int position) {
        final int itemPosition = position % mImgUrl.length;

                mVideoPlayerView.getPicasso().load(mImgUrl[itemPosition]).into(imageView);
    }

    private void defaultConfig(Context context) {
        if (context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            mVideoPlayerView.setPadding(mVideoPlayerView.getScreenWidth()/4, 0, mVideoPlayerView.getScreenWidth()/4, 0);
            mVideoPlayerView.setMargins(0, 0, 0, 0);
            mVideoPlayerView.setGuidline(0.8f);
        } else {
            mVideoPlayerView.setPadding(mVideoPlayerView.getScreenWidth() / 4,
                    0,
                    mVideoPlayerView.getScreenWidth() / 4,
                    0);
            mVideoPlayerView.setMargins(mVideoPlayerView.getScreenWidth() / 13,
                    0,
                    mVideoPlayerView.getScreenWidth() / 13,
                    0);
            mVideoPlayerView.setGuidline(0.6f);
        }

        mVideoPlayerView.setUrl(mImgUrl);
        mVideoPlayerView.setExtraSettings();
    }
}
