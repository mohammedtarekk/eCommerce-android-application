package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentPageAdapter extends FragmentStateAdapter {
    public FragmentPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new MobileFragment();
            case 1 :
                return new CameraFragment();
            case 2 :
                return new LaptopFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
