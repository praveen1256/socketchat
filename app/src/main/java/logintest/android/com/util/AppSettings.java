package logintest.android.com.util;

import android.content.Context;
import android.content.SharedPreferences;

//This class is used to store data in shared preference
public class AppSettings {

    //Required to store objects in Shared Preference
    //Required to store objects in Shared Preference
    //Required to store objects in Shared Preference

    public static final String PREFERENCE_NAME = "socket_chat";
    public static final String TEST = "test";
    public static final String USERNAME = "username";

    private static SharedPreferences getPrefs() {
        return AppController.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setTest(String testValue) {
        getPrefs().edit().putString(TEST, testValue).commit();
    }

    public static String getTest() {
        return getPrefs().getString(TEST, "Testing");
    }

    public static void setUserName(String username) {
        getPrefs().edit().putString(USERNAME, username);
    }

    public static String getUsername() {
        return getPrefs().getString(USERNAME, "EMPTY");
    }
}
