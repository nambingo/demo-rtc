package com.giahan.app.vietskindoctor.base;

import static com.giahan.app.vietskindoctor.utils.Constant.TAG_LOGIN_SOCKET;
import static com.giahan.app.vietskindoctor.utils.Constant.TAG_LOGOUT_SOCKET;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.facebook.login.LoginManager;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.LoginActivity;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.activity.PassCodeActivity;
import com.giahan.app.vietskindoctor.model.event.TimeOutEvent;
import com.giahan.app.vietskindoctor.services.NetworkChanged;
import com.giahan.app.vietskindoctor.services.NetworkListenerReceiver;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.ProgressDialogUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pham.duc.nam
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialogUtil mProgressDialogUtil;
    public PrefHelper_ pref;
    private boolean isShow = false;
    private boolean isBack = true;
    private NetworkListenerReceiver networkStateReceiver = new NetworkListenerReceiver();
    private Socket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        GeneralUtil.registerEventBus(this);
        ButterKnife.bind(this);
        pref = new PrefHelper_(VietSkinDoctorApplication.getInstance());
        createView();
        mProgressDialogUtil = new ProgressDialogUtil(this);
        if (!TextUtils.isEmpty(pref.token().get())) {
            setupSocket();
        }
    }

    private void setupSocket() {
        {
            try {
                mSocket = IO.socket(Constant.URL_SOCKET);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_token", pref.token().get());
            mSocket.emit(TAG_LOGIN_SOCKET, jsonObject);
        } catch (JSONException ignored) {
            Toast.makeText(this, "Socket error!", Toast.LENGTH_SHORT).show();
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void setTranslucentModeOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        showNetworkStateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onDestroy() {
        if (mSocket != null) {
            mSocket.disconnect();
//        mSocket.off(TAG_RECEIVE_MESSAGE, OnNewMessage);
            mSocket.emit(TAG_LOGOUT_SOCKET);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void showNetworkStateView() {
//        Crouton.cancelAllCroutons();
        boolean isConnected = GeneralUtil.checkInternet(this);
        isOnline(isConnected);
    }

    public boolean isOnline(boolean isOnline){
        return isOnline;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkChanged event) {
        showNetworkStateView();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void createView();

    public void showActivity(Class t) {
        Intent intent = new Intent(this, t);
        startActivity(intent);
    }

    public void showActivity(Class t, Bundle bundle) {
        Intent intent = new Intent(this, t);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void makeToast(String msg) {
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
        }
    }

    public void makeToast(int msgId) {
        String msg = getString(msgId);
        makeToast(msg);
    }

    public void logout(boolean back) {
        isBack = back;
        if (pref.isLoginFb().get()) {
            disconnectFromFacebook();
        } else {
            signOut();
        }
    }

    public void disconnectFromFacebook() {
        showLoad();
        pref.token().put("");
        pref.user().put("");
        pref.isLogged().put(false);
        LoginManager.getInstance().logOut();
        goControlScreen();
    }

    private void signOut() {
        Log.e("BaseActivity", "signOut: 1 -----> ");
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
                        goControlScreen();
                    }
                });
    }

    private void goControlScreen() {
        if (isBack) {
            EventBus.getDefault().post(new TimeOutEvent());
        }
        Intent intent = new Intent(getApplicationContext(), PassCodeActivity.class);
        intent.putExtra(Constant.CHANGE_TAB, true);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
        hideLoading();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                hideLoading();
//            }
//        }, SplashActivity.TIME_LOADING);
    }

    public void hideLoading() {
//        if (mProgressDialogUtil != null && mProgressDialogUtil.isShowing()) {
//            mProgressDialogUtil.dismiss();
//        }
        mProgressDialogUtil.hideProgress();
    }

    public void showLoad() {
        if (mProgressDialogUtil != null && !mProgressDialogUtil.isShowing()) {
            mProgressDialogUtil.show(false);
        }
    }

    public boolean isValid() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                ? !isFinishing() && !isDestroyed()
                : !isFinishing();
    }

    public void checkCodeShowDialog(int code) {
        if (code == 401 && !isShow) {
            isShow = true;
            showAlertBackDialog("Thông báo", getString(R.string.session_timed_out_content),
                    () -> {
                        logout(true);
                        isShow = false;
                    });
        }
    }

    public void showAlertDialog(String title, String content) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                })
                .show();
    }

    public void showAlertBackDialog(String title, String content, OkListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                        listener.okClick();
                    }
                })
                .show();
    }

    public void showAlertConfirmDialog(String title, String content, OkListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.okClick();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }

    public interface OkListener {
        void okClick();
    }

    //auto hide keyboard when not focus widget EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (checkHideKeyboard(v, ev)) {
            int[] scrcoords = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
//                hideKeyboard();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean checkHideKeyboard(View v, MotionEvent ev) {
        return v != null &&
                checkEv(ev) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.");
    }

    private boolean checkEv(MotionEvent ev) {
        return (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE);
    }

    public void showToastMsg(String message) {
        try {
            hideKeyboard();
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                    .setAction("Đóng", null);
            snackbar.setActionTextColor(Color.parseColor("#FDB043"));
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
