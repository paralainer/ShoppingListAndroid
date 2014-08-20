package com.paralainer.shoppinglist.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class KeyValueStorage {

    private static final String KEY_VALUE_STORAGE = "key_value_storage";

    public static <T> T getProperty(Context context, String key, Class<T> clazz){
        SharedPreferences keyValueStorage = context.getSharedPreferences(KEY_VALUE_STORAGE, Context.MODE_PRIVATE);
        String json = keyValueStorage.getString(key, StringUtils.EMPTY);
        return new Gson().fromJson(json, clazz);
    }

    public static void putPropertyAsync(Context context, String key, Object object){
        putValue(context, key, object)
                .apply();
    }

    public static void putProperty(Context context, String key, Object object){
       putValue(context, key, object)
                .commit();
    }

    private static SharedPreferences.Editor putValue(Context context, String key, Object object) {
        return context.getSharedPreferences(KEY_VALUE_STORAGE, Context.MODE_PRIVATE).edit()
                 .putString(key, new Gson().toJson(object));
    }

}
