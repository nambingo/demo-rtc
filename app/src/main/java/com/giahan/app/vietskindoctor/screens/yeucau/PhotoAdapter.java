package com.giahan.app.vietskindoctor.screens.yeucau;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.giahan.app.vietskindoctor.R;
import java.util.List;

/**
 * Created by pham.duc.nam on 06/06/2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> result;
    private OnClickViewListener mOnClickViewListener;

    public PhotoAdapter(Context context, List<String> result) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.result = result;
    }

    public void setOnClickViewListener(OnClickViewListener onClick){
        this.mOnClickViewListener = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .priority(Priority.HIGH);
//
//        new GlideImageLoader(holder.ivPhoto, ).load(result.get(position),options);

        Glide.with(context).load(result.get(position)).centerCrop().into(holder.ivPhoto);
        holder.rlPhoto.setOnClickListener(v -> {
            if (mOnClickViewListener == null) return;
            mOnClickViewListener.onClickView(result.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.rlPhoto)
        RelativeLayout rlPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface  OnClickViewListener{
        void onClickView(String url);
    }
}
