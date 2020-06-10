package com.quyet.banhang.admin_app_ban_hang;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.quyet.banhang.admin_app_ban_hang.ui.activity.DonHangActivity;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.KhoActivity;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.NhapDuLieuActivity;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.SanPhamActivity;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.ThongKeActivity;


public class MainActivity extends AppCompatActivity {
    CardView mSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mSanPham.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int e = event.getAction();
                switch (e) {
                    case MotionEvent.ACTION_DOWN:
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        mSanPham = findViewById(R.id.cSanPham);
    }

    public void clickSanPham(View view) {
        startActivity(new Intent(MainActivity.this, SanPhamActivity.class));
    }


    public void clickKhoHang(View view) {
        startActivity(new Intent(this, KhoActivity.class));
    }

    public void clickNhapDuLieu(View view) {
        startActivity(new Intent(this, NhapDuLieuActivity.class));
    }

    public void donhang(View view) {
        startActivity(new Intent(this, DonHangActivity.class));
    }

    public void thongke(View view) {
        startActivity(new Intent(this, ThongKeActivity.class));
    }
}
