package com.paralainer.shoppinglist;

import android.app.Application;
import android.content.Context;

/**
 * Created by paralainer on 25.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        ShoppingListApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ShoppingListApplication.context;
    }

}
