package com.paralainer.shoppinglist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paralainer on 25.08.2014.
 * email: serg.talov@gmail.com
 */
public class DefaultProductList {

    private int version = 1;

    private List<DefaultProduct> products = new ArrayList<DefaultProduct>();

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<DefaultProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DefaultProduct> products) {
        this.products = products;
    }
}
