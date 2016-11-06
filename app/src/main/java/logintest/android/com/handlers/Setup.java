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

public class Setup {

    public static Emitter.Listener handleSetup = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.v("Message", "Image Receive");
            Log.v("Setup", "Setup : " + args[0]);
        }
    };

}
