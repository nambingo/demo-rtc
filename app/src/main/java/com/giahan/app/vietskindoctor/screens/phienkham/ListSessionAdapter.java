package com.giahan.app.vietskindoctor.screens.phienkham;

import android.content.Context;
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
    private List<Session> mList = new ArrayList<>();
    private OnClickOpenSessionListener mOpenSessionListener;
    private OnClickCancelSessionLestener mCancelSessionLestener;

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

    void setCancelSessionListener(OnClickCancelSessionLestener onClick) {
        this.mCancelSessionLestener = onClick;
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
        holder.rlItemSession.setOnClickListener(v -> {
            if (mOpenSessionListener == null) return;
            mOpenSessionListener.onClickSession(session);
        });
        holder.lnCancel.setOnClickListener(view -> {
            if (mCancelSessionLestener == null) return;
            mCancelSessionLestener.onClickCancel(session);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickOpenSessionListener {
        void onClickSession(Session session);
    }

    public interface OnClickCancelSessionLestener {
        void onClickCancel(Session session);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvDoctor)
        TextView tvDoctor;
        @BindView(R.id.rlItemSession)
        RelativeLayout rlItemSession;
        @BindView(R.id.lnCancel)
        LinearLayout lnCancel;
        @BindView(R.id.lnRemain)
        LinearLayout lnRemain;
        @BindView(R.id.tvRemain)
        TextView tvRemain;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void binData(Session session) {
            switch (session.getStatus()){
                case TAG_WAIT:
                    tvTime.setText(DateUtils.convertDateString(session.getCreateAt()));
                    lnRemain.setVisibility(View.GONE);
                    lnCancel.setVisibility(View.VISIBLE);
                    tvDescription.setText(session.getDescription());
                    break;
                case TAG_ACCEPT:
                    tvTime.setText(TextUtils.isEmpty(session.getLastMessage())
                            ? DateUtils.convertDateString(session.getCreateAt())
                            : DateUtils.convertDateString(session.getLastMessageAt()));
                    lnCancel.setVisibility(View.GONE);
                    lnRemain.setVisibility(View.VISIBLE);
                    tvRemain.setText(String.format("Còn %s ngày", DateUtils.getDateRemain(session.getCreateAt())));
                    tvDescription.setText(TextUtils.isEmpty(session.getLastMessage()) ? session.getDescription() : session.getLastMessage());
                    break;
                case TAG_COMPLETE:
                    tvTime.setText(DateUtils.convertDateString(session.getLastMessageAt()));
                    lnCancel.setVisibility(View.GONE);
                    tvDescription.setText(session.getLastMessage());
                    break;
            }
            tvDoctor.setText(session.getPatientName());
        }
    }
}
