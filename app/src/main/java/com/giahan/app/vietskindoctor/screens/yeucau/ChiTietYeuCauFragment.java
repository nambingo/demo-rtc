package com.giahan.app.vietskindoctor.screens.yeucau;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;

public class ChiTietYeuCauFragment extends BaseFragment {

    @BindView(R.id.tvDoiTuong)
    TextView tvDoiTuong;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvNgaySinh)
    TextView tvNgaySinh;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.rvResultPhoto)
    RecyclerView rvResultPhoto;
    @BindView(R.id.tvTuChoi)
    TextView tvTuChoi;
    @BindView(R.id.tvChapNhan)
    TextView tvChapNhan;

    private Session mSession;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chitiet_yeucau;
    }

    @Override
    protected void createView(final View view) {
        initData();
        setupView();
    }

    private void setupView() {
        switch (mSession.getRelationship()){
            case "1":
                tvDoiTuong.setText("Tôi");
                break;
            case "2":
                tvDoiTuong.setText("Vợ/Chồng");
                break;
            case "3":
                tvDoiTuong.setText("Con");
                break;
            case "4":
                tvDoiTuong.setText("Cháu");
                break;
            case "5":
                tvDoiTuong.setText("Bố/Mẹ");
                break;

        }
        tvName.setText(mSession.getPatientName());
        tvWeight.setText(mSession.getWeight());
        tvNgaySinh.setText(DateUtils.convertDayRequest(mSession.getBirthdate()));
        tvSex.setText(TextUtils.isEmpty(mSession.getSex()) ? "N/A" : mSession.getSex().equals("0") ? "Nữ" : "Nam");
        tvDescription.setText(mSession.getDescription());

    }

    private void initData() {
        mSession = GeneralUtil.fromJSon(Session.class, mPref.currentRequest().get());
    }

    @OnClick(R.id.lnBack)
    public void onClick() {
        getMainActivity().popFragment();
    }
}
