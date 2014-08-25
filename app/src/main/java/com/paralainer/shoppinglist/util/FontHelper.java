package com.paralainer.shoppinglist.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.paralainer.shoppinglist.ShoppingListApplication;

/**
 * Created by paralainer on 21.08.2014.
 * email: serg.talov@gmail.com
 */
public class FontHelper {

    private static Typeface awesomeTypeface;

    public static void setFontAwesome(TextView textView){
        if (awesomeTypeface == null) {
            awesomeTypeface = Typeface.createFromAsset(ShoppingListApplication.getAppContext().getAssets(), "fontawesome-webfont.ttf");
        }
        textView.setTypeface(awesomeTypeface);
    }

}
