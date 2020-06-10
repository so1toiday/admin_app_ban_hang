package com.quyet.banhang.admin_app_ban_hang.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.quyet.banhang.admin_app_ban_hang.adapter.ThanhCongAdapter;
import com.quyet.banhang.admin_app_ban_hang.model.Cart;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThanhCongFragment extends Fragment {
    RecyclerView re;
    DatabaseReference reference;
    FirebaseUser user;
    TextView mEmptyTextView;
    public ThanhCongFragment() {
    }
    ThanhCongAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_cong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if(user!=null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Cart> list=new ArrayList<>();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        list.add(snapshot.getValue(Cart.class));
                    }
                    if(list.size()>0){
                        mEmptyTextView.setVisibility(View.GONE);
                        re.setVisibility(View.VISIBLE);
                    }else {
                        mEmptyTextView.setVisibility(View.VISIBLE);
                        re.setVisibility(View.GONE);
                    }
                    if(adapter==null){
                        adapter=new ThanhCongAdapter(getContext(),list);
                        re.setAdapter(adapter);
                        re.setLayoutManager(new LinearLayoutManager(getContext()));
                    }else {
                        adapter.setList(list);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void initView(View view) {
        re=view.findViewById(R.id.reThanhCong);
        reference= FirebaseDatabase.getInstance().getReference("shopthanhcong");
        user= FirebaseAuth.getInstance().getCurrentUser();
        mEmptyTextView=view.findViewById(R.id.tvEmpty);

    }
}
