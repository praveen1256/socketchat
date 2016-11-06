package logintest.android.com.socket;

import android.util.Log;

import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import logintest.android.com.handlers.EndTyping;
import logintest.android.com.handlers.IncomingImage;
import logintest.android.com.handlers.IncomingMessage;
import logintest.android.com.handlers.Setup;
import logintest.android.com.handlers.Typing;
import logintest.android.com.handlers.UserJoined;
import logintest.android.com.handlers.UserLeft;
import logintest.android.com.util.AppSettings;
import logintest.android.com.util.Constants;
import logintest.android.com.util.SocketConstants;
import logintest.android.com.util.Utils;

/**
 * Created by Praveen on 12-10-2016.
 */

public class SocketConnection {

    private static SocketConnection socketConnection = new SocketConnection();

    private static Socket mSocket;
    private static String socketUrl = "http://192.168.203.1:3000";

    private SocketConnection() {

    }

    public static SocketConnection getInstance() {
        return socketConnection;
    }

    public static Socket getSocketConnection() {
        if (mSocket != null) {
            if (!mSocket.connected())
                mSocket.connect();
            return mSocket;
        } else {
            try {
                mSocket = IO.socket(socketUrl);
                if (!mSocket.connected())
                    mSocket.connect();
                return mSocket;
            } catch (URISyntaxException e) {
                return null;
            }
        }
    }

    public static boolean connectToSocket() {
        mSocket = getSocketConnection();
        if (mSocket != null) {
            return true;
        } else
            return false;
    }

    public void disconnectSocket() {
        if (mSocket != null) {
            mSocket.disconnect();
        }
    }

    public void onIncomingMessage() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_INCOMING_MESSAGE))
            mSocket.on(SocketConstants.EMIT_INCOMING_MESSAGE, IncomingMessage.handleIncomingMessage);
    }

    public void onUserJoined() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_USER_JOINED))
            mSocket.on(SocketConstants.EMIT_USER_JOINED, UserJoined.userJoined);
    }

    public void onUserLeft() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_USER_LEFT))
            mSocket.on(SocketConstants.EMIT_USER_LEFT, UserLeft.userLeft);
    }

    public void sendMessage(String message) {
        JSONObject sendText = new JSONObject();
        try {
            sendText.put(SocketConstants.EMIT_INCOMING_MESSAGE, message);
            mSocket.emit(SocketConstants.EMIT_INCOMING_MESSAGE, sendText);
        } catch (JSONException e) {

        }
    }

    public void addUser(String userName) {
        JSONObject sendText = new JSONObject();
        try {
            sendText.put(SocketConstants.FUNCTION_ADD_USER, userName);
            mSocket.emit(SocketConstants.FUNCTION_ADD_USER, sendText);
        } catch (JSONException e) {

        }
    }

    public void leftUser(String userName) {
        JSONObject sendText = new JSONObject();
        try {
            sendText.put(SocketConstants.FUNCTION_REMOVE_USER, userName);
            mSocket.emit(SocketConstants.FUNCTION_REMOVE_USER, sendText);
        } catch (JSONException e) {

        }
    }

    public void typing() {
        mSocket.emit(SocketConstants.FUNCTION_TYPING, new JSONObject());
    }

    public void whoTyping() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_TYPING))
            mSocket.on(SocketConstants.EMIT_TYPING, Typing.typing);
    }

    public void endTyping() {
        mSocket.emit(SocketConstants.FUNCTION_END_TYPING, new JSONObject());
    }

    public void whoEndTyping() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_END_TYPING))
            mSocket.on(SocketConstants.EMIT_END_TYPING, EndTyping.endTyping);
    }

    public void sendImage(String imageData) {
        JSONObject sendImage = new JSONObject();
        try {
            sendImage.put(SocketConstants.FUNCTION_IMAGE, imageData);
            sendImage.put(SocketConstants.FUNCTION_ADD_USER, AppSettings.getUsername());
            mSocket.emit(SocketConstants.FUNCTION_IMAGE, sendImage);
        } catch (JSONException e) {

        }
    }

    public void receiveImage() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_IMAGE))
            mSocket.on(SocketConstants.EMIT_IMAGE, IncomingImage.handleIncomingImage);
    }

    public void setup() {
        if (!mSocket.hasListeners(SocketConstants.EMIT_SETUP))
            mSocket.on(SocketConstants.EMIT_SETUP, Setup.handleSetup);
    }

}
