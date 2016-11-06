package logintest.android.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import logintest.android.com.androidchatsocket.R;

/**
 * Created by Praveen on 05-11-2016.
 */

public class OnlineUsersFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onlineusers, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
