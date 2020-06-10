package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;

import com.quyet.banhang.admin_app_ban_hang.ui.activity.CategoryActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    private List<TheLoai> list;
    Context context;


    public CategoryAdapter(Context context, List<TheLoai> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnDatachange(List<TheLoai> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        TheLoai tl = list.get(position);
        Picasso.with(context).load(tl.getImage()).placeholder(R.color.black).into(holder.mImage);
        holder.mTitle.setText(tl.getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView mTitle;
        CircleImageView mImage;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvItemCategory);
            mImage = itemView.findViewById(R.id.imItemCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, CategoryActivity.class);
                    if(getAdapterPosition()==0){
                        i.putExtra("all","all");
                    }else {
                        i.putExtra("category", (Serializable) list.get(getAdapterPosition()));

                    }
                    context.startActivity(i);
                }
            });
        }
    }
}
