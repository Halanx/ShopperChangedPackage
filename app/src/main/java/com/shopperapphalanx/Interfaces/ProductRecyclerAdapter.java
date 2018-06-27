package com.shopperapphalanx.Interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopperapphalanx.R;

import java.util.ArrayList;

/**
 * Created by Dell on 6/29/2017.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductObjectHolder> {

    private ArrayList<ProductObject> arrayList;

    public ProductRecyclerAdapter(ArrayList<ProductObject> arrayList) {
        this.arrayList = arrayList;
    }

    public class ProductObjectHolder extends RecyclerView.ViewHolder {

        TextView proName, price, inst;

        public ProductObjectHolder(final View itemView) {
            super(itemView);

            proName = (TextView) itemView.findViewById(R.id.pro_name);
            price = (TextView) itemView.findViewById(R.id.price);
            inst = (TextView) itemView.findViewById(R.id.instr);
        }
    }
    @Override
    public ProductRecyclerAdapter.ProductObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler,parent,false);
        ProductRecyclerAdapter.ProductObjectHolder productObjectHolder = new ProductRecyclerAdapter.ProductObjectHolder(view);
        return productObjectHolder;
    }
    @Override
    public void onBindViewHolder(ProductRecyclerAdapter.ProductObjectHolder holder, int position) {
        holder.proName.setText(arrayList.get(position).getProductName());
        holder.price.setText(arrayList.get(position).getProductPrice());
        holder.inst.setText(arrayList.get(position).getInstructions());
    }
    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }
}
