package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.ThongKeAdapter;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.ThongKe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {

    RecyclerView re;
    TextView tvDoanhthu;
    ThongKeAdapter adapter;
    List<ThongKe> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setToolbar();
        findView();
        list = new ArrayList<>();
        adapter = new ThongKeAdapter(this, list);
        re.setAdapter(adapter);
        re.setLayoutManager(new GridLayoutManager(this, 2));
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        FirebaseDatabase.getInstance().getReference().child("thongke").child(month + "").child("daban").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ThongKe> l = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThongKe k = new ThongKe();
                    k.setKey(snapshot.getKey());
                    k.setCount(snapshot.getValue(Integer.class));
                    l.add(k);
                }
                Collections.sort(l);
                list.clear();
                for (int i = 0; i < 10 && i < l.size(); i++) {
                    list.add(l.get(i));
                }
                adapter.updateDatachange();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("thongke").child(month + "").child("doanhthu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    int doanhthu = dataSnapshot.getValue(Integer.class);
                    tvDoanhthu.setText(FormatMany.getMany(doanhthu));
                } catch (Exception e) {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        setTitle("Thống Kê Tháng Này");
    }

    private void findView() {
        tvDoanhthu = findViewById(R.id.tvDoanhthu);
        re = findViewById(R.id.re);
    }
}
