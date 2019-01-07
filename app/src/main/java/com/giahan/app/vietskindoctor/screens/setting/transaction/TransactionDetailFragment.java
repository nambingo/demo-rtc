package com.giahan.app.vietskindoctor.screens.setting.transaction;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.ReadMessageBody;
import com.giahan.app.vietskindoctor.domains.ReadMessageResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.domains.SessionInfoResult;
import com.giahan.app.vietskindoctor.domains.Transaction;
import com.giahan.app.vietskindoctor.domains.TransactionList;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.ChatFragment;
import com.giahan.app.vietskindoctor.screens.phienkham.KhamOnlineFragment;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailFragment extends BaseFragment implements TransactionLogsAdapter.TransactionOnClickListener {

    @BindView(R.id.rvTransByDate)
    RecyclerView rvTransByDate;

    @BindView(R.id.tvNoTransMsg)
    TextView tvnoTransMsg;

    private String name, dateAndTime, money;
    private List<Transaction> mTransList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private Map<String, List<Transaction>> mTransListByDate = new HashMap<>();

    private TransactionLogsAdapter adapter = new TransactionLogsAdapter(mTransListByDate, dateList, getActivity());

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transacstion_logs;
    }

    @Override
    protected void createView(View view) {
        showLoading();
        setupView();
        getData();

    }

    private void getData() {
        Call<TransactionList> call = RequestHelper.getRequest(false, getActivity()).getTransactionLogs();
        call.enqueue(new Callback<TransactionList>() {
            @Override
            public void onResponse(Call<TransactionList> call, Response<TransactionList> response) {
                hideLoading();
                if(response.body()!=null){
                    rvTransByDate.setVisibility(View.VISIBLE);
                    tvnoTransMsg.setVisibility(View.GONE);
                    mTransList = response.body().getmTransList();
                    if(mTransList.size()>0){
                        shortTransByDate(mTransList);
                    }else {
                        rvTransByDate.setVisibility(View.GONE);
                        tvnoTransMsg.setVisibility(View.VISIBLE);
                    }
                }else {
                    rvTransByDate.setVisibility(View.GONE);
                    tvnoTransMsg.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<TransactionList> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void shortTransByDate(List<Transaction> mTransList) {
        List<Transaction> transList = new ArrayList<>();
        String dateKey = mTransList.get(0).getTransTime().substring(0, mTransList.get(0).getTransTime().indexOf(" "));
        for(int i=0; i<mTransList.size(); i++){
            String date = mTransList.get(i).getTransTime().substring(0, mTransList.get(i).getTransTime().indexOf(" "));
            if(dateKey.equals(date)){
                transList.add(mTransList.get(i));
                mTransList.remove(mTransList.get(i));
                i=0;
            }
        }
        if (!dateList.contains(dateKey)) {
            dateList.add(dateKey);
        }
        if(!mTransListByDate.containsKey(dateKey)) {
            mTransListByDate.put(dateKey, transList);
        }else {
            mTransListByDate.get(dateKey).addAll(transList);
        }
        if(mTransList.size()==0){
            adapter.notifyDataSetChanged();
        }else {
            shortTransByDate(mTransList);
        }
    }

    private void setupView() {
        adapter = new TransactionLogsAdapter(mTransListByDate, dateList, getActivity());
        adapter.setmTransactionOnClickListener(this);
        rvTransByDate.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransByDate.setAdapter(adapter);
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        getMainActivity().popFragment();
    }

    @Override
    public void onTransClick(String sessionId) {
        getSessions(sessionId);
//        openChatSession(sessionId);
    }

    private void openChatSession(String sessionId) {
        KhamOnlineFragment khamOnlineFragment = new KhamOnlineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.EXT_MAIN_ID, sessionId);
        bundle.putBoolean(MainActivity.EXT_FROM_NOTIFICATION,true);
        khamOnlineFragment.setArguments(bundle);
        getMainActivity().pushFragment(khamOnlineFragment);
    }

    private void getSessions(String sessionId) {
        Call<SessionInfoResult> call = RequestHelper.getRequest(false, getActivity()).getSessionInfo(sessionId);
        call.enqueue(new Callback<SessionInfoResult>() {
            @Override
            public void onResponse(Call<SessionInfoResult> call,
                                   Response<SessionInfoResult> response) {
                if (response == null) {
                    return;
                }
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) {
                    return;
                }
                sendReadMessage(response.body());
            }

            @Override
            public void onFailure(Call<SessionInfoResult> call, Throwable t) {
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

    private void sendReadMessage(SessionInfoResult session) {
        ReadMessageBody readMessageBody = new ReadMessageBody(String.valueOf(session.getId()), DateUtils.convertCurrentTime());
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

    private void openSession(SessionInfoResult session) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAG_DSSESION_ID, String.valueOf(session.getId()));
        bundle.putString(Constant.TAG_PATIENT_ID, session.getPatientId());
        bundle.putString(Constant.TAG_REMAIN, DateUtils.convertDateWithTimeZone(session.getCreatedAt()));
        bundle.putString(Constant.TAG_PATIENT_NAME,
                TextUtils.isEmpty(session.getName()) ? session.getPatientName() : session.getName());
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getMainActivity().pushFragment(chatFragment);
    }
}
