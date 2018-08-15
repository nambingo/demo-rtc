package com.giahan.app.vietskindoctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment;
import com.giahan.app.vietskindoctor.screens.setting.CaiDatFragment;
import com.giahan.app.vietskindoctor.screens.yeucau.YeuCauFragment;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener;
import com.ncapdevi.fragnav.FragNavController.TransactionListener;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements RootFragmentListener, TransactionListener {

    //index for tabs in main screen
    public static final int INDEX_PHIEN = FragNavController.TAB1;

    public static final int INDEX_YEU_CAU = FragNavController.TAB2;

    public static final int INDEX_CAI_DAT = FragNavController.TAB3;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomView;

    //fragment manager
    private FragNavController mFragNavController;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setUpUnity();
        mFragNavController =
                FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(),
                        R.id.main_content)
                        .transactionListener(this)
                        .rootFragmentListener(this, 3)
                        .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
                        .switchController(
                                (index, transactionOptions) -> {
                                    mBottomView.setSelectedItemId(R.id.itemPhienTuVan);
                                })
                        .build();
        mBottomView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.itemPhienTuVan:
                    onPhienClick();
                    break;
                case R.id.itemNotify:
                    onNotifyClick();
                    break;
                case R.id.itemSetting:
                    onSettingClick();
                    break;
            }
            return true;
        });
        Toolbox.setStatusBarColor(this, getResources(), R.color.color_header_bar);
    }

    public void onSettingClick() {
        mFragNavController.switchTab(INDEX_CAI_DAT);
    }

    public void onNotifyClick() {
        mFragNavController.switchTab(INDEX_YEU_CAU);
    }

    public void onPhienClick() {
        mFragNavController.switchTab(INDEX_PHIEN);
    }

    public void selectPhien(){
        mBottomView.setSelectedItemId(R.id.itemPhienTuVan);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void createView() {
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_PHIEN:
                return new KhamOnlineFragment();
            case INDEX_YEU_CAU:
                return new YeuCauFragment();
            case INDEX_CAI_DAT:
                return new CaiDatFragment();
        }
        throw new IllegalStateException();
    }

    @Override
    public void onBackPressed() {
        if (!mFragNavController.popFragment()) {
            super.onBackPressed();
        }
    }

    public void initialize() {
        if (mFragNavController != null) {
            mFragNavController.clearStack();
        }
    }

    public void pushFragment(Fragment fragment) {
        if (mFragNavController != null) {
            mFragNavController.pushFragment(fragment);
        }
    }

    public void popFragment() {
        if (mFragNavController != null) {
            mFragNavController.popFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
//        mUnityPlayer.quit();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!EventBus.getDefault().isRegistered(MainActivity.this)) {
//            EventBus.getDefault().register(MainActivity.this);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onTabTransaction(@Nullable Fragment fragment, int i) {
        if (getSupportActionBar() != null && mFragNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mFragNavController.isRootFragment());
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mFragNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mFragNavController.isRootFragment());
        }
    }

    ;
}
