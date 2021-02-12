package com.techelevator;

public class Drink extends Item implements Consumable {

    @Override
    public String printSound() {
        return "Glug Glug, Yum!";
    }

}
