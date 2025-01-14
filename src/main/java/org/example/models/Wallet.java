package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Wallet {
    private double balance;
    private List<Transaction> transactions;
    private Map<String,BudgetCategory> budgetCategories;

    public Wallet(){
        this.budgetCategories = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.balance = 0.0;
    }
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public boolean hasCategory(String name){
        return budgetCategories.containsKey(name);
    }

    public BudgetCategory getCategory(String name) {
        return budgetCategories.get(name);
    }

    public void addCategory(BudgetCategory category) {
        if (budgetCategories.containsKey(category.getName())) {
            throw new IllegalArgumentException("Категория уже существует: " + category.getName());
        }
        budgetCategories.put(category.getName(), category);
    }
    public double getSpentByCategory(String category) {
        BudgetCategory cat = budgetCategories.get(category);
        return (cat != null) ? cat.getSpent() : 0.0;
    }



    /*public static Wallet createWallet(){
        Wallet wallet = new Wallet();
        return wallet;
    }*/

}
