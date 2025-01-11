package org.example;

public interface WalletAction {
    WalletAction create ();
    boolean remove();
    void addExpense();
    void deleteExpense();
    void addIncome();
    void deleteIncome();
    

}
