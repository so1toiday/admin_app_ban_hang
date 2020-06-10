package com.quyet.banhang.admin_app_ban_hang.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.quyet.banhang.admin_app_ban_hang.ui.fragment.LoginFragment;
import com.quyet.banhang.admin_app_ban_hang.ui.fragment.RegisterFragment;


public class LoginViewPagerAdapter extends FragmentStatePagerAdapter {

    public LoginViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LoginFragment();
                break;
            case 1:
                fragment = new RegisterFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title="Đăng Nhập";
                break;
            case 1:
                title="Đăng Ký";
                break;


        }
        return title;
    }
}
