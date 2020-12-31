package com.example.rentalApplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rentalApplication.ui.main.BaumaschinenFragment;
import com.example.rentalApplication.ui.main.KundenFragment;
import com.example.rentalApplication.ui.main.VertraegeFragment;

public class BaumaquipAdapter extends FragmentStateAdapter {

    public BaumaquipAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BaumaschinenFragment();
            case 1:
                return new KundenFragment();
            default:
                return new VertraegeFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
