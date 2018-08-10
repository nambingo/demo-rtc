package com.giahan.app.vietskindoctor.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.BaseResponse;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.ChangeEvent;
import com.giahan.app.vietskindoctor.model.event.ChangeFragEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 6/28/2018.
 */

public class ControlActivity extends BaseActivity {

    @BindView(R.id.btnLogin)
    TextView btnLogin;

    @BindView(R.id.btnIntro)
    TextView btnIntro;

    public PrefHelper_ pref;
    private boolean isFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentModeOn();
        isFromMain = getIntent().getBooleanExtra("fromMain", false);
        boolean isChange = getIntent().getBooleanExtra(Constant.CHANGE_TAB, false);
        if (isChange) {
            EventBus.getDefault().post(new ChangeEvent());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_v2;
    }

    @Override
    protected void createView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (pref == null) {
            pref = new PrefHelper_(VietSkinApplication.getInstance());
        }
        if (pref.isLogged().get()) {
            getUserInfo();
            btnLogin.setVisibility(View.GONE);
            btnIntro.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnIntro.setVisibility(View.GONE);
        }
        pref.dataSelected().put("");
    }

    private void getUserInfo() {
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, this).getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call,
                                   Response<UserInfoResponse> response) {
                Log.e("api GetInfo", "onResponse:  -----> " + response.message());
                checkCodeShowDialog(response.code());
                if (response.body() != null) {
                    UserInfoResponse user = UserInfoResponse.getUser(pref);
                    if (user != null) {
                        user.setAgainData(response.body());
                        pref.user().put(Toolbox.gson().toJson(user));
                        pref.token().put(user.getAccessToken());
                        if (Toolbox.isEmpty(response.body().getName()) || Toolbox.isEmpty(response.body().getBirthdate()) ||
                                Toolbox.isEmpty(response.body().getGender()) || Toolbox.isEmpty(response.body().getPhone())) {
                            if (!VietSkinApplication.isIsShowDialogUpdate()) {
                                VietSkinApplication.setIsShowDialogUpdate(true);
                                showDialogUpdate();
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                //
            }
        });
    }

    public void setTranslucentModeOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    void update(String name, String date, String gender, String phone, Dialog dialog) {
        hideKeyboard();
        showLoad();
        InfoUpdateBody infoUpdateBody = new InfoUpdateBody(name, date, phone, gender);
        Call<UpdateInfoResponse> call = RequestHelper.getRequest(false, this).updateInfo(infoUpdateBody);
        call.enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                hideLoading();
                pref.token().put(response.headers().get("Access-Token"));
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getSuccess() == 1) {
                        dialog.dismiss();
                        showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_update_success));
                        getUserInfo();
                        return;
                    }
                } else {
                    if (response.errorBody() != null) {
                        BaseResponse baseResponse = Toolbox.gson().fromJson(response.errorBody().charStream(), BaseResponse.class);
                        if (baseResponse.getErrorCode() != null && baseResponse.getErrorCode().equals("4")) {
                            showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_phone_error));
                            return;
                        }
                    }
                }
                checkCodeShowDialog(response.code());
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                hideLoading();
                Log.e("Update Info", "error:  -----> " + t.getMessage());
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    public void showDialogUpdate() {
        UserInfoResponse user = UserInfoResponse.getUser(pref);
        pref.isUpdateFirst().put(false);
        if (user != null) {
            DialogUtils.showDialogAlertUpdate(this, user, new DialogUtils.onListener() {
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
    }

    @OnClick(R.id.btnLogin)
    void login() {
        startActivity(new Intent(getApplicationContext(), LoginV2Activity.class));
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @OnClick(R.id.btnIntro)
    void intro() {
        showDialogUpdate();
    }

    @OnClick(R.id.btnGuide)
    void guide() {
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        intent.putExtra(Constant.OPEN_GUIDER, true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
    }

//    @OnClick(R.id.btnSelf)
//    void openVietSkinScreen() {
//        Intent intent = new Intent(getApplicationContext(), VietSkinContainActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//    }

    @OnClick(R.id.btnConsultant)
    void goToMainScreen() {
        if (!isFromMain) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("index", MainActivity.INDEX_KHAM_ONLINE);
            startActivity(intent);
//            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            EventBus.getDefault().post(new ChangeFragEvent(MainActivity.INDEX_KHAM_ONLINE));
            finish();
        }
    }

    @OnClick(R.id.btnDoctor)
    void goToMainDoctorScreen() {
        pref.isListDoctor().put(true);
        if (!isFromMain) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("index", MainActivity.INDEX_DANH_SACH_BS);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            EventBus.getDefault().post(new ChangeFragEvent(MainActivity.INDEX_DANH_SACH_BS));
            finish();
        }
    }
}
