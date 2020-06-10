package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.DetailsSanPham;

import java.util.List;

public class AdapterCategoryProduct extends RecyclerView.Adapter<AdapterCategoryProduct.viewholder> {
    Context context;
    List<DetailsSanPham> list;

    public AdapterCategoryProduct(Context context, List<DetailsSanPham> list) {
        this.context = context;
        this.list = list;
    }
    public void addData(DetailsSanPham data){
        list.add(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productdetails, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        DetailsSanPham sp=list.get(position);
        holder.mPrice.setText(FormatMany.getMany(sp.getPrice()));
        holder.mCount.setText(String.valueOf(sp.getCount()));
        holder.mSize.setText(String.valueOf(sp.getSize()));
        holder.mColor.setText(sp.getColor());
        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView mPrice,mColor,mSize,mCount;
        Button mRemove;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            mPrice=itemView.findViewById(R.id.tvPrice);
            mColor=itemView.findViewById(R.id.tvColor);
            mSize=itemView.findViewById(R.id.tvSize);
            mCount=itemView.findViewById(R.id.tvCount);
            mRemove=itemView.findViewById(R.id.btnRemove);
        }
    }
}
