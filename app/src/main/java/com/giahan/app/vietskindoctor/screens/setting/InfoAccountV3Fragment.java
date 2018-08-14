//package com.giahan.app.vietskindoctor.screens.setting;
//
//import android.app.Dialog;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.giahan.app.vietskindoctor.R;
//import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
//import com.giahan.app.vietskindoctor.base.BaseFragment;
//import com.giahan.app.vietskindoctor.model.BaseResponse;
//import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
//import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
//import com.giahan.app.vietskindoctor.model.UserInfoResponse;
//import com.giahan.app.vietskindoctor.network.NoConnectivityException;
//import com.giahan.app.vietskindoctor.services.RequestHelper;
//import com.giahan.app.vietskindoctor.utils.Constant;
//import com.giahan.app.vietskindoctor.utils.DialogUtils;
//import com.giahan.app.vietskindoctor.utils.Toolbox;
//import com.squareup.picasso.Picasso;
//
//import java.text.SimpleDateFormat;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Created by NamVT on 7/18/2018.
// */
//
//public class InfoAccountV3Fragment extends BaseFragment {
//
//    @BindView(R.id.tvSoTien)
//    TextView tvSoTien;
//    @BindView(R.id.tv_profile_name)
//    TextView tvProfileName;
//    @BindView(R.id.tv_id)
//    TextView tvId;
//    @BindView(R.id.tv_email)
//    TextView tvEmail;
//    @BindView(R.id.tv_coin)
//    TextView tvCoin;
//    @BindView(R.id.tv_phone)
//    TextView tvPhone;
//    @BindView(R.id.tv_gender)
//    TextView tvGender;
//    @BindView(R.id.tv_birthday)
//    TextView tvBirthday;
//    @BindView(R.id.tv_weight)
//    TextView tvWeight;
//    @BindView(R.id.profile_image)
//    ImageView profileImage;
//
//    public String birthDayDate = "";
//    public String weight = "";
//    private SimpleDateFormat formatSend;
//    private SimpleDateFormat formatShow;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_info_account_v3;
//    }
//
//    @Override
//    protected void createView(View view) {
//        showLoading();
//        initControl();
//    }
//
//    private void initControl() {
//        if (formatSend == null || formatShow == null) {
//            formatSend = new SimpleDateFormat("yyyy-MM-dd", Constant.LOCALE_VN);
//            formatShow = new SimpleDateFormat("dd-MM-yyyy", Constant.LOCALE_VN);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getUserInfo();
//    }
//
//    private void initViews() {
//        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
//        if (user != null) {
//            String linkAvatar = user.getAvatarAcc();
//            String nameProfile = user.getName();
//            String phone = user.getPhone();
//            String credits = user.getCredits();
//            String email = user.getEmail();
//            tvId.setText(String.valueOf("Mã bệnh nhân: " + user.getId()));
//            if (!Toolbox.isEmpty(linkAvatar)) {
//                Picasso.with(getContext()).load(linkAvatar).into(profileImage);
//            } else {
//                profileImage.setImageResource(R.drawable.ic_avatar);
//            }
//            tvProfileName.setText(nameProfile);
//            if (!Toolbox.isEmpty(email)) {
//                tvEmail.setText(email);
//            } else {
//                tvEmail.setText(getString(R.string.title_not_installed));
//            }
//            if (!Toolbox.isEmpty(phone)) {
//                tvPhone.setText(Toolbox.formatPhone0(phone));
//            } else {
//                tvPhone.setText(getString(R.string.title_not_installed));
//            }
//            if (!Toolbox.isEmpty(user.getGender())) {
//                tvGender.setText(user.getGender().equals("male") ? "Nam" : "Nữ");
//            } else {
//                tvGender.setText(getString(R.string.title_not_installed));
//            }
//            if (!Toolbox.isEmpty(user.getBirthdate())) {
//                tvBirthday.setText(Toolbox.formatDate(user.getBirthdate(), formatSend, formatShow));
//                birthDayDate = user.getBirthdate();
//            } else {
//                tvBirthday.setText(getString(R.string.title_not_installed));
//            }
//            if (!Toolbox.isEmpty(user.getWeight())) {
//                tvWeight.setText(user.getWeight());
//                weight = user.getWeight();
//            } else {
//                tvWeight.setText(getString(R.string.title_not_installed));
//            }
//            if (!Toolbox.isEmpty(credits)) {
//                tvCoin.setText(getString(R.string.title_money_default4, Toolbox.formatMoney(credits)));
//            } else {
//                tvCoin.setText(getString(R.string.title_money_default));
//            }
////            tvSoTien.setText(TextUtils.isEmpty(user.getCredits()) ? " 0 VNĐ"
////                    : Toolbox.formatMoney(user.getCredits()) + " VNĐ");
//            tvSoTien.setText(TextUtils.isEmpty(user.getCredits()) ? getResources().getText(
//                    R.string.vi_vietskin_cua_ban) + " 0 VNĐ"
//                    : getResources().getText(R.string.vi_vietskin_cua_ban) + " " + Toolbox.formatMoney(
//                    user.getCredits()) + " VNĐ");
//        }
//    }
//
//    void update(String name, String date, String gender, String phone, Dialog dialog) {
//        hideKeyboard();
//        showLoading();
//        InfoUpdateBody infoUpdateBody = new InfoUpdateBody(name, date, phone, gender);
//        Call<UpdateInfoResponse> call = RequestHelper.getRequest(false, getActivity()).updateInfo(infoUpdateBody);
//        call.enqueue(new Callback<UpdateInfoResponse>() {
//            @Override
//            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
//                hideLoading();
//                getMainActivity().pref.token().put(response.headers().get("Access-Token"));
//                if (response.isSuccessful()) {
//                    if (response.body() != null && response.body().getSuccess() == 1) {
//                        dialog.dismiss();
//                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_update_success));
//                        getUserInfo();
//                        return;
//                    }
//                } else {
//                    if (response.errorBody() != null) {
//                        BaseResponse baseResponse = Toolbox.gson().fromJson(response.errorBody().charStream(), BaseResponse.class);
//                        if (baseResponse.getErrorCode() != null && baseResponse.getErrorCode().equals("4")) {
//                            getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_phone_error));
//                            return;
//                        }
//                    }
//                }
//                getMainActivity().checkCodeShowDialog(response.code());
//            }
//
//            @Override
//            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
//                hideLoading();
//                Log.e("Update Info", "error:  -----> " + t.getMessage());
//                if (t instanceof NoConnectivityException) {
//                    // No internet connection
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
//                } else {
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
//                }
//            }
//        });
//    }
//
//    private void getUserInfo() {
//        Call<UserInfoResponse> call = RequestHelper.getRequest(false, getActivity()).getUserInfo();
//        call.enqueue(new Callback<UserInfoResponse>() {
//            @Override
//            public void onResponse(Call<UserInfoResponse> call,
//                                   Response<UserInfoResponse> response) {
//                Log.e("api GetInfo", "onResponse:  -----> " + response.message());
//                hideLoading();
//                getMainActivity().checkCodeShowDialog(response.code());
//                if (response.body() != null) {
//                    UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
//                    if (user != null) {
//                        user.setAgainData(response.body());
//                        getMainActivity().pref.user().put(Toolbox.gson().toJson(user));
//                        getMainActivity().pref.token().put(user.getAccessToken());
//                    }
//                    initViews();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
////                DialogUtils.showDialogError(mActivity, t.getMessage());
//                hideLoading();
//                if (t instanceof NoConnectivityException) {
//                    // No internet connection
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
//                    getMainActivity().popFragment();
//                } else {
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
//                }
//            }
//        });
//    }
//
//    @OnClick(R.id.btn_back)
//    void back() {
//        getMainActivity().popFragment();
//    }
//
//    @OnClick(R.id.wallet)
//    void openRecharge() {
//        VietSkinDoctorApplication.setIsBackToSession(false);
//        getMainActivity().pushFragment(new RechargeFragment());
//    }
//
//    @OnClick(R.id.btn_update)
//    void openScreenUpdateInfo() {
//        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
//        DialogUtils.showDialogAlertUpdate(getActivity(), user, new DialogUtils.onListener() {
//            @Override
//            public void onListen(String name, String date, String gender, String phone, Dialog dialog) {
//                update(name, date, gender, phone, dialog);
//            }
//
//            @Override
//            public void onDismiss(Dialog dialog) {
//                dialog.dismiss();
//            }
//        });
//    }
//}
