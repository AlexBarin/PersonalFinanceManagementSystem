package org.example.services;

import org.example.models.Transaction;
import org.example.models.Wallet;
import org.example.models.BudgetCategory;

import java.util.Map;
import java.util.stream.Collectors;

public class WalletService {
    private Wallet wallet;

    public WalletService(Wallet wallet) {
        this.wallet = wallet;
    }
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    public void addIncome(double amount, String category) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть больше нуля.");
        wallet.setBalance(wallet.getBalance() + amount);
        wallet.addTransaction(new Transaction(true, category, amount));
        System.out.println("Доход добавлен: " + amount + " в категории \"" + category + "\".");
    }

    public void addExpense(double amount, String category) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть больше нуля.");
        if (!wallet.hasCategory(category)) {
            throw new IllegalArgumentException("Категория не найдена: " + category + " создайте сначала категорию.");
        }
        BudgetCategory cat = wallet.getCategory(category);
        if (cat.getSpent() + amount > cat.getBudgetLimit()) {
            System.out.println("Предупреждение: Превышен лимит бюджета категории \"" + category + "\".");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        cat.addExpense(amount);
        wallet.addTransaction(new Transaction(false, category, amount));
        System.out.println("Расход добавлен: " + amount + " в категории \"" + category + "\".");
    }

    public void createCategory(String name, double budgetLimit) {
        if (wallet.hasCategory(name)) {
            throw new IllegalArgumentException("Категория \"" + name + "\" уже существует.");
        }
        wallet.addCategory(new BudgetCategory(name, budgetLimit));
        System.out.println("Категория \"" + name + "\" создана с лимитом: " + budgetLimit);
    }

    public String getDetailedSummary() {
        StringBuilder summary = new StringBuilder();

        // Общий доход и расходы
        double totalIncome = wallet.getTransactions().stream()
                .filter(Transaction::isIncome)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = wallet.getTransactions().stream()
                .filter(t -> !t.isIncome())
                .mapToDouble(Transaction::getAmount)
                .sum();

        summary.append("Общий доход: ").append(totalIncome).append("\n");
        summary.append("Доходы по категориям:\n");

        // Доходы по категориям
        Map<String, Double> incomeByCategory = wallet.getTransactions().stream()
                .filter(Transaction::isIncome)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        incomeByCategory.forEach((category, amount) ->
                summary.append(String.format("    %s: %.1f\n", category, amount))
        );

        summary.append("\nОбщие расходы: ").append(totalExpenses).append("\n");
        summary.append("Бюджет по категориям:\n");

        // Бюджеты с остатками
        wallet.getBudgetCategories().forEach((name, category) -> {
            double spent = category.getSpent();
            double limit = category.getBudgetLimit();
            double remaining = limit - spent;

            summary.append(String.format("    %s: %.1f, Оставшийся бюджет: %.1f\n",
                    name, limit, remaining));
        });

        return summary.toString();
    }


}
