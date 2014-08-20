package com.paralainer.shoppinglist;

import java.util.ArrayList;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class ShoppingListHolder {

    private ArrayList<ShoppingItem> shoppingList;

    public ShoppingListHolder(ArrayList<ShoppingItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public ShoppingListHolder() {
    }

    public ArrayList<ShoppingItem> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ArrayList<ShoppingItem> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
