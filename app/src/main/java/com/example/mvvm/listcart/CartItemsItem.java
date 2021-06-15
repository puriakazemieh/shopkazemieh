package com.example.mvvm.listcart;

import com.google.gson.annotations.SerializedName;

public class CartItemsItem {
    private boolean isChangeCount;
    private boolean isRemove;

    public boolean getRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    @SerializedName("product")
    private Product product;

    @SerializedName("color")
    private String color;

    @SerializedName("name_colors")
    private String nameColors;

    @SerializedName("count")
    private String count;

    @SerializedName("id")
    private String id;

    public Product getProduct() {
        return product;
    }

    public String getColor() {
        return color;
    }

    public String getNameColors() {
        return nameColors;
    }

    public String getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean getChangeCount() {
        return isChangeCount;
    }

    public void setChangeCount(boolean changeCount) {
        isChangeCount = changeCount;
    }

}