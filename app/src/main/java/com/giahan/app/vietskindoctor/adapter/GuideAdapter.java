package com.giahan.app.vietskindoctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;

/**
 * Created by NamVT on 6/3/2018.
 */

public class GuideAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] mStringTitles, mStringDetails;

    public GuideAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mStringDetails = mContext.getResources().getStringArray(R.array.content_intro);

//        mStringTitles = mContext.getResources().getStringArray(R.array.title_intro);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView;
        /*if (position == 3) {
            itemView = mLayoutInflater.inflate(R.layout.item_image_slider_2, container, false);
        }else {

        }*/
        itemView = mLayoutInflater.inflate(R.layout.item_slider, container, false);

//        TextView title = (TextView) itemView.findViewById(R.id.tv_title);
//        TextView btnStart = (TextView) imageView.findViewById(R.id.btn_start);
//        if (title != null) {
//            title.setText(mStringTitles[position]);
//        }
//
//        TextView detail = (TextView) itemView.findViewById(R.id.tv_detail);
//
//        if (detail != null)
//            detail.setText(mStringDetails[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
