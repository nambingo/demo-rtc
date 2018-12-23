package com.giahan.app.vietskindoctor.activity;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePassCodeActivity extends BaseActivity {

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.btnDone)
    Button btnDone;
    /*@BindView(R.id.ll_currentPass)
    LinearLayout ll_currentPass;*/
   /* @BindView(R.id.edtCurrentPassword)
    EditText edtCurrentPassword;*/


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_password;
    }

    @Override
    protected void createView() {
//        ll_currentPass.setVisibility(pref.isCreatePassCode().get() ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.btnDone)
    void onClickDone() {
//        String currentPass = edtCurrentPassword.getText().toString();
        String currentPass = "";
        if(getIntent()!=null){
            currentPass = getIntent().getStringExtra("PASSCODE");
        }
        String newPass = edtPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();
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
                    if (pref.isCreatePassCode().get()) {
                        //TODO : show dialog update info
                    }else {
                        finish();
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
