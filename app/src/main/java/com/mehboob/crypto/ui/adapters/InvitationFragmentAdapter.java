package com.mehboob.crypto.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mehboob.crypto.ui.fragments.LevelFiveFragment;
import com.mehboob.crypto.ui.fragments.LevelFourFragment;
import com.mehboob.crypto.ui.fragments.LevelOneFragment;
import com.mehboob.crypto.ui.fragments.LevelThreeFragment;
import com.mehboob.crypto.ui.fragments.LevelTwoFragment;

public class InvitationFragmentAdapter extends FragmentStateAdapter {


    public InvitationFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
     switch (position){
         case 1:
             return new LevelOneFragment();
         case 2:
             return new LevelTwoFragment();
         case 3:
             return new LevelThreeFragment();
         case 4:
             return new LevelFourFragment();


     }
     return new LevelFiveFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
