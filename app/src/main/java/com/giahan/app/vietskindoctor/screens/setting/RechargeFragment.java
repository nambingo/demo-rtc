package com.giahan.app.vietskindoctor.screens.setting;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.model.PayUrlBody;
import com.giahan.app.vietskindoctor.model.PayUrlResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.RechargeResponseEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 5/23/2018.
 */

public class RechargeFragment extends BaseFragment {

    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.tv_coin2)
    TextView tvCoin2;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.edt_amount)
    EditText edtAmount;

    private int money = 0;
    private int moneyRecharge = 0;
    private boolean isClear = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge_v2;
    }

    @Override
    protected void createView(View view) {
//        initViews();
        initControl();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getUserInfo() {
        showLoading();
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, getActivity()).getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call,
                                   Response<UserInfoResponse> response) {
                hideLoading();
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() != null) {
                    UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
                    if (user != null) {
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
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                    getMainActivity().popFragment();
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    @SuppressLint("StringFormatInvalid")
    private void initViews() {
        if (isClear) {
            edtAmount.setText("");
            isClear = false;
        }
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        if (user != null) {
            String credits = user.getCredits();
            String creditsHold = user.getCreditHold();
            if (!Toolbox.isEmpty(credits)) {
                tvCoin.setText(getString(R.string.title_money_default4, Toolbox.formatMoney(credits)));
                try {
                    money = Integer.parseInt(credits);
                } catch (NumberFormatException ex) {
                    money = 0;
                }
            } else {
                tvCoin.setText(getString(R.string.title_money_default));
                money = 0;
            }
            if (!Toolbox.isEmpty(creditsHold)) {
                tvCoin2.setText(getString(R.string.title_money_default4, Toolbox.formatMoney(creditsHold)));
            } else {
                tvCoin2.setText(getString(R.string.title_money_default));
            }
        } else {
            tvCoin.setText(getString(R.string.title_money_default4, "0"));
            tvCoin2.setText(getString(R.string.title_money_default4, "0"));
        }
    }

    private void initControl() {
        Toolbox.autoFormatEditTextNumber(edtAmount);
        Toolbox.autoNumberToText(edtAmount, tvMoney);
        Toolbox.setOnFocusChangeListener(edtAmount, this::hideKeyboard);
    }

    private boolean showErrorEdit() {
        boolean checkError = false;
        String transAmount = Toolbox.getText(edtAmount);
        int amount = 0;
        try {
            amount = Integer.parseInt(transAmount);
        } catch (NumberFormatException ignore) {
            amount = 0;
        }
        if (Toolbox.isEmpty(transAmount)) {
            checkError = true;
            Toolbox.setViewError(edtAmount, R.string.error_empty_amount);
        }
        if (!Toolbox.isEmpty(transAmount) && amount < 20000) {
            checkError = true;
            Toolbox.setViewError(edtAmount, R.string.msg_error_input_recharge);
        }
        if (!Toolbox.isEmpty(transAmount) && amount > 20000000) {
            checkError = true;
            Toolbox.setViewError(edtAmount, R.string.msg_error_input_recharge2);
        }
        moneyRecharge = amount;
        return checkError;
    }

    @OnClick(R.id.btn_back)
    void back() {
        getMainActivity().hideKeyboard();
        getMainActivity().popFragment();
    }

    @OnClick(R.id.btn_confirm)
    void confirm() {
        getMainActivity().hideKeyboard();
        if (!showErrorEdit()) {
            showLoading();
            String transAmount = Toolbox.getText(edtAmount);
            PayUrlBody payUrlBody = new PayUrlBody(transAmount);
            Call<PayUrlResponse> call = RequestHelper.getRequest(false, getActivity()).getPayUrl(payUrlBody);
            call.enqueue(new Callback<PayUrlResponse>() {
                @Override
                public void onResponse(Call<PayUrlResponse> call, Response<PayUrlResponse> response) {
                    Log.e("Recharge", "onResponse:  -----> " + response.message());
                    hideLoading();
                    getMainActivity().checkCodeShowDialog(response.code());
                    if (response.body() != null) {
                        Log.e("Recharge", "url:  -----> " + response.body().getPayUrl());
                        String urlRes = response.body().getPayUrl();
                        getMainActivity().pref.token().put(response.body().getAccessToken());

                        WebviewRechargeFragment webviewRechargeFragment = new WebviewRechargeFragment();
                        webviewRechargeFragment.setUrl(urlRes);
                        webviewRechargeFragment.setMoney(money, moneyRecharge);
                        getMainActivity().pushFragment(webviewRechargeFragment);
                    }
                }

                @Override
                public void onFailure(Call<PayUrlResponse> call, Throwable t) {
                    hideLoading();
                    Log.e("Recharge", "error:  -----> " + t.getMessage());
                    if (t instanceof NoConnectivityException) {
                        // No internet connection
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                    } else {
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeEvent(RechargeResponseEvent event) {
        /* Do something */
        if (event != null) {
            isClear = true;
        }
    }
}
