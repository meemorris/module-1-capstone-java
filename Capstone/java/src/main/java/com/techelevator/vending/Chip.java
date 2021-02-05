package com.techelevator.vending;

public class Chip extends Item implements Purchasable {

    @Override
    public String printSound() {
        return "Crunch Crunch, Yum!";
    }

}
