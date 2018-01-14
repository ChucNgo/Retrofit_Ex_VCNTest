package com.chucnq.retrofit_practice_vcn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chucnq.retrofit_practice_vcn.R;
import com.chucnq.retrofit_practice_vcn.data.model.model.model.Product;

import java.util.ArrayList;

/**
 * Created by Chức Ngô on 1/13/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Product> mProducts;

    public ProductAdapter(Context context,ArrayList<Product> arrayList){
        this.mContext = context;
        this.mProducts = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rc_list_product,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvId.setText(String.valueOf(mProducts.get(position).getId()));
        holder.tvName.setText(mProducts.get(position).getName());
        holder.tvPrice.setText(String.valueOf(mProducts.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void updateProducts(ArrayList<Product> arrProducts){
        mProducts = arrProducts;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvId,tvName,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

}
