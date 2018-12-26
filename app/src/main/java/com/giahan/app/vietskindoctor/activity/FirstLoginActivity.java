package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import butterknife.BindView;
import butterknife.OnClick;

public class FirstLoginActivity extends BaseActivity{

    @BindView(R.id.edtPhoneNUmber)
    EditText edtPhoneNum;

    @BindView(R.id.btnNext)
    Button btnNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_login;
    }

    @Override
    protected void createView() {
        if(pref.isHasPasscode().get()){
            startActivity(new Intent(this, PassCodeActivity.class));
        }
        if(pref.isFirstOpenApp().get()){
            pref.isFirstOpenApp().put(false);
            showGuide();
        }
    }

    @OnClick(R.id.btnNext)
    public void onNext(){
        String phoneNum = edtPhoneNum.getText().toString();
        if(isValidPhoneNum(phoneNum)){
            showLoad();
            GeneralUtil.goToLogin(this, phoneNum);
        }else {
            DialogUtils.showDialogOneChoice(this, false, true, getString(R.string.error_phone_number)
                    ,getString(R.string.close), view ->{
                        DialogUtils.hideAlert();
                    });
        }
    }

    private static boolean isValidPhoneNum(String phoneNum) {
        boolean isValid = false;
        if(!Toolbox.isEmpty(phoneNum)){
            if(phoneNum.substring(0,1).equals("0")) {
                if (phoneNum.length() == 10) isValid = true;
            }
        }

        return isValid;
    }

    void showGuide() {
        pref.isFirstOpenApp().put(false);
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        intent.putExtra(Constant.OPEN_GUIDER, true);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GeneralUtil.onActivityResult(this,pref, requestCode, resultCode, data);
    }
}
