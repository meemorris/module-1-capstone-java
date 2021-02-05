package com.techelevator.vending;

public class Drink extends Item implements Purchasable {

    @Override
    public String printSound() {
        return "Glug Glug, Yum!";
    }

}
