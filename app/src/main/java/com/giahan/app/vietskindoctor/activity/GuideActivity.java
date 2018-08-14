package com.giahan.app.vietskindoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.adapter.GuideAdapter;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.gw.swipeback.SwipeBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NamVT on 6/2/2018.
 */

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.pager_splash_screen)
    ViewPager viewPager;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.indicator_1)
    View mIndicator1;
    @BindView(R.id.indicator_2)
    View mIndicator2;
    public boolean isGuider = false;
    public PrefHelper_ pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_guider);
        ButterKnife.bind(this);
        pref = new PrefHelper_(VietSkinDoctorApplication.getInstance());

        isGuider = getIntent().getBooleanExtra(Constant.OPEN_GUIDER, false);

        if(pref.isStartApp().get() || isGuider) {
            initControl();

            GuideAdapter mAdapter = new GuideAdapter(this);
            viewPager.setAdapter(mAdapter);
            viewPager.setCurrentItem(0);
        }else {
            onSkip();
        }
    }

    private void initControl() {
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_RIGHT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {

            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                if(isEnd) {
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
                        break;
                    case 1:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_normal);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_selected);
                        break;
                    default:
                        mIndicator1.setBackgroundResource(R.drawable.indicator_selected);
                        mIndicator2.setBackgroundResource(R.drawable.indicator_normal);
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
        if(isGuider){
            finish();
        }else {
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
}
