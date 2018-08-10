package com.giahan.app.vietskindoctor.screens.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Message;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 11/06/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_TEXT = 1;
    private static final int MESSAGE_FILE = 2;
    private static final int MESSAGE_TEST_SAMPLE = 3;
    private static final int MESSAGE_PRESCRIPTION = 4;
    private static final int MESSAGE_DATE = 5;
    private LayoutInflater mLayoutInflater;

    private List<Message> mMessages = new ArrayList<>();
    private String mUserID;
    private Context mContext;
    private String mAvatarUrl;
    private OnClickImageListener mOnClickImageListener;
    private OnClickMtesListener mOnClickMtesListener;

    public MessageAdapter(Context context, List<Message> messages, String userID,
            String avatarUrl) {
        mContext = context;
        mMessages = messages;
        mUserID = userID;
        mAvatarUrl = avatarUrl;
        //mUsernameColors = context.getResources().getColor(R.color.red);
    }

    public void setOnClickImageListener(OnClickImageListener onClickImageListener) {
        this.mOnClickImageListener = onClickImageListener;
    }

    public void setOnClickMtesListener(OnClickMtesListener onClickMtesListener) {
        this.mOnClickMtesListener = onClickMtesListener;
    }

    public void setData(List<Message> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        mMessages = list;
        notifyDataSetChanged();
    }

    public List<Message> getListMessages() {
        return mMessages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
            case MESSAGE_TEXT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_message_text, parent, false);
                return new ChatTextViewHolder(view);
            case MESSAGE_FILE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_message_file, parent, false);
                return new ChatFileViewHolder(view);
            case MESSAGE_TEST_SAMPLE:
            case MESSAGE_PRESCRIPTION:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_message_mtest, parent, false);
                return new ChatMTesViewHolder(view);
            case MESSAGE_DATE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_message_date, parent, false);
                return new ChatTextViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        if (viewHolder instanceof ChatTextViewHolder) {
            ((ChatTextViewHolder) viewHolder).bind(message);
            ((ChatTextViewHolder) viewHolder).tvMe.setOnClickListener(this::showDate);
            ((ChatTextViewHolder) viewHolder).tvYou.setOnClickListener(this::showDate);
        } else if (viewHolder instanceof ChatFileViewHolder) {
            ((ChatFileViewHolder) viewHolder).bind(message);

            onClickImage(((ChatFileViewHolder) viewHolder).imgMe, mOnClickImageListener, message);
            onClickImage(((ChatFileViewHolder) viewHolder).imgYou, mOnClickImageListener, message);

            ((ChatFileViewHolder) viewHolder).imgMe.setOnLongClickListener(view -> {
                showDate(view);
                return false;
            });
            ((ChatFileViewHolder) viewHolder).imgYou.setOnLongClickListener(view -> {
                showDate(view);
                return false;
            });
        } else if (viewHolder instanceof ChatMTesViewHolder) {
            ((ChatMTesViewHolder) viewHolder).bind(message);
            ((ChatMTesViewHolder) viewHolder).tvMtest.setOnClickListener(view -> {
                if (mOnClickMtesListener == null) return;
                mOnClickMtesListener.onClickMtes(message);
            });
            ((ChatMTesViewHolder) viewHolder).tvMtest.setOnLongClickListener(view -> {
                showDate(view);
                return false;
            });
        } else {
            ((ChatDateViewHolder) viewHolder).bind(message);
        }
    }

    private void showDate(View view) {
        Message message1 = (Message) view.getTag();
        message1.setClick(!message1.isClick());
        for (int i = 0; i < getListMessages().size(); i++) {
            if (getListMessages().get(i) == message1) continue;
            getListMessages().get(i).setClick(false);
        }
        notifyDataSetChanged();
    }

    private void onClickImage(ImageView imageView, OnClickImageListener onClick, Message message) {
        imageView.setOnClickListener(view -> {
            if (onClick == null) return;
            onClick.onClickImage(message);
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        String type = message.getType();
        switch (type) {
            case Message.TYPE_TEXT:
                return MESSAGE_TEXT;
            case Message.TYPE_FILE:
                return MESSAGE_FILE;
            case Message.TYPE_PRESCRIPTION:
                return MESSAGE_PRESCRIPTION;
            case Message.TYPE_TEST_SAMPLE:
                return MESSAGE_TEST_SAMPLE;
            default:
                return MESSAGE_TEXT;
        }
    }

    public void addMessage(Message message) {
        if (mMessages.contains(message)) return;
        mMessages.add(message);
    }

    public interface OnClickImageListener {
        void onClickImage(Message message);
    }

    public interface OnClickMtesListener {
        void onClickMtes(Message message);
    }

    class ChatTextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chat_text_other)
        LinearLayout chat_text_other;
        @BindView(R.id.chat_text_me)
        LinearLayout chat_text_me;
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tvYou)
        TextView tvYou;
        @BindView(R.id.tvMe)
        TextView tvMe;
        @BindView(R.id.tvDateMe)
        TextView tvDateMe;
        @BindView(R.id.tvDateYou)
        TextView tvDateYou;

        public ChatTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Message message) {
            boolean isMe = message.getUserId().equals(mUserID);
            chat_text_other.setVisibility(isMe ? View.GONE : View.VISIBLE);
            chat_text_me.setVisibility(isMe ? View.VISIBLE : View.GONE);
            tvYou.setText(isMe ? null : message.getMessage());
            tvMe.setText(isMe ? message.getMessage() : null);
            Picasso.with(mContext)
                    .load(mAvatarUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgAvatar);
            tvMe.setTag(message);
            tvYou.setTag(message);
            tvDateMe.setText(DateUtils.convertDate(message.getCreatedAt()));
            tvDateYou.setText(DateUtils.convertDate(message.getCreatedAt()));
            tvDateMe.setVisibility(message.isClick() ? View.VISIBLE : View.GONE);
            tvDateYou.setVisibility(message.isClick() ? View.VISIBLE : View.GONE);
        }
    }

    class ChatFileViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chat_text_other)
        LinearLayout chat_text_other;
        @BindView(R.id.chat_text_me)
        LinearLayout chat_text_me;
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.imgYou)
        ImageView imgYou;
        @BindView(R.id.imgMe)
        ImageView imgMe;
        @BindView(R.id.tvDateYou)
        TextView tvDateYou;
        @BindView(R.id.tvDateMe)
        TextView tvDateMe;

        public ChatFileViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Message message) {
            boolean isMe = message.getUserId().equals(mUserID);
            chat_text_other.setVisibility(isMe ? View.GONE : View.VISIBLE);
            chat_text_me.setVisibility(isMe ? View.VISIBLE : View.GONE);
            Picasso.with(mContext)
                    .load(message.getObjUrl())
                    .centerCrop()
                    .fit()
                    .into(isMe ? imgMe : imgYou);
            Picasso.with(mContext)
                    .load(mAvatarUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgAvatar);
            imgMe.setTag(message);
            imgYou.setTag(message);
            tvDateMe.setText(DateUtils.convertDate(message.getCreatedAt()));
            tvDateYou.setText(DateUtils.convertDate(message.getCreatedAt()));
            tvDateMe.setVisibility(message.isClick() ? View.VISIBLE : View.GONE);
            tvDateYou.setVisibility(message.isClick() ? View.VISIBLE : View.GONE);
        }
    }

    class ChatMTesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvMtest)
        TextView tvMtest;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ChatMTesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Message message) {
            if (TextUtils.isEmpty(message.getOnjId())) return;
            tvMtest.setText(String.format("%s %s",
                    message.getType().equals(Message.TYPE_TEST_SAMPLE) ? mContext.getString(
                            R.string.mau_xet_nghiem) : mContext.getString(R.string.don_thuoc),
                    message.getOnjId()));
            tvMtest.setTag(message);
            tvDate.setText(DateUtils.convertDate(message.getCreatedAt()));
            tvDate.setVisibility(message.isClick() ? View.VISIBLE : View.GONE);
        }
    }

    class ChatDateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvDefault)
        TextView tvDefault;

        public ChatDateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Message message) {
            tvDate.setText(message.getCreatedAt());
        }
    }
}
