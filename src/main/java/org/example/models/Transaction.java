package org.example.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private boolean type; // INCOME (1) or EXPENSE (0)
    private String category;
    private double amount;
    private String date;

    private Transaction(boolean type, String category, double amount) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
