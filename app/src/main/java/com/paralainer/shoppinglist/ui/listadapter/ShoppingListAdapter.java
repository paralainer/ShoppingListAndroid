package com.paralainer.shoppinglist.ui.listadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.model.Product;
import com.paralainer.shoppinglist.service.ShoppingListService;
import com.paralainer.shoppinglist.util.FontHelper;

import java.util.List;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListAdapter extends ArrayAdapter<Product> implements ShoppingListService.ChangeListener{

    private ShoppingListService shoppingListService = ShoppingListService.getInstance();

    public ShoppingListAdapter(Context context, List<Product> objects) {
        super(context, R.layout.shopping_list_element, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Product item = getItem(position);

        convertView = initView(convertView, parent, item);

        handleViewAppearance(convertView, item);

        return convertView;
    }

    private void handleViewAppearance(View convertView, final Product item) {
        if (!item.isDeleted()){
            final Button boughtButton = (Button) convertView.findViewById(R.id.shoppingListItemBought);
            boughtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.isBought()){
                       shoppingListService.markAsNotBought(item);
                    } else {
                        shoppingListService.markAsBought(item);
                    }
                }
            });

            final TextView labelView = (TextView) convertView.findViewById(R.id.shoppingListItem);
            labelView.setText(item.toString());

            applyRowStyle(labelView, boughtButton, item);
        }
    }

    private View initView(View convertView, ViewGroup parent, Product item) {
        if (convertView == null || ((ShoppingItemTag) convertView.getTag()).deleted != item.isDeleted()) {
            if (item.isDeleted()) {
                convertView = applyDeletedLayout(parent, item);
            } else {
                convertView = applyNormalLayout(parent, item);
            }
        }
        return convertView;
    }

    private View applyNormalLayout(ViewGroup parent, Product item) {
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View convertView = layoutInflater.inflate(R.layout.shopping_list_element, parent, false);
        FontHelper.setFontAwesome((Button) convertView.findViewById(R.id.shoppingListItemBought));
        convertView.setTag(new ShoppingItemTag(false));
        return convertView;
    }

    private View applyDeletedLayout(ViewGroup parent, final Product product) {
        LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
        View convertView = layoutInflater.inflate(R.layout.shopping_list_element_deleted, parent, false);
        FontHelper.setFontAwesome((TextView) convertView.findViewById(R.id.shoppingListItemRestoreIcon));
        convertView.findViewById(R.id.shoppingListItemRestore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingListService.markNotDeleted(product);
            }
        });
        convertView.setTag(new ShoppingItemTag(true));
        return convertView;
    }

    private void applyRowStyle(TextView labelView, Button boughtButton, final Product product) {

        if (product.isBought()) {
            labelView.setTextColor(Color.LTGRAY);
            labelView.setPaintFlags(labelView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            boughtButton.setText(getContext().getString(R.string.icon_revert));
        } else {
            labelView.setTextColor(Color.BLACK);
            labelView.setPaintFlags(labelView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            boughtButton.setText(getContext().getString(R.string.icon_check));
        }

       /* Bitmap mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rice);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), 30);
        parentView.setBackground(new BitmapDrawable(getContext().getResources(), mBitmap));*/
    }

    @Override
    public void onShoppingListChanged() {
        notifyDataSetChanged();
    }

    static class ShoppingItemTag {
        boolean deleted = false;

        ShoppingItemTag(boolean deleted) {
            this.deleted = deleted;
        }
    }
}
