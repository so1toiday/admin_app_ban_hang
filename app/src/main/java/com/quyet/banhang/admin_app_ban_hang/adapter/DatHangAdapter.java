package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.Cart;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.DetailsBillActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DatHangAdapter extends RecyclerView.Adapter<DatHangAdapter.viewholder> {
    Context context;
    List<Cart> list;
    DatabaseReference reference, referenceShop, huyreference, danggiaoUser, danggiaoshop;
    DatabaseReference cancelReference;

    public DatHangAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
        reference = FirebaseDatabase.getInstance().getReference("order");
        referenceShop = FirebaseDatabase.getInstance().getReference("shoporder");
        huyreference = FirebaseDatabase.getInstance().getReference("huyshop");
        cancelReference = FirebaseDatabase.getInstance().getReference("cancel");
        danggiaoUser = FirebaseDatabase.getInstance().getReference("comminguser");
        danggiaoshop = FirebaseDatabase.getInstance().getReference("commingshop");

    }

    public void setList(List<Cart> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dathang, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        final Cart c = list.get(position);
        holder.mCount.setText("Số lượng: " + c.getCount());
        holder.mColor.setText("Loại: " + c.getSanPham().getSize() + " - " + c.getSanPham().getColor());
        holder.mName.setText(c.getName());
        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new Builder(context).create();
                dialog.setTitle("Hủy đơn hàng");
                dialog.setMessage("Bạn có chắc chắn hủy đơn hàng này");
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference mcancelReference = cancelReference.child(c.getUid());
                        DatabaseReference mreference1 = reference.child(c.getUid());
                        referenceShop.child(c.getIdCart()).removeValue();
                        mreference1.child(c.getIdCart()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                c.setIdCart(null);
                                Map<String, Object> map = new HashMap<>();
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                                map.put("date", format.format(new Date()));
                                String k=huyreference.push().getKey();
                                huyreference.child(k).setValue(c);
                                mcancelReference.child(k).setValue(c);
                                mcancelReference.child(k).updateChildren(map);
                                huyreference.child(k).updateChildren(map);
                                Toast.makeText(context, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog.dismiss();
                    }
                });
                dialog.setButton(Dialog.BUTTON_NEGATIVE, "Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        holder.mPrice.setText(FormatMany.getMany(c.getSanPham().getPrice()));
        Picasso.with(context).load(c.getImage()).into(holder.mImage);
        holder.mDate.setText(c.getDate());
        holder.mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mreference = reference.child(c.getUid());
                mreference.child(c.getIdCart()).removeValue();
                referenceShop.child(c.getIdCart()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        c.setIdCart(null);
                        final String key=danggiaoUser.child(c.getUid()).push().getKey();
                        danggiaoUser.child(c.getUid()).child(key).setValue(c);
                        danggiaoshop.child(key).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Map<String, Object> map = new HashMap<>();
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                                map.put("date", format.format(new Date()));
                                danggiaoshop.child(key).updateChildren(map);
                                danggiaoUser.child(c.getUid()).child(key).updateChildren(map);
                            }
                        });

                    }
                });
            }
        });
        holder.mButtonKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DetailsBillActivity.class);
                i.putExtra("bill",c);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        Button mCancel, mButtonKhachHang, mButtonConfirm;
        TextView mName, mPrice, mColor, mCount,mDate;
        CircleImageView mImage;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            mCancel = itemView.findViewById(R.id.btnCancel);
            mName = itemView.findViewById(R.id.tvName);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mImage = itemView.findViewById(R.id.imCart);
            mColor = itemView.findViewById(R.id.tvColor);
            mCount = itemView.findViewById(R.id.tvCount);
            mDate=itemView.findViewById(R.id.tvDate);
            mButtonConfirm = itemView.findViewById(R.id.btnConfirm);
            mButtonKhachHang = itemView.findViewById(R.id.btnDetailsUser);
        }
    }
}
