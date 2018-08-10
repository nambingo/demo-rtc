package com.giahan.app.vietskindoctor.adapter;

import android.content.Context;
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
    private int[] mStringTitles, mStringDetails;

    public GuideAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStringDetails = new int[]{R.string.flash_screen_detail_01, R.string.flash_screen_detail_02};

        mStringTitles = new int[]{R.string.flash_screen_title_01, R.string.flash_screen_title_02};
    }

    @Override
    public int getCount() {
        return mStringTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView;
        /*if (position == 3) {
            itemView = mLayoutInflater.inflate(R.layout.item_image_slider_2, container, false);
        }else {

        }*/
        itemView = mLayoutInflater.inflate(R.layout.item_slider, container, false);

        TextView title = (TextView) itemView.findViewById(R.id.tv_title);
//        TextView btnStart = (TextView) imageView.findViewById(R.id.btn_start);
        if (title != null) {
            title.setText(mStringTitles[position]);
        }

        TextView detail = (TextView) itemView.findViewById(R.id.tv_detail);

        if (detail != null)
            detail.setText(mStringDetails[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
