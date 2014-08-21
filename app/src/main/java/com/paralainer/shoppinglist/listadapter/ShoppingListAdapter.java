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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.ShoppingItem;
import com.paralainer.shoppinglist.ShoppingListHolder;
import com.paralainer.shoppinglist.util.FontHelper;

import java.util.List;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {

    public ShoppingListAdapter(Context context, List<ShoppingItem> objects) {
        super(context, R.layout.shopping_list_element, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ShoppingItem item = getItem(position);
        if (convertView == null || ((ShoppingItemTag) convertView.getTag()).deleted != item.isDeleted()) {
            item.setModified(false);
            if (item.isDeleted()) {
                convertView = applyDeletedLayout(parent, item);
            } else {
                convertView = applyNormalLayout(parent, item);
            }
        }

        if (!item.isDeleted()){
            TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);
            applyRowAppearance(labelView, item, convertView);
        }

        return convertView;
    }

    private View applyNormalLayout(ViewGroup parent, ShoppingItem item) {
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View convertView = layoutInflater.inflate(R.layout.shopping_list_element, parent, false);
        FontHelper.setFontAwesome((Button) convertView.findViewById(R.id.shoppingListItemBought), getContext());

        TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);
        labelView.setText(item.getName());
        convertView.setTag(new ShoppingItemTag(false));
        return convertView;
    }

    private View applyDeletedLayout(ViewGroup parent, final ShoppingItem item) {
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View convertView = layoutInflater.inflate(R.layout.shopping_list_element_deleted, parent, false);
        FontHelper.setFontAwesome((TextView) convertView.findViewById(R.id.shoppingListItemRestoreIcon), getContext());
        convertView.findViewById(R.id.shoppingListItemRestore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setDeleted(false);
                notifyDataSetChanged();
            }
        });
        convertView.setTag(new ShoppingItemTag(true));
        return convertView;
    }

    private void applyRowAppearance(TextView labelView, final ShoppingItem item, View parentView) {
        final Button boughtButton = (Button) parentView.findViewById(R.id.shoppingListItemBought);
        if (item.isBought()) {
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

    static class ShoppingItemTag {
        boolean deleted = false;

        ShoppingItemTag(boolean deleted) {
            this.deleted = deleted;
        }
    }
}
