package com.paralainer.shoppinglist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.paralainer.shoppinglist.listadapter.ShoppingListAdapter;
import com.paralainer.shoppinglist.swipedismiss.SwipeDismissListViewTouchListener;
import com.paralainer.shoppinglist.util.FontHelper;
import com.paralainer.shoppinglist.util.KeyValueStorage;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListFragment extends Fragment {

    public static final String SHOPPING_LIST_CACHE_ITEM = "shoppingList";
    private EditText addToListText;
    private ShoppingListAdapter shoppingListAdapter;
    private ArrayList<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        initAddToListButton(rootView);

        initAddToListEditor(rootView);

        restoreShoppingList();

        initListAdapter(rootView);

        return rootView;
    }

    private void initAddToListButton(View rootView) {
        Button button = (Button)rootView.findViewById(R.id.addToListButton);
        FontHelper.setFontAwesome(button, rootView.getContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToShoppingList();
            }
        });
    }

    private void initAddToListEditor(View rootView) {
        addToListText = (EditText) rootView.findViewById(R.id.addToListText);
        addToListText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addToShoppingList();
                    return true;
                }
                return false;
            }
        });
    }

    private void initListAdapter(final View rootView) {
        shoppingListAdapter = new ShoppingListAdapter(rootView.getContext(), R.layout.shopping_list_element, shoppingList);
        ListView shoppingListView = (ListView) rootView.findViewById(R.id.shoppingListView);
        shoppingListView.setAdapter(shoppingListAdapter);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        shoppingListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    removeFromShoppingList(shoppingListAdapter.getItem(position));
                                }
                            }
                        });
        shoppingListView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        shoppingListView.setOnScrollListener(touchListener.makeScrollListener());
    }

    private void restoreShoppingList() {
        try {
            //noinspection unchecked
            ShoppingListHolder holder = KeyValueStorage.getProperty(getActivity(), SHOPPING_LIST_CACHE_ITEM, ShoppingListHolder.class);
            shoppingList = holder == null ? null : holder.getShoppingList();
        }catch (Exception e){
            Log.w("Can't restore shopping list", e);
        }

        if (shoppingList == null) {
            shoppingList = new ArrayList<ShoppingItem>();
        }
    }

    private void removeFromShoppingList(ShoppingItem item){
        shoppingListAdapter.remove(item);

        KeyValueStorage.putPropertyAsync(getActivity(), SHOPPING_LIST_CACHE_ITEM, new ShoppingListHolder(shoppingList));
    }

    public void addToShoppingList() {
        String text = addToListText.getText().toString();
        if (StringUtils.isBlank(text)) {
            return;
        }

        ShoppingItem item = new ShoppingItem();
        item.setName(text);

        shoppingListAdapter.add(item);
        addToListText.setText("");

        KeyValueStorage.putPropertyAsync(getActivity(), SHOPPING_LIST_CACHE_ITEM, new ShoppingListHolder(shoppingList));
    }
}
