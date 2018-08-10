package com.giahan.app.vietskindoctor.screens.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Message;
import com.giahan.app.vietskindoctor.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XetNghiemAdapter extends RecyclerView.Adapter<XetNghiemAdapter.MyViewHolder> {
    private Context mContext;
    private List<Message> mList = new ArrayList<>();
    private OnClickMtesListener mOnClickMtesListener;

    public XetNghiemAdapter(Context mContext, List<Message> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnClickImageListener(OnClickMtesListener onClick) {
        this.mOnClickMtesListener = onClick;
    }

    @NonNull
    @Override
    public XetNghiemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_right_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull XetNghiemAdapter.MyViewHolder holder, int position) {
        final Message message = mList.get(position);
        holder.binData(message);
        holder.tvName.setOnClickListener(view1 -> {
            if (mOnClickMtesListener == null) return;
            mOnClickMtesListener.onClickMtes(message);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickMtesListener{
        void onClickMtes(Message message);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvHours)
        TextView tvHours;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binData(Message message) {
            if (TextUtils.isEmpty(message.getOnjId())) return;
            tvName.setText(String.format("%s %s",
                    message.getType().equals(Message.TYPE_PRESCRIPTION)
                            ? mContext.getString(R.string.don_thuoc)
                            : mContext.getString(R.string.phieu_xet_nghiem),
                    message.getOnjId()));
            tvDate.setText(DateUtils.convertDay(message.getCreatedAt()));
            tvHours.setText(DateUtils.convertHours(message.getCreatedAt()));
        }
    }
}
