package com.giahan.app.vietskindoctor.screens.setting;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.PayUrlBody;
import com.giahan.app.vietskindoctor.model.PayUrlResponse;
import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.NumberToTextUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 6/7/2018.
 */

public class UpdateInfoAccountFragment extends BaseFragment {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.edt_phone)
    EditText edtPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_account;
    }

    @Override
    protected void createView(View view) {
        initViews();
        initControl();
    }

    private void initViews() {
        tvTitle.setText(getString(R.string.title_update_account));
    }

    private void initControl() {
        Toolbox.setOnEditTextInputDone(edtPhone, this::hideKeyboard);
        edtPhone.addTextChangedListener(new TextWatcher() {
            String phoneCurrent = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 3 && !s.toString().equals(phoneCurrent)) {
                    edtPhone.removeTextChangedListener(this);
                    String phoneNumber = Toolbox.formatPhone0(s.toString());
                    int totalSelection = s.toString().length();
                    int currentSelection = edtPhone.getSelectionStart();
                    phoneCurrent = phoneNumber;
                    edtPhone.setText(phoneNumber);
                    int rightSelection = totalSelection - currentSelection;
                    if (rightSelection < 0) rightSelection = 0;
                    try {
                        edtPhone.setSelection(phoneNumber.length() - rightSelection > 0 ? phoneNumber.length() - rightSelection : 0);
                    } catch (Exception e) {
                        edtPhone.setSelection(edtPhone.getText().length());
                    }
                    edtPhone.addTextChangedListener(this);
                }
            }
        });
    }

    private boolean showErrorEdit(){
        boolean checkError = false;
        String phoneNumber = Toolbox.getText(edtPhone);
        if(Toolbox.isEmpty(phoneNumber)){
            Toolbox.setViewError(edtPhone, R.string.error_empty_phone);
            checkError = true;
        }
        return checkError;
    }

    @OnClick(R.id.btn_back)
    void back() {
        getMainActivity().hideKeyboard();
        getMainActivity().popFragment();
    }

    @OnClick(R.id.btn_update)
    void update() {
//        getMainActivity().hideKeyboard();
//        if(!showErrorEdit()){
//            showLoading();
//            String phoneNumber = Toolbox.getText(edtPhone);
//            InfoUpdateBody infoUpdateBody = new InfoUpdateBody(null, phoneNumber, null);
//            Call<List<Integer>> call = RequestHelper.getRequest(false, getActivity()).updateInfo(infoUpdateBody);
//            call.enqueue(new Callback<List<Integer>>() {
//                @Override
//                public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
//                    Log.e("api Update Info", "onResponse:  -----> "+response.headers().get("Access-Token"));
//                    hideLoading();
//                    getMainActivity().checkCodeShowDialog(response.code());
//                    if(response.body().get(0) == 1){
//                        getMainActivity().pref.token().put(response.headers().get("Access-Token"));
//                        initViews();
//                        back();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Integer>> call, Throwable t) {
//                    hideLoading();
//                    Log.e("Update Info", "error:  -----> "+t.getMessage());
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
//                }
//            });
//        }
    }
}
