package com.techelevator.vending;

public class Candy extends Item implements Purchasable {

    @Override
    public String printSound() {
        return "Munch Munch, Yum!";
    }

}
