package com.giahan.app.vietskindoctor.screens.setting;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.activity.GuideActivity;
import com.giahan.app.vietskindoctor.activity.LoginActivity;
import com.giahan.app.vietskindoctor.activity.PassCodeActivity;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.model.event.ChangeEvent;
import com.giahan.app.vietskindoctor.services.NetworkChanged;
import com.giahan.app.vietskindoctor.utils.Constant;

import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by pham.duc.nam
 */

public class CaiDatFragment extends BaseFragment {

    @BindView(R.id.ll_info)
    View llInfo;
    @BindView(R.id.ll_change_password)
    View llChangePass;
    @BindView(R.id.ll_recharge)
    View llRecharge;
    @BindView(R.id.ll_login)
    View llLogin;
    @BindView(R.id.ll_quide)
    View llGuide;
    @BindView(R.id.ll_table_fee)
    View llTableFee;
    @BindView(R.id.ll_logout)
    View llLogout;

    private boolean isShow = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_v2;
    }

    @Override
    protected void createView(View view) {
        initViews();
    }

    private void initViews() {
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        if (user != null) {
            llInfo.setVisibility(View.VISIBLE);
            llChangePass.setVisibility(View.VISIBLE);
            llRecharge.setVisibility(View.GONE);
            llGuide.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.VISIBLE);
            llTableFee.setVisibility(View.GONE);
            llLogin.setVisibility(View.GONE);
        } else {
            llInfo.setVisibility(View.GONE);
            llChangePass.setVisibility(View.GONE);
            llRecharge.setVisibility(View.GONE);
            llGuide.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.GONE);
            llTableFee.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @OnClick(R.id.ll_quide)
    void guide() {
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        intent.putExtra(Constant.OPEN_GUIDER, true);
        startActivity(intent);
        getMainActivity().overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
    }

    /*@OnClick(R.id.ll_table_fee)
    void openTableFee() {
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        intent.putExtra(Constant.OPEN_GUIDER, true);
        startActivity(intent);
        getMainActivity().overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
    }
*/
    @OnClick(R.id.ll_logout)
    void logout() {
        getMainActivity().showAlertConfirmDialog("Đăng xuất", "Bạn có chắc chắn muốn đăng xuất tài khoản?", new BaseActivity.OkListener() {
            @Override
            public void okClick() {
//                getMainActivity().logout(false);
                startActivity(new Intent(getMainActivity(), PassCodeActivity.class));
                getMainActivity().finish();
            }
        });
    }

    @OnClick(R.id.ll_info)
    void openInfoAccount() {
        getMainActivity().pushFragment(new InfoAccountV3Fragment());
    }

    @OnClick(R.id.ll_change_password)
    void onChagePass(){
        Intent intent = new Intent(getMainActivity(), PassCodeActivity.class);
        intent.putExtra("TAG_CHANGE_PASS", true);
        getMainActivity().startActivity(intent);
    }

    @OnClick(R.id.ll_recharge)
    void openRecharge() {
//        VietSkinDoctorApplication.setIsBackToSession(false);
//        getMainActivity().pushFragment(new RechargeFragment());
    }

    @OnClick(R.id.ll_login)
    void login() {
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.putExtra(Constant.CHANGE_TAB, false);
//        startActivity(intent);
//        getMainActivity().overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
        startActivity(new Intent(getActivity(), PassCodeActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeEvent(ChangeEvent event) {
        /* Do something */
        if (event != null) {
            initViews();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkChanged event) {
        showNetworkStateView();
    }

    private void showNetworkStateView() {
//        Crouton.cancelAllCroutons();
        boolean isConnected = GeneralUtil.checkInternet(getActivity());
        if (!isConnected) {
            Log.e("CD", "showNetworkStateView:  -----> OFF");
        }else {
            Log.e("CD", "showNetworkStateView:  -----> ON");
        }
    }
}
