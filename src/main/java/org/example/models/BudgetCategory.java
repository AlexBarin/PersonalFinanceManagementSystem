package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetCategory {
    private String name;
    private double budgetLimit;
    private double spent;

    public BudgetCategory(String name, double budgetLimit) {
        this.name = name;
        this.budgetLimit = budgetLimit;
        this.spent = 0.0;
    }


    public void addExpense(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма расхода должна быть положительной.");
        }
        spent += amount;
    }


}
