package com.techelevator.vending;

public class Gum extends Item implements Purchasable {

    @Override
    public String printSound() {
        return "Chew Chew, Yum!";
    }

}
