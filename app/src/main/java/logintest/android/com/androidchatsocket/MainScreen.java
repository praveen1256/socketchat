package logintest.android.com.androidchatsocket;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import logintest.android.com.adapter.PagerAdapter;

/**
 * Created by Praveen on 05-11-2016.
 */

public class MainScreen extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    TabLayout tl_mainscreen;
    ViewPager vp_mainscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        tl_mainscreen = (TabLayout) findViewById(R.id.tl_mainscreen);
        vp_mainscreen = (ViewPager) findViewById(R.id.vp_mainscreen);

        //Adding the tabs using addTab() method
        tl_mainscreen.addTab(tl_mainscreen.newTab().setText("Chat Rooms"));
        tl_mainscreen.addTab(tl_mainscreen.newTab().setText("Online Users"));
        tl_mainscreen.setTabGravity(TabLayout.GRAVITY_FILL);

        //Creating our pager adapter
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tl_mainscreen.getTabCount());

        //Adding adapter to pager
        vp_mainscreen.setAdapter(adapter);
        //Adding onTabSelectedListener to swipe views
        tl_mainscreen.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_mainscreen.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
