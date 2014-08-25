package com.paralainer.shoppinglist.model;

/**
 * Created by paralainer on 22.08.2014.
 * email: serg.talov@gmail.com
 */
public class DefaultProduct {

    private Float defaultQuantity;
    private String defaultMeasure;
    private String group;
    private String name;

    public Float getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Float defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
