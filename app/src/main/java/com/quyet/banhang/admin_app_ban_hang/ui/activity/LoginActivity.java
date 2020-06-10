package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.LoginViewPagerAdapter;

public class LoginActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findview();
        LoginViewPagerAdapter adapter=new LoginViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        tab.setSelectedTabIndicatorColor(Color.parseColor("#FFA725"));
        tab.setupWithViewPager(vp);
    }

    private void findview() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Đăng nhập / Đăng ký");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tab=findViewById(R.id.tlLogin);
        vp=findViewById(R.id.vpLogin);
    }
}
