package com.paralainer.shoppinglist.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.paralainer.shoppinglist.R;
import com.paralainer.shoppinglist.model.DefaultProduct;
import com.paralainer.shoppinglist.model.Product;
import com.paralainer.shoppinglist.service.DefaultProductsService;
import com.paralainer.shoppinglist.service.MeasureService;
import com.paralainer.shoppinglist.service.ShoppingListService;
import com.paralainer.shoppinglist.ui.listadapter.ShoppingListAdapter;
import com.paralainer.shoppinglist.ui.swipedismiss.SwipeDismissListViewTouchListener;
import com.paralainer.shoppinglist.util.FontHelper;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListFragment extends Fragment {

    private Handler handler = new Handler();

    private AutoCompleteTextView productNameEditor;
    private EditText quantityEditor;
    private AutoCompleteTextView measureEditor;

    private DefaultProductsService defaultProductsService;
    private ShoppingListService shoppingListService;

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        defaultProductsService = DefaultProductsService.getInstance();
        shoppingListService = ShoppingListService.getInstance();
        initAddToListButton(rootView);
        initAddToListEditors(rootView);
        initListAdapter(rootView);
        startRemoveTimers();

        return rootView;
    }

    private void startRemoveTimers() {
        for (Product product : shoppingListService.getShoppingList()) {
            if (product.isDeleted()) {
                startRemoveTimer(product);
            }
        }
    }

    private void initAddToListButton(View rootView) {
        Button button = (Button) rootView.findViewById(R.id.addToListButton);
        FontHelper.setFontAwesome(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToShoppingList();
            }
        });
    }

    private void initAddToListEditors(View rootView) {
        TextView.OnEditorActionListener editorActionListener = getOnEditorActionListener();
        productNameEditor = (AutoCompleteTextView) rootView.findViewById(R.id.addToListText);
        productNameEditor.setOnEditorActionListener(editorActionListener);
        productNameEditor.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, defaultProductsService.getProductNames()));
        productNameEditor.setThreshold(0);
        productNameEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productNameEditor.showDropDown();
            }
        });
        productNameEditor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setDefaultShoppingItemParams();
            }
        });


        productNameEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    productNameEditor.showDropDown();
                } else {
                    setDefaultShoppingItemParams();
                }
            }
        });
        productNameEditor.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                setDefaultShoppingItemParams();
            }
        });

        quantityEditor = (EditText) rootView.findViewById(R.id.addToListQuantity);
        quantityEditor.setText("");
        quantityEditor.setOnEditorActionListener(editorActionListener);
        quantityEditor.setSelectAllOnFocus(true);

        measureEditor = (AutoCompleteTextView) rootView.findViewById(R.id.addToListMeasure);
        ArrayList<String> strings = new ArrayList<String>(MeasureService.getMeasures());
        Collections.sort(strings);
        measureEditor.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, strings));
        measureEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureEditor.showDropDown();
            }
        });
        measureEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused) {
                    measureEditor.showDropDown();
                }
            }
        });
        measureEditor.setThreshold(0);
        measureEditor.setOnEditorActionListener(editorActionListener);
        measureEditor.setSelectAllOnFocus(true);
    }

    private TextView.OnEditorActionListener getOnEditorActionListener() {
        return new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        addToShoppingList();
                        return true;
                    }
                    return false;
                }
            };
    }

    private void setDefaultShoppingItemParams() {
        DefaultProduct params = defaultProductsService.getDefaultShoppingItemParams(productNameEditor.getText().toString());
        if (params == null) {
            quantityEditor.setText(null);
            measureEditor.setText("");
            return;
        }
        Float defaultQuantity = params.getDefaultQuantity();
        quantityEditor.setText(defaultQuantity == null ? null : new DecimalFormat("#.###").format(defaultQuantity));
        measureEditor.setText(params.getDefaultMeasure());
    }

    private void initListAdapter(final View rootView) {
        final ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(rootView.getContext(), shoppingListService.getShoppingList());
        shoppingListService.addOnChangeListener(shoppingListAdapter);
        ListView shoppingListView = (ListView) rootView.findViewById(R.id.shoppingListView);
        shoppingListView.setAdapter(shoppingListAdapter);
        SwipeDismissListViewTouchListener touchListener = getSwipeDismissListViewTouchListener(shoppingListAdapter, shoppingListView);
        shoppingListView.setOnTouchListener(touchListener);
        shoppingListView.setOnScrollListener(touchListener.makeScrollListener());
    }

    private SwipeDismissListViewTouchListener getSwipeDismissListViewTouchListener(final ShoppingListAdapter shoppingListAdapter, ListView shoppingListView) {
        return new SwipeDismissListViewTouchListener(
                shoppingListView,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            try {
                                final Product item = shoppingListAdapter.getItem(position);
                                if (item.isDeleted()) {
                                    //if user swipes already deleted element
                                    shoppingListService.remove(item);
                                } else {
                                    shoppingListService.markDeleted(item);
                                    startRemoveTimer(item);
                                }
                            } catch (Exception e) {
                                Log.w("Can't delete shopping list item", e);
                            }
                        }
                    }
                });
    }

    private void startRemoveTimer(final Product item) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (item.isDeleted()) {
                    shoppingListService.remove(item);
                }
            }
        }, 3000);
    }

    private void addToShoppingList() {
        Product product = makeProduct();
        if (product == null) {
            return;
        }

        shoppingListService.add(product);

        productNameEditor.setText("");
        quantityEditor.setText("");
        measureEditor.setText("");
    }

    private Product makeProduct() {
        String productName = productNameEditor.getText().toString();
        if (StringUtils.isBlank(productName)) {
            return null;
        }

        Product product = new Product();
        product.setName(productName);
        String quantityString = quantityEditor.getText().toString();
        if (StringUtils.isNotBlank(quantityString)) {
            product.setQuantity(Float.valueOf(quantityString));
        } else {
            product.setQuantity(null);
        }
        product.setMeasure(measureEditor.getText().toString());
        DefaultProduct params = defaultProductsService.getDefaultShoppingItemParams(productName);
        if (params != null) {
            product.setGroup(params.getGroup());
        }

        return product;
    }
}
