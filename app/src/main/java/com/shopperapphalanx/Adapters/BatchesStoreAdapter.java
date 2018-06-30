package com.shopperapphalanx.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shopperapphalanx.Interfaces.DataInterface;
import com.shopperapphalanx.POJO.BatchInfo;
import com.shopperapphalanx.POJO.BatchItem;
import com.shopperapphalanx.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.json.JSONObject;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shopperapphalanx.Activities.OnWayUserActivity.cod;
import static com.shopperapphalanx.Activities.OnWayUserActivity.order;
import static com.shopperapphalanx.GlobalClass.djangoBaseUrl;

/**
 * Created by samarthgupta on 19/07/17.
 */

public class BatchesStoreAdapter extends RecyclerView.Adapter<BatchesStoreAdapter.BatchesHolder> {

    BatchInfo batch;
    List<BatchItem> batchItemlist;
    Context c;
    private Dialog dialAddMoney;

    private ListView listView;
    private String report_type;
    private Button report;

    String[] Reports = {"Wrong price","Wrong image","Other wrong details"};

    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;

    public BatchesStoreAdapter(BatchInfo b, Context applicationContext) {

        this.batch = b;
        batchItemlist = batch.getBatchItems();
        c = applicationContext;
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
    public void onBindViewHolder(final BatchesHolder holder, final int position) {

        holder.setIsRecyclable(false);
        if (position != 0)
        {
            if ((batchItemlist.get(position).getItem().getRelatedStore().getStoreName().
                    equals(batchItemlist.get(position - 1).getItem().getRelatedStore().getStoreName()))
                    && batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name().equals(batchItemlist.get(position - 1).getCartUser().getCustomer().getUser().getFirst_name()))
            {
                Log.d("enteres","enteres");

                holder.store.setVisibility(View.GONE);
                holder.user.setVisibility(View.GONE);
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);

                String pPrice = "₹" + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);

            } else if ((batchItemlist.get(position).getItem().getRelatedStore().getStoreName().
                    equals(batchItemlist.get(position - 1).getItem().getRelatedStore().getStoreName()))
                    && !(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name().equals(batchItemlist.get(position - 1).getCartUser().getCustomer().getUser().getFirst_name()))) {
                Log.d("enteres","enteres12");
                holder.store.setVisibility(View.GONE);
                holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "₹" + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);
                if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                    holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
                }
                else{
                    holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

                }

            } else {
                Log.d("enteres","enteres12345");

                holder.tvStoreName.setText(batchItemlist.get(position).getItem().getRelatedStore().getStoreName());
                holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
                holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
                holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
                Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
                String pPrice = "₹" + batchItemlist.get(position).getItem().getPrice();
                holder.tvProductPrice.setText(pPrice);
                if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                    holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
                }
                else{
                    holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

                }
            }
        }
        else
            {
            holder.tvStoreName.setText(batchItemlist.get(position).getItem().getRelatedStore().getStoreName());
            holder.tvUserName.setText(batchItemlist.get(position).getCartUser().getCustomer().getUser().getFirst_name());
            holder.tvProduct.setText(batchItemlist.get(position).getItem().getProductName());
            holder.tvProductQuantity.setText(String.valueOf(batchItemlist.get(position).getQuantity()));
            Picasso.with(c).load(batchItemlist.get(position).getItem().getProductImage()).into(holder.ivProductImage);
            String pPrice = "₹" + batchItemlist.get(position).getItem().getPrice();
            holder.tvProductPrice.setText(pPrice);
            Log.d("data", String.valueOf(batchItemlist.get(position).getOrderIdId()));
            if (!String.valueOf(batchItemlist.get(position).getOrderIdId()).equals("null")) {
                holder.tvUserAddress.setText(batchItemlist.get(position).getOrderIdId().getDeliveryAddress());
            }
            else{
                holder.tvUserAddress.setText(batchItemlist.get(position).getSubscriptionid().getDeliveryAddress());

            }

        }

        holder.report.setVisibility(View.VISIBLE);
        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(c, holder.report);
                //inflating menu from xml resource
                popup.inflate(R.menu.report);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.report:
                                //handle menu1 click

                                dialAddMoney = new Dialog(c);
                                dialAddMoney.setContentView(R.layout.newsfeeddialogue);
                                report = dialAddMoney.findViewById(R.id.report);
                                Button cancel = dialAddMoney.findViewById(R.id.cancel);
                                ListView listView = dialAddMoney.findViewById(R.id.list_item);
                                ListViewAdapter adapter = new ListViewAdapter(c,Reports);
                                listView.setAdapter(adapter);

                                report.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                         Retrofit.Builder builder = new Retrofit.Builder().baseUrl(djangoBaseUrl).addConverterFactory(GsonConverterFactory.create());
                                        Retrofit retrofit = builder.build();

                                        DataInterface client = retrofit.create(DataInterface.class);

                                        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST, djangoBaseUrl + "products/" + holder.items.get(position).getId() + "/report/", new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                dialAddMoney.dismiss();
                                                Toast.makeText(c,"Successfully reported", Toast.LENGTH_SHORT).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                dialAddMoney.dismiss();
                                            }
                                        }){
                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("Content-Type", "application/json");
                                                params.put("Authorization", c.getSharedPreferences("Tokenkey",Context.MODE_PRIVATE).getString("token",null));
                                                return params;
                                            }

                                        });


                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialAddMoney.dismiss();
                                    }
                                });


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        Log.d("position", String.valueOf(i));
//                                                relationstatus.setText(Relations[i]);
                                        dialAddMoney.dismiss();

                                    }
                                });

                                dialAddMoney.show();



                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });



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

        ImageView report;
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
            report = itemView.findViewById(R.id.menu);

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

    public class ListViewAdapter extends BaseAdapter {

        // Declare Variables

        Context mContext;
        LayoutInflater inflater;
        String[] suggestions;


        public ListViewAdapter(Context context, String[] suggestions) {
            mContext = context;

            inflater = LayoutInflater.from(mContext);
            this.suggestions = suggestions;
        }

        public class ViewHolder {
            TextView name;
            RadioButton radio_relation;
        }

        @Override
        public int getCount() {
            return suggestions.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ListViewAdapter.ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.newsfeed_list_item, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.relationtext);
                holder.radio_relation = view.findViewById(R.id.radiorelation);
                view.setTag(holder);

            } else {
                holder = (ListViewAdapter.ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.name.setText(suggestions[position]);


            holder.radio_relation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(position != mSelectedPosition && mSelectedRB != null)
                    {
                        mSelectedRB.setChecked(false);
                    }

                    report_type = Reports[position];
                    Log.d("reporttype", report_type);

                    mSelectedPosition = position;
                    mSelectedRB = (RadioButton) v;

                    report.setTextColor(ContextCompat.getColor(c, R.color.dark_red));

                    report.setEnabled(true);
                    report.setClickable(true);
                }
            });


            if(mSelectedPosition != position)
            {
                holder.radio_relation.setChecked(false);
                report_type = Reports[position];
                report_type = "";
                report.setEnabled(false);
                report.setTextColor(Color.parseColor("#b9b9b9"));
            }
            else
            {

                holder.radio_relation.setChecked(true);
                report_type = Reports[position];
                Log.d("reporttype", report_type);
                if(mSelectedRB != null && holder.radio_relation != mSelectedRB){
                    mSelectedRB = holder.radio_relation;
                }
            }




            return view;

        }
    }
}