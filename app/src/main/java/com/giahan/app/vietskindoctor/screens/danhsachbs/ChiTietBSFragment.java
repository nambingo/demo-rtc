package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.domains.Filter;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idlestar.ratingstar.RatingStarView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 23/05/2018.
 */

public class ChiTietBSFragment extends BaseFragment {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvKinhNghiem)
    TextView tvKinhNghiem;
    @BindView(R.id.tvDangKy)
    TextView tvDangKy;
    @BindView(R.id.imgSex)
    ImageView imgSex;
    @BindView(R.id.imgStatus)
    ImageView imgStatus;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.rating)
    RatingStarView rating;
    @BindView(R.id.lnMajor)
    LinearLayout lnMajor;
    @BindView(R.id.lnCity)
    LinearLayout lnCity;
    @BindView(R.id.lnDegree)
    LinearLayout lnDegree;
    private Doctor mDoctor;
    private List<Filter> mFilterList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chi_tiet_bs;
    }

    @Override
    protected void createView(View view) {
        getData();
        setUpView();
    }

    private void getData() {
        mDoctor = GeneralUtil.fromJSon(Doctor.class, mPref.currentDoctor().get());
        if (!TextUtils.isEmpty(mPref.filterDoctor().get())) {
            Type type = new TypeToken<List<Filter>>() {
            }.getType();
            mFilterList = new Gson().fromJson(mPref.filterDoctor().get(), type);
        }
    }

    private void setupData(String key) {
        List<String> listLabel = new ArrayList<>();
        List<String> listValue = new ArrayList<>();
        for (int i = 0; i < mFilterList.size(); i++) {
            if (!mFilterList.get(i).getKey().equals(key)) continue;
            for (int j = 0; j < mFilterList.get(i).getOptions().size(); j++) {
                listLabel.add(mFilterList.get(i).getOptions().get(j).getLabel());
                listValue.add(mFilterList.get(i).getOptions().get(j).getValue());
            }
        }

        switch (key) {
            case Constant.KEY_MAJOR:
                setupListData(listLabel, listValue, mDoctor.getMajors(), null, lnMajor);
                break;
            case Constant.KEY_DEGREE:
                setupListData(listLabel, listValue, null, mDoctor.getDegree(), lnDegree);
                break;
        }
    }

    private void setupListData(List<String> listLabel, List<String> listValue, List<String> list,
            String value, LinearLayout lnLayout) {
        if (value != null && listValue.contains(value)){
            setText(listLabel.get(listValue.indexOf(value)), lnLayout);
        }else if (list != null){
            List<String> listShow = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                listShow.add(listLabel.get(listValue.indexOf(list.get(j))));
            }
            for (int i = 0; i < listShow.size(); i++) {
                setText(listShow.get(i),lnLayout);
            }
        }
    }

    private void setText(String value, LinearLayout lnLayout){
        TextView textView = new TextView(mActivity);
        LayoutParams layoutParams =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(value);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.black));
        lnLayout.addView(textView);
    }

    private void setUpView() {
        setupData(Constant.KEY_MAJOR);
        setupData(Constant.KEY_DEGREE);
        tvName.setText(mDoctor.getName());
        tvStatus.setText(mDoctor.isOnline() ? "Online" : "Offline");
        imgSex.setImageDrawable(
                mDoctor.getSex().equals("male") ? getResources().getDrawable(R.drawable.ic_male)
                        : getResources().getDrawable(R.drawable.ic_female));
        imgStatus.setImageDrawable(
                mDoctor.isOnline() ? getResources().getDrawable(R.drawable.ic_status_online)
                        : getResources().getDrawable(R.drawable.ic_status_offline));
        Picasso.with(mActivity)
                .load(mDoctor.getAvatar_url())
                .placeholder(R.mipmap.ic_launcher)
                .into(imgAvatar);
        rating.setRating(mDoctor.getAverage_rating() == null ? 0 : mDoctor.getAverage_rating());

    }

    @OnClick(R.id.imgBack)
    public void onClick() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.tvDangKy)
    public void onDangKy(){
//        Bundle bundle = new Bundle();
//        bundle.putString(Constant.TAG_DOCTOR_ID, String.valueOf(mDoctor.getId()));
//        CreateSessionFragment createSessionFragment = new CreateSessionFragment();
//        createSessionFragment.setArguments(bundle);
//        getMainActivity().pushFragment(createSessionFragment);
    }
}
