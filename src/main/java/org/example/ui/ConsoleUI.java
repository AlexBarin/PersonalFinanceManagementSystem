package org.example.ui;

import org.example.services.UserService;
import org.example.services.WalletService;

import java.util.Scanner;

public class ConsoleUI {
    private final UserService userService;
    private WalletService walletService;
    private final Scanner scanner;

    public ConsoleUI(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Добро пожаловать в систему управления личными финансами!");
        boolean i = true;
        while (i) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Войти");
            System.out.println("2. Зарегистрироваться");
            System.out.println("3. Выйти");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                     i = !login(scanner);
                    break;
                case "2":
                    register(scanner);
                    break;
                case "3":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите 1, 2 или 3.");
            }
        }

        while(true){
            displayMainMenu();
        }
    }

    private boolean login(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Используем метод authenticate для входа
        if (userService.authenticate(username, password)) {
            walletService.setWallet(userService.getCurrentUserWallet());
            System.out.println("Добро пожаловать, " + username + "!");
            return true;
        } else {
            System.out.println("Ошибка входа. Проверьте имя пользователя и пароль.");
            return false;
        }
    }

    private void register(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Используем метод registerUser для регистрации
        try {
            userService.registerUser(username, password);
            System.out.println("Регистрация прошла успешно! Теперь вы можете войти.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }


    private void displayMainMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1. Добавить доход");
        System.out.println("2. Добавить расход");
        System.out.println("3. Создать категорию");
        System.out.println("4. Посмотреть сводку бюджета");
        System.out.println("5. Сохранить и выйти из учетной записи");
        System.out.println("6. Выйти из приложения");
        System.out.print("Введите команду: ");

        String command = scanner.nextLine();
        try {
            switch (command) {
                case "1":
                    addIncome();
                    break;
                case "2":
                    addExpense();
                    break;
                case "3":
                    createCategory();
                    break;
                case "4":
                    displayWalletSummary();
                    break;
                case "5":
                    saveAndLogout();
                    return;
                case "6":
                    saveAndExit();
                    break;
                default:
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    private void addIncome() {
        System.out.print("Введите сумму дохода: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите категорию дохода: ");
        String category = scanner.nextLine();

        walletService.addIncome(amount, category);
        System.out.println("Доход добавлен успешно.");
    }

    private void addExpense() {
        System.out.print("Введите сумму расхода: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите категорию расхода: ");
        String category = scanner.nextLine();

        walletService.addExpense(amount, category);
        System.out.println("Расход добавлен успешно.");
    }

    private void createCategory() {
        System.out.print("Введите название категории: ");
        String name = scanner.nextLine();
        System.out.print("Введите лимит бюджета для категории: ");
        double limit = Double.parseDouble(scanner.nextLine());

        walletService.createCategory(name, limit);
        System.out.println("Категория создана успешно.");
    }

    private void displayWalletSummary() {
        String summary = walletService.getDetailedSummary();
        System.out.println(summary);
    }

    private void saveAndLogout() {
        System.out.println("Сохранение данных пользователя...");
        userService.saveUserData();
        userService.logout();
        System.out.println("Вы успешно вышли из учетной записи.");
    }

    private void saveAndExit() {
        if (userService.getCurrentUser() != null) {
            System.out.println("Сохранение данных пользователя...");
            userService.saveUserData();
        }
        System.out.println("До свидания!");
        System.exit(0);
    }
}