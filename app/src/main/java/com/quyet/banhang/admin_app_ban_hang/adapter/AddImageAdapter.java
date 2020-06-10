package com.quyet.banhang.admin_app_ban_hang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.viewholder> {
    Context context;
    List<String> list;

    public AddImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    public void addImage(String url) {
        list.add(url);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_image, parent, false);
        return new viewholder(view);
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(list.get(position));
                    storage.delete();
                } catch (Exception e) {
                }
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        Picasso.with(context).load(list.get(position)).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView mImage;
        Button mButton;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imImage);
            mButton = itemView.findViewById(R.id.btnRemove);
        }
    }
}
