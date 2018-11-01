package com.giahan.app.vietskindoctor.screens.yeucau;

import static com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment.TAG_WAIT;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.ListRequestResult;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.model.event.MessageEvent;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.yeucau.YeuCauAdapter.OnClickOpenSessionListener;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeuCauFragment extends BaseFragment implements OnClickOpenSessionListener{

    @BindView(R.id.cardViewWait)
    CardView cardViewWait;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.rvListSessionWait)
    RecyclerView rvListSessionWait;
    @BindView(R.id.lnSignIn)
    LinearLayout lnSignIn;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    private List<Session> mListWait = new ArrayList<>();
//    private ListSessionAdapter mAdapterWait;
    private YeuCauAdapter mAdapter;

    @Override
    public void onClickSession(final Session session) {
        mPref.currentRequest().put(GeneralUtil.toJSon(session));
        getMainActivity().pushFragment(new ChiTietYeuCauFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yeu_cau;
    }

    @Override
    protected void createView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        clearData(true);
    }

    private void setupView() {
        String mToken = mPref.token().get();
        swipeContainer.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(mToken)) {
            setupList();
            getData();
            onSwipeRefresh();
        }else {
            hideLoading();
        }
    }

    private void setupList(){
        mAdapter = new YeuCauAdapter(mActivity, mListWait);
        mAdapter.setOpenSessionListener(this);
        setupList(rvListSessionWait, mAdapter);
//        mAdapterWait.setCancelSessionListener(this);
    }

    private void setupList(RecyclerView recyclerView, YeuCauAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
//        adapter.setOpenSessionListener(this);
    }

    private void getData(){
        Call<ListSessionResult> call = RequestHelper.getRequest(false, getActivity()).getListSessionWait(TAG_WAIT);
        call.enqueue(new Callback<ListSessionResult>() {
            @Override
            public void onResponse(Call<ListSessionResult> call,
                    Response<ListSessionResult> response) {
                hideLoading();
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                mListWait.clear();
                for (int i = 0; i < response.body().getDsessions().size(); i++) {
                    Session session = response.body().getDsessions().get(i);
                    switch (response.body().getDsessions().get(i).getStatus()){
                        case TAG_WAIT:
                            mListWait.add(session);
                            break;
                    }
                }
                tvEmpty.setVisibility(mListWait.size() == 0 ? View.VISIBLE : View.GONE);
                cardViewWait.setVisibility(mListWait.size() == 0 ? View.GONE : View.VISIBLE);
                mAdapter.notifyDataSetChanged();
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
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.color_default));
    }

    private void resetData(){
        mListWait.clear();
        getData();
    }

    void clearData(boolean isShow) {
        if (isShow) {
            showLoading();
            mListWait.clear();
            cardViewWait.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
        }
        setupView();
        nsv.scrollTo(0, 0);
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
            getMainActivity().addBadge(mListWait.size());
        }
    }
}
