package com.tempcall.sajidhz.tempcall;

import android.app.Application;
import android.content.Context;

/**
 * Created by sajidh on 2/5/2017.
 */

public class TempCallApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext(); // Grab the Context you want.
    }

    public static Context getContext() { return context; }

}
