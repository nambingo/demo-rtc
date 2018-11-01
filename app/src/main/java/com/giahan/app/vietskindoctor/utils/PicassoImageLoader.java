//package com.giahan.app.vietskindoctor.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import com.giahan.app.vietskindoctor.R;
//import com.squareup.picasso.Picasso;
//import com.yancy.gallerypick.inter.ImageLoader;
//import com.yancy.gallerypick.widget.GalleryImageView;
//
///**
// * Created by pham.duc.nam on 06/06/2018.
// */
//
//public class PicassoImageLoader implements ImageLoader{
//    @Override
//    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
//
//        Picasso.with(context)
//                .load("file://" + path)
//                .resize(width, height)
//                .placeholder(R.mipmap.gallery_pick_photo)
//                .error(R.mipmap.ic_launcher)
//                .into(galleryImageView);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//
//    }
//}
