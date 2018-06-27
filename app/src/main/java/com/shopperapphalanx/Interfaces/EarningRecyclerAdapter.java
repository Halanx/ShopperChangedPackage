package com.shopperapphalanx.Interfaces;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopperapphalanx.R;

import java.util.ArrayList;

/**
 * Created by Dell on 6/30/2017.
 */

public class EarningRecyclerAdapter extends RecyclerView.Adapter<EarningRecyclerAdapter.EarningBaseHolder> {
    private ArrayList<EarningObject> arrayList;

    public EarningRecyclerAdapter(ArrayList<EarningObject> arrayList1) {
        arrayList  =new ArrayList<>();
        Log.d("list", String.valueOf(arrayList1.get(0)));
        for(int i=arrayList1.size()-1;i>=0;i--){
            this.arrayList.add(arrayList1.get(i));
        }
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
        holder.batchNo.setText(String.valueOf(arrayList.get(position).getBatchNo()));
        holder.earn.setText(String.valueOf(arrayList.get(position).getIncome()));
        holder.orderNo.setText(String.valueOf(arrayList.get(position).getNoOfOrders()));
    }
    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }
}
