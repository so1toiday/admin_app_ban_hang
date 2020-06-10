package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.CategoryAdapter;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class KhoActivity extends AppCompatActivity {
    RecyclerView mListCatetory;
    DatabaseReference reference;
    List<TheLoai> list;
    CategoryAdapter adapter;
    TextView mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);
        setToolbar();
        findView();
        mListCatetory.setLayoutManager(new GridLayoutManager(this, 4));
        mListCatetory.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCount.setText("Tổng số lượng: "+dataSnapshot.getChildrenCount());
                list.clear();
                list.add(new TheLoai("Tất Cả","https://firebasestorage.googleapis.com/v0/b/appbanhang-ca5e7.appspot.com/o/all.png?alt=media&token=947c91b1-43df-4984-8dae-3e722b8d16ae"));
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    list.add(s.getValue(TheLoai.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findView() {
        mListCatetory = findViewById(R.id.reCategory);
        reference = FirebaseDatabase.getInstance().getReference("category");
        list = new ArrayList<>();
        adapter = new CategoryAdapter(this, list);
        mCount = findViewById(R.id.tvCount);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Kho Hàng");
    }
}
