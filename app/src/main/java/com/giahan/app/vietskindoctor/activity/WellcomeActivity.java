package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NamVT on 6/26/2018.
 */

public class WellcomeActivity extends AppCompatActivity {

    public PrefHelper_ pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_v2);
        ButterKnife.bind(this);
        setTranslucentModeOn();
        pref = new PrefHelper_(VietSkinApplication.getInstance());
        if (!pref.isStartApp().get()) {
            cont();
        }
    }

    public void setTranslucentModeOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @OnClick(R.id.btnCont)
    void cont() {
        pref.isStartApp().put(false);
        startActivity(new Intent(getApplicationContext(), ControlActivity.class));
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
}
