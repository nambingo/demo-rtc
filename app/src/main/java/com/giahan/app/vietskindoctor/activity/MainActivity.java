package com.giahan.app.vietskindoctor.activity;

import static com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment.TAG_WAIT;
import static com.giahan.app.vietskindoctor.utils.Constant.TAG_LOGIN_SOCKET;
import static com.giahan.app.vietskindoctor.utils.Constant.TAG_LOGOUT_SOCKET;
import static com.giahan.app.vietskindoctor.utils.Constant.TAG_RECEIVE_MESSAGE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.domains.ListRequestResult;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment;
import com.giahan.app.vietskindoctor.screens.setting.CaiDatFragment;
import com.giahan.app.vietskindoctor.screens.yeucau.YeuCauFragment;
import com.giahan.app.vietskindoctor.services.NetworkChanged;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener;
import com.ncapdevi.fragnav.FragNavController.TransactionListener;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;

import butterknife.BindView;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements RootFragmentListener, TransactionListener {

    //index for tabs in main screen
    public static final int INDEX_PHIEN = FragNavController.TAB1;

    public static final int INDEX_YEU_CAU = FragNavController.TAB2;

    public static final int INDEX_CAI_DAT = FragNavController.TAB3;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomView;

    private TextView tvNumber;

    private String mSessionId;
    private boolean isFromNitification = false;

    public boolean isGoToChatScreen = false;

    public boolean isGoToChatScreen() {
        return isGoToChatScreen;
    }

    public void setGoToChatScreen(final boolean goToChatScreen) {
        isGoToChatScreen = goToChatScreen;
    }

    //fragment manager
    private FragNavController mFragNavController;

    public static final String EXT_MAIN_ID = "extra_main_id";
    public static final String EXT_FROM_NOTIFICATION = "from_notification";


    public static Intent getIntent(Context context, String sessionID) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXT_MAIN_ID, sessionID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

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
        setupBadge();
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
        getCountRequest();



    }

    private void openChatFromNoti() {
        if(getIntent() == null){
            return;
        }
        mSessionId = getIntent().getStringExtra(EXT_MAIN_ID);
        if(mSessionId != null){
            isFromNitification = true;
        }
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

    public void selectPhien() {
        mFragNavController.switchTab(INDEX_PHIEN);
        mBottomView.setSelectedItemId(R.id.itemPhienTuVan);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void createView() {
    }

    private void setupBadge() {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) mBottomView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);
        tvNumber = badge.findViewById(R.id.tvNumber);

        itemView.addView(badge);
        if (GeneralUtil.checkInternet(VietSkinDoctorApplication.getInstance())) {
            tvNumber.setVisibility(View.VISIBLE);
        } else {
            tvNumber.setVisibility(View.GONE);
        }
    }

    private void getCountRequest() {

        Call<ListSessionResult> call = RequestHelper.getRequest(false, this).getListSessionWait(TAG_WAIT);
        call.enqueue(new Callback<ListSessionResult>() {
            @Override
            public void onResponse(final Call<ListSessionResult> call, final Response<ListSessionResult> response) {
                if (response == null) {
                    return;
                }
                checkCodeShowDialog(response.code());
                if (response.body() == null) {
                    return;
                }
                hideLoading();
                List<Session> mListWait = new ArrayList<>();
                for (int i = 0; i < response.body().getDsessions().size(); i++) {
                    Session session = response.body().getDsessions().get(i);
                    switch (response.body().getDsessions().get(i).getStatus()) {
                        case TAG_WAIT:
                            mListWait.add(session);
                            break;
                    }
                }
                addBadge(mListWait.size());
            }

            @Override
            public void onFailure(final Call<ListSessionResult> call, final Throwable t) {

            }
        });
    }

    public void addBadge(final int size) {
        if (!GeneralUtil.checkInternet(VietSkinDoctorApplication.getInstance())) {
            tvNumber.setVisibility(View.GONE);
        } else {
            tvNumber.setVisibility(size != 0 ? View.VISIBLE : View.GONE);
            tvNumber.setText(String.valueOf(size));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GeneralUtil.showPasscodeActivity(MainActivity.this, pref);
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_PHIEN:
                //Open Chat screen from notification
                openChatFromNoti();
                KhamOnlineFragment khamOnlineFragment = new KhamOnlineFragment();
                Bundle bundle = new Bundle();
                if(isFromNitification){
                    isFromNitification = false;
                    bundle.putString(EXT_MAIN_ID, mSessionId);
                    bundle.putBoolean(EXT_FROM_NOTIFICATION,true);
                }
                khamOnlineFragment.setArguments(bundle);
                return khamOnlineFragment;
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
            DialogUtils.showDialogTwoChoice(this, false, false, true, getString(R.string.ask_exit), "",
                    getString(R.string.cancel), getString(R.string.ok), view -> DialogUtils.hideAlert(),
                    view -> super.onBackPressed());
        }
    }

    public void initialize() {
        if (mFragNavController != null) {
            mFragNavController.clearStack();
        }
    }

    public void pushFragment(Fragment fragment) {
        if (mFragNavController != null) {
            mFragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder().allowStateLoss(true).build());
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
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
        if (event != null) {
            getCountRequest();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkChanged event) {
        showNetworkStateView();
    }

    private void showNetworkStateView() {
//        Crouton.cancelAllCroutons();
        boolean isConnected = GeneralUtil.checkInternet(VietSkinDoctorApplication.getInstance());
        if (!isConnected) {
            tvNumber.setVisibility(View.GONE);
        } else {
            getCountRequest();
            tvNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
                pref.isBackground().put(false);
            }
        }

    }
}
