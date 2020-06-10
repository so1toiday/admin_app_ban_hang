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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.Cart;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.DetailsBillActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DangGiaoAdapter extends RecyclerView.Adapter<DangGiaoAdapter.viewholder> {
    Context context;
    List<Cart> list;
    DatabaseReference comminguser,commingshop,shophuy,userhuy,userthanhcong,shopthanhcong;

    public DangGiaoAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
        comminguser = FirebaseDatabase.getInstance().getReference("comminguser");
        commingshop = FirebaseDatabase.getInstance().getReference("commingshop");
        shophuy = FirebaseDatabase.getInstance().getReference("huyshop");
        userhuy = FirebaseDatabase.getInstance().getReference("cancel");
        shopthanhcong = FirebaseDatabase.getInstance().getReference("shopthanhcong");
        userthanhcong = FirebaseDatabase.getInstance().getReference("userthanhcong");
    }

    public void setList(List<Cart> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danggiao, parent, false);
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
                        comminguser.child(c.getUid()).child(c.getIdCart()).removeValue();
                        commingshop.child(c.getIdCart()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                String key=shophuy.push().getKey();
                                c.setDate(new SimpleDateFormat("hh:mm dd/MM/yyyy").format(new Date()));
                                shophuy.child(key).setValue(c);
                                userhuy.child(c.getUid()).child(key).setValue(c);
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
                AlertDialog dialog = new Builder(context).create();
                dialog.setTitle("Xác Nhận Thành Công");
                dialog.setMessage("Bạn có chắc chắn không");
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference daban=FirebaseDatabase.getInstance().getReference("products").child(c.getPid());
                        daban.child("daban").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String, Object> ban = new HashMap<>();
                                int k=dataSnapshot.getValue(Integer.class);
                                k++;
                                ban.put("daban",k);
                                daban.updateChildren(ban);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Calendar calendar=Calendar.getInstance();
                        int month=calendar.get(Calendar.MONTH);
                        final DatabaseReference thongke=FirebaseDatabase.getInstance().getReference("thongke").child(month+"");
                        thongke.child("doanhthu").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              try {
                                  int doanhthu=dataSnapshot.getValue(Integer.class);
                                  doanhthu+=c.getSanPham().getPrice()*c.getCount();
                                  thongke.child("doanhthu").setValue(doanhthu);
                              }catch (NullPointerException e){
                                  thongke.child("doanhthu").setValue(c.getSanPham().getPrice()*c.getCount());
                              }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        thongke.child("daban").child(c.getPid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    int count = dataSnapshot.getValue(Integer.class);
                                    count++;
                                    thongke.child("daban").child(c.getPid()).setValue(count);
                                } catch (NullPointerException e) {
                                    thongke.child("daban").child(c.getPid()).setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                        comminguser.child(c.getUid()).child(c.getIdCart()).removeValue();
                        commingshop.child(c.getIdCart()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                String key=shopthanhcong.push().getKey();
                                c.setDate(new SimpleDateFormat("hh:mm dd/MM/yyyy").format(new Date()));
                                shopthanhcong.child(key).setValue(c);
                                userthanhcong.child(c.getUid()).child(key).setValue(c);
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
