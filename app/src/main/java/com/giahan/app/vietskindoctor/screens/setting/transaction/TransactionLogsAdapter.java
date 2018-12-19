package com.giahan.app.vietskindoctor.screens.setting.transaction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.ReadMessageBody;
import com.giahan.app.vietskindoctor.domains.ReadMessageResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.domains.Transaction;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionLogsAdapter extends RecyclerView.Adapter<TransactionLogsAdapter.MyViewHolder> implements SingleTransactionAdaper.OnClickOpenTransactionListener{

    private Map<String, List<Transaction>> mListTransByDate;
    private List<String> dateList;
    private Context context;
    private TransactionOnClickListener mTransactionOnClickListener;

    public void setmTransactionOnClickListener(TransactionOnClickListener mTransactionOnClickListener) {
        this.mTransactionOnClickListener = mTransactionOnClickListener;
    }

    public TransactionLogsAdapter(Map<String, List<Transaction>> mListTransByDate, List<String> dateList, Context context) {
        this.mListTransByDate = mListTransByDate;
        this.dateList = dateList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_by_date,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String date =dateList.get(position);
        int totalMoneyIn = 0;
        int totalMoneyOut = 0;
        for(Transaction t : mListTransByDate.get(date)){
            if(t.getTransType().equals("1")){
                totalMoneyIn += (Integer.parseInt(t.getPostMoney()) - Integer.parseInt(t.getPreMoney()));
            }else {
                totalMoneyOut += (Integer.parseInt(t.getPostMoney()) - Integer.parseInt(t.getPreMoney()));
            }
        }
        holder.tvMoneyIn.setText(context.getString(R.string.title_money_default4, Toolbox.formatMoney(String.valueOf(totalMoneyIn))));
        holder.tvMoneyOut.setText("-" + context.getString(R.string.title_money_default4, Toolbox.formatMoney(String.valueOf(totalMoneyOut))));
        holder.tvDate.setText(date);
        SingleTransactionAdaper adaper = new SingleTransactionAdaper(mListTransByDate.get(date), context);
        adaper.setmOnClickOpenTransactionListener(this);
        holder.rvTransaction.setLayoutManager(new LinearLayoutManager(context));
        holder.rvTransaction.setAdapter(adaper);
    }

    @Override
    public int getItemCount() {
        return mListTransByDate.size();
    }

    @Override
    public void onClickTransaction(String transactionId) {
        mTransactionOnClickListener.onTransClick(transactionId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvMoneyIn)
        TextView tvMoneyIn;
        @BindView(R.id.tvMoneyOut)
        TextView tvMoneyOut;
        @BindView(R.id.rvTransaction)
        RecyclerView rvTransaction;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface TransactionOnClickListener{
        void onTransClick(String sessionId);
    }
}
