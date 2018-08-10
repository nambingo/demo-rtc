package com.giahan.app.vietskindoctor.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.model.event.ChangeFragEvent;
import com.giahan.app.vietskindoctor.model.event.TimeOutEvent;
import com.giahan.app.vietskindoctor.screens.danhsachbs.DanhSachBSFragment;
import com.giahan.app.vietskindoctor.screens.khamonline.KhamOnlineFragment;
import com.giahan.app.vietskindoctor.screens.setting.CaiDatFragment;
import com.giahan.app.vietskindoctor.screens.setting.RechargeFragment;
import com.giahan.app.vietskindoctor.utils.FragNavController;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity
        implements FragNavController.RootFragmentListener {

    //private static int[] Data = {28, 32};
    String mStringData = "";
    //index for tabs in main screen
    public static final int INDEX_VIET_SKIN = FragNavController.TAB_1;
    public static final int INDEX_DANH_SACH_BS = FragNavController.TAB_2;
    public static final int INDEX_KHAM_ONLINE = FragNavController.TAB_3;
    public static final int INDEX_CAI_DAT = FragNavController.TAB_4;

    //    public static UnityPlayer mUnityPlayer;
    //fragment manager
    public FragNavController mFragNavController;
    @BindView(R.id.nav)
    View bottomTab;

    @BindView(R.id.rlVietSkin)
    RelativeLayout rlVietSkin;

    @BindView(R.id.rlDanhSach)
    RelativeLayout rlDanhSach;

    @BindView(R.id.rlKhamOnline)
    RelativeLayout rlKhamOnline;

    @BindView(R.id.rlCaiDat)
    RelativeLayout rlCaiDat;

    @BindView(R.id.tvVietSkin)
    TextView tvVietSkin;

    @BindView(R.id.tvDanhSach)
    TextView tvDanhSach;

    @BindView(R.id.tvKhamOnline)
    TextView tvKhamOnline;

    @BindView(R.id.tvCaiDat)
    TextView tvCaiDat;

    @BindView(R.id.img_bac_si)
    ImageView imgBacSi;

    @BindView(R.id.img_home)
    ImageView imgHome;

    @BindView(R.id.img_kham_onl)
    ImageView imgKhamOnl;

    @BindView(R.id.img_cai_dat)
    ImageView imgCaiDat;

    private boolean isChange = false;
    private boolean isSaveSate = false;
    private int index = 3;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setUpUnity();
        index = getIntent().getIntExtra("index", INDEX_CAI_DAT);
        bottomTab.post(() -> {
            VietSkinApplication.getInstance().setBottomTabHeight(bottomTab.getHeight());
            switch (index) {
                case 1:
                    mFragNavController =
                            new FragNavController(savedInstanceState, getSupportFragmentManager(),
                                    R.id.main_content, MainActivity.this, 4, INDEX_DANH_SACH_BS);
                    break;
                case 2:
                    mFragNavController =
                            new FragNavController(savedInstanceState, getSupportFragmentManager(),
                                    R.id.main_content, MainActivity.this, 4, INDEX_KHAM_ONLINE);
                    break;
                case 3:
                    mFragNavController =
                            new FragNavController(savedInstanceState, getSupportFragmentManager(),
                                    R.id.main_content, MainActivity.this, 4, INDEX_CAI_DAT);
                    break;
            }
            selectItem(index);
        });
        Toolbox.setStatusBarColor(this, getResources(), R.color.color_header_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void createView() {
    }


    @OnClick({R.id.rlVietSkin, R.id.rlDanhSach, R.id.rlKhamOnline, R.id.rlCaiDat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlVietSkin:
                selectItem(INDEX_VIET_SKIN);
//                mFragNavController.switchTab(INDEX_VIET_SKIN);
                break;
            case R.id.rlDanhSach:
                pref.dataSearch().put("");
                pref.isListDoctor().put(true);
                selectItem(INDEX_DANH_SACH_BS);
                mFragNavController.switchTab(INDEX_DANH_SACH_BS);
                break;
            case R.id.rlKhamOnline:
                selectItem(INDEX_KHAM_ONLINE);
                mFragNavController.switchTab(INDEX_KHAM_ONLINE);
                break;
            case R.id.rlCaiDat:
                selectItem(INDEX_CAI_DAT);
                mFragNavController.switchTab(INDEX_CAI_DAT);
                break;
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
//            case INDEX_VIET_SKIN:
//                return new VietSkinFragment();
            case INDEX_DANH_SACH_BS:
                return new DanhSachBSFragment();
            case INDEX_KHAM_ONLINE:
                return new KhamOnlineFragment();
            case INDEX_CAI_DAT:
                return new CaiDatFragment();
        }
        throw new IllegalStateException();
    }

    @Override
    public void onBackPressed() {
        if (mFragNavController.canPop()) {
            mFragNavController.pop(R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            super.onBackPressed();
        }
    }

    public void pushFragment(Fragment fragment) {
        if (mFragNavController != null) {
            mFragNavController.push(fragment, R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

    public void pushFragmentNoAnim(Fragment fragment) {
        if (mFragNavController != null) {
            mFragNavController.push(fragment, 0, 0);
        }
    }

    public void popFragment() {
        if (mFragNavController != null) {
            mFragNavController.pop(R.anim.enter_from_left, R.anim.exit_to_right);
        }
    }

    public void initialize() {
        if (mFragNavController != null) {
            mFragNavController.clearStack();
        }
    }

    public void pushFragmentQ1(Fragment fragment) {
        if (mFragNavController != null) {
            mFragNavController.push(fragment, 0, 0);
        }
    }

    public void popFragmentNoAnim() {
        if (mFragNavController != null) {
            mFragNavController.pop(0, 0);
        }
    }

    public void goToRechargeScreen() {
        if (mFragNavController != null) {
            selectItem(INDEX_CAI_DAT);
            mFragNavController.switchTab(INDEX_CAI_DAT);
            if (mFragNavController.getCurrentFrag().getTag() != null && !mFragNavController.getCurrentFrag().getTag().contains("RechargeFragment")) {
                pushFragment(new RechargeFragment());
            }
        }
    }

    public void goToSessionScreen() {
        if (mFragNavController != null) {
            selectItem(INDEX_DANH_SACH_BS);
            mFragNavController.switchTab(INDEX_DANH_SACH_BS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isSaveSate = true;
        if (mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }

    void openControlSceen() {
//        Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
//        intent.putExtra("fromMain", true);
//        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    private void selectItem(int INDEX) {
        tvVietSkin.setEnabled(true);

        tvKhamOnline.setEnabled(true);
        tvCaiDat.setEnabled(true);

        switch (INDEX) {
            case INDEX_VIET_SKIN:
//                tvVietSkin.setEnabled(false);
//                tvVietSkin.setSelected(true);
//                tvDanhSach.setSelected(false);
//                tvKhamOnline.setSelected(false);
//                tvCaiDat.setSelected(false);

//                tvVietSkin.setTextColor(getResources().getColor(R.color.color_default));
//                tvDanhSach.setTextColor(getResources().getColor(R.color.color_default2));
//                tvKhamOnline.setTextColor(getResources().getColor(R.color.color_default2));
//                tvCaiDat.setTextColor(getResources().getColor(R.color.color_default2));
//                imgHome.setImageResource(R.drawable.home3);
//                imgBacSi.setImageResource(R.drawable.bac_si);
//                imgKhamOnl.setImageResource(R.drawable.kham_onl);
//                imgCaiDat.setImageResource(R.drawable.cai_dat);

                openControlSceen();
                break;
            case INDEX_DANH_SACH_BS:
                tvDanhSach.setEnabled(false);
                tvVietSkin.setSelected(false);
                tvDanhSach.setSelected(true);
                tvKhamOnline.setSelected(false);
                tvCaiDat.setSelected(false);

                tvVietSkin.setTextColor(getResources().getColor(R.color.color_default2));
                tvDanhSach.setTextColor(getResources().getColor(R.color.color_default));
                tvKhamOnline.setTextColor(getResources().getColor(R.color.color_default2));
                tvCaiDat.setTextColor(getResources().getColor(R.color.color_default2));
                imgHome.setImageResource(R.drawable.home);
                imgBacSi.setImageResource(R.drawable.bac_si2);
                imgKhamOnl.setImageResource(R.drawable.kham_onl);
                imgCaiDat.setImageResource(R.drawable.cai_dat);
                break;
            case INDEX_KHAM_ONLINE:
                tvKhamOnline.setEnabled(false);
                tvVietSkin.setSelected(false);
                tvDanhSach.setSelected(false);
                tvKhamOnline.setSelected(true);
                tvCaiDat.setSelected(false);

                tvVietSkin.setTextColor(getResources().getColor(R.color.color_default2));
                tvDanhSach.setTextColor(getResources().getColor(R.color.color_default2));
                tvKhamOnline.setTextColor(getResources().getColor(R.color.color_default));
                tvCaiDat.setTextColor(getResources().getColor(R.color.color_default2));
                imgHome.setImageResource(R.drawable.home);
                imgBacSi.setImageResource(R.drawable.bac_si);
                imgKhamOnl.setImageResource(R.drawable.kham_onl2);
                imgCaiDat.setImageResource(R.drawable.cai_dat);
                break;
            case INDEX_CAI_DAT:
                tvCaiDat.setEnabled(false);
                tvVietSkin.setSelected(false);
                tvDanhSach.setSelected(false);
                tvKhamOnline.setSelected(false);
                tvCaiDat.setSelected(true);

                tvVietSkin.setTextColor(getResources().getColor(R.color.color_default2));
                tvDanhSach.setTextColor(getResources().getColor(R.color.color_default2));
                tvKhamOnline.setTextColor(getResources().getColor(R.color.color_default2));
                tvCaiDat.setTextColor(getResources().getColor(R.color.color_default));
                imgHome.setImageResource(R.drawable.home);
                imgBacSi.setImageResource(R.drawable.bac_si);
                imgKhamOnl.setImageResource(R.drawable.kham_onl);
                imgCaiDat.setImageResource(R.drawable.cai_dat2);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isChange) {
            isChange = false;
            isSaveSate = false;
            switch (index) {
                case 1:
                    rlDanhSach.performClick();
                    break;
                case 2:
                    rlKhamOnline.performClick();
                    break;
                case 3:
                    rlCaiDat.performClick();
                    break;
            }
            rlVietSkin.performClick();
        }
//        mUnityPlayer.resume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
//        mUnityPlayer.quit();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mUnityPlayer.pause();
    }

    //    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mUnityPlayer.configurationChanged(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        mUnityPlayer.windowFocusChanged(hasFocus);
    }

//    // For some reason the multiple keyevent type is not supported by the ndk.
//    // Force event injection by overriding dispatchKeyEvent().
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_MULTIPLE) return mUnityPlayer.injectEvent(event);
//        return super.dispatchKeyEvent(event);
//    }
//
//    // Pass any events not handled by (unfocused) views straight to UnityPlayer
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }
//
//    /*API12*/
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        return mUnityPlayer.injectEvent(event);
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(MainActivity.this)) {
            EventBus.getDefault().register(MainActivity.this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setTab(int index) {
        selectItem(index);
        mFragNavController.switchTab(index);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeOutEvent(TimeOutEvent event) {
        /* Do something */
        if (event != null) {
            int count = mFragNavController.getSize();
            for (int i = 0; i < count; i++) {
                popFragment();
            }
        }
    }

    ;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragEvent(ChangeFragEvent event) {
        /* Do something */
        if (event != null) {
            index = event.getIndex();
            if (!isSaveSate) {
                switch (event.getIndex()) {
                    case 1:
                        rlDanhSach.performClick();
                        break;
                    case 2:
                        rlKhamOnline.performClick();
                        break;
                    case 3:
                        rlCaiDat.performClick();
                        break;
                }
            } else {
                isChange = true;
            }
        }
    }

    ;
}
