package com.giahan.app.vietskindoctor.screens.setting;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 6/29/2018.
 */

public class InfoAccountV2Fragment extends BaseFragment {

    @BindView(R.id.tv_profile_name) TextView tvProfileName;
    @BindView(R.id.tv_id) TextView tvId;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.edt_phone) MaterialEditText edtPhone;
    @BindView(R.id.edt_gender) MaterialEditText edtGender;
    @BindView(R.id.edt_from_date) MaterialEditText edtDate;
    @BindView(R.id.edt_address) MaterialEditText edtAddress;
    @BindView(R.id.profile_image) ImageView profileImage;
    @BindView(R.id.btn_save) Button btnSave;
    @BindView(R.id.delPhone) View delPhone;
    @BindView(R.id.delGender) View delGender;
    @BindView(R.id.delDate) View delDate;
    @BindView(R.id.delAddress) View delAddress;
    @BindView(R.id.rlGender) View rlGender;
    @BindView(R.id.rlUpdateGender) View rlUpdateGender;
    @BindView(R.id.llChooseDate) View llChooseDate;
    @BindView(R.id.radioM) RadioButton radioM;
    @BindView(R.id.radioF) RadioButton radioF;
    @BindView(R.id.radioGrp) RadioGroup radioGrp;

    public String birthDayDate = "";
    private SimpleDateFormat formatSend;
    private SimpleDateFormat formatShow;

    private static int fy, fm, fd;
    private boolean clickable = false;
    private String phone = "";
    private String gender = "";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_account_v2;
    }

    @Override
    protected void createView(View view) {
        showLoading();
        initControl();
    }

    private void initControl() {
        edtPhone.setInputType(InputType.TYPE_NULL);
        edtAddress.setInputType(InputType.TYPE_NULL);
        edtDate.setInputType(InputType.TYPE_NULL);
        edtGender.setInputType(InputType.TYPE_NULL);
        if (formatSend == null || formatShow == null) {
            formatSend = new SimpleDateFormat("yyyy-MM-dd", Constant.LOCALE_VN);
            formatShow = new SimpleDateFormat("dd/MM/yyyy", Constant.LOCALE_VN);
        }
//        edtDate.setText(Toolbox.formatDate(birthDayDate, formatSend, formatShow));
        Toolbox.setOnEditTextInputDone(edtPhone, () -> edtAddress.requestFocus());
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
                if(!s.toString().equals(getString(R.string.title_not_installed))){
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

    @Override
    public void onResume() {
        super.onResume();
        if(!clickable) {
            getUserInfo();
        }
    }

    private void initViews() {
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        if(user != null) {
            String linkAvatar = user.getAvatarAcc();
            String nameProfile = user.getName();
            String phone = user.getPhone();
            String credits = user.getCredits();
            String email = user.getEmail();
            tvId.setText("id: " + user.getId());
            if (!Toolbox.isEmpty(linkAvatar)) {
                Picasso.with(getContext()).load(linkAvatar).into(profileImage);
            }else {
                profileImage.setImageResource(R.drawable.ic_avatar);
            }
            tvProfileName.setText(nameProfile);
            if (!Toolbox.isEmpty(email)) {
                tvEmail.setText(email);
            }else {
                tvEmail.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(phone)) {
                edtPhone.setText(Toolbox.formatPhone0(phone));
            }else {
                edtPhone.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(user.getGender())) {
                edtGender.setText(user.getGender().equals("male")?"Nam": "Nữ");
                radioM.setChecked(user.getGender().equals("male"));
                radioF.setChecked(!user.getGender().equals("male"));
            }else {
                edtGender.setText(getString(R.string.title_not_installed));
                radioM.setChecked(false);
                radioF.setChecked(false);
            }
            if (!Toolbox.isEmpty(user.getAddress())) {
                edtAddress.setText(user.getAddress());
            }else {
                edtAddress.setText(getString(R.string.title_not_installed));
            }
            if (!Toolbox.isEmpty(user.getBirthdate())) {
                edtDate.setText(user.getBirthdate());
                birthDayDate = user.getBirthdate();
            }else {
                edtDate.setText(getString(R.string.title_not_installed));
                fy = -1;
            }
        }
    }

    private void getUserInfo() {
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, getActivity()).getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call,
                                   Response<UserInfoResponse> response) {
                Log.e("api GetInfo", "onResponse:  -----> "+response.message());
                hideLoading();
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() != null){
                    UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
                    if(user != null) {
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

    void setFocusable(MaterialEditText editText, boolean c){
        editText.setFocusableInTouchMode(c);
        editText.setFocusable(c);
        editText.setClickable(c);
    }

    void viewDel(int idVisib){
        delPhone.setVisibility(idVisib);
        delGender.setVisibility(idVisib);
        delDate.setVisibility(idVisib);
        delAddress.setVisibility(idVisib);
    }

    void setViewDefault(){
        edtPhone.setInputType(InputType.TYPE_NULL);
        edtAddress.setInputType(InputType.TYPE_NULL);
        btnSave.setVisibility(View.GONE);
        rlGender.setVisibility(View.VISIBLE);
        rlUpdateGender.setVisibility(View.GONE);
        viewDel(View.INVISIBLE);
        setFocusable(edtPhone, false);
        setFocusable(edtAddress, false);
        if(Toolbox.isEmpty(phone)){
            edtPhone.setText(getString(R.string.title_not_installed));
        }
    }

    void update() {
//        getMainActivity().hideKeyboard();
//        showLoading();
//        InfoUpdateBody infoUpdateBody = new InfoUpdateBody(birthDayDate, phone, gender);
//        Call<List<Integer>> call = RequestHelper.getRequest(false, getActivity()).updateInfo(infoUpdateBody);
//        call.enqueue(new Callback<List<Integer>>() {
//            @Override
//            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
//                Log.e("api Update Info", "onResponse:  -----> "+response.headers().get("Access-Token"));
//                Log.e("api Update Info", "onResponse:  -----> "+response.body());
//                hideLoading();
//                getMainActivity().checkCodeShowDialog(response.code());
//                if(response.body() != null && response.body().get(0) == 1){
//                    setViewDefault();
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_update_success));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Integer>> call, Throwable t) {
//                hideLoading();
//                Log.e("Update Info", "error:  -----> "+t.getMessage());
//                if (t instanceof NoConnectivityException) {
//                    // No internet connection
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
//                } else {
//                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
//                }
//            }
//        });
    }

    @OnClick(R.id.btn_back)
    void back() {
        getMainActivity().popFragment();
    }

    @OnClick(R.id.btn_update)
    void openScreenUpdateInfo() {
        clickable = true;
        edtPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtAddress.setInputType(InputType.TYPE_CLASS_TEXT);
        btnSave.setVisibility(View.VISIBLE);
        rlGender.setVisibility(View.GONE);
        rlUpdateGender.setVisibility(View.VISIBLE);
        viewDel(View.VISIBLE);
        setFocusable(edtPhone, true);
        setFocusable(edtAddress, true);
        if(edtPhone.getText().toString().equals(getString(R.string.title_not_installed))) {
            edtPhone.setText("");
            phone = "";
        }
        edtPhone.requestFocus();
    }

    @OnClick(R.id.btn_save)
    void save() {
        clickable = false;
        if(radioM.isChecked()){
            edtGender.setText("Nam");
            gender = "male";
        }else {
            if(radioF.isChecked()){
                edtGender.setText("Nữ");
                gender = "female";
            }else {
                edtGender.setText(getString(R.string.title_not_installed));
                gender = "";
            }
        }
        if(!Toolbox.isEmpty(edtPhone.getText().toString())) {
            phone = Toolbox.getText(edtPhone);
        }else {
            phone = "";
        }
        update();
    }

    @OnClick({R.id.delPhone, R.id.delAddress, R.id.delGender2, R.id.delDate})
    void onClick(View view) {
        switch(view.getId()){
            case R.id.delPhone:
                edtPhone.setText("");
                break;
            case R.id.delAddress:
                edtAddress.setText("");
                break;
            case R.id.delDate:
                edtDate.setText(getString(R.string.title_not_installed));
                birthDayDate = "";
                break;
            case R.id.delGender2:
                edtGender.setText("");
                radioGrp.clearCheck();
                break;
        }
    }

    @OnClick(R.id.edt_from_date)
    void clickFromDate() {
        if(clickable) {
            Calendar fc = Calendar.getInstance();
            Calendar fc2 = Calendar.getInstance();
            fc.add(Calendar.YEAR, -18);
            fc2.add(Calendar.YEAR, -18);
            fc2.add(Calendar.DATE, -1);
            if (fy != -1) {
                fc.set(Calendar.YEAR, fy);
                fc.set(Calendar.MONTH, fm);
                fc.set(Calendar.DAY_OF_MONTH, fd);
            }
            Toolbox.showDatePickerDialog(getContext(), fc, null, fc2.getTime(), (view, year, month, dayOfMonth) -> {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fy = year;
                fm = month;
                fd = dayOfMonth;
                edtDate.setText(formatShow.format(c.getTime()));
                birthDayDate = formatSend.format(c.getTime());
            });
        }
    }
}
