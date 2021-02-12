package com.techelevator;

public class Candy extends Item implements Consumable {

    @Override
    public String printSound() {
        return "Munch Munch, Yum!";
    }

}
