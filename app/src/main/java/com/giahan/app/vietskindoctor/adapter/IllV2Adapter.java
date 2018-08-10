package com.giahan.app.vietskindoctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Ill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NamVT on 7/3/2018.
 */

public class IllV2Adapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Ill> ills = new ArrayList<>();
    private OnClickIllListener onClick;

    public IllV2Adapter(Context context, List<Ill> list) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ills.addAll(list);
    }

    public void setOnClickIll(OnClickIllListener onClickIll) {
        this.onClick = onClickIll;
    }

    @Override
    public int getCount() {
        return ills.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView;
        itemView = mLayoutInflater.inflate(R.layout.item_imagev2, container, false);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(8f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(Color.parseColor("#8f6f6f"), PorterDuff.Mode.SRC_IN);
        circularProgressDrawable.start();
        ImageView imageIll = (ImageView) itemView.findViewById(R.id.img_ill);
        Ill ill = ills.get(position);
        if (ill.getPhotos() != null && ill.getPhotos().size() > 0) {
            Glide.with(mContext)
                    .load(ill.getPhotos().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .placeholder(circularProgressDrawable)
                    .into(imageIll);
        } else {
            imageIll.setImageResource(R.drawable.no_image);
        }

        itemView.setOnClickListener((View v) -> {
            if (onClick == null) return;
            onClick.onClickDoctor(ill);
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public interface OnClickIllListener {
        void onClickDoctor(Ill ill);
    }
}
