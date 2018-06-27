package com.shopperapphalanx.Interfaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shopperapphalanx.R;

import java.util.ArrayList;

/**
 * Created by Dell on 6/28/2017.
 */

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.DataObjectHolder> {
    String mobNo;

    RecyclerView.LayoutManager manager1;
    RecyclerView.Adapter adapter1;
    private ArrayList<DataObject> arrayList;
    String[] products = {"chips", "corns", "potato"};
    String[] price = {"100", "200", "300"};
    String[] instr = {"abc", "dedefsgdgdhdgdgdg", "sfdsfsf"};
    int noOfOrders = 3;

    public OrderRecyclerAdapter(ArrayList<DataObject> arrayList) {
        this.arrayList = arrayList;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView name, mobile, orderNo, seePro;
        Button sms, call;
        RecyclerView recyclerView1;
        boolean b = false;

        public DataObjectHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.mr_name);
            sms = (Button) itemView.findViewById(R.id.textsms);
            call = (Button) itemView.findViewById(R.id.callcust);
            mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            orderNo = (TextView) itemView.findViewById(R.id.orderno);
            recyclerView1 = (RecyclerView) itemView.findViewById(R.id.nested_recycler);
            seePro = (TextView) itemView.findViewById(R.id.see_pro);


            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("ashish", "inside");
                    Log.d("ashish", mobile.getText().toString());

                    itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.fromParts("sms",mobile.getText().toString(), null)));
                }
            });
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ashish", "inside");
                    Log.d("ashish", mobile.getText().toString());

                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",mobile.getText().toString(),null));
                    if (ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {


                        itemView.getContext().startActivity(intent);
                        return;
                    }

                }
            });



            manager1 = new LinearLayoutManager(itemView.getContext());
            recyclerView1.setLayoutManager(manager1);

            adapter1 = new ProductRecyclerAdapter(getProductData());
            recyclerView1.setAdapter(adapter1);


           seePro.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (b){
                       recyclerView1.setVisibility(View.GONE);
                       seePro.setText("Show Item");
                       b = !b;
                   }
                   else{
                       recyclerView1.setVisibility(View.VISIBLE);
                       seePro.setText("Hide Item");
                       b = !b;
                   }



               }
           });


        }

    }
    public ArrayList<ProductObject> getProductData(){

        ArrayList<ProductObject> prod = new ArrayList<>();

        for (int j = 0; j < noOfOrders; j++){
            ProductObject productObject = new ProductObject(products[j], price[j], instr[j] );
            prod.add(j, productObject);
        }
        return prod;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        mobNo = arrayList.get(position).getMobileNumber();
        holder.name.setText(arrayList.get(position).getName());
        holder.mobile.setText(mobNo);
        holder.orderNo.setText(arrayList.get(position).getOrderNo());


    }
    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }
}
