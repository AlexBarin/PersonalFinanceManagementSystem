package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private double balance;
    private List<Transaction> transactions;
    private Map<String,BudgetCategory> budgetCategories;

    private Wallet(){
        this.budgetCategories = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.balance = 0.0;
    }

    public static Wallet createWallet(){
        Wallet wallet = new Wallet();
        return wallet;
    }
    //add some method
}
