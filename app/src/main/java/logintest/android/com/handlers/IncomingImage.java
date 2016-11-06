package logintest.android.com.handlers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import logintest.android.com.model.ChatMessage;
import logintest.android.com.util.Utils;

/**
 * Created by Praveen on 12-10-2016.
 */

public class IncomingImage {

    public static Emitter.Listener handleIncomingImage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "Image Receive");
            String image = "EMPTY";
            String username = "EMPTY";
            try {
                JSONObject jsonObject = (JSONObject) (args[0]);
                image = jsonObject.getJSONObject("username").getString("image").toString();
                username = jsonObject.getJSONObject("username").getString("add_user").toString();
                Log.v("UserName", image + " :: " + username);
            } catch (Exception e) {
                image = "Error";
                username = "Error";
                e.printStackTrace();
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setChatImage(Utils.decodeImage(image));
            chatMessage.setReceived(true);
            chatMessage.setImage(true);
            chatMessage.setUserName(username);
            EventBus.getDefault().post(chatMessage);
        }
    };

}
