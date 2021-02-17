package project.topmovies.logic.adapters;


import project.topmovies.visual.fragments.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPager_Adapter extends FragmentPagerAdapter {

    private int tabsNumber;

    public ViewPager_Adapter(@NonNull FragmentManager fm, int behavior, int tabs) {

        super(fm, behavior);
        tabsNumber = tabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0: return new Billboard_Fragment();

            case 1: return new ComingSoon_Fragment();

            default: return null;

        }

    }

    @Override
    public int getCount() {

        return tabsNumber;

    }

}
