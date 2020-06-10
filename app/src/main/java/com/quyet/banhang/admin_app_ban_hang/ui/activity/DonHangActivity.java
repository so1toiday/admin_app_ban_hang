package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.DonHangViewpagerAdapter;


public class DonHangActivity extends AppCompatActivity {
    ViewPager mPage;
    TabLayout mtab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        initToolbar();
        initView();
        DonHangViewpagerAdapter adapter=new DonHangViewpagerAdapter(getSupportFragmentManager());
        mPage.setAdapter(adapter);
        mtab.setupWithViewPager(mPage);
        mtab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    private void initView() {
        mPage=findViewById(R.id.vpPage);
        mtab=findViewById(R.id.tabDonHang);
    }
    private void initToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đơn Hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
