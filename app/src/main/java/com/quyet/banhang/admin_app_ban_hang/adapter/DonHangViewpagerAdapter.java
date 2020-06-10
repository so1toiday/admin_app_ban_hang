package com.quyet.banhang.admin_app_ban_hang.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.quyet.banhang.admin_app_ban_hang.ui.fragment.DaHuyFragment;
import com.quyet.banhang.admin_app_ban_hang.ui.fragment.DatHangFragment;
import com.quyet.banhang.admin_app_ban_hang.ui.fragment.GiaoHangFragment;
import com.quyet.banhang.admin_app_ban_hang.ui.fragment.ThanhCongFragment;


public class DonHangViewpagerAdapter extends FragmentStatePagerAdapter {
    public DonHangViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new DatHangFragment();

                break;
            case 1:
                fragment = new GiaoHangFragment();

                break;
            case 2:
                fragment = new ThanhCongFragment();
                break;
            case 3:
                fragment = new DaHuyFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Đã đặt";
                break;
            case 1:
                title = "Đang giao";
                break;
            case 2:
                title = "Thành công";
                break;
            case 3:
                title = "Đã hủy";
                break;
        }
        return title;
    }
}
