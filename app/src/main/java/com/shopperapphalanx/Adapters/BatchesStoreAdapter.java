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
import android.widget.ImageView;
import android.widget.TextView;

import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.POJO.BatchItem;
import com.shopperapphalanx.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by samarthgupta on 19/07/17.
 */

public class BatchesStoreAdapter extends RecyclerView.Adapter<BatchesStoreAdapter.BatchesHolder> {

    BatchInfo batch;
    List<BatchItem> batchItemlist;
    Context c;

    public BatchesStoreAdapter(BatchInfo b, Context ctx) {

        this.batch = b;
        batchItemlist = batch.getBatchItems();
        c = ctx;
        Collections.sort(batchItemlist, new Comparator<BatchItem>() {
            @Override
            public int compare(BatchItem b1, BatchItem b2) {
                return new CompareToBuilder().append(b1.getItem().getRelatedStore().getId(), b2.getItem().getRelatedStore().getId()).
                        append(b1.cartUser.customer.getPhonenumber(),b2.cartUser.customer.getPhonenumber()).toComparison();
            }
        });

    }

    @Override
    public BatchesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BatchesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_batch, parent, false), batch.getBatchItems());
    }

    @Override
    public void onBindViewHolder(BatchesHolder holder, int position) {

        holder.setIsRecyclable(false);
        if (position != 0) {
            if ((batchItemlist.get(position).getItem().getRelatedStore().getStoreName().
                    equals(batchItemlist.get(position - 1).getItem().getRelatedStore().getStoreName()))
                    && batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name().equals(batchItemlist.get(position - 1).getCartUser().getCustomer().getUser().getFirst_name())) {
                holder.store.setVisibility(View.GONE);
                holder.user.setVisibility(View.GONE);
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);

                String pPrice = "Rs. " + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);

            } else if ((batchItemlist.get(position).getItem().getRelatedStore().getStoreName().
                    equals(batchItemlist.get(position - 1).getItem().getRelatedStore().getStoreName()))
                    && !(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name().equals(batchItemlist.get(position - 1).getCartUser().getCustomer().getUser().getFirst_name()))) {
                holder.store.setVisibility(View.GONE);
                holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "Rs. " + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);
                if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                    holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
                }
                else{
                    holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

                }

            } else {
                holder.tvStoreName.setText(batchItemlist.get(position).getItem().getRelatedStore().getStoreName());
                holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "Rs. " + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);
                if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                    holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
                }
                else{
                    holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

                }
            }
        } else {
            holder.tvStoreName.setText(batchItemlist.get(position).getItem().getRelatedStore().getStoreName());
            holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
            holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
            holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
            Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
            String pPrice = "Rs. " + batchItemlist.get(position).getItem().getPrice();
            holder.tvProductPrice.setText(pPrice);
            Log.d("data", String.valueOf(batchItemlist.get(position).getOrderIdId()));
            if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
            }
            else{
                holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

            }

        }


    }

    @Override
    public int getItemCount() {
        return batchItemlist.size();
    }

    public class BatchesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvOrderNo, tvUserAddress;
        CardView store, user, product, order;
        TextView tvStoreName, tvUserName, tvProduct, tvProductQuantity, tvProductPrice;
        ImageView ivStoreNav, ivUserNav, ivProductImage;
        Button btUserCall;

        List<BatchItem> items;

        public BatchesHolder(View itemView, List<BatchItem> batchItems) {
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

            items = batchItems;
            ivStoreNav.setOnClickListener(this);
            ivUserNav.setOnClickListener(this);
            btUserCall.setOnClickListener(this);

            order.setVisibility(View.GONE);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            switch (view.getId()) {
                case R.id.bt_store_nav:

                    Double lat = items.get(pos).getItem().getRelatedStore().getLatitude();
                    Double lon = items.get(pos).getItem().getRelatedStore().getLongitude();
                    navigate(lat, lon);

                    break;

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
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + String.valueOf(latitude) + "," + String.valueOf(longitude));
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