package com.paralainer.shoppinglist.model;

import com.paralainer.shoppinglist.model.Product;

import java.util.ArrayList;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListHolder {

    private ArrayList<Product> shoppingList;

    public ShoppingListHolder() {
    }

    public ArrayList<Product> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ArrayList<Product> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
