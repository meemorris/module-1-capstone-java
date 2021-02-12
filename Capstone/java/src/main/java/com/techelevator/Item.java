package com.techelevator;

import java.math.BigDecimal;

public abstract class Item implements Consumable {

    private String slotNumber;
    private String name;
    private BigDecimal price;
    private int inventory;
    private int purchases;
    private static final int STARTING_INVENTORY = 5;
    private VendingMachine vm = new VendingMachine("inventory.txt");

    public Item() {
        inventory = STARTING_INVENTORY;
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

    public void purchase(String input) {
        String message = "";
        Item item = vm.getAllItems().get(input);
        vm.setCustomerBalance(vm.getCustomerBalance().subtract(item.price));//null pointer exception, item is showing up as null
        setInventory(getInventory() - 1);
        setPurchases(getPurchases() + 1);

        vm.setTotalSales(vm.getTotalSales().add(item.price));
        VmLog.log(" " + item.name + " " + item.slotNumber + " $" + item.price + " $" + vm.getCustomerBalance());
        message = item.printSound();
    }


}
