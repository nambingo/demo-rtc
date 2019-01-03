package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.PassCodeBody;
import com.giahan.app.vietskindoctor.model.PassCodeResponse;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePassCodeActivity extends BaseActivity {

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

    @BindView(R.id.edtConfirmNum1)
    EditText edtConfirmNum1;
    @BindView(R.id.edtConfirmNum2)
    EditText edtConfirmNum2;
    @BindView(R.id.edtConfirmNum3)
    EditText edtConfirmNum3;
    @BindView(R.id.edtConfirmNum4)
    EditText edtConfirmNum4;
    @BindView(R.id.edtConfirmNum5)
    EditText edtConfirmNum5;
    @BindView(R.id.edtConfirmNum6)
    EditText edtConfirmNum6;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_password;
    }

    @Override
    protected void createView() {
        Toolbox.autoMovingText(edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6);
        Toolbox.autoMovingText(edtConfirmNum1, edtConfirmNum2, edtConfirmNum3, edtConfirmNum4, edtConfirmNum5, edtConfirmNum6);
    }

    @OnClick(R.id.btnDone)
    void onClickDone() {
//        String currentPass = edtCurrentPassword.getText().toString();
        String currentPass = "";
        if(getIntent()!=null){
            currentPass = getIntent().getStringExtra("PASSCODE");
        }
        String confirmPass = (edtConfirmNum1.getText().toString()
                + edtConfirmNum2.getText().toString()
                + edtConfirmNum3.getText().toString()
                + edtConfirmNum4.getText().toString()
                + edtConfirmNum5.getText().toString()
                + edtConfirmNum6.getText().toString());
        String newPass = (edtNum1.getText().toString()
                + edtNum2.getText().toString()
                + edtNum3.getText().toString()
                + edtNum4.getText().toString()
                + edtNum5.getText().toString()
                + edtNum6.getText().toString());
        if (!pref.isHasPasscode().get()) {
            if (TextUtils.isEmpty(newPass)
                    || TextUtils.isEmpty(confirmPass)
                    || newPass.length() < 6
                    || confirmPass.length() < 6) {
                Toast.makeText(this, getString(R.string.mat_khau_6_ky_tu), Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, getString(R.string.mat_khau_khong_khop), Toast.LENGTH_SHORT).show();
            } else {
                sendPassCode("", confirmPass);
            }
        } else {
            if (TextUtils.isEmpty(currentPass)
                    || TextUtils.isEmpty(newPass)
                    || TextUtils.isEmpty(confirmPass)
                    || currentPass.length() < 6
                    || newPass.length() < 6
                    || confirmPass.length() < 6) {
                Toast.makeText(this, getString(R.string.mat_khau_6_ky_tu), Toast.LENGTH_SHORT).show();
            } else if(newPass.equals(currentPass)){
                Toast.makeText(this, getString(R.string.trung_mat_khau), Toast.LENGTH_SHORT).show();
            }else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, getString(R.string.mat_khau_khong_khop), Toast.LENGTH_SHORT).show();
            } else {
                sendPassCode(currentPass, newPass);
            }
        }
    }

    private void sendPassCode(final String currentPass, String newPass) {
        PassCodeBody passCodeBody = new PassCodeBody();
        passCodeBody.setCurrentPasscode(currentPass);
        passCodeBody.setNewPasscode(newPass);
        Call<PassCodeResponse> call = RequestHelper.getRequest(false, this).createPassCode(passCodeBody);
        call.enqueue(new Callback<PassCodeResponse>() {
            @Override
            public void onResponse(final Call<PassCodeResponse> call, final Response<PassCodeResponse> response) {
                if (response != null && response.body() != null && response.errorBody() == null) {
                    pref.isHasPasscode().put(true);
                    pref.isLogged().put(true);
                    if(pref.isCreatePassCode().get()) {
                        DialogUtils.showDialogOneChoice(CreatePassCodeActivity.this, false, true,getString(R.string.password_created)
                                ,getString(R.string.close), view ->{
                                    pref.isCreatePassCode().put(false);
                                    startActivity(new Intent(CreatePassCodeActivity.this, MainActivity.class));
                                    finish();
                                    DialogUtils.hideAlert();
                                });
                    }else {
                        DialogUtils.showDialogOneChoice(CreatePassCodeActivity.this, false, true,getString(R.string.password_changed)
                                ,getString(R.string.close), view ->{
                                    startActivity(new Intent(CreatePassCodeActivity.this, MainActivity.class));
                                    finish();
                                    DialogUtils.hideAlert();
                                });
                    }
                }
            }

            @Override
            public void onFailure(final Call<PassCodeResponse> call, final Throwable t) {
                Log.e("CreatePassCodeActivity", "onFailure:  -----> "+t.getMessage());
            }
        });
    }
}
