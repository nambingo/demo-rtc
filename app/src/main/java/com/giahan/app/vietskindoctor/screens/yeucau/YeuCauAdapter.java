package com.giahan.app.vietskindoctor.screens.yeucau;

import android.content.Context;
import android.support.annotation.NonNull;
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
import java.util.List;

public class YeuCauAdapter extends RecyclerView.Adapter<YeuCauAdapter.MyViewHolder> {

    private Context mContext;
    private List<Session> mList;
    private OnClickOpenSessionListener mOpenSessionListener;

    public YeuCauAdapter(final Context context, final List<Session> list) {
        mContext = context;
        mList = list;
    }

    public interface OnClickOpenSessionListener {
        void onClickSession(Session session);
    }

    public void setOpenSessionListener(OnClickOpenSessionListener onClick) {
        this.mOpenSessionListener = onClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvPatientName)
        TextView tvPatientName;
        @BindView(R.id.tvInfo)
        TextView tvInfo;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvSex)
        TextView tvSex;
        @BindView(R.id.tvAge)
        TextView tvAge;
        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.lnItemSession)
        LinearLayout lnItemSession;

        public MyViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binData(final Session session) {
            tvPatientName.setText(TextUtils.isEmpty(session.getPatientName()) ? "N/A"
                    : session.getPatientName());
            tvInfo.setText(session.getDescription());
            tvTime.setText(DateUtils.convertDateString(session.getCreateAt()));
            tvSex.setVisibility(TextUtils.isEmpty(session.getSex()) ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getSex())) {
                tvSex.setText(session.getSex().equals("0") ? "Nữ" : "Nam");
            }
            tvAge.setVisibility(
                    TextUtils.isEmpty(session.getBirthdate()) && TextUtils.isEmpty(session.getPatientAge())
                            ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getBirthdate()))
            tvAge.setText(String.format("%s tuổi",
                    DateUtils.getAge(session.getBirthdate())));
            tvWeight.setVisibility(TextUtils.isEmpty(session.getWeight()) ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(session.getWeight()))
            tvWeight.setText(
                    String.format("%s kg", session.getWeight()));
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Session session = mList.get(position);
        holder.binData(session);
        holder.lnItemSession.setOnClickListener(v -> {
            if (mOpenSessionListener == null) return;
            mOpenSessionListener.onClickSession(session);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
