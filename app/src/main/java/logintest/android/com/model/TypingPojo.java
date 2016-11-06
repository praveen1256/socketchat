package logintest.android.com.model;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Praveen on 15-10-2016.
 */

public class TypingPojo {

    String userName;

    public TypingPojo(){
//        if(!EventBus.getDefault().hasSubscriberForEvent(TypingPojo.class)) {
//            EventBus.getDefault().register(this);
//        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
