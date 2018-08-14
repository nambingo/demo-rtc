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
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.yeucau.YeuCauAdapter.OnClickOpenSessionListener;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import java.util.ArrayList;
import java.util.List;
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
        mListWait.clear();
        cardViewWait.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
        setupView();
        nsv.scrollTo(0, 0);
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
        Call<ListRequestResult> call = RequestHelper.getRequest(false, getActivity()).getListSessionRequest();
        call.enqueue(new Callback<ListRequestResult>() {
            @Override
            public void onResponse(Call<ListRequestResult> call,
                    Response<ListRequestResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                hideLoading();
                mListWait.clear();
                for (int i = 0; i < response.body().getRequests().size(); i++) {
                    Session session = response.body().getRequests().get(i);
                    switch (response.body().getRequests().get(i).getStatus()){
                        case TAG_WAIT:
                            mListWait.add(session);
                            break;
                    }
                }
                cardViewWait.setVisibility(mListWait.size() == 0 ? View.GONE : View.VISIBLE);
                tvEmpty.setVisibility(mListWait.size() == 0 ? View.VISIBLE : View.GONE);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListRequestResult> call, Throwable t) {
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
        mListWait.clear();
        getData();
    }
}
