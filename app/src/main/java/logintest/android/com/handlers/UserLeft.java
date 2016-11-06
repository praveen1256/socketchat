package logintest.android.com.handlers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import logintest.android.com.model.ChatMessage;

/**
 * Created by Praveen on 12-10-2016.
 */

public class UserLeft {

    public static Emitter.Listener userLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "Receive");
            String userName = "EMPTY";
            String usersCount = "No Users";
            try {
                Log.v("Message", "userJoined " + args[0]);
                JSONObject jsonObject = (JSONObject) (args[0]);
                usersCount = jsonObject.getString("numUsers").toString();
                userName = jsonObject.getJSONObject("username").getString("remove_user").toString();
            } catch (Exception e) {
                userName = "Error";
                e.printStackTrace();
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage(userName + " Left ChatRoom");
            chatMessage.setReceived(false);
            chatMessage.setNewUser(true);
            chatMessage.setOnlineCount(usersCount);
            EventBus.getDefault().post(chatMessage);
        }
    };

}
