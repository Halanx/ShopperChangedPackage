package com.shopperapphalanx.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.POJO.BatchItem;
import com.shopperapphalanx.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by samarthgupta on 20/07/17.
 */

public class BatchUserAdapter extends RecyclerView.Adapter<BatchUserAdapter.BatchHolderUser> {

    BatchInfo batch;
    Context c;
    List<BatchItem> batchItemlist;
    static int orderNo = 1;


    public BatchUserAdapter(BatchInfo batch, Context ctx) {
        this.batch = batch;
        c = ctx;
        batchItemlist = batch.getBatchItems();

    }

    @Override
    public BatchHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BatchUserAdapter.BatchHolderUser(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_batch, parent, false), batch.getBatchItems());
    }

    @Override
    public void onBindViewHolder(final BatchHolderUser holder, int position) {

        holder.setIsRecyclable(false);

        if (position > 0) {
            Log.d("data",batchItemlist.get(0).getCartUser().getCustomer().getUser().getFirst_name());
            if (batchItemlist.get(position).getCartUser().getCustomer().getPhonenumber().equals(batchItemlist.get(position - 1).getCartUser().getCustomer().getPhonenumber())) {
                holder.user.setVisibility(View.GONE);
                holder.order.setVisibility(View.GONE);
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "Rs. "+ batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);

            } else {
                orderNo++;
                holder.tvOrderNo.setText(String.valueOf(orderNo));
                holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "Rs. "+ batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);
                if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                    holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
                }
                else{
                    holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

                }
            }

        }
        //For position = 0
        else {
            holder.tvOrderNo.setText(String.valueOf(orderNo));
            holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
            holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
            holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
            Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
            String pPrice = "Rs. "+ batchItemlist.get(position).getItem().getPrice();
            if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
            }
            else{
                holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

            }holder.tvProductPrice.setText(pPrice);
        }

        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cb.isChecked()){
                    holder.cb.setChecked(true);
                    holder.setIsRecyclable(false);
                }
                else{
                    holder.cb.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return batchItemlist.size();
    }

    public class BatchHolderUser extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvOrderNo,tvUserAddress;
        CardView store, user, product, order;
        TextView tvStoreName, tvUserName, tvProduct,tvProductQuantity,tvProductPrice;
        ImageView ivStoreNav, ivUserNav,ivProductImage;
        Button btUserCall;
        CheckBox cb;

        ImageView menu;

        List<BatchItem> items;

        public BatchHolderUser(View itemView, List<BatchItem> batchItems) {
            super(itemView);

            store = (CardView) itemView.findViewById(R.id.cv_store);
            user = (CardView) itemView.findViewById(R.id.cv_user);
            product = (CardView) itemView.findViewById(R.id.cv_products);
            order = (CardView) itemView.findViewById(R.id.cv_order);

            tvStoreName = (TextView) itemView.findViewById(R.id.tv_store_name);
            ivStoreNav = (ImageView) itemView.findViewById(R.id.bt_store_nav);
            tvOrderNo = (TextView) itemView.findViewById(R.id.tv_order_number);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            ivUserNav = (ImageView) itemView.findViewById(R.id.bt_user_nav);
            btUserCall = (Button) itemView.findViewById(R.id.bt_user_call);
            tvProduct = (TextView) itemView.findViewById(R.id.tv_product);
            tvProductQuantity = (TextView) itemView.findViewById(R.id.tv_product_quantity);
            ivProductImage = (ImageView) itemView.findViewById(R.id.iv_product_image);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            tvUserAddress = (TextView) itemView.findViewById(R.id.tv_user_address);
            cb = (CheckBox) itemView.findViewById(R.id.checkbox);
            items = batchItems;
            store.setVisibility(View.GONE);

            ivStoreNav.setOnClickListener(this);
            ivUserNav.setOnClickListener(this);
            btUserCall.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();

            switch (view.getId()) {

                case R.id.bt_user_nav:

                    Double userLat = items.get(pos).getOrderIdId().getLatitude();
                    Double userLong = items.get(pos).getOrderIdId().getLongitude();
                    navigate(userLat, userLong);

                    break;

                case R.id.bt_user_call:

                    String mobile = Long.toString(Long.parseLong(items.get(pos).getCartUser().getCustomer().getPhonenumber()));
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile, null));
                    if (ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {

                        c.startActivity(intent);
                    }


                    break;
            }

        }

        void navigate(Double latitude, Double longitude) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+String.valueOf(latitude)+","+String.valueOf(longitude));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            c.startActivity(mapIntent);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
