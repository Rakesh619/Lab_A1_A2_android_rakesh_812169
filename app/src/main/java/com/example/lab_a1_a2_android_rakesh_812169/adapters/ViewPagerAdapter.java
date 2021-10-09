package com.example.lab_a1_a2_android_rakesh_812169.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lab_a1_a2_android_rakesh_812169.fragments.ProductsFragment;
import com.example.lab_a1_a2_android_rakesh_812169.fragments.ProvidersFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ProductsFragment();
        } else if (position == 1) {
            return new ProvidersFragment();
        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Products";
        } else if (position == 1) {
            return "Providers";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
