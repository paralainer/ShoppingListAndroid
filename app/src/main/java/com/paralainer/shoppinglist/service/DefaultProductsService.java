package com.paralainer.shoppinglist.service;

import com.google.gson.Gson;
import com.paralainer.shoppinglist.ShoppingListApplication;
import com.paralainer.shoppinglist.model.DefaultProduct;
import com.paralainer.shoppinglist.model.DefaultProductList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paralainer on 22.08.2014.
 * email: serg.talov@gmail.com
 */
public class DefaultProductsService {
    private static volatile DefaultProductsService instance;

    private DefaultProductList productList;

    private List<String> productNames;
    private Map<String, DefaultProduct> shoppingItemMap;

    protected DefaultProductsService() {
        reload();
    }

    public static DefaultProductsService getInstance() {
        if (instance == null) {
            synchronized (DefaultProductsService.class) {
                if (instance == null) {
                    instance = new DefaultProductsService();
                }
            }
        }
        return instance;
    }

    public void reload() {
        try {
            InputStream stream = ShoppingListApplication.getAppContext().getAssets().open("default_products.json");
            productList = new Gson().fromJson(new InputStreamReader(stream), DefaultProductList.class);
            shoppingItemMap = new HashMap<String, DefaultProduct>();
            productNames = new ArrayList<String>(productList.getProducts().size());
            for (DefaultProduct product : productList.getProducts()) {
                shoppingItemMap.put(product.getName(), product);
                productNames.add(product.getName());

                MeasureService.regMeasure(product.getDefaultMeasure());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DefaultProduct> getProducts() {
        return productList.getProducts();
    }

    public DefaultProduct getDefaultShoppingItemParams(String productName) {
        return shoppingItemMap.get(productName);
    }

    public List<String> getProductNames() {
        return productNames;
    }
}
