package com.quyet.banhang.admin_app_ban_hang.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.DatHangAdapter;
import com.quyet.banhang.admin_app_ban_hang.model.Cart;


import java.util.ArrayList;
import java.util.List;

/**
 */
public class DatHangFragment extends Fragment {
    DatabaseReference reference;
    FirebaseUser user;
    List<Cart> list;
    DatHangAdapter adapter;
    RecyclerView mRecycleview;
    TextView mEmptyTextView;
    public DatHangFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dat_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list=new ArrayList<>();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Cart c=snapshot.getValue(Cart.class);
                        c.setIdCart(snapshot.getKey());
                        list.add(c);
                    }
                    if(list.size()>0){
                        mEmptyTextView.setVisibility(View.GONE);
                        mRecycleview.setVisibility(View.VISIBLE);
                    }else {
                        mEmptyTextView.setVisibility(View.VISIBLE);
                        mRecycleview.setVisibility(View.GONE);
                    }

                    if(adapter!=null){
                        adapter.setList(list);
                    }else {
                        adapter=new DatHangAdapter(getContext(),list);
                        mRecycleview.setAdapter(adapter);
                        mRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    private void initView(View view) {
        reference= FirebaseDatabase.getInstance().getReference("shoporder");
        user= FirebaseAuth.getInstance().getCurrentUser();
        mRecycleview=view.findViewById(R.id.reDatHang);
        mEmptyTextView=view.findViewById(R.id.tvEmpty);
    }
}
