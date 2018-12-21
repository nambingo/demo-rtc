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
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.KeyBoardUtil;
import com.giahan.app.vietskindoctor.utils.SimpleTextWatcher;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassCodeActivity extends BaseActivity {
    private String mPassCode;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_passcode_login;
    }

    @Override
    protected void createView() {
//        moveTaskToBack(true);
        edtNum1.requestFocus();
        KeyBoardUtil.show(this);
        settingEdittext();
        getDataUser();
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
        }else {
            pref.isBackground().put(false);
            callToLoginPassCode(mPassCode);
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
        AccountKit.logOut();
        logout(false);
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
                        if (pref.isFirstLogin().get()) {
                            Intent intent = new Intent(PassCodeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else {
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(PassCodeActivity.this, getString(R.string.mat_khau_khong_chinh_xac), Toast.LENGTH_SHORT).show();
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

    private void settingEdittext() {
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

    private void clearData() {
        edtNum1.setText("");
        edtNum2.setText("");
        edtNum3.setText("");
        edtNum4.setText("");
        edtNum5.setText("");
        edtNum6.setText("");
        edtNum1.requestFocus();
    }
}
