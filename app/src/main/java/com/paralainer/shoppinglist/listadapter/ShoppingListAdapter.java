package com.paralainer.shoppinglist.listadapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.ShoppingItem;

import java.util.List;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {

    private int resource;

    public ShoppingListAdapter(Context context, int resource, List<ShoppingItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
            convertView = layoutInflater.inflate(resource, parent, false);
        }

        TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);

        ShoppingItem item = getItem(position);
        labelView.setText(item.getName());
        if (item.isBought()){
            labelView.setTextColor(Color.LTGRAY);
        }

        return convertView;
    }
}
