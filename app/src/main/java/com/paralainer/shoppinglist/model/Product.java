package com.paralainer.shoppinglist.model;

import java.text.DecimalFormat;

/**
 * Created by paralainer on 20.08.2014.
 * email: serg.talov@gmail.com
 */
public class Product {

    private String name;

    private boolean bought;

    private String group;

    private Float quantity;

    private String measure;

    private boolean deleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(name);

        if (quantity != null) {
            sb.append(" ").append(new DecimalFormat("#.###").format(quantity));

            if (measure != null) {
                sb.append(" ").append(measure);
            }
        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product that = (Product) o;

        return name.equals(that.name);
    }
}
