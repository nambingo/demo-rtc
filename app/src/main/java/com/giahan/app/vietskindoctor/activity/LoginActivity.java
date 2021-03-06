package com.giahan.app.vietskindoctor.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.BaseResponse;
import com.giahan.app.vietskindoctor.model.FbModelBody;
import com.giahan.app.vietskindoctor.model.GoogleModelBody;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.ChangeEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 6/28/2018.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_button)
    LoginButton loginButton;

    private static final String TAG = "GoogleActivity";

    private static final int RC_SIGN_IN = 9001;

    private FbModelBody fbModelBody;

    private GoogleModelBody googleModelBody;

    private String fbAccessToken = "";

    private String fbUserId = "";

    private String email = "";

    private String username = "";

    private String profilePicUrl = "";

    private UserInfoResponse userInfoResponse;

    //Fb
    CallbackManager callbackManager;

    private void initDb() {
    }

    public void showPopupUpdateInfo() {
        VietSkinDoctorApplication.setIsShowDialogUpdate(true);
        UserInfoResponse user = UserInfoResponse.getUser(pref);
        pref.isUpdateFirst().put(false);
        DialogUtils.showDialogAlertUpdate(this, user, new DialogUtils.onListener() {
            @Override
            public void onListen(String name, String date, String gender, String phone, Dialog dialog) {
                update(name, date, gender, phone, dialog);
            }

            @Override
            public void onDismiss(Dialog dialog) {
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDb();
        boolean isChange = getIntent().getBooleanExtra(Constant.CHANGE_TAB, false);
        if (isChange) {
            EventBus.getDefault().post(new ChangeEvent());
        }
        setTranslucentModeOn();
        hashFromSHA1("E7:4B:2C:A5:C8:BA:23:F8:40:C0:6B:68:7B:61:C4:90:A4:FE:AD:E7");
//        getKey();
//        Toolbox.getKeyHashFb(this);
    }

    private void getKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.giahan.app.vietskindoctor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void hashFromSHA1(String sha1) {
        Log.e("ControlActivity", "hashFromSHA1:  -----> ");
        String[] arr = sha1.split(":");
        byte[] byteArr = new byte[arr.length];

        for (int i = 0; i < arr.length; i++) {
            byteArr[i] = Integer.decode("0x" + arr[i]).byteValue();
        }

        Log.e("hash222--- : ", Base64.encodeToString(byteArr, Base64.NO_WRAP));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_v2;
    }

    @Override
    protected void createView() {
        accessFacebook();
    }

    private void accessFacebook() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
    }

    private void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v(TAG, response.toString());
                        try {
                            username = object.getString("name");
                        } catch (JSONException e) {
                            username = "";
                        }
                        try {
                            fbUserId = object.getString("id");
                        } catch (JSONException e) {
                            fbUserId = "";
                        }
                        try {
                            email = object.getString("email");
                        } catch (JSONException e) {
                            email = "";
                        }
                        try {
                            profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                        } catch (JSONException e) {
                            profilePicUrl = "";
                        }
                        pref.isLoginFb().put(true);
                        loginFb();
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.type(large)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
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
                        return;
                    }
                } else {
                    if (response.errorBody() != null) {
                        BaseResponse baseResponse = Toolbox.gson()
                                .fromJson(response.errorBody().charStream(), BaseResponse.class);
                        if (baseResponse.getErrorCode() != null && baseResponse.getErrorCode().equals("4")) {
                            showAlertDialog(getString(R.string.title_alert_info),
                                    getString(R.string.msg_phone_error));
                            return;
                        }
                    }
                }
                checkCodeShowDialog(response.code());
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
//                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    void updateEmail() {
        hideKeyboard();
        showLoad();
        InfoUpdateBody infoUpdateBody = new InfoUpdateBody(email);
        Call<UpdateInfoResponse> call = RequestHelper.getRequest(false, this).updateInfo(infoUpdateBody);
        call.enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                hideLoading();
                checkCodeShowDialog(response.code());
                if (response.body() != null && response.body().getSuccess() == 1) {
                    pref.token().put(response.headers().get("Access-Token"));
                    if (Toolbox.isEmpty(userInfoResponse.getAvatarAcc())) {
                        userInfoResponse.setAvatarAcc(profilePicUrl);
                    }
                    pref.user().put(Toolbox.gson().toJson(userInfoResponse));
                    pref.isLogged().put(true);

                    EventBus.getDefault().post(new ChangeEvent());

                    if (Toolbox.isEmpty(userInfoResponse.getName()) || Toolbox
                            .isEmpty(userInfoResponse.getBirthdate()) ||
                            Toolbox.isEmpty(userInfoResponse.getGender()) || Toolbox
                            .isEmpty(userInfoResponse.getPhone())) {
                        showPopupUpdateInfo();
                    } else {
                        finish();
                    }
                } else {
                    pref.token().put("");
                    pref.user().put("");
                    pref.isLogged().put(false);
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
//                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
                pref.token().put("");
                pref.user().put("");
                pref.isLogged().put(false);
                LoginManager.getInstance().logOut();
            }
        });
    }

    @OnClick(R.id.btnSkip)
    void cancel() {
        finish();
    }

    @OnClick(R.id.btnRule)
    void rule() {

    }

    @OnClick(R.id.btnLoginFb)
    void startLoginFb() {
        if (Toolbox.isNetworkAvailable(this)) {
            if (pref.isLoginFb().get() && !pref.isLogged().get()) {
                pref.token().put("");
                pref.user().put("");
                pref.isLogged().put(false);
                LoginManager.getInstance().logOut();
            }
            loginButton.performClick();

            loginButton.setPressed(true);

            loginButton.invalidate();

            loginButton.registerCallback(callbackManager, mCallBack);

            loginButton.setPressed(false);

            loginButton.invalidate();
        } else {
            showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
        }
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            fbAccessToken = loginResult.getAccessToken().getToken();
            fbUserId = loginResult.getAccessToken().getUserId();
            getUserDetails(loginResult);
        }

        @Override
        public void onCancel() {
            showAlertDialog("Thông báo", "Đã hủy đăng nhập Facebook!");
        }

        @Override
        public void onError(FacebookException e) {
            showAlertDialog("Thông báo", "Đăng nhập bằng Facebook không thành công!");
        }
    };

    @OnClick(R.id.btn_login_google)
    void accessGoogle() {
        if (Toolbox.isNetworkAvailable(this)) {
            signIn();
        } else {
            showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
        }
    }

    @OnClick(R.id.btnSkip)
    void onCancel() {
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                // Google Sign In failed, update UI appropriately
                showAlertDialog("Thông báo", "Đăng nhập bằng Google không thành công!");

                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loginFb() {
        fbModelBody = new FbModelBody(fbAccessToken, fbUserId, email, username,
                FirebaseInstanceId.getInstance().getToken(), Constant.OS);
        showLoad();
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, this).loginFb(fbModelBody);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                hideLoading();
                checkCodeShowDialog(response.code());
                if (response.body() != null) {
                    pref.token().put(response.headers().get("Access-Token"));
                    userInfoResponse = response.body();
                    if (Toolbox.isEmpty(response.body().getEmail())) {
                        DialogUtils.showDialogAlertInputEmail(LoginActivity.this,
                                new DialogUtils.onListenerInputEmail() {
                                    @Override
                                    public void onListen(String emailInput, Dialog dialog) {
                                        dialog.dismiss();
                                        email = emailInput;
                                        updateEmail();
                                    }

                                    @Override
                                    public void onDismiss(Dialog dialog) {
                                        dialog.dismiss();
                                        pref.token().put("");
                                        pref.user().put("");
                                        pref.isLogged().put(false);
                                        LoginManager.getInstance().logOut();
                                    }
                                });
                    } else {
                        if (Toolbox.isEmpty(response.body().getAvatarAcc())) {
                            response.body().setAvatarAcc(profilePicUrl);
                        }
                        pref.token().put(response.body().getAccessToken());
                        pref.user().put(Toolbox.gson().toJson(response.body()));
                        pref.isLogged().put(true);

                        EventBus.getDefault().post(new ChangeEvent());

//                        if (Toolbox.isEmpty(response.body().getName()) || Toolbox.isEmpty(response.body().getBirthdate()) ||
//                                Toolbox.isEmpty(response.body().getGender()) || Toolbox.isEmpty(response.body().getPhone())) {
//                            showPopupUpdateInfo();
//                        } else {
//                            finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        }
                    }
//                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
//                        @Override
//                        public void onCompleted(GraphResponse graphResponse) {
//
//                            LoginManager.getInstance().logOut();
//
//                        }
//                    }).executeAsync();
                } else {
                    pref.token().put("");
                    pref.user().put("");
                    pref.isLogged().put(false);
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                hideLoading();
                pref.token().put("");
                pref.user().put("");
                pref.isLogged().put(false);
                LoginManager.getInstance().logOut();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
//                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        VietSkinDoctorApplication.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = VietSkinDoctorApplication.getmAuth().getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = VietSkinDoctorApplication.getmGoogleSignInClient(this).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void updateUI(FirebaseUser user) {
        profilePicUrl = "";
        if (user != null) {
            profilePicUrl = user.getPhotoUrl() == null ? "" : user.getPhotoUrl().toString();
            user.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                // ...
                                googleModelBody = new GoogleModelBody(idToken,
                                        FirebaseInstanceId.getInstance().getToken(), Constant.OS);
                                loginGoogle();
                            } else {
//                                showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                            }
                        }
                    });
        }
    }

    private void loginGoogle() {
        showLoad();
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, this).loginGoogle(googleModelBody);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                hideLoading();
                checkCodeShowDialog(response.code());
                if (response.body() != null) {
                    if (response.body().getActivated().equals("0")) {
                        DialogUtils.showDialogOneChoice(LoginActivity.this, true, false,
                                getString(R.string.tai_khoan_chua_active), getString(R.string.ok), view -> {
                                    DialogUtils.hideAlert();
                                    signOut();
                                    return;
                                });
                    } else {
                        if (Toolbox.isEmpty(response.body().getAvatarAcc())) {
                            response.body().setAvatarAcc(profilePicUrl);
                        }
                        pref.isLoginFb().put(false);
                        pref.token().put(response.body().getAccessToken());
                        pref.user().put(Toolbox.gson().toJson(response.body()));
                        pref.isLogged().put(true);

                        EventBus.getDefault().post(new ChangeEvent());
//                    if (Toolbox.isEmpty(response.body().getName()) || Toolbox.isEmpty(response.body().getBirthdate()) ||
//                            Toolbox.isEmpty(response.body().getGender()) || Toolbox.isEmpty(response.body().getPhone())) {
//                        showPopupUpdateInfo();
//                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
//                    }
                    }
                } else {
                    signOut();
                    if (response.errorBody() != null) {
                        try {
                            BaseResponse baseResponse = Toolbox.gson()
                                    .fromJson(response.errorBody().charStream(), BaseResponse.class);
                            if (baseResponse.getError() != null) {
                                showAlertDialog(getString(R.string.title_alert_info), baseResponse.getError());
                            }
                        } catch (Exception e) {
                            showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
                signOut();
            }
        });
    }

    private void signOut() {
        // Firebase sign out
        VietSkinDoctorApplication.getmAuth().signOut();

        // Google sign out
        VietSkinDoctorApplication.getmGoogleSignInClient(this).signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pref.token().put("");
                        pref.user().put("");
                        pref.isLogged().put(false);
                    }
                });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void checkWrongAccoutType(Response<UserInfoResponse> response) {
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            String message = jsonObject.getString("error");
            String errorCode = jsonObject.getString("errorCode");
            if (errorCode.equals("10")) {
                DialogUtils.showDialogOneChoice(LoginActivity.this, true, false, message,
                        getString(R.string.close), view -> {
                            DialogUtils.hideAlert();
                            signOut();
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
