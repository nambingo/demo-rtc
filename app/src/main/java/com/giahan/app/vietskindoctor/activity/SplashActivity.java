package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;

import com.giahan.app.vietskindoctor.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.base.BaseActivity;

/**
 * Created by pham.duc.nam
 */

public class SplashActivity extends BaseActivity {
    public static final int TIME_LOADING = 3000;

    private Handler handler = null;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_intro_v2);
//        ButterKnife.bind(this);
//        handler = new Handler();
//        handler.postDelayed(this::skip, TIME_LOADING);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intro_v2;
    }

    @Override
    protected void createView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        handler = new Handler();
        handler.postDelayed(this::skip, TIME_LOADING);
    }

    @OnClick(R.id.btnSkip)
    void skip() {
        handler.removeCallbacksAndMessages(null);
        if (TextUtils.isEmpty(pref.token().get())) {
            startActivity(new Intent(getApplicationContext(), LoginV2Activity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
}
