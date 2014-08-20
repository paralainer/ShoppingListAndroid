package com.paralainer.shoppinglist.listadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
            convertView = layoutInflater.inflate(resource, parent, false);
            ImageButton button = (ImageButton)convertView.findViewById(R.id.shoppingListItemDelete);
            final View itemView = convertView;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteItemClick(position, itemView);
                }
            });
        }

        TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);

        ShoppingItem item = getItem(position);
        labelView.setText(item.getName());
        applyTextAppearance(labelView, item);

        return convertView;
    }

    public void onDeleteItemClick(int position, View itemView) {
    }

    private void applyTextAppearance(TextView labelView, ShoppingItem item) {
        if (item.isBought()){
            labelView.setTextColor(Color.LTGRAY);
            labelView.setPaintFlags(labelView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            labelView.setTextColor(Color.BLACK);
            labelView.setPaintFlags(labelView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
