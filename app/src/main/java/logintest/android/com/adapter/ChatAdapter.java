package logintest.android.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageHolder> {

    private ArrayList<ChatMessage> chatMessageArrayList;

    public ChatAdapter(ArrayList<ChatMessage> chatMessageArrayList) {
        this.chatMessageArrayList = chatMessageArrayList;
    }

    public class ChatMessageHolder extends RecyclerView.ViewHolder {
        public TextView tv_message;
        public ImageView iv_message_pic;
        public TextView tv_message_name;
        public TextView tv_message_ll;
        public ImageView iv_message_ll;
        public LinearLayout ll_message_name;


        public TextView tv_received;
        public ImageView iv_receive_pic;
        public TextView tv_received_name;
        public TextView tv_received_ll;
        public ImageView iv_receive_ll;
        public LinearLayout ll_received_name;


        public TextView tv_newuser;
        public RelativeLayout activity_main;

        public ChatMessageHolder(View view) {
            super(view);
            tv_message = (TextView) view.findViewById(R.id.tv_message);
            iv_message_pic = (ImageView) view.findViewById(R.id.iv_message_pic);
            tv_message_name = (TextView) view.findViewById(R.id.tv_message_name);
            tv_message_ll = (TextView) view.findViewById(R.id.tv_message_ll);
            iv_message_ll = (ImageView) view.findViewById(R.id.iv_message_ll);
            ll_message_name = (LinearLayout) view.findViewById(R.id.ll_message_name);

            tv_received = (TextView) view.findViewById(R.id.tv_received);
            iv_receive_pic = (ImageView) view.findViewById(R.id.iv_receive_pic);
            tv_received_name = (TextView) view.findViewById(R.id.tv_received_name);
            tv_received_ll = (TextView) view.findViewById(R.id.tv_received_ll);
            iv_receive_ll = (ImageView) view.findViewById(R.id.iv_receive_ll);
            ll_received_name = (LinearLayout) view.findViewById(R.id.ll_received_name);

            tv_newuser = (TextView) view.findViewById(R.id.tv_newuser);
            activity_main = (RelativeLayout) view.findViewById(R.id.activity_main);
        }
    }


    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);

        return new ChatMessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatMessageHolder holder, int position) {
        if (!chatMessageArrayList.get(position).isNewUser()) {
            if (!chatMessageArrayList.get(position).isReceived()) {
                holder.tv_received_name.setText(chatMessageArrayList.get(position).getUserName());
                holder.tv_received.setText(chatMessageArrayList.get(position).getMessage());
                holder.tv_received_ll.setText(chatMessageArrayList.get(position).getMessage());
                if (chatMessageArrayList.get(position).getChatImage() != null) {
                    holder.iv_receive_pic.setImageBitmap(chatMessageArrayList.get(position).getChatImage());
                    holder.iv_receive_ll.setImageBitmap(chatMessageArrayList.get(position).getChatImage());
                }

                holder.tv_message.setVisibility(View.GONE);
                holder.iv_message_pic.setVisibility(View.GONE);
                holder.iv_message_ll.setVisibility(View.GONE);
                holder.tv_message_name.setVisibility(View.GONE);
                holder.ll_message_name.setVisibility(View.GONE);

                Log.v("Check", "Check Is Con Rec : " + checkIsContinueMessage(position));
                Log.v("Check", "Check Is Image : " + chatMessageArrayList.get(position).isImage());
                if (checkIsContinueMessage(position)) {
                    holder.ll_received_name.setVisibility(View.GONE);
                    if (chatMessageArrayList.get(position).isImage()) {
                        holder.iv_receive_pic.setVisibility(View.VISIBLE);
                        holder.tv_received.setVisibility(View.GONE);
                    } else {
                        holder.tv_received.setVisibility(View.VISIBLE);
                        holder.iv_receive_pic.setVisibility(View.GONE);
                    }
                } else {
                    holder.tv_received.setVisibility(View.GONE);
                    holder.ll_received_name.setVisibility(View.VISIBLE);
                    if (chatMessageArrayList.get(position).isImage()) {
                        holder.iv_message_ll.setVisibility(View.VISIBLE);
                        holder.tv_received_ll.setVisibility(View.GONE);
                    } else {
                        holder.tv_message_ll.setVisibility(View.VISIBLE);
                        holder.iv_message_ll.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.tv_message_name.setText(chatMessageArrayList.get(position).getUserName());
                holder.tv_message.setText(chatMessageArrayList.get(position).getMessage());
                holder.tv_message_ll.setText(chatMessageArrayList.get(position).getMessage());
                if (chatMessageArrayList.get(position).getChatImage() != null) {
                    holder.iv_message_pic.setImageBitmap(chatMessageArrayList.get(position).getChatImage());
                    holder.iv_message_ll.setImageBitmap(chatMessageArrayList.get(position).getChatImage());
                }

                holder.tv_received.setVisibility(View.GONE);
                holder.iv_receive_pic.setVisibility(View.GONE);
                holder.iv_receive_ll.setVisibility(View.GONE);
                holder.tv_received_name.setVisibility(View.GONE);
                holder.ll_received_name.setVisibility(View.GONE);

                Log.v("Check", "Check Is Con Sent : " + checkIsContinueMessage(position));
                Log.v("Check", "Check Is Image : " + chatMessageArrayList.get(position).isImage());
                if (checkIsContinueMessage(position)) {
                    holder.ll_message_name.setVisibility(View.GONE);
                    if (chatMessageArrayList.get(position).isImage()) {
                        holder.iv_message_pic.setVisibility(View.VISIBLE);
                        holder.tv_message.setVisibility(View.GONE);
                    } else {
                        holder.iv_message_pic.setVisibility(View.GONE);
                        holder.tv_message.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.ll_message_name.setVisibility(View.VISIBLE);
                    if (chatMessageArrayList.get(position).isImage()) {
                        holder.iv_message_ll.setVisibility(View.VISIBLE);
                        holder.tv_message.setVisibility(View.GONE);
                    } else {
                        holder.tv_message.setVisibility(View.VISIBLE);
                        holder.iv_message_ll.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            holder.tv_newuser.setVisibility(View.VISIBLE);
            holder.tv_received.setVisibility(View.GONE);
            holder.tv_received_name.setVisibility(View.GONE);
            holder.iv_receive_ll.setVisibility(View.GONE);
            holder.iv_receive_pic.setVisibility(View.GONE);
            holder.ll_received_name.setVisibility(View.GONE);
            holder.tv_message.setVisibility(View.GONE);
            holder.tv_message_name.setVisibility(View.GONE);
            holder.iv_message_pic.setVisibility(View.GONE);
            holder.iv_message_ll.setVisibility(View.GONE);
            holder.ll_message_name.setVisibility(View.GONE);
            holder.tv_newuser.setText(chatMessageArrayList.get(position).getMessage());
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
