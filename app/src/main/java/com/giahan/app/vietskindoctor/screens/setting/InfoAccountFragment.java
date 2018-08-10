package com.giahan.app.vietskindoctor.screens.setting;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.ListFilterResult;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 5/23/2018.
 */

public class InfoAccountFragment extends BaseFragment {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.ll_update) View llUpdate;
    @BindView(R.id.tv_profile_name) TextView tvProfileName;
    @BindView(R.id.profile_image) ImageView profileImage;
    @BindView(R.id.tv_credits_account) TextView tvCredits;
    @BindView(R.id.tv_phone_account) TextView tvPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_account;
    }

    @Override
    protected void createView(View view) {
        tvTitle.setText(getString(R.string.title_info_account));
        showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void initViews() {
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        llUpdate.setVisibility(View.VISIBLE);
        if(user != null) {
            String linkAvatar = user.getAvatarAcc();
            String nameProfile = user.getName();
            String phone = user.getPhone();
            String credits = user.getCredits();
            if (!Toolbox.isEmpty(linkAvatar)) {
                Picasso.with(getContext()).load(linkAvatar).into(profileImage);
            }else {
                profileImage.setImageResource(R.drawable.ic_avatar_default);
            }
            tvProfileName.setText(nameProfile);
            if (!Toolbox.isEmpty(phone)) {
                tvPhone.setText(Toolbox.formatPhone0(phone));
            }else {
                tvPhone.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(credits)) {
                tvCredits.setText(getString(R.string.title_money_default2, Toolbox.formatMoney(credits)));
            }else {
                tvCredits.setText(getString(R.string.title_money_default));
            }
        }
    }

    private void getUserInfo() {
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, getActivity()).getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call,
                                   Response<UserInfoResponse> response) {
                Log.e("api GetInfo", "onResponse:  -----> "+response.message());
                hideLoading();
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() != null){
                    UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
                    if(user != null) {
                        user.setAgainData(response.body());
                        getMainActivity().pref.user().put(Toolbox.gson().toJson(user));
                        getMainActivity().pref.token().put(user.getAccessToken());
                    }
                    initViews();
                }

            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
//                DialogUtils.showDialogError(mActivity, t.getMessage());
                hideLoading();
            }
        });
    }

    @OnClick(R.id.btn_back)
    void back() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.ll_update)
    void openScreenUpdateInfo() {
        getMainActivity().pushFragment(new UpdateInfoAccountFragment());
    }
}
