package com.giahan.app.vietskindoctor.screens.phienkham;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.ReadMessageBody;
import com.giahan.app.vietskindoctor.domains.ReadMessageResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.ChatFragment;
import com.giahan.app.vietskindoctor.screens.phienkham.ListSessionAdapter.OnClickOpenSessionListener;
import com.giahan.app.vietskindoctor.services.NetworkChanged;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pham.duc.nam
 */

public class KhamOnlineFragment extends BaseFragment implements OnClickOpenSessionListener {

    public static final String TAG_WAIT = "waiting";

    public static final String TAG_ACCEPT = "accepted";

    public static final String TAG_COMPLETE = "completed";

    @BindView(R.id.rvListSession)
    RecyclerView rvListSession;

    @BindView(R.id.rvListSessionComplete)
    RecyclerView rvListSessionComplete;

    @BindView(R.id.lnSignIn)
    LinearLayout lnSignIn;

    @BindView(R.id.cardViewOnline)
    CardView cardViewOnline;

    @BindView(R.id.cardViewComplete)
    CardView cardViewComplete;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    @BindView(R.id.nsv)
    NestedScrollView nsv;

    private ListSessionAdapter mAdapterOnline;

    private ListSessionAdapter mAdapterComplete;

    private List<Session> mListOnline = new ArrayList<>();

    private List<Session> mListComplete = new ArrayList<>();

    private boolean isFromNotification = false;
    private String mSessionId;
    private Session mSessionFromNoti;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kham_online;
    }

    @Override
    protected void createView(View view) {
        Log.e("KhamOnlineFragment", "createView:  -----> "+getMainActivity().isGoToChatScreen());
    }

    private void setupView() {
        String mToken = mPref.token().get();
        swipeContainer.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(mToken)) {
            setupList();
            getExtraFromMain();
            getData();
            onSwipeRefresh();
        } else {
            hideLoading();
        }
    }

    private void getExtraFromMain() {
         Bundle bundle = this.getArguments();
         if (bundle == null) {
             return;
         }
         isFromNotification = bundle.getBoolean(MainActivity.EXT_FROM_NOTIFICATION);
         mSessionId = bundle.getString(MainActivity.EXT_MAIN_ID);
    }

    @Override
    public void onResume() {
        super.onResume();
        clearData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.getArguments().clear();
    }

    private void setupList() {
        mAdapterOnline = new ListSessionAdapter(mActivity, mListOnline);
        mAdapterComplete = new ListSessionAdapter(mActivity, mListComplete);
        setupList(rvListSession, mAdapterOnline);
        setupList(rvListSessionComplete, mAdapterComplete);
    }

    private void setupList(RecyclerView recyclerView, ListSessionAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOpenSessionListener(this);
    }

    private void getData() {
        Call<ListSessionResult> call = RequestHelper.getRequest(false, getActivity()).getListSession();
        call.enqueue(new Callback<ListSessionResult>() {
            @Override
            public void onResponse(Call<ListSessionResult> call,
                    Response<ListSessionResult> response) {
                if (response == null) {
                    return;
                }
                if(getMainActivity()!=null){
                    getMainActivity().checkCodeShowDialog(response.code());
                }
                if (response.body() == null) {
                    return;
                }
                hideLoading();
                mListOnline.clear();
                mListComplete.clear();
                for (int i = 0; i < response.body().getDsessions().size(); i++) {
                    Session session = response.body().getDsessions().get(i);
                    switch (response.body().getDsessions().get(i).getStatus()) {
                        case TAG_ACCEPT:
                            mListOnline.add(session);
                            break;
                        case TAG_COMPLETE:
                            mListComplete.add(session);
                            break;
                        case TAG_WAIT:

                    }
                }
                GeneralUtil.sortSession(mListOnline);
                GeneralUtil.sortSession(mListComplete);
                if (mListOnline.size() == 0 && mListComplete.size() ==0){
                    tvEmpty.setVisibility(View.VISIBLE);
                }else {
                    tvEmpty.setVisibility(View.GONE);
                }
                cardViewOnline.setVisibility(mListOnline.size() == 0 ? View.GONE : View.VISIBLE);
                cardViewComplete.setVisibility(mListComplete.size() == 0 ? View.GONE : View.VISIBLE);
                mAdapterOnline.notifyDataSetChanged();
                mAdapterComplete.notifyDataSetChanged();
                if(isFromNotification){
                    for(Session session: mListOnline){
                        if(session.getId().equals(mSessionId)){
                            mSessionFromNoti = session;
                        }
                    }

                    if(mSessionFromNoti != null){
                        openSession(mSessionFromNoti);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListSessionResult> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void onSwipeRefresh() {
        swipeContainer.setOnRefreshListener(() -> {
            resetData();
            swipeContainer.setRefreshing(false);
        });
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.color_default));
    }

    private void resetData() {
        mListOnline.clear();
        mListComplete.clear();
        getData();
    }

    void clearData(boolean isShow) {
        if (isShow) {
            showLoading();
            mListOnline.clear();
            mListComplete.clear();
            cardViewOnline.setVisibility(View.GONE);
            cardViewComplete.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
        }
        setupView();
        nsv.scrollTo(0, 0);
    }

    @Override
    public void onClickSession(Session session) {
        sendReadMessage(session);
    }

    private void sendReadMessage(Session session) {
        ReadMessageBody readMessageBody = new ReadMessageBody(session.getId(), DateUtils.convertCurrentTime());
        Call<ReadMessageResult> call = RequestHelper.getRequest(false, getActivity()).sendLastRead(readMessageBody);
        call.enqueue(new Callback<ReadMessageResult>() {
            @Override
            public void onResponse(final Call<ReadMessageResult> call, final Response<ReadMessageResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                hideLoading();
                openSession(session);
            }

            @Override
            public void onFailure(final Call<ReadMessageResult> call, final Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info),
                            getString(R.string.msg_alert_info));
                }
            }
        });

    }

    private void openSession(Session session) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_DSSESION_ID, session.getId());
        bundle.putString(Constant.TAG_PATIENT_ID, session.getPatientID());
        bundle.putString(Constant.TAG_REMAIN, session.getCreateAt());
        bundle.putString(Constant.TAG_PATIENT_NAME,
                TextUtils.isEmpty(session.getName()) ? session.getPatientName() : session.getName());
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getMainActivity().pushFragment(chatFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
        if (event != null) {
            clearData(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkChanged event) {
        showNetworkStateView();
    }

    private void showNetworkStateView() {
//        Crouton.cancelAllCroutons();
        boolean isConnected = GeneralUtil.checkInternet(VietSkinDoctorApplication.getInstance());
        if (isConnected) {
            resetData();
        }
    }
}
