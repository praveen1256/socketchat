package logintest.android.com.util;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Rashmi on 16-12-2015.
 */
public class AppController extends MultiDexApplication {

    //**************** tag is used for  cancel the request from the rerqestQueue***************

    public static final String TAG = AppController.class.getSimpleName();
    public static Context context;
    public static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context=this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
