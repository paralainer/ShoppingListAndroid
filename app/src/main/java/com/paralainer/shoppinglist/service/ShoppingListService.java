package com.paralainer.shoppinglist.service;

import android.util.Log;

import com.paralainer.shoppinglist.ShoppingListApplication;
import com.paralainer.shoppinglist.model.Product;
import com.paralainer.shoppinglist.model.ShoppingListHolder;
import com.paralainer.shoppinglist.util.KeyValueStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by paralainer on 25.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListService {

    private static volatile ShoppingListService instance = new ShoppingListService();

    private static final String SHOPPING_LIST_CACHE_ITEM = "shoppingList";

    private ShoppingListHolder shoppingListHolder = new ShoppingListHolder();

    private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
    private Product currentProduct;

    protected ShoppingListService() {
        reload();
        addOnChangeListener(new ChangeListener() {
            @Override
            public void onShoppingListChanged() {
                save();
            }
        });
        startRemoveTimer();
    }

    private void startRemoveTimer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean listChanged = false;
                for (Iterator<Product> iterator = getShoppingList().iterator(); iterator.hasNext(); ) {
                    Product product = iterator.next();
                    if (product.isDeleted() && System.currentTimeMillis() - product.getDeletedTimestamp() > 3000){
                        iterator.remove();
                        listChanged = true;
                    }
                }
                if (listChanged){
                    notifyListChanged();
                }
            }
        }, 2000, 1000);
    }

    public static ShoppingListService getInstance() {
        if (instance == null) {
            synchronized (ShoppingListService.class) {
                if (instance == null) {
                    instance = new ShoppingListService();
                }
            }
        }

        return instance;
    }

    public void reload() {
        try {
            //noinspection unchecked
            shoppingListHolder = KeyValueStorage.getProperty(SHOPPING_LIST_CACHE_ITEM, ShoppingListHolder.class);
            shoppingListHolder = shoppingListHolder == null ? new ShoppingListHolder() : shoppingListHolder;
        } catch (Exception e) {
            Log.w("Can't restore shopping list", e);
        }

        if (shoppingListHolder.getShoppingList() == null) {
            shoppingListHolder.setShoppingList(new ArrayList<Product>());
        }

        notifyListChanged();
    }

    public void addOnChangeListener(ChangeListener changeListener){
        changeListeners.add(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener){
        changeListeners.remove(changeListener);
    }


    public void save() {
        KeyValueStorage.putPropertyAsync(SHOPPING_LIST_CACHE_ITEM, shoppingListHolder);
    }

    public void add(Product item) {
        shoppingListHolder.getShoppingList().add(item);
        notifyListChanged();
    }

    public void remove(Product item) {
        shoppingListHolder.getShoppingList().remove(item);
        notifyListChanged();
    }

    public void markAsBought(Product product){
        product.setBought(true);
        notifyListChanged();
    }

    public void markAsNotBought(Product product){
        product.setBought(false);
        notifyListChanged();
    }

    public void markDeleted(Product product){
        product.setDeleted(true);
        notifyListChanged();
    }

    public void markNotDeleted(Product product){
        product.setDeleted(false);
        notifyListChanged();
    }


    public List<Product> getShoppingList() {
        return shoppingListHolder.getShoppingList();
    }

    public static interface ChangeListener {
        public void onShoppingListChanged();
    }

    private void notifyListChanged(){
        for (ChangeListener changeListener : changeListeners) {
            try {
               changeListener.onShoppingListChanged();
            } catch (Exception e){
                Log.w("Shopping List change listener failed", e);
            }
        }
    }

    public void setCurrentProduct(Product currentProduct){
        this.currentProduct = currentProduct;
    }

    public void setCurrentProduct(int position){
        this.currentProduct = shoppingListHolder.getShoppingList().get(position);
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public Product nextProduct(){
        if (currentProduct == null){
            return null;
        }
        ArrayList<Product> shoppingList = shoppingListHolder.getShoppingList();
        int index = shoppingList.indexOf(currentProduct);
        index++;
        if (index >= shoppingList.size()){
            return null;
        }

        currentProduct = shoppingList.get(index);
        return currentProduct;
    }

    public Product previousProduct(){
        if (currentProduct == null){
            return null;
        }
        ArrayList<Product> shoppingList = shoppingListHolder.getShoppingList();
        int index = shoppingList.indexOf(currentProduct);
        index--;
        if (index < 0){
            return null;
        }

        currentProduct =  shoppingList.get(index);
        return currentProduct;
    }
}
