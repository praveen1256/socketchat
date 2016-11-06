package logintest.android.com.handlers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import logintest.android.com.model.EndTypingPojo;
import logintest.android.com.model.TypingPojo;

/**
 * Created by Praveen on 12-10-2016.
 */

public class EndTyping {

    public static Emitter.Listener endTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "EndTypingPojo Receive");
            Log.v("Message", "EndTypingPojo " + args[0]);
            JSONObject typingJson = (JSONObject)args[0];
            try {
                String typingUser = typingJson.getJSONObject("username").getString("add_user");
                EndTypingPojo typingPojo = new EndTypingPojo();
                typingPojo.setUserName(typingUser);
                EventBus.getDefault().post(typingPojo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
