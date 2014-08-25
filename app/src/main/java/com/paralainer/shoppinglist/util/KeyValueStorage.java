package com.paralainer.shoppinglist.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.paralainer.shoppinglist.ShoppingListApplication;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class KeyValueStorage {

    private static final String KEY_VALUE_STORAGE = "key_value_storage";

    public static <T> T getProperty(String key, Class<T> clazz){
        Context appContext = ShoppingListApplication.getAppContext();
        if (appContext == null){
            return null;
        }
        SharedPreferences keyValueStorage = appContext.getSharedPreferences(KEY_VALUE_STORAGE, Context.MODE_PRIVATE);
        String json = keyValueStorage.getString(key, StringUtils.EMPTY);
        return new Gson().fromJson(json, clazz);
    }

    public static void putPropertyAsync(String key, Object object){
        Context appContext = ShoppingListApplication.getAppContext();
        if (appContext == null){
            return;
        }
        appContext.getSharedPreferences(KEY_VALUE_STORAGE, Context.MODE_PRIVATE).edit()
                .putString(key, new Gson().toJson(object)).apply();
    }
}
