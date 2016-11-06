package logintest.android.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import logintest.android.com.androidchatsocket.R;

/**
 * Created by Praveen on 06-11-2016.
 */

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ChatRoomHolder> {

    @Override
    public ChatRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatRoomHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatRoomHolder extends RecyclerView.ViewHolder {
        public TextView tv_message;


        public ChatRoomHolder(View view) {
            super(view);
            tv_message = (TextView) view.findViewById(R.id.tv_message);
        }
    }

}
