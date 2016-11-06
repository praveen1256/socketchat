package logintest.android.com.handlers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import logintest.android.com.model.ChatMessage;

/**
 * Created by Praveen on 12-10-2016.
 */

public class IncomingMessage {

    public static Emitter.Listener handleIncomingMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "Receive");
            String message = "EMPTY";
            String username = "EMPTY";
            try {
                Log.v("Message", "Receive " + args[0]);
                JSONObject jsonObject = (JSONObject) (args[0]);
                message = jsonObject.getJSONObject("message").getString("message").toString();
                username = jsonObject.getJSONObject("username").getString("add_user").toString();
                Log.v("UserName", message+" :: " + username);
            } catch (Exception e) {
                message = "Error";
                username = "Error";
                e.printStackTrace();
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage(message);
            chatMessage.setReceived(true);
            chatMessage.setUserName(username);
            EventBus.getDefault().post(chatMessage);
        }
    };

}
