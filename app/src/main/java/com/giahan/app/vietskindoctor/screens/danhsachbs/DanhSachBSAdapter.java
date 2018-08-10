package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.utils.FilterDoctorHelper;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.idlestar.ratingstar.RatingStarView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

/**
 * Created by pham.duc.nam on 22/05/2018.
 */

public class DanhSachBSAdapter extends RecyclerView.Adapter<DanhSachBSAdapter.MyViewHolder> {

    private List<Doctor> mDoctorList;
    private Context mContext;
    private OnClickDoctorListener onClick;
    private PrefHelper_ mPrefHelper_;

    DanhSachBSAdapter(Context context, List<Doctor> list, PrefHelper_ helper_) {
        this.mContext = context;
        this.mDoctorList = list;
        this.mPrefHelper_ = helper_;
    }

    public void setData(List<Doctor> list) {
        this.mDoctorList.clear();
        this.mDoctorList.addAll(list);
        this.notifyDataSetChanged();
    }

    void setOnClickDoctor(OnClickDoctorListener onClickDoctor) {
        this.onClick = onClickDoctor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danh_sach_bs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Doctor doctor = mDoctorList.get(position);
        FilterDoctorHelper filterDoctorHelper = new FilterDoctorHelper(mContext, mPrefHelper_,
                doctor);
        holder.setData(doctor);
        filterDoctorHelper.init(null, holder.tvBangCap);
        //GeneralUtil.setBackgroundColor(mContext, holder.itemView, position);
        holder.lnItem.setOnClickListener((View v) -> {
            if (onClick == null) return;
            onClick.onClickDoctor(doctor);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }

    public interface OnClickDoctorListener {
        void onClickDoctor(Doctor doctor);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgSex)
        ImageView imgSex;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.lnItem)
        LinearLayout lnItem;
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.rating)
        RatingStarView rating;
        @BindView(R.id.tvRating)
        TextView tvRating;
        @BindView(R.id.tvBangCap)
        TextView tvBangCap;
        @BindView(R.id.tvBenhVien)
        TextView tvBenhVien;
        @BindView(R.id.tvGiaTien)
        TextView tvGiaTien;
        @BindView(R.id.tvStatus)
        TextView tvStatus;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setData(Doctor doctor) {
            imgSex.setImageDrawable(doctor.getSex().equals("male") ? mContext.getResources()
                    .getDrawable(R.drawable.ic_male)
                    : mContext.getResources().getDrawable(R.drawable.ic_female));
            tvName.setText(doctor.getName());
            Picasso.with(mContext)
                    .load(doctor.getAvatar_url())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgAvatar);
            rating.setRating(doctor.getAverage_rating() == null ? 0 : doctor.getAverage_rating());
            tvRating.setText(doctor.getAverage_rating() == null ? "0.0"
                    : String.valueOf(doctor.getAverage_rating()));
            if (Toolbox.isEmpty(doctor.getWorkplace())) {
                tvBenhVien.setText(mContext.getString(R.string.not_update));
            } else {;
                tvBenhVien.setText(doctor.getWorkplace());
            }
            tvGiaTien.setText(String.format("%s VNĐ", Toolbox.formatMoney(doctor.getFee())));
            tvStatus.setText(doctor.isOnline() ? mContext.getString(R.string.tilte_online) : mContext.getString(R.string.title_offline));
            if (doctor.isOnline()) {
                tvStatus.setTextColor(Color.parseColor("#279E24"));
            } else {
                tvStatus.setTextColor(Color.parseColor("#8f6f6f"));
            }
        }
    }
}
