package com.giahan.app.vietskindoctor.screens.chat;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 02/07/2018.
 */
public class DocumentAdapter extends BaseAdapter {
    private List<Message> listData;
    private Context mContext;
    private MyViewHolder viewHolder;
    private OnClickMtesListener mOnClickMtesListener;


    public DocumentAdapter(Context context, List<Message> listData) {
        this.mContext = context;
        this.listData = listData;
    }

    public void setOnClickImageListener(OnClickMtesListener onClickImageListener) {
        this.mOnClickMtesListener = onClickImageListener;
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_message_mtest, null);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            if (view.getTag() != null)
                viewHolder = (MyViewHolder) view.getTag();
        }
        Message message = (Message) getItem(i);
        if (TextUtils.isEmpty(message.getOnjId())) return null;
        viewHolder.tvMtest.setText(String.format("%s %s",
                message.getType().equals(Message.TYPE_PRESCRIPTION)
                        ? mContext.getString(R.string.don_thuoc)
                        : mContext.getString(R.string.mau_xet_nghiem),
                message.getOnjId()));
        viewHolder.tvMtest.setOnClickListener(view1 -> {
            if (mOnClickMtesListener == null) return;
            mOnClickMtesListener.onClickMtes(message);
        });

        return view;
    }

    class MyViewHolder {
        @BindView(R.id.tvMtest)
        TextView tvMtest;
        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickMtesListener{
        void onClickMtes(Message message);
    }
}
