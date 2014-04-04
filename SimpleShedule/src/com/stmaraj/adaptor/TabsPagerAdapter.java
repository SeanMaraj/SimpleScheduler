package com.stmaraj.adaptor;

//import info.androidhive.tabsswipe.GamesFragment;
//import info.androidhive.tabsswipe.MoviesFragment;
import com.stmaraj.simpleshedule.Schedules;
import com.stmaraj.simpleshedule.ToDo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Schedules fragment activity
            return new Schedules();
        case 1:
            // Notes fragment activity
            return new ToDo();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}