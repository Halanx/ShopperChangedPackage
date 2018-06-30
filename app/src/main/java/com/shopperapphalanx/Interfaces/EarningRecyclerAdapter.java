package com.shopperapphalanx.Interfaces;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 6/30/2017.
 */

public class EarningRecyclerAdapter extends RecyclerView.Adapter<EarningRecyclerAdapter.EarningBaseHolder> {
    private List<BatchInfo> arrayList;

    public EarningRecyclerAdapter(List<BatchInfo> arrayList1) {

        this.arrayList = arrayList1;
        }

    public class EarningBaseHolder extends RecyclerView.ViewHolder {

        TextView batchNo, earn, orderNo;

        public EarningBaseHolder(final View itemView) {
            super(itemView);

            batchNo = (TextView) itemView.findViewById(R.id.batchno);
            earn = (TextView) itemView.findViewById(R.id.rs_earned);
            orderNo = (TextView) itemView.findViewById(R.id.no_orders);
        }
    }
    @Override
    public EarningRecyclerAdapter.EarningBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_card,parent,false);
        EarningRecyclerAdapter.EarningBaseHolder productObjectHolder = new EarningRecyclerAdapter.EarningBaseHolder(view);
        return productObjectHolder;
    }
    @Override
    public void onBindViewHolder(EarningRecyclerAdapter.EarningBaseHolder holder, int position) {
        holder.batchNo.setText(String.valueOf(arrayList.get(position).getId()));
        holder.earn.setText(String.valueOf(arrayList.get(position).getEarnings()));
    }
    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }
}
