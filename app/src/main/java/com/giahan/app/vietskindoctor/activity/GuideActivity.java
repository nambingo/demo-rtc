package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.accountkit.Tracker;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.adapter.GuideAdapter;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.ViewPagerFixed;
import com.gw.swipeback.SwipeBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.pager_splash_screen)
    ViewPagerFixed viewPager;
    @BindView(R.id.rlBackground)
    RelativeLayout rlBackground;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.indicator_1)
    View mIndicator1;
    @BindView(R.id.indicator_2)
    View mIndicator2;
    @BindView(R.id.indicator_3)
    View mIndicator3;
    @BindView(R.id.indicator_4)
    View mIndicator4;
    @BindView(R.id.indicator_5)
    View mIndicator5;
    @BindView(R.id.indicator_6)
    View mIndicator6;

    public boolean isGuider = false;
    public PrefHelper_ pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            setTheme(R.style.Theme_Swipe_Back_NoActionBar);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setTheme(R.style.Theme_Swipe_Back_NoActionBar);
        }
        setContentView(R.layout.activity_swipe_guider);
        ButterKnife.bind(this);

        pref = new PrefHelper_(VietSkinDoctorApplication.getInstance());

        isGuider = getIntent().getBooleanExtra(Constant.OPEN_GUIDER, false);

        if (pref.isStartApp().get() || isGuider) {
            initControl();

            GuideAdapter mAdapter = new GuideAdapter(this);
            viewPager.setAdapter(mAdapter);
            viewPager.setCurrentItem(0);
        } else {
            onSkip();
        }
    }

    private void initControl() {
        rlBackground.setBackgroundResource(R.drawable.hdsd_1);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_RIGHT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {

            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                if (isEnd) {
                    onSkip();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_normal);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_1);
                        break;
                    case 1:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_normal);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_2);
                        break;
                    case 2:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_normal);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_3);
                        break;
                    case 3:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_normal);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_4);
                        break;
                    case 4:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_normal);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_5);
                        break;
                    case 5:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator3.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator4.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator5.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator6.setBackgroundResource(R.drawable.indicator_selected);
                        rlBackground.setBackgroundResource(R.drawable.hdsd_6);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @OnClick(R.id.tv_skip)
    void onSkip() {
        if (isGuider) {
            finish();
        } else {
            pref.isStartApp().put(false);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
