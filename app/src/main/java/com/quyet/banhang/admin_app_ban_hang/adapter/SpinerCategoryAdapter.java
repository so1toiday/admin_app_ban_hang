package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpinerCategoryAdapter implements SpinnerAdapter {
    Context context;
    List<TheLoai> loais;
    public SpinerCategoryAdapter(Context context, List<TheLoai> list){
     this.context=context;
     this.loais=list;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinercategory, parent, false);
        TextView tv = convertView.findViewById(R.id.tvCategory);
        ImageView mImage = convertView.findViewById(R.id.imImage);
        tv.setText(loais.get(position).getTitle());
        Picasso.with(context).load(loais.get(position).getImage()).into(mImage);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return loais.size();
    }

    @Override
    public TheLoai getItem(int position) {
        return loais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinercategory, parent, false);
        TextView tv = convertView.findViewById(R.id.tvCategory);
        ImageView mImage = convertView.findViewById(R.id.imImage);
        tv.setText(loais.get(position).getTitle());
        Picasso.with(context).load(loais.get(position).getImage()).into(mImage);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_spinercategory;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
