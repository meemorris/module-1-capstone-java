package com.techelevator.vending;

import java.math.BigDecimal;

public abstract class Item implements Purchasable {

    private String slotNumber;
    private String name;
    private BigDecimal price;
    private int inventory;
    private int purchases;


    public Item() {
        inventory = 5;
        purchases = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }

    @Override
    public abstract String printSound();
}
