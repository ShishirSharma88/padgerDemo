package com.carrousel.shishir.imagecarrousel.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carrousel.shishir.imagecarrousel.R;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends PagerAdapter {

    private Context context;
    private int count;
    private VideoPlayerPresenter videoPlayerPresenter;

    ImageAdapter(Context context, int count, VideoPlayerPresenter videoPlayerPresenter) {
        this.context = context;
        this.count = count;
        this.videoPlayerPresenter = videoPlayerPresenter;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (CardView) object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        videoPlayerPresenter.setImage(imageView, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CardView) object);
    }
}
