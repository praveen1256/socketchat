package logintest.android.com.model;

import android.graphics.Bitmap;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by FuGenX-50 on 04-10-2016.
 */

public class ChatMessage {

    String userName;
    String message;
    boolean isReceived;
    boolean isNewUser;
    String onlineCount;
    Bitmap chatImage;
    boolean isImage;

    public ChatMessage(){

    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(String onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }

    public Bitmap getChatImage() {
        return chatImage;
    }

    public void setChatImage(Bitmap chatImage) {
        this.chatImage = chatImage;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
