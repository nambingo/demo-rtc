package com.giahan.app.vietskindoctor.screens.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<Message> result;
    private OnClickPhotoListener mOnClickPhoto;

    public void setOnClickPhoto(OnClickPhotoListener onClick){
        this.mOnClickPhoto = onClick;
    }

    public PhotoAdapter(Context context, List<Message> result) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(result.get(position).getObjUrl())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivPhoto);
        holder.ivPhoto.setOnClickListener(v -> {
            if (mOnClickPhoto == null) return;
            mOnClickPhoto.OnClickPhoto(result.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickPhotoListener{
        void OnClickPhoto(Message message);
    }
}
