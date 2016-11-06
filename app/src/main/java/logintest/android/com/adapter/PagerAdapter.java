package logintest.android.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import logintest.android.com.fragments.ChatRoomFragment;
import logintest.android.com.fragments.OnlineUsersFragment;

/**
 * Created by Praveen on 05-11-2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
                return chatRoomFragment;
            case 1:
                OnlineUsersFragment onlineUsersFragment = new OnlineUsersFragment();
                return onlineUsersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
