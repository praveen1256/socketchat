package logintest.android.com.handlers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import logintest.android.com.model.ChatMessage;
import logintest.android.com.model.TypingPojo;

/**
 * Created by Praveen on 12-10-2016.
 */

public class Typing {

    public static Emitter.Listener typing = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "Typing Receive");
            Log.v("Message", "Typing " + args[0]);
            JSONObject typingJson = (JSONObject)args[0];
            try {
                String typingUser = typingJson.getJSONObject("username").getString("add_user");
                TypingPojo typingPojo = new TypingPojo();
                typingPojo.setUserName(typingUser);
                EventBus.getDefault().post(typingPojo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
