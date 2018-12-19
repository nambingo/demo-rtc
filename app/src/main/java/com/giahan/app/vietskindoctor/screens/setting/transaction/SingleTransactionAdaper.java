package com.giahan.app.vietskindoctor.screens.setting.transaction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Transaction;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleTransactionAdaper extends RecyclerView.Adapter<SingleTransactionAdaper.MyViewHolder> {

    private List<Transaction> mTransList;
    private Context context;

    private OnClickOpenTransactionListener mOnClickOpenTransactionListener;

    public SingleTransactionAdaper(List<Transaction> mTransList, Context context) {
        this.mTransList = mTransList;
        this.context = context;
    }

    public void setmOnClickOpenTransactionListener(OnClickOpenTransactionListener mOnClickOpenTransactionListener) {
        this.mOnClickOpenTransactionListener = mOnClickOpenTransactionListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction t = mTransList.get(position);
        holder.tvSessionID.setText(t.getReferenceId());

        holder.tvDateAndTime.setText(t.getTransTime());
        int money = Integer.parseInt(t.getPostMoney()) - Integer.parseInt(t.getPreMoney());
        if(t.getTransType().equals("1")){
            holder.lnTitleSession.setVisibility(View.VISIBLE);
            holder.lnTitleWithdraw.setVisibility(View.GONE);
            holder.tvMoney.setText(context.getString(R.string.title_money_default4, Toolbox.formatMoney(String.valueOf(money))));
            holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_default));

        }else {
            holder.lnTitleSession.setVisibility(View.GONE);
            holder.lnTitleWithdraw.setVisibility(View.VISIBLE);
            holder.tvMoney.setText("-" + context.getString(R.string.title_money_default4, Toolbox.formatMoney(String.valueOf(money))));
            holder.tvMoney.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.rlTransCOntainer.setOnClickListener(view ->{
            if(mOnClickOpenTransactionListener == null)return ;
            mOnClickOpenTransactionListener.onClickTransaction(t.getReferenceId());
        });
    }

    @Override
    public int getItemCount() {
        return mTransList.size();
    }

    public interface OnClickOpenTransactionListener{
        void onClickTransaction(String transactionId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvSessionID)
        TextView tvSessionID;
        @BindView(R.id.tvMoney)
        TextView tvMoney;
        @BindView(R.id.titleSession)
        LinearLayout lnTitleSession;
        @BindView(R.id.titleWithdraw)
        LinearLayout lnTitleWithdraw;
        @BindView(R.id.tvDateAndTime)
        TextView tvDateAndTime;
        @BindView(R.id.rlTransContaner)
        RelativeLayout rlTransCOntainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
