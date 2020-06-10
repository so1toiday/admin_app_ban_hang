package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.SanPham;
import com.quyet.banhang.admin_app_ban_hang.model.ThongKe;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.SanPhamActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.viewholder> {
    List<ThongKe> list;
    Context context;
    DatabaseReference reference;

    public ThongKeAdapter(Context context, List<ThongKe> list) {
        this.list = list;
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference("products");
    }

    public void updateDatachange() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongKeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThongKeAdapter.viewholder holder, int position) {
        try {
            reference.child(list.get(position).getKey()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SanPham s=dataSnapshot.getValue(SanPham.class);
                    holder.mProductPrice.setText(FormatMany.getMany(s.getSanPhams().get(0).getPrice()));
                    holder.mProductName.setText(s.getName());
                    Picasso.with(context).load(s.getImage().get(0)).into(holder.mProductImage);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            holder.mCount.setText(list.get(position).getCount() + " đã bán tháng này");
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView mProductName, mProductPrice, mCount;
        ImageView mProductImage;

        public viewholder(@NonNull final View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.imProduct);
            mProductName = itemView.findViewById(R.id.tvProductName);
            mProductPrice = itemView.findViewById(R.id.tvPriceProduct);
            mCount = itemView.findViewById(R.id.tvDaBan);

        }
    }
}
