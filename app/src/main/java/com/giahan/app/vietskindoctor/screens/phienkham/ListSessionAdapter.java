package com.giahan.app.vietskindoctor.screens.phienkham;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import java.util.ArrayList;
import java.util.List;

import static com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment.*;
import static com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment.TAG_ACCEPT;
import static com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment.TAG_COMPLETE;

/**
 * Created by pham.duc.nam on 08/06/2018.
 */

public class ListSessionAdapter extends RecyclerView.Adapter<ListSessionAdapter.MyViewHolder> {

    private Context mContext;

    private List<Session> mList;

    private OnClickOpenSessionListener mOpenSessionListener;

    public ListSessionAdapter(Context context, List<Session> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setData(List<Session> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    void setOpenSessionListener(OnClickOpenSessionListener onClick) {
        this.mOpenSessionListener = onClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Session session = mList.get(position);
        holder.binData(session);
        holder.lnItemSession.setOnClickListener(v -> {
            if (mOpenSessionListener == null) {
                return;
            }
            mOpenSessionListener.onClickSession(session);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickOpenSessionListener {

        void onClickSession(Session session);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.tvMessage)
        TextView tvMessage;

        @BindView(R.id.tvPatientName)
        TextView tvPatientName;

        @BindView(R.id.lnItemSession)
        LinearLayout lnItemSession;

        @BindView(R.id.tvRemain)
        TextView tvRemain;

        @BindView(R.id.tvSex)
        TextView tvSex;

        @BindView(R.id.tvAge)
        TextView tvAge;

        @BindView(R.id.tvWeight)
        TextView tvWeight;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binData(Session session) {
            switch (session.getStatus()) {
                case TAG_ACCEPT:
                    tvTime.setText(TextUtils.isEmpty(session.getLastMessage())
                            ? DateUtils.convertDateString(session.getCreateAt())
                            : DateUtils.convertDateString(session.getLastMessageAt()));
                    tvRemain.setText(String.format("Còn %s ngày ", DateUtils.getDateRemain(session.getCreateAt())));
                    tvMessage.setText(TextUtils.isEmpty(session.getLastMessage()) ? session.getDescription()
                            : session.getLastMessage());
                    break;
                case TAG_COMPLETE:
                    tvTime.setText(DateUtils.convertDateString(session.getLastMessageAt()));
                    tvMessage.setText(session.getLastMessage());
                    break;
            }
            tvPatientName.setText(TextUtils.isEmpty(session.getPatientName()) ? "N/A"
                    : session.getPatientName());
            tvSex.setVisibility(TextUtils.isEmpty(session.getSex()) ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getSex())) {
                tvSex.setText(session.getSex().equals("0") ? "Nữ" : "Nam");
            }
            tvAge.setVisibility(TextUtils.isEmpty(session.getPatientAge()) ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getPatientAge())) {
                tvAge.setText(String.format("%s tuổi",
                        DateUtils.getAge(session.getPatientAge())));
            }
            tvWeight.setVisibility(TextUtils.isEmpty(session.getWeight()) ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getWeight())) {
                tvWeight.setText(
                        String.format("%s kg", session.getWeight()));
            }
            if (TextUtils.isEmpty(session.getLastMessageAt()) || TextUtils.isEmpty(session.getLastReadAt())) {
                return;
            }
            if (DateUtils.compareDates(session.getLastMessageAt(), session.getLastReadAt())) {
                tvPatientName.setTypeface(Typeface.DEFAULT);
                tvPatientName.setTextColor(mContext.getResources().getColor(R.color.warm_grey_three));
                tvMessage.setTextColor(mContext.getResources().getColor(R.color.warm_grey_three));
            } else {
                tvPatientName.setTypeface(Typeface.DEFAULT_BOLD);
                tvPatientName.setTextColor(mContext.getResources().getColor(R.color.black));
                tvMessage.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
    }
}
