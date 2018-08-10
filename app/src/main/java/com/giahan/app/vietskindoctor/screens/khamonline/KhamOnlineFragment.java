package com.giahan.app.vietskindoctor.screens.khamonline;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.activity.LoginV2Activity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.CancelBody;
import com.giahan.app.vietskindoctor.domains.CancelResult;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.ChatFragment;
import com.giahan.app.vietskindoctor.screens.danhsachbs.DanhSachBSFragment;
import com.giahan.app.vietskindoctor.screens.khamonline.ListSessionAdapter.OnClickOpenSessionListener;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.giahan.app.vietskindoctor.screens.khamonline.ListSessionAdapter.*;

/**
 * Created by pham.duc.nam
 */

public class KhamOnlineFragment extends BaseFragment implements OnClickOpenSessionListener, OnClickCancelSessionLestener{
    public static final String TAG_WAIT = "waiting";
    public static final String TAG_ACCEPT = "accepted";
    public static final String TAG_COMPLETE = "completed";
    public static final String TAG_REJECT = "rejected";
    public static final String TAG_CANCEL = "cancelled";

    @BindView(R.id.btnImgAdd)
    LinearLayout lnAddSession;
    @BindView(R.id.tvDangNhap)
    TextView tvDangNhap;
    @BindView(R.id.rvListSession)
    RecyclerView rvListSession;
    @BindView(R.id.rvListSessionWait)
    RecyclerView rvListSessionWait;
    @BindView(R.id.rvListSessionComplete)
    RecyclerView rvListSessionComplete;
    @BindView(R.id.lnNotSignIn)
    LinearLayout lnNotSignIn;
    @BindView(R.id.lnSignIn)
    LinearLayout lnSignIn;
    @BindView(R.id.cardViewWait)
    CardView cardViewWait;
    @BindView(R.id.cardViewOnline)
    CardView cardViewOnline;
    @BindView(R.id.cardViewComplete)
    CardView cardViewComplete;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.lnBack)
    LinearLayout lnBack;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    private ListSessionAdapter mAdapterOnline;
    private ListSessionAdapter mAdapterWait;
    private ListSessionAdapter mAdapterComplete;

    private List<Session> mListOnline = new ArrayList<>();
    private List<Session> mListComplete = new ArrayList<>();
    private List<Session> mListWait = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kham_online;
    }

    @Override
    protected void createView(View view) {
    }

    @OnClick(R.id.lnBack)
    public void onBack() {
        getMainActivity().onBackPressed();
    }

    @OnClick(R.id.btnImgAdd)
    public void onClickAdd() {
        getMainActivity().pushFragment(new CreateSessionFragment());
    }

    @OnClick(R.id.tvDangNhap)
    public void onLogin(){
        showActivity(LoginV2Activity.class);
    }

    private void setupView() {
        String mToken = mPref.token().get();
        lnNotSignIn.setVisibility(TextUtils.isEmpty(mToken) ? View.VISIBLE : View.GONE);
        swipeContainer.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        lnAddSession.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(mToken)) {
            setupList();
            getData();
            onSwipeRefresh();
        }else {
            hideLoading();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        mListOnline.clear();
        mListWait.clear();
        mListComplete.clear();
        cardViewWait.setVisibility(View.GONE);
        cardViewOnline.setVisibility(View.GONE);
        cardViewComplete.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
        setupView();
        nsv.scrollTo(0, 0);
    }

    private void setupList(){
        mAdapterOnline = new ListSessionAdapter(mActivity, mListOnline);
        mAdapterWait = new ListSessionAdapter(mActivity, mListWait);
        mAdapterComplete = new ListSessionAdapter(mActivity, mListComplete);
        setupList(rvListSession, mAdapterOnline);
        setupList(rvListSessionWait, mAdapterWait);
        setupList(rvListSessionComplete, mAdapterComplete);
        mAdapterWait.setCancelSessionListener(this);
    }

    private void setupList(RecyclerView recyclerView, ListSessionAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOpenSessionListener(this);
    }

    private void getData(){
        Call<ListSessionResult> call = RequestHelper.getRequest(false, getActivity()).getListSession();
        call.enqueue(new Callback<ListSessionResult>() {
            @Override
            public void onResponse(Call<ListSessionResult> call,
                    Response<ListSessionResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                hideLoading();
                mListOnline.clear();
                mListWait.clear();
                mListComplete.clear();
                for (int i = 0; i < response.body().getDsessions().size(); i++) {
                    Session session = response.body().getDsessions().get(i);
                    switch (response.body().getDsessions().get(i).getStatus()){
                        case TAG_WAIT:
                            mListWait.add(session);
                            break;
                        case TAG_ACCEPT:
                            mListOnline.add(session);
                            break;
                        case TAG_COMPLETE:
                            mListComplete.add(session);
                            break;
                    }
                }
                cardViewWait.setVisibility(mListWait.size() == 0 ? View.GONE : View.VISIBLE);
                cardViewOnline.setVisibility(mListOnline.size() == 0 ? View.GONE : View.VISIBLE);
                cardViewComplete.setVisibility(mListComplete.size() == 0 ? View.GONE : View.VISIBLE);
                tvEmpty.setVisibility(mListWait.size() == 0 && mListOnline.size() == 0 &&
                        mListComplete.size() == 0 ? View.VISIBLE : View.GONE);
                mAdapterOnline.notifyDataSetChanged();
                mAdapterWait.notifyDataSetChanged();
                mAdapterComplete.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListSessionResult> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void onSwipeRefresh(){
        swipeContainer.setOnRefreshListener(() -> {
            resetData();
            swipeContainer.setRefreshing(false);
        });
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.color_background));
    }

    private void resetData(){
        mListOnline.clear();
        mListWait.clear();
        mListComplete.clear();
        getData();
    }

    @Override
    public void onClickSession(Session session) {
        if (session.getStatus().equals(TAG_WAIT)) {
//            DialogUtils.showDialogTwoChoice(getActivity(), false, false, true,
//                    getString(R.string.title_alert_info), getString(R.string.phien_kham_chua_co_bac_si),
//                    getString(R.string.chon_bac_si_khac), getString(R.string.tiep_tuc_cho_bac_si), view -> {
//                        DialogUtils.hideAlert();
//                        getMainActivity().pushFragment(new DanhSachBSFragment());
//                    }, view -> {
//                        DialogUtils.hideAlert();
//                    });
        } else {
            openSession(session);
        }
    }

    private void openSession(Session session ){
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_DSSESION_ID, session.getId());
        bundle.putString(Constant.TAG_DOCTER_NAME, session.getDoctorName());
        bundle.putString(Constant.TAG_DOCTER_AVATAR, session.getDoctorAvatarUrl());
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getMainActivity().pushFragment(chatFragment);
    }

    @Override
    public void onClickCancel(Session session) {
        DialogUtils.showDialogTwoChoice(getActivity(), true, false, true,
                getString(R.string.cancel_ask), getString(R.string.cancel_title), getString(R.string.yes),
                getString(R.string.no),
                view -> {
                    DialogUtils.hideAlert();
                    onCancelSession(session);
                },
                view -> {
                    DialogUtils.hideAlert();
                });
    }

    private void onCancelSession(Session session){
        CancelBody cancelBody = new CancelBody(session.getId());
        Call<CancelResult> call = RequestHelper.getRequest(false,
                getActivity()).cancelSession(cancelBody);
        call.enqueue(new Callback<CancelResult>() {
            @Override
            public void onResponse(Call<CancelResult> call, Response<CancelResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                hideLoading();
                if (!response.body().getSuccess().equals("1")) return;
                DialogUtils.showDialogOneChoice(getActivity(), false, true,
                        getString(R.string.cancel_success),getString(R.string.close),view -> {
                            DialogUtils.hideAlert();
                            resetData();
                        });
            }

            @Override
            public void onFailure(Call<CancelResult> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }
}
