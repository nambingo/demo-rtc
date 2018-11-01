package com.giahan.app.vietskindoctor;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.gw.swipeback.tools.WxSwipeBackActivityManager;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pham.duc.nam
 */

public class VietSkinDoctorApplication extends MultiDexApplication {
    private static VietSkinDoctorApplication INSTANCE;
    private static boolean isShowDialogUpdate;
    private static boolean isOpenDetailScreen;
    private static boolean isBackToSession;
    private static GoogleSignInClient mGoogleSignInClient;
    // [START declare_auth]
    private static FirebaseAuth mAuth;
    // [END declare_auth]
    private int bottomTabHeight = 168;

    @Override
    public void onCreate(){
        super.onCreate();
        INSTANCE = this;
        isShowDialogUpdate = false;
        isOpenDetailScreen = false;
        isBackToSession = false;
        setupFabric();
        FirebaseApp.initializeApp(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        WxSwipeBackActivityManager.getInstance().init(this);
        MultiDex.install(this);
    }

    private void setupFabric(){
        Fabric fabric = new Fabric.Builder(this).kits(new Crashlytics()).debuggable(true).build();
        Fabric.with(fabric);
    }

    public static GoogleSignInClient getmGoogleSignInClient(Context context) {
        if(mGoogleSignInClient != null) return mGoogleSignInClient;
        else {
            // [START config_signin]
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            // [END config_signin]

            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
            return mGoogleSignInClient;
        }
    }

    public static FirebaseAuth getmAuth() {
        if(mAuth != null) return mAuth;
        else {
            return FirebaseAuth.getInstance();
        }
    }

    public static VietSkinDoctorApplication getInstance() {
        return INSTANCE;
    }

    public int getBottomTabHeight() {
        return bottomTabHeight;
    }

    public void setBottomTabHeight(int bottomTabHeight) {
        this.bottomTabHeight = bottomTabHeight;
    }

    public static boolean isIsShowDialogUpdate() {
        return isShowDialogUpdate;
    }

    public static void setIsShowDialogUpdate(boolean isShowDialogUpdate) {
        VietSkinDoctorApplication.isShowDialogUpdate = isShowDialogUpdate;
    }

    public static boolean isIsBackToSession() {
        return isBackToSession;
    }

    public static void setIsBackToSession(boolean isBackToSession) {
        VietSkinDoctorApplication.isBackToSession = isBackToSession;
    }

    public static boolean isIsOpenDetailScreen() {
        return isOpenDetailScreen;
    }

    public static void setIsOpenDetailScreen(boolean isOpenDetailScreen) {
        VietSkinDoctorApplication.isOpenDetailScreen = isOpenDetailScreen;
    }
}
