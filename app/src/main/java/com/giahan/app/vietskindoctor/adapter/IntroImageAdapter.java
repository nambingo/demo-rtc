package com.giahan.app.vietskindoctor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NamVT on 6/14/2018.
 */

public class IntroImageAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Photo> photos = new ArrayList<>();

    public IntroImageAdapter(Context context, List<Photo> list) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        photos.addAll(list);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView;
        itemView = mLayoutInflater.inflate(R.layout.item_slider_image, container, false);
        ImageView imageIll = (ImageView) itemView.findViewById(R.id.img_ill);
        if (photos.get(position) != null && photos.get(position).getUrl() != null) {
            Glide.with(mContext)
                    .load(photos.get(position).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .into(imageIll);
            if(photos.get(position).getBitmap() == null) {
                Glide.with(mContext)
                        .load(photos.get(position).getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                int nh = (int) (resource.getHeight() * (512.0 / resource.getWidth()));
                                Bitmap scaled = Bitmap.createScaledBitmap(resource, 512, nh, true);
                                photos.get(position).setBitmap(scaled);
                            }
                        });
            }
//            Glide.with(mContext)
//                    .load(photos.get(position).getUrl())
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .dontAnimate()
//                    .into(new GlideDrawableImageViewTarget(imageIll) {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                            super.onResourceReady(resource, animation);
//                            progress.setVisibility(View.INVISIBLE);
//                            //never called
//                        }
//
//                        @Override
//                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            super.onLoadFailed(e, errorDrawable);
//                            progress.setVisibility(View.INVISIBLE);
//                            //never called
//                        }
//                    });
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ScrollView) object);
    }
}
