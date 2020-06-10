package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quyet.banhang.admin_app_ban_hang.R;

public class NhapDuLieuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_du_lieu);
        setToolbar();
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
        setTitle("Nhập Liệu");
    }


    public void themdanhmuc(View view) {
        startActivity(new Intent(this,AddCategoryActivity.class));
    }

    public void themthongbao(View view) {
        startActivity(new Intent(this,AddNotification.class));
    }

    public void thembanner(View view) {
        startActivity(new Intent(this,AddBannerActivity.class));
    }

    public void themsanpham(View view) {
        startActivity(new Intent(this,AddProductActivity.class));
    }
}
