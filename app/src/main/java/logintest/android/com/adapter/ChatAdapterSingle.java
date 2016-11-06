package logintest.android.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import logintest.android.com.androidchatsocket.R;
import logintest.android.com.model.ChatMessage;

/**
 * Created by FuGenX-50 on 04-10-2016.
 */

public class ChatAdapterSingle extends RecyclerView.Adapter<ChatAdapterSingle.ChatMessageHolder> {

    private ArrayList<ChatMessage> chatMessageArrayList;

    public ChatAdapterSingle(ArrayList<ChatMessage> chatMessageArrayList) {
        this.chatMessageArrayList = chatMessageArrayList;
    }

    public class ChatMessageHolder extends RecyclerView.ViewHolder {

        public RelativeLayout rl_item_main;
        public LinearLayout ll_message;
        public TextView tv_user_status;
        public TextView tv_name;
        public TextView tv_message;
        public ImageView iv_message;

        public ChatMessageHolder(View view) {
            super(view);
            rl_item_main = (RelativeLayout) view.findViewById(R.id.rl_item_main);
            ll_message = (LinearLayout) view.findViewById(R.id.ll_message);
            tv_user_status = (TextView) view.findViewById(R.id.tv_user_status);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_message = (TextView) view.findViewById(R.id.tv_message);
            iv_message = (ImageView) view.findViewById(R.id.iv_message);
        }
    }


    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_single, parent, false);
        return new ChatMessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatMessageHolder holder, int position) {

        holder.rl_item_main.setGravity(Gravity.LEFT);
        holder.ll_message.setVisibility(View.GONE);
        holder.tv_user_status.setVisibility(View.GONE);
        holder.tv_name.setVisibility(View.GONE);
        holder.tv_message.setVisibility(View.GONE);
        holder.iv_message.setVisibility(View.GONE);

        if (chatMessageArrayList.get(position).isNewUser()) {
            holder.tv_user_status.setVisibility(View.VISIBLE);
            holder.tv_user_status.setText(chatMessageArrayList.get(position).getMessage());
        } else {
            holder.ll_message.setVisibility(View.VISIBLE);
            holder.tv_name.setText(chatMessageArrayList.get(position).getUserName());
            holder.tv_message.setText(chatMessageArrayList.get(position).getMessage());
            if (chatMessageArrayList.get(position).getChatImage() != null)
                holder.iv_message.setImageBitmap(chatMessageArrayList.get(position).getChatImage());

            if (chatMessageArrayList.get(position).isReceived())
                holder.rl_item_main.setGravity(Gravity.LEFT);
            else
                holder.rl_item_main.setGravity(Gravity.RIGHT);

            if (chatMessageArrayList.get(position).isImage()) {
                if (checkIsContinueMessage(position)) {
                    holder.tv_name.setVisibility(View.GONE);
                    holder.tv_message.setVisibility(View.GONE);
                    holder.iv_message.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_name.setVisibility(View.VISIBLE);
                    holder.tv_message.setVisibility(View.GONE);
                    holder.iv_message.setVisibility(View.VISIBLE);
                }
            } else {
                if (checkIsContinueMessage(position)) {
                    holder.tv_name.setVisibility(View.GONE);
                    holder.tv_message.setVisibility(View.VISIBLE);
                    holder.iv_message.setVisibility(View.GONE);
                } else {
                    holder.tv_name.setVisibility(View.VISIBLE);
                    holder.tv_message.setVisibility(View.VISIBLE);
                    holder.iv_message.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    private boolean checkIsContinueMessage(int position) {
        Log.v("Position ", "Position " + position);
        if (chatMessageArrayList.size() >= 1 && position >= 1) {
            if (!chatMessageArrayList.get(position - 1).isNewUser() && chatMessageArrayList.get(position - 1).getUserName().equalsIgnoreCase(chatMessageArrayList.get(position).getUserName())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
