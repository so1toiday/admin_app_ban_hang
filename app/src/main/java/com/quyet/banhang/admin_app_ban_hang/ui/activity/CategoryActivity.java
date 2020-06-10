package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.ProductAdapter;
import com.quyet.banhang.admin_app_ban_hang.model.SanPham;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;


import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView re;
    TextView mTitleCategory;
    ProductAdapter adapter;
    TextView mCount;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Sản Phẩm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCount.setText("Tổng số lượng: "+dataSnapshot.getChildrenCount());
                List<SanPham> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SanPham sp = snapshot.getValue(SanPham.class);
                    sp.setId(snapshot.getKey());
                    list.add(sp);
                }
                if (adapter != null) {
                    adapter.updateDatachange(list);
                } else {
                    adapter = new ProductAdapter(CategoryActivity.this, list);
                    re.setAdapter(adapter);
                    re.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 2));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void setList() {
        Intent i = getIntent();
        if (i.hasExtra("category")) {
            TheLoai tl = (TheLoai) i.getSerializableExtra("category");
            reference.orderByChild("category").equalTo(tl.getTitle()).addValueEventListener(eventListener);
            mTitleCategory.setText(tl.getTitle());
        } else if (i.hasExtra("all")) {
            reference.addValueEventListener(eventListener);
            mTitleCategory.setText("Tất Cả");
        }else {
            reference.addListenerForSingleValueEvent(eventListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(eventListener);
    }

    private void initView() {
        reference = FirebaseDatabase.getInstance().getReference("products");
        re = findViewById(R.id.re);
        mTitleCategory = findViewById(R.id.tvTitleCategory);
        mCount=findViewById(R.id.tvCount);
    }
}
