package com.paralainer.shoppinglist.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;

/**
 * Created by paralainer on 21.08.2014.
 * email: serg.talov@gmail.com
 */
public class FontHelper {

    public static void setFontAwesome(TextView textView, Context context){
        textView.setTypeface(
                Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf")
        );
    }

}
