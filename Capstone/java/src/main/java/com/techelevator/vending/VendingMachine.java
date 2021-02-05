package com.techelevator.vending;

import com.techelevator.util.VmLog;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

public class VendingMachine {

    private Map<String, Item> allItems = new LinkedHashMap<>();
    private BigDecimal customerBalance = new BigDecimal(0);
    private String inventoryFile;
    private BigDecimal totalSales = new BigDecimal(0);
    private static DecimalFormat f = new DecimalFormat("#0.00");

    public VendingMachine(String inventoryFile) {
        this.inventoryFile = inventoryFile;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public String displayCustomerBalance() {
        return "$" + getCustomerBalance();
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
    }

    public Map<String, Item> getAllItems() {
        return allItems;
    }

    public void setInventoryFile(String inventoryFile) {
        this.inventoryFile = inventoryFile;
    }

    public void loadInventory() {
        Path path = Path.of(inventoryFile);
        try (Scanner inventoryScanner = new Scanner(path)) {
            while (inventoryScanner.hasNextLine()) {
                String line = inventoryScanner.nextLine();
                String[] items = line.split("\\|");
                String category = items[3];
                Item newItem = null;
                if (category.equals("Candy")) {
                    newItem = new Candy();
                } else if (category.equals("Chip")) {
                    newItem = new Chip();
                } else if (category.equals("Drink")) {
                    newItem = new Drink();
                } else if (category.equals("Gum")) {
                    newItem = new Gum();
                }
                newItem.setName(items[1]);
                BigDecimal pricePrep = new BigDecimal(items[2]);
                newItem.setPrice(pricePrep);
                newItem.setSlotNumber(items[0]);
                allItems.put(items[0], newItem);
            }

        } catch (IOException e) {
            System.out.println("File does not exist");
        }

    }

    public String displayInventory() {
        StringBuilder display = new StringBuilder();
        for (Map.Entry<String, Item> item : allItems.entrySet()) {
            display.append(item.getKey()).append(" ");
            display.append(item.getValue().getName()).append(" ");
            display.append(item.getValue().getPrice());
            if (item.getValue().getInventory() == 0) {
                display.append(" **SOLD OUT**\n");
            } else {
                display.append("\n");
            }
        }
        return display.toString();
    }

    public void depositMoney(String input) {
        if (input.contains(".")) {
            throw new IllegalArgumentException();
        }
        BigDecimal money = new BigDecimal(input);
        customerBalance = customerBalance.add(money);
        VmLog.log(" FEED MONEY: $" + f.format(money) + " $" + f.format(getCustomerBalance()));
    }

    public String purchaseItem(String input) {
        String message = "";
        Item item = allItems.get(input);
        if (allItems.containsKey(input) && item.getPrice().compareTo(getCustomerBalance()) < 0 && item.getInventory() >= 1) {
            setCustomerBalance(getCustomerBalance().subtract(item.getPrice()));
            item.setInventory(item.getInventory() - 1);
            item.setPurchases(item.getPurchases() + 1);
            totalSales = totalSales.add(item.getPrice());
            VmLog.log(" " + item.getName() + " " + item.getSlotNumber() + " $" + item.getPrice() + " $" + f.format(getCustomerBalance()));
            message = item.printSound();
        } else if (allItems.containsKey(input) && item.getPrice().compareTo(getCustomerBalance()) > 0) {
            message = "*** Not enough money, please deposit more ***";
        } else if (allItems.containsKey(input) && item.getInventory() <= 0) {
            message = "*** Item out of Stock, please choose another ***";
        } else if (!allItems.containsKey(input)) {
            message = "*** Item does not exist, please choose another ***";
        }

        return message;
    }

    public String returnChange() {
        int dollars = 0;
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        BigDecimal convertingInt = new BigDecimal(100);
        BigDecimal exp = getCustomerBalance().multiply(convertingInt);
        int balance = exp.intValue();

        while (balance > 4) {
            while (balance >= 100) {
                dollars++;
                balance = balance - 100;
            }
            while (balance >= 25) {
                quarters++;
                balance = balance - 25;
            }
            while (balance >= 10) {
                dimes++;
                balance = balance - 10;
            }
            while (balance >= 5) {
                nickels++;
                balance = balance - 5;
            }
        }

        int change = (quarters * 25) + (nickels * 5) + (dimes * 10) + (dollars * 100);
        BigDecimal displayChange = BigDecimal.valueOf(change).movePointLeft(2);

        setCustomerBalance(BigDecimal.ZERO);
        VmLog.log(" GIVE CHANGE: $" + displayChange + " $" + f.format(getCustomerBalance()));
        return "Your change is:\n" + dollars + " dollar(s)" + "\n" + quarters + " quarter(s)" + "\n" +
                dimes + " dime(s)" + "\n" + nickels + " nickel(s)";
    }


    public void createSalesReport() {
        try (PrintWriter writer = new PrintWriter("sale_report.txt")) {
            for (Map.Entry<String, Item> item : allItems.entrySet()) {
                writer.println(item.getValue().getName() + " | " + item.getValue().getPurchases());
            }
            writer.println("\n**TOTAL SALES** $" + totalSales);

            System.out.println("Sales Report has been created.");
        } catch (IOException e) {
            System.out.println("Couldn't create or replace file.");
        }
    }

    public String displaySalesReport() {
        StringBuilder sales = new StringBuilder();
        for (Map.Entry<String, Item> item : allItems.entrySet()) {
            sales.append(item.getValue().getName()).append(" | ").append(item.getValue().getPurchases()).append("\n");
        }
        sales.append("\n**TOTAL SALES** $").append(totalSales);

        return sales.toString();
    }


}

