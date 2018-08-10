package com.giahan.app.vietskindoctor.screens.danhsachbs;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.activity.LoginV2Activity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.CreateSessionResult;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.domains.Filter;
import com.giahan.app.vietskindoctor.domains.Photo;
import com.giahan.app.vietskindoctor.domains.SessionBody;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.khamonline.CreateSessionFragment;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idlestar.ratingstar.RatingStarView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 7/13/2018.
 */

public class DetailDoctorV2Fragment extends BaseFragment {
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.cardViewPhoto)
    CardView cardViewPhoto;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSoTien)
    TextView tvSoTien;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.tvKinhNghiem)
    TextView tvKinhNghiem;
    @BindView(R.id.tvDangKy)
    TextView tvDangKy;
    @BindView(R.id.tvWorkplace)
    TextView tvWorkplace;
    @BindView(R.id.imgSex)
    ImageView imgSex;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvFee)
    TextView tvFee;
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
    @BindView(R.id.rvListImg)
    RecyclerView rvListImg;
    private Doctor mDoctor;
    private List<Filter> mFilterList = new ArrayList<>();
    private List<Photo> photos = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_doctor;
    }

    @Override
    protected void createView(View view) {
        getData();
        if (mDoctor != null) {
            getInfoDoctor();
        }
        setUpView();
    }

    private void getInfoDoctor() {
        Call<Doctor> call =
                RequestHelper.getRequest(false, getActivity()).getInfoDoctor(String.valueOf(mDoctor.getId()));
        call.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                hideLoading();
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                mDoctor.updateData(response.body());
                setUpView();
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String mToken = mPref.token().get();
        cardView.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        if (user == null) return;
        tvSoTien.setText(TextUtils.isEmpty(user.getCredits()) ? getResources().getText(
                R.string.vi_vietskin_cua_ban) + " 0 VNĐ"
                : getResources().getText(R.string.vi_vietskin_cua_ban) + " " + Toolbox.formatMoney(
                user.getCredits()) + " VNĐ");
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
        if (value != null && listValue.contains(value)) {
            setText(listLabel.get(listValue.indexOf(value)), lnLayout);
        } else {
            List<String> listShow = new ArrayList<>();
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    listShow.add(listLabel.get(listValue.indexOf(list.get(j))));
                }
            }
            if (listShow.size() == 0) {
                listShow.add(getString(R.string.not_update));
            }
            for (int i = 0; i < listShow.size(); i++) {
                setText(listShow.get(i), lnLayout);
            }
        }
    }

    private void setText(String value, LinearLayout lnLayout) {
        lnLayout.removeAllViews();
        TextView textView = new TextView(mActivity);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(value);
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.black));
        lnLayout.addView(textView);
    }

    private void setUpView() {
        setupData(Constant.KEY_MAJOR);
        setupData(Constant.KEY_DEGREE);
        tvName.setText(String.valueOf("BS. " + mDoctor.getName()));
        if (mDoctor.getAverage_rating() == null) {
            tvRate.setText(getString(R.string.not_update2));
        } else {
            tvRate.setText(String.valueOf(mDoctor.getAverage_rating()));
        }
        if (Toolbox.isEmpty(mDoctor.getWorkplace())) {
            tvWorkplace.setText(getString(R.string.not_update));
        } else {
            tvWorkplace.setText(mDoctor.getWorkplace());
        }
        if (Toolbox.isEmpty(mDoctor.getCertdate())) {
            tvKinhNghiem.setText(getString(R.string.not_update));
        } else {
            tvKinhNghiem.setText(Toolbox.getExp(mDoctor.getCertdate(), "yyyy-MM-dd"));
        }
        if (Toolbox.isEmpty(mDoctor.getIntro())) {
            tvInfo.setText(R.string.info_no_update);
        } else {
            tvInfo.setText(mDoctor.getIntro());
        }
//        tvStatus.setText(mDoctor.isOnline() ? "Sẵn sằng" : "Chưa sẵn sàng");
        tvStatus.setText(mDoctor.isOnline() ? getString(R.string.tilte_online) : getString(R.string.title_offline));
        if (mDoctor.isOnline()) {
            tvStatus.setTextColor(Color.parseColor("#279E24"));
        } else {
            tvStatus.setTextColor(Color.parseColor("#8f6f6f"));
        }
        tvFee.setText(String.valueOf(Toolbox.formatMoney(mDoctor.getFee()) + " VNĐ"));
        imgSex.setImageDrawable(
                mDoctor.getSex().equals("male") ? getResources().getDrawable(R.drawable.ic_male)
                        : getResources().getDrawable(R.drawable.ic_female));
        Picasso.with(mActivity)
                .load(mDoctor.getAvatar_url())
                .placeholder(R.mipmap.ic_launcher)
                .into(imgAvatar);
        rating.setRating(mDoctor.getAverage_rating() == null ? 0 : mDoctor.getAverage_rating());

        if (mDoctor.getPhotos() != null && mDoctor.getPhotos().size() > 0) {
            photos.addAll(mDoctor.getPhotos());
            cardViewPhoto.setVisibility(View.VISIBLE);
        } else {
            cardViewPhoto.setVisibility(View.GONE);
        }
//        adapter = new ImageAdapter(getActivity(), photos);
//        adapter.setOnClick(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvListImg.setLayoutManager(mLayoutManager);
        rvListImg.setItemAnimator(new DefaultItemAnimator());
//        rvListImg.setAdapter(adapter);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.tvDangKy)
    public void onDangKy() {
        if (mPref.isLogged().get()) {
            checkUserCredits();
        } else {
            DialogUtils.showDialogLogin(getMainActivity(), () -> {
                Intent intent = new Intent(getApplicationContext(), LoginV2Activity.class);
                intent.putExtra(Constant.CHANGE_TAB, false);
                startActivity(intent);
                getMainActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            });
        }
    }

    private void checkUserCredits() {
        UserInfoResponse user = UserInfoResponse.getUser(mPref);
        if (TextUtils.isEmpty(user.getCredits()) || Integer.parseInt(user.getCredits()) < Integer.parseInt(mDoctor.getFee())) {
            DialogUtils.showDialogTwoChoice(getActivity(), true, false, false, null
                    , getString(R.string.vi_khong_du_tien), getString(R.string.close), getString(R.string.title_recharge_money)
                    , view -> DialogUtils.hideAlert(), view -> {
                        DialogUtils.hideAlert();
                        VietSkinApplication.setIsBackToSession(true);
                        getMainActivity().goToRechargeScreen();
                    });
        } else {
            DialogUtils.showDialogShowFee(getMainActivity(), mDoctor.getFee(), () -> {
                if (!TextUtils.isEmpty(mPref.sessionBodyNotDoctor().get()) && mDoctor.getId() != 0) {
                    createSession(mDoctor.getId());
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TAG_DOCTOR_ID, String.valueOf(mDoctor.getId()));
                    CreateSessionFragment createSessionFragment = new CreateSessionFragment();
                    createSessionFragment.setArguments(bundle);
                    getMainActivity().pushFragment(createSessionFragment);
                }
            });
        }
    }

    private void createSession(int id) {
        SessionBody sessionBody = GeneralUtil.fromJSon(SessionBody.class, mPref.sessionBodyNotDoctor().get());
        sessionBody.setDoctor_id(String.valueOf(id));
        onCreateSession(sessionBody);
    }

    private void onCreateSession(SessionBody sessionBody) {
        Call<CreateSessionResult> call = RequestHelper.getRequest(false, getActivity()).createSession(sessionBody);
        call.enqueue(new Callback<CreateSessionResult>() {
            @Override
            public void onResponse(Call<CreateSessionResult> call,
                                   Response<CreateSessionResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null || TextUtils.isEmpty(response.body().getId())) return;
                DialogUtils.showDialogOneChoice(getActivity(), false, true,
                        getString(R.string.yeu_cau_thanh_cong), getString(R.string.close), view -> {
                            mPref.sessionBodyNotDoctor().put("");
                            DialogUtils.hideAlert();
                            getMainActivity().initialize();
                        });
            }

            @Override
            public void onFailure(Call<CreateSessionResult> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    @OnClick(R.id.cardView)
    public void onGoToRechargeScreen() {
        VietSkinApplication.setIsBackToSession(false);
        getMainActivity().goToRechargeScreen();
    }

//    @Override
//    public void onClickImage(Photo photo) {
//        if(!VietSkinApplication.isIsOpenDetailScreen()) {
//            VietSkinApplication.setIsOpenDetailScreen(true);
//            Intent intent = new Intent(getMainActivity(), IntroImageActivity.class);
//            intent.putExtra("data", Toolbox.gson().toJson(photos));
//            intent.putExtra("index", photos.indexOf(photo));
//            startActivity(intent);
//            getMainActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
//        }
//    }
}
