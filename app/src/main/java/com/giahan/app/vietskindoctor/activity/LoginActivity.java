package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import butterknife.BindView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.FbModelBody;
import com.giahan.app.vietskindoctor.model.GoogleModelBody;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.ChangeEvent;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.FragNavController;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NamVT on 5/23/2018.
 */

public class LoginActivity extends BaseActivity implements FragNavController.RootFragmentListener{

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

    //Fb
    CallbackManager callbackManager;

    private void initDb() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDb();
        boolean isChange = getIntent().getBooleanExtra(Constant.CHANGE_TAB, false);
        if(isChange){
            EventBus.getDefault().post(new ChangeEvent());
        }
//        Toolbox.getKeyHashFb(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

                        loginFb();
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.type(large)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }

    @OnClick(R.id.btn_cancel)
    void cancel() {
        finish();
    }

    @OnClick(R.id.btn_login_fb)
    void startLoginFb() {
        loginButton.performClick();

        loginButton.setPressed(true);

        loginButton.invalidate();

        loginButton.registerCallback(callbackManager, mCallBack);

        loginButton.setPressed(false);

        loginButton.invalidate();
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
        signIn();
    }

    @OnClick(R.id.btn_cancel)
    void onCancel(){
        finish();
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
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showAlertDialog("Thông báo", "Đăng nhập bằng Google không thành công!");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loginFb(){
        fbModelBody = new FbModelBody(fbAccessToken, fbUserId, email, username, FirebaseInstanceId.getInstance().getToken(), Constant.OS);
        showLoad();
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, this).loginFb(fbModelBody);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                Log.e("api LoginFb", "onResponse:  -----> "+response.message());
                hideLoading();
                checkCodeShowDialog(response.code());
                if(response.body() != null){
                    if(Toolbox.isEmpty(response.body().getAvatarAcc())){
                        response.body().setAvatarAcc(profilePicUrl);
                    }
                    pref.isLoginFb().put(true);
                    pref.token().put(response.body().getAccessToken());
                    pref.user().put(Toolbox.gson().toJson(response.body()));

                    EventBus.getDefault().post(new ChangeEvent());
                    finish();
//                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
//                        @Override
//                        public void onCompleted(GraphResponse graphResponse) {
//
//                            LoginManager.getInstance().logOut();
//
//                        }
//                    }).executeAsync();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                hideLoading();
                showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
            }
        });
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        VietSkinApplication.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = VietSkinApplication.getmAuth().getCurrentUser();
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
        Intent signInIntent = VietSkinApplication.getmGoogleSignInClient(this).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        VietSkinApplication.getmAuth().signOut();

        // Google sign out
        VietSkinApplication.getmGoogleSignInClient(this).signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        VietSkinApplication.getmAuth().signOut();

        // Google revoke access
        VietSkinApplication.getmGoogleSignInClient(this).revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        profilePicUrl = "";
        if (user != null) {
            profilePicUrl = user.getPhotoUrl()== null ? "" : user.getPhotoUrl().toString();
            user.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                Log.d(TAG, "GetTokenResult result = " + idToken);
                                // Send token to your backend via HTTPS
                                // ...
                                googleModelBody = new GoogleModelBody(idToken, FirebaseInstanceId.getInstance().getToken(), Constant.OS);
                                loginGoogle();
                            } else {
                                showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                            }
                        }
                    });
        }
    }

    private void loginGoogle(){
        showLoad();
        Call<UserInfoResponse> call = RequestHelper.getRequest(false, this).loginGoogle(googleModelBody);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                Log.e("api LoginGoogle", "onResponse:  -----> "+response.message());
                hideLoading();
                checkCodeShowDialog(response.code());
                if(response.body() != null){
                    if(Toolbox.isEmpty(response.body().getAvatarAcc())){
                        response.body().setAvatarAcc(profilePicUrl);
                    }
                    pref.isLoginFb().put(false);
                    pref.token().put(response.body().getAccessToken());
                    pref.user().put(Toolbox.gson().toJson(response.body()));

                    EventBus.getDefault().post(new ChangeEvent());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                hideLoading();
                showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Fragment getRootFragment(int index) {
        return null;
    }
}
