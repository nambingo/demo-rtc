package com.giahan.app.vietskindoctor.screens.yeucau;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Scroller;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.IntroImageActivity;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.AcceptRequest;
import com.giahan.app.vietskindoctor.domains.AcceptRequestBody;
import com.giahan.app.vietskindoctor.domains.Photo;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.ChatFragment;
import com.giahan.app.vietskindoctor.screens.yeucau.PhotoAdapter.OnClickViewListener;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietYeuCauFragment extends BaseFragment implements OnClickViewListener {

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

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;




    private Session mSession;

    private PhotoAdapter mAdapter;

    private List<String> mListPath = new ArrayList<>();

    @Override
    public void onClickView(final String url) {
        showDetailImage(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chitiet_yeucau;
    }

    @Override
    protected void createView(final View view) {
        initData();
        setupView();
        initListPhoto();
    }

    private void setupView() {
        switch (mSession.getRelationship()) {
            case "1":
                tvDoiTuong.setText(getString(R.string.me));
                break;
            case "2":
                tvDoiTuong.setText(getString(R.string.wife_husd));
                break;
            case "3":
                tvDoiTuong.setText(getString(R.string.parent));
                break;
            case "4":
                tvDoiTuong.setText(getString(R.string.child));
                break;
            default:
                tvDoiTuong.setText(getString(R.string.me));
                break;
        }
        tvName.setText(mSession.getPatientName());
        tvWeight.setText(mSession.getWeight());
        if (!TextUtils.isEmpty(mSession.getBirthdate())) {
            tvNgaySinh.setText(mSession.getBirthdate().substring(0,mSession.getBirthdate().indexOf(" ")));
        }
        tvSex.setText(TextUtils.isEmpty(mSession.getSex()) ? "N/A" : mSession.getSex().equals("2") ? "Nữ" : "Nam");
        tvDescription.setText(mSession.getDescription());
        setupDescription();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDescription() {
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        scrollView.setOnTouchListener((v, event) -> {
            tvDescription.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        });
        tvDescription.setOnTouchListener((v, event) -> {
            tvDescription.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
    }

    private void initData() {
        mSession = GeneralUtil.fromJSon(Session.class, mPref.currentRequest().get());
    }

    private void initListPhoto() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvResultPhoto.setLayoutManager(linearLayoutManager);
        mListPath.clear();
        for (int i = 0; i < mSession.getPhotos().size(); i++) {
            mListPath.add(mSession.getPhotos().get(i).getUrl());
        }
        mAdapter = new PhotoAdapter(mActivity, mListPath);
        mAdapter.setOnClickViewListener(this);
        rvResultPhoto.setAdapter(mAdapter);
    }

    @OnClick(R.id.lnBack)
    public void onClick() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.tvChapNhan)
    public void onAccept() {
        actionDoctor(true);
    }

    @OnClick(R.id.tvTuChoi)
    public void onReject() {
        DialogUtils.showDialogTwoChoice(getActivity(), false, false, true,
                getString(R.string.confirm_reject), "", getString(R.string.cancel),
                getString(R.string.chac_chan), view -> {
                    DialogUtils.hideAlert();
                }, view -> {
                    DialogUtils.hideAlert();
                    actionDoctor(false);
                });
    }

    private void actionDoctor(boolean isAccept){
        AcceptRequestBody acceptRequestBody = new AcceptRequestBody(isAccept, mSession.getId());
        Call<AcceptRequest> call = RequestHelper.getRequest(false, getActivity()).acceptRequest(acceptRequestBody);
        call.enqueue(new Callback<AcceptRequest>() {
            @Override
            public void onResponse(final Call<AcceptRequest> call, final Response<AcceptRequest> response) {
                if (response == null) {
                    return;
                }
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) {
                    return;
                }
                hideLoading();
                EventBus.getDefault().post(new MessageEvent());
                if (isAccept) {
                    DialogUtils.showDialogOneChoice(getActivity(), false, true, getString(R.string.da_ket_noi_phien),
                            getString(R.string.ok), view -> {
                                getMainActivity().initialize();
                                openSession(mSession);
                                DialogUtils.hideAlert();
                            });
                }else {
                    DialogUtils.showDialogOneChoice(getActivity(), false, true, getString(R.string.da_tu_choi_kham),
                            getString(R.string.ok), view -> {
                                getMainActivity().initialize();
                                getMainActivity().selectPhien();
                                DialogUtils.hideAlert();
                            });
                }
            }

            @Override
            public void onFailure(final Call<AcceptRequest> call, final Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void openSession(Session session) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_DSSESION_ID, session.getId());
        bundle.putString(Constant.TAG_PATIENT_NAME, session.getPatientName());
        bundle.putString(Constant.TAG_PATIENT_ID, session.getPatientID());
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getMainActivity().pushFragment(chatFragment);
    }

    private void showDetailImage(String url) {
        if (!VietSkinDoctorApplication.isIsOpenDetailScreen()) {
            VietSkinDoctorApplication.setIsOpenDetailScreen(true);
            if (mListPath.size() == 0) return;
            Intent intent = new Intent(getMainActivity(), IntroImageActivity.class);
            intent.putExtra("data", Toolbox.gson().toJson(mSession.getPhotos()));
            intent.putExtra("index", mListPath.indexOf(url));
            startActivity(intent);
        }
    }
}
