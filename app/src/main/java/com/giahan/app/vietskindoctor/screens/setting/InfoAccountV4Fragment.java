package com.giahan.app.vietskindoctor.screens.setting;

import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.WithdrawListReSult;
import com.giahan.app.vietskindoctor.domains.WithdrawalResult;
import com.giahan.app.vietskindoctor.model.BaseResponse;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.WithdrawBody;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.setting.transaction.TransactionDetailFragment;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 7/18/2018.
 */

public class InfoAccountV4Fragment extends BaseFragment {

    @BindView(R.id.tvSoTien)
    TextView tvSoTien;
    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.tvWorkplace)
    TextView tvWorkplace;
    @BindView(R.id.lnkhoanhgiu)
    LinearLayout holdCreditContainer;
    @BindView(R.id.tvHoldCredit)
    TextView tvHoldCredit;
    @BindView(R.id.tvDate)
    TextView tvDate;

    public String birthDayDate = "";
    private SimpleDateFormat formatSend;
    private SimpleDateFormat formatShow;
    private String withdrawAmount = "";
    private String passcode;
    UserInfoResponse currentUser;
    private List<WithdrawalResult> withdrawList= new ArrayList<>();
    boolean requestInProcess = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_account_v4;
    }

    @Override
    protected void createView(View view) {
        showLoading();
        initControl();
    }

    private void initControl() {
        if (formatSend == null || formatShow == null) {
            formatSend = new SimpleDateFormat("yyyy-MM-dd", Constant.LOCALE_VN);
            formatShow = new SimpleDateFormat("dd-MM-yyyy", Constant.LOCALE_VN);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkRequestingWithdraw();
        getUserInfo();
    }

    private void checkRequestingWithdraw() {
        Call<WithdrawListReSult> call = RequestHelper.getRequest(false, getActivity()).getAllWithdrawal();
        call.enqueue(new Callback<WithdrawListReSult>() {
            @Override
            public void onResponse(Call<WithdrawListReSult> call, Response<WithdrawListReSult> response) {
                hideLoading();
                if(response.body()!=null){
                    withdrawList = response.body().getWithdraws();
                    if(withdrawList.size()>0){
                        sumRequestingWithdraw(withdrawList);
                    }
                }
            }

            @Override
            public void onFailure(Call<WithdrawListReSult> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void sumRequestingWithdraw(List<WithdrawalResult> withdrawList) {
        for (WithdrawalResult withdrawalResult : withdrawList){
            if(withdrawalResult.getStatus().equals("requesting")){
                requestInProcess = true;
                tvHoldCredit.setText(Toolbox.formatMoney(withdrawalResult.getAmount()));
                tvDate.setText(withdrawalResult.getCreatedAt().substring(0,withdrawalResult.getCreatedAt().indexOf(" ")));
            }
        }
        holdCreditContainer.setVisibility(requestInProcess ? View.VISIBLE : View.GONE);
    }

    private void initViews() {
        currentUser = UserInfoResponse.getUser(getMainActivity().pref);
        if (currentUser != null) {
            String linkAvatar = currentUser.getAvatar();
            String nameProfile = currentUser.getName();
            String phone = currentUser.getPhone();
            String credits = currentUser.getCredits();
            String email = currentUser.getEmail();
            if (!Toolbox.isEmpty(linkAvatar)) {
                Picasso.with(getContext()).load(linkAvatar).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.ic_launcher);
            }
            tvProfileName.setText(
                    String.format("%s %s", TextUtils.isEmpty(currentUser.getDegree()) ? "BS." : currentUser.getDegree(),
                            nameProfile));
            if (!Toolbox.isEmpty(email)) {
                tvEmail.setText(email);
            } else {
                tvEmail.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(phone)) {
                tvPhone.setText(Toolbox.formatPhone0(phone));
            } else {
                tvPhone.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(currentUser.getGender())) {
                tvGender.setText(currentUser.getGender().equals("male") ? "Nam" : "Nữ");
            } else {
                tvGender.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(currentUser.getBirthdate())) {
                tvBirthday.setText(Toolbox.formatDate(currentUser.getBirthdate(), formatSend, formatShow));
                birthDayDate = currentUser.getBirthdate();
            } else {
                tvBirthday.setText(getString(R.string.title_not_installed));
            }
            tvSoTien.setText(TextUtils.isEmpty(currentUser.getCredits()) ? String.format("%s 0 VNĐ", getResources().getText(
                    R.string.vi_vietskin_cua_ban))
                    : String.format("%s %s VNĐ", getResources().getText(R.string.vi_vietskin_cua_ban),
                            Toolbox.formatMoney(
                                    currentUser.getCredits())));
            /*if(TextUtils.isEmpty(currentUser.getCredits())){
                tvSoTien.setText(String.format("%s 0 VNĐ", getResources().getText(
                        R.string.vi_vietskin_cua_ban)));
            }else {
                int currentCredit = Integer.parseInt(currentUser.getCredits()) - holdCreadit;
                tvSoTien.setText(String.format("%s %s VNĐ", getResources().getText(R.string.vi_vietskin_cua_ban),
                        Toolbox.formatMoney(
                                String.valueOf(currentCredit))));
            }*/
            tvWorkplace.setText(
                    TextUtils.isEmpty(currentUser.getWorkplace()) ? getString(R.string.chua_cap_nhat) : currentUser.getWorkplace());
        }
    }

    void update(String name, String date, String gender, String phone, Dialog dialog) {
        hideKeyboard();
        showLoading();
        InfoUpdateBody infoUpdateBody = new InfoUpdateBody(name, date, phone, gender);
        Call<UpdateInfoResponse> call = RequestHelper.getRequest(false, getActivity()).updateInfo(infoUpdateBody);
        call.enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                hideLoading();
                getMainActivity().pref.token().put(response.headers().get("Access-Token"));
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() == 1) {
                        dialog.dismiss();
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_update_success));
                        getUserInfo();
                        return;
                    }
                } else {
                    if (response.errorBody() != null) {
                        BaseResponse baseResponse = Toolbox.gson().fromJson(response.errorBody().charStream(), BaseResponse.class);
                        if (baseResponse.getErrorCode() != null && baseResponse.getErrorCode().equals("4")) {
                            getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_phone_error));
                            return;
                        }
                    }
                }
                getMainActivity().checkCodeShowDialog(response.code());
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                hideLoading();
                Log.e("Update Info", "error:  -----> " + t.getMessage());
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void getUserInfo() {
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, getActivity()).getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call,
                                   Response<UserInfoResponse> response) {
                Log.e("api GetInfo", "onResponse:  -----> " + response.message());
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

    @OnClick(R.id.btn_back)
    void back() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.wallet)
    void openRecharge() {
        VietSkinDoctorApplication.setIsBackToSession(false);
//        getMainActivity().pushFragment(new RechargeFragment());
    }

    @OnClick(R.id.btn_update)
    void openScreenUpdateInfo() {
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        DialogUtils.showDialogAlertUpdate(getActivity(), user, new DialogUtils.onListener() {
            @Override
            public void onListen(String name, String date, String gender, String phone, Dialog dialog) {
                update(name, date, gender, phone, dialog);
            }

            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.btnWithdrawContainer)
    public void onWithdraw() {
        if (requestInProcess) {
            DialogUtils.showDialogOneChoice(getContext(), true,false, getString(R.string.msg_withdraw_once)
            ,getString(R.string.close), view->{
                DialogUtils.hideAlert();
                    });

        } else{
            DialogUtils.showDialogWithdrawProcess(mActivity, false, getString(R.string.msg_withdraw_amount), currentUser.getCredits(), view -> {
                        DialogUtils.hideAlert();
                    }
                    , new DialogUtils.onListenerWithdrawDialogInput() {
                        @Override
                        public void onListen(String value) {
                            withdrawAmount = Toolbox.isEmpty(value) ? "0" : value;
                            if (currentUser != null && !Toolbox.isEmpty(currentUser.getCredits())) {
                                if (Integer.parseInt(withdrawAmount) >= 200000 && Integer.parseInt(withdrawAmount) <= Integer.parseInt(currentUser.getCredits())) {
                                    DialogUtils.hideAlert();
                                    showPasscodeRequire();
                                } else {
                                    DialogUtils.hideAlert();
                                    DialogUtils.showDialogOneChoice(mActivity, true, false, getString(R.string.error_invalid_money)
                                            , getString(R.string.close), view -> {
                                                withdrawAmount = "";
                                                DialogUtils.hideAlert();
                                                onWithdraw();
                                            });
                                }
                            }

                        }
                    });
        }
    }

    @OnClick(R.id.btnTransRecord)
    public void onGotoTransactionDetail(){
        getMainActivity().pushFragment(new TransactionDetailFragment());
    }

    private void showPasscodeRequire() {
        DialogUtils.showDialogWithdrawProcess(mActivity, true,  getString(R.string.msg_pass_code_required), null
                , view -> {
                    DialogUtils.hideAlert();
                }
                , new DialogUtils.onListenerWithdrawDialogInput() {
                    @Override
                    public void onListen(String value) {
                        passcode = value;
                        WithdrawBody withdrawBody = new WithdrawBody(withdrawAmount, passcode);
                        sendWithdrawRequest(withdrawBody);
                    }
                });
    }

    private void sendWithdrawRequest(WithdrawBody withdrawBody) {
        showLoading();
        Call<WithdrawalResult> call = RequestHelper.getRequest(false, getActivity()).sendWithdrawalRequest(withdrawBody);
        call.enqueue(new Callback<WithdrawalResult>() {
            @Override
            public void onResponse(Call<WithdrawalResult> call, Response<WithdrawalResult> response) {
                DialogUtils.hideAlert();
                hideLoading();
                if(response.body()!=null){
                    String msg = "Yêu cầu rút " + Toolbox.formatMoney(withdrawAmount) +" VNĐ đang trong quá trình xử lý.\n Vui lòng chờ nhân viên VietSkin liên lạc trực tiếp";
                            DialogUtils.showDialogOneChoice(mActivity, false, true, msg
                            ,getString(R.string.close), view -> {
                                checkRequestingWithdraw();
                        DialogUtils.hideAlert();
                    });
                }
                if(response.errorBody() != null){
                    BaseResponse baseResponse = Toolbox.gson().fromJson(response.errorBody().charStream(), BaseResponse.class);

                    if(baseResponse.getErrorCode().equals("22")){
                    DialogUtils.showDialogOneChoice(mActivity, true, false, getString(R.string.error_invaild_passcode)
                        ,getString(R.string.close), view -> {
                            passcode = "";
                            DialogUtils.hideAlert();
                            showPasscodeRequire();
                                });
                    }else {
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                    }
                }
            }

            @Override
            public void onFailure(Call<WithdrawalResult> call, Throwable t) {
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
}
