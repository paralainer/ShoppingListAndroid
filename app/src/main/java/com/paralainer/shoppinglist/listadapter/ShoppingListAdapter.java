package com.paralainer.shoppinglist.listadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.ShoppingItem;
import com.paralainer.shoppinglist.util.FontHelper;

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
            FontHelper.setFontAwesome((Button) convertView.findViewById(R.id.shoppingListItemBought), getContext());
        }

        ShoppingItem item = getItem(position);

        TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);
        labelView.setText(item.getName());
        applyRowAppearance(labelView, item, convertView);

        return convertView;
    }

    private void applyRowAppearance(TextView labelView, final ShoppingItem item, View parentView) {
        final Button boughtButton = (Button)parentView.findViewById(R.id.shoppingListItemBought);
        if (item.isBought()){
            labelView.setTextColor(Color.LTGRAY);
            labelView.setPaintFlags(labelView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            boughtButton.setText(getContext().getString(R.string.icon_revert));
        } else {
            labelView.setTextColor(Color.BLACK);
            labelView.setPaintFlags(labelView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            boughtButton.setText(getContext().getString(R.string.icon_check));
        }

        boughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setBought(!item.isBought());
                notifyDataSetChanged();
            }
        });



       /* Bitmap mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rice);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), 30);
        parentView.setBackground(new BitmapDrawable(getContext().getResources(), mBitmap));*/
    }
}
