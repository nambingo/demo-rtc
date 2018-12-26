package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.accountkit.AccountKit;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.PassCodeResponse;
import com.giahan.app.vietskindoctor.model.PasscodeLoginBody;
import com.giahan.app.vietskindoctor.model.RegisterDeviceBody;
import com.giahan.app.vietskindoctor.model.RegisterResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.services.firebase.FMService;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.KeyBoardUtil;
import com.giahan.app.vietskindoctor.utils.SimpleTextWatcher;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassCodeActivity extends BaseActivity {
    private String mPassCode;
    private String FORMAT_DATE = "yyyy:MM:dd:HH:mm";

    @BindView(R.id.edtNum1)
    EditText edtNum1;

    @BindView(R.id.edtNum2)
    EditText edtNum2;

    @BindView(R.id.edtNum3)
    EditText edtNum3;

    @BindView(R.id.edtNum4)
    EditText edtNum4;

    @BindView(R.id.edtNum5)
    EditText edtNum5;

    @BindView(R.id.edtNum6)
    EditText edtNum6;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvphone)
    TextView tvphone;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.tvForgetPass)
    TextView tvForgetPass;

    @BindView(R.id.tvLogout)
    TextView tvLogout;

    boolean isChangePass;
    private int count = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_passcode_login;
    }

    @Override
    protected void createView() {
//        moveTaskToBack(true);
        getIntentData();
        edtNum1.requestFocus();
        KeyBoardUtil.show(this);
        settingEdittext();
        getDataUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstLogin(pref.isHasPasscode().get());
    }

    private void getIntentData() {
        if(getIntent()!=null){
            isChangePass = getIntent().getBooleanExtra("TAG_CHANGE_PASS", false);
        }
    }

    private void firstLogin(Boolean hasPasscode) {
        if(!hasPasscode){
            startActivity(new Intent(this, FirstLoginActivity.class));
        }
    }

    void showGuide() {
        pref.isFirstOpenApp().put(false);
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        intent.putExtra(Constant.OPEN_GUIDER, true);
        startActivity(intent);
    }

    private void getDataUser(){
        if (pref.isLogged().get()) {
            UserInfoResponse userInfoResponse = new Gson().fromJson(pref.user().get(), UserInfoResponse.class);
            tvName.setText(userInfoResponse.getName());
            tvphone.setText(userInfoResponse.getPhone());
        }
    }

    @OnClick(R.id.btnLogin)
    void onLogin() {
        mPassCode = edtNum1.getText().toString() + edtNum2.getText().toString()
                + edtNum3.getText().toString() + edtNum4.getText().toString()
                + edtNum5.getText().toString() + edtNum6.getText().toString();
        if (TextUtils.isEmpty(mPassCode) || mPassCode.length() != 6) {
            Toast.makeText(this, getString(R.string.nhap_day_du_passcode), Toast.LENGTH_SHORT).show();
        } else {
            if (compareTime(getCurrentTime(), pref.currentTime().get())) {
                DialogUtils.showDialogOneChoice(PassCodeActivity.this, true, false,
                        getString(R.string.nhap_qua_3_lan), getString(R.string.close),
                        view -> {
                            clearData();
                            DialogUtils.hideAlert();
                        });
            } else {
                pref.isBackground().put(false);
                pref.currentTime().put("");
                callToLoginPassCode(mPassCode);
            }
        }
    }

    @OnClick(R.id.tvForgetPass)
    void onForgetPass() {
        tvForgetPass.setEnabled(false);
        Toast.makeText(this, getString(R.string.mat_khau_moi_duoc_gui), Toast.LENGTH_SHORT).show();
        callToForgetPass();
    }

    @OnClick(R.id.tvLogout)
    void onLogout() {
        logout();
    }



    private void callToForgetPass() {
        Call<PassCodeResponse> call = RequestHelper.getRequest(false, this).recoveryPassCode();
        call.enqueue(new Callback<PassCodeResponse>() {
            @Override
            public void onResponse(final Call<PassCodeResponse> call, final Response<PassCodeResponse> response) {
                if (response != null && response.body() != null && response.errorBody() == null) {
                    if (response.body().getSuccess() == 1){
                        tvForgetPass.setEnabled(true);
                        clearData();
                    }
                }
            }

            @Override
            public void onFailure(final Call<PassCodeResponse> call, final Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void callToLoginPassCode(final String passCode) {
        PasscodeLoginBody passcodeLoginBody = new PasscodeLoginBody();
        passcodeLoginBody.setPasscode(passCode);
        Call<PassCodeResponse> call = RequestHelper.getRequest(false, this).loginPassCode(passcodeLoginBody);
        call.enqueue(new Callback<PassCodeResponse>() {
            @Override
            public void onResponse(final Call<PassCodeResponse> call, final Response<PassCodeResponse> response) {
                if (response != null && response.body() != null && response.errorBody() == null) {
                    if (response.body().getSuccess() == 1){
                        pref.isHasPasscode().put(true);
                        pref.isLogged().put(true);
                        /*if (pref.isFirstLogin().get()) {
                            Intent intent = new Intent(PassCodeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else {
                            finish();
                        }*/
                        if(isChangePass){
                            Intent intent = new Intent(PassCodeActivity.this, CreatePassCodeActivity.class);
                            pref.isCreatePassCode().put(false);
                            intent.putExtra("PASSCODE", mPassCode);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(PassCodeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        finish();
                    }
                } else {
                    count ++;
                    if (count == 3) {
                        saveTime();
                        DialogUtils.showDialogOneChoice(PassCodeActivity.this, true, false,
                                getString(R.string.nhap_qua_3_lan), getString(R.string.close),
                                view -> DialogUtils.hideAlert());
                    }else {
                        Toast.makeText(PassCodeActivity.this, getString(R.string.mat_khau_khong_chinh_xac),
                                Toast.LENGTH_SHORT).show();
                    }
                    clearData();
                }
            }

            @Override
            public void onFailure(final Call<PassCodeResponse> call, final Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void saveTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        String endTime = simpleDateFormat.format(calendar.getTime());
        pref.currentTime().put(endTime);
    }

    private void settingEdittext() {
//        GeneralUtil.autoMovingText(edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6);
        edtNum1.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum1.getText().toString().length() == 1) {
                    edtNum2.requestFocus();
                }
            }
        });
        edtNum2.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum2.getText().toString().length() == 1) {
                    edtNum3.requestFocus();
                } else if (edtNum2.getText().toString().length() == 0) {
                    edtNum1.requestFocus();
                    edtNum1.setText("");
                }
            }
        });
        edtNum3.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum3.getText().toString().length() == 1) {
                    edtNum4.requestFocus();
                } else if (edtNum3.getText().toString().length() == 0) {
                    edtNum2.requestFocus();
                    edtNum2.setText("");
                }
            }
        });
        edtNum4.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum4.getText().toString().length() == 1) {
                    edtNum5.requestFocus();
                } else if (edtNum4.getText().toString().length() == 0) {
                    edtNum3.requestFocus();
                    edtNum3.setText("");
                }
            }
        });
        edtNum5.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum5.getText().toString().length() == 1) {
                    edtNum6.requestFocus();
                } else if (edtNum5.getText().toString().length() == 0) {
                    Log.e("PassCodeActivity", "onTextChanged:  -----> ");
                    edtNum4.requestFocus();
                    edtNum4.setText("");
                }
            }
        });
        edtNum6.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                if (edtNum6.getText().toString().length() == 0) {
                    edtNum5.requestFocus();
                    edtNum5.setText("");
                }
            }
        });
    }

    private boolean compareTime(String time1, String time2){
        boolean isOld = false;
        if (TextUtils.isEmpty(time2)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE, Locale.US);
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            isOld = date1.compareTo(date2) < 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isOld;
    }

    private String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE, Locale.US);
        return sdf.format(new Date());
    }

    private void clearData() {
        edtNum1.setText("");
        edtNum2.setText("");
        edtNum3.setText("");
        edtNum4.setText("");
        edtNum5.setText("");
        edtNum6.setText("");
        edtNum1.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GeneralUtil.onActivityResult(this, pref, requestCode,requestCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event!=null){
            getDataUser();
        }
    }

}
