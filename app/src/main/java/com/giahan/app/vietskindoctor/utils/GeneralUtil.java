package com.giahan.app.vietskindoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitActivity.ResponseType;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.activity.CreatePassCodeActivity;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.activity.PassCodeActivity;
import com.giahan.app.vietskindoctor.domains.Message;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.model.AccountKitBody;
import com.giahan.app.vietskindoctor.model.BaseResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pham.duc.nam on 23/05/2018.
 */

public class GeneralUtil {
    public static int REQUEST_CODE = 999;
    public static void setBackgroundColor(Context context, View view, int position) {
        if (position % 2 == 0) {
            view.setBackgroundColor(context.getResources().getColor(R.color.bg_item_gray));
        } else if (position % 2 == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    public static String toJSon(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T fromJSon(Class<T> clazz, String json) {
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<Message> sortMessage(List<Message> messageList) {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }

        Collections.sort(messageList, (o1, o2) -> Objects.requireNonNull(
                DateUtils.getDate(o1.getCreatedAt())).compareTo
                (DateUtils.getDate(o2.getCreatedAt())));
        return messageList;
    }

    public static List<Session> sortSession(List<Session> list){
        if (list == null) {
            list = new ArrayList<>();
        }

        Collections.sort(list, (o1, o2) -> Objects.requireNonNull(DateUtils.getDate(o2.getLastMessageAt()))
                .compareTo(DateUtils.getDate(o1.getLastMessageAt())));
        return list;
    }

    public static boolean checkInternet(Context context) {
        ConnectionDetector cd = new ConnectionDetector(context);
        return cd.isConnectingToInternet();
    }

    public static void registerEventBus(Object obj) {
        if (!EventBus.getDefault().isRegistered(obj)) {
            EventBus.getDefault().register(obj);
        }
    }

//    public static void goToFirstLogin(Activity activity) {
//        DialogUtils.showDialogFirstLogin(activity, new DialogUtils.onListenerPhoneNumber() {
//            @Override
//            public void onListen(String phoneNum) {
//                if(isValidPhoneNum(phoneNum)){
//                    GeneralUtil.goToLogin(activity, phoneNum);
//                }else {
//                    DialogUtils.showDialogOneChoice(activity, false, true, activity.getString(R.string.error_phone_number)
//                            ,activity.getString(R.string.close), view ->{
//                                DialogUtils.hideAlert();
//                            });
//                }
//            }
//        });
//    }

    public static void goToLogin(Activity activity, String phoneNum) {
        Intent intent = new Intent(activity, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE, ResponseType.TOKEN);
        configurationBuilder.setInitialPhoneNumber(new PhoneNumber("+84", phoneNum.substring(1)));
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static void onActivityResult(Activity activity, PrefHelper_ prefHelper_, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null) {
                Toast.makeText(activity, "" + result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT)
                        .show();
                return;
            } else if (result.wasCancelled()) {
                Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                prefHelper_.isHasPasscode().put(true);
                callToLogin(activity, prefHelper_, result.getAccessToken().getToken());
            }
        }
    }

    public static void goToFirstLogin(Activity activity) {
        DialogUtils.showDialogFirstLogin(activity, new DialogUtils.onListenerPhoneNumber() {
            @Override
            public void onListen(String phoneNum) {
                if(isValidPhoneNum(phoneNum)){
                    GeneralUtil.goToLogin(activity, phoneNum);
                }else {
                    DialogUtils.showDialogOneChoice(activity, false, true, activity.getString(R.string.error_phone_number)
                            ,activity.getString(R.string.close), view ->{
                                DialogUtils.hideAlert();
                            });
                }
            }
        });
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

    public static void callToLogin(Activity activity, PrefHelper_ prefHelper_, String token) {
        AccountKitBody accountKitBody = new AccountKitBody();
        accountKitBody.setCode(token);
        accountKitBody.setIsMobile(1);
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, activity).loginAccountKit(accountKitBody);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(final Call<UserInfoResponse> call, final Response<UserInfoResponse> response) {
                if(response.errorBody() != null)
                {
                    BaseResponse baseResponse = Toolbox.gson().fromJson(response.errorBody().charStream(), BaseResponse.class);
                    if(baseResponse.getErrorCode().equals("10")){
                        DialogUtils.showDialogOneChoice(activity, true, false, activity.getString(R.string.msg_phone_error1),activity.getString(R.string.close)
                                ,view ->{
                                    prefHelper_.isHasPasscode().put(false);
                                    DialogUtils.hideAlert();
                                });
                    }
                }

                if (response != null && response.body() != null && response.errorBody() == null) {
                    UserInfoResponse userInfoResponse = response.body();
                    if (userInfoResponse != null) {
                        userInfoResponse.setAgainData(response.body());
                        prefHelper_.user().put(new Gson().toJson(userInfoResponse));
                        prefHelper_.token().put(response.headers().get("Access-Token"));
                    }

                    if (!TextUtils.isEmpty(userInfoResponse.getPassCode())){
                        prefHelper_.isLogged().put(true);
                        prefHelper_.isHasPasscode().put(true);
                        prefHelper_.isBackground().put(false);
                        activity.startActivity(new Intent(activity, PassCodeActivity.class ));
                        activity.finish();
//                        EventBus.getDefault().post(new MessageEvent());
                    }else {
                        prefHelper_.isHasPasscode().put(false);
                        showFirstPasscode(activity);
                    }
                }
            }

            @Override
            public void onFailure(final Call<UserInfoResponse> call, final Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showFirstPasscode(Activity activity){
        Intent intent = new Intent(activity, CreatePassCodeActivity.class);
        activity.startActivity(intent);
    }

    public static void showPasscodeActivity(Activity activity, PrefHelper_ prefHelper_){
        if (prefHelper_.isBackground().get()) {
            prefHelper_.isBackground().put(false);
            if (prefHelper_.isLogged().get()) {
                activity.startActivity(new Intent(activity, PassCodeActivity.class));
            }else {
                return;
            }
        }
    }

    public static void autoMovingText(EditText edt1, EditText edt2,EditText edt3, EditText edt4,EditText edt5, EditText edt6){
        edt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });
        edt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });

        edt1.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt2.requestFocus();
                        }
                    },10);
                }
            }
        });
        edt2.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt3.requestFocus();
                        }
                    },10);
                }else {
                    edt1.requestFocus();
                }
            }
        });
        edt3.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt4.requestFocus();
                        }
                    },10);
                }else {
                    edt2.requestFocus();
                }
            }
        });
        edt4.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt5.requestFocus();
                        }
                    },10);
                }else {
                    edt3.requestFocus();
                }
            }
        });
        edt5.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt6.requestFocus();
                        }
                    },10);
                }else {
                    edt4.requestFocus();
                }
            }
        });
        edt6.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0){
                    edt6.requestFocus();
                }
            }
        });
    }

}
