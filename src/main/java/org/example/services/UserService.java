package org.example.services;

import lombok.Getter;
import org.example.models.User;
import org.example.models.Wallet;
import org.example.persistence.PersistenceManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


/// make all method private (only US and menu public)
@Getter
public class UserService {
    private static final String DATA_DIRECTORY = "data";
    private PersistenceManager persistenceManager;
    private User currentUser;

    public UserService(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
    public Wallet getWallet() {
        return currentUser.getWallet();
    }
    public Wallet getCurrentUserWallet(){
        return currentUser.getWallet();
    }
    public boolean authenticate(String username, String password) {
        Path filePath = Paths.get(DATA_DIRECTORY, username + ".json");

        if (!Files.exists(filePath)) {
            System.out.println("Пользователя не найден!");
            return false;
        }
        currentUser = persistenceManager.loadUser(username);
        if(!currentUser.access(password)){
            System.out.println("Неверный пароль");
            currentUser = null;
            return false;
        }
        System.out.println("Вход успеный!");
        return true;
    }
    public void logout(){
        this.currentUser = null;
    }

    public void saveUserData() {
        if (currentUser == null) {
            throw new IllegalStateException("Пользователь не авторизован.");
        }
        persistenceManager.saveUser(currentUser);
    }

    public void registerUser(String username, String password) {
        currentUser =new User(username, password);
        System.out.println("Пользователь успешно зарегистрирован: " + username);
    }
    public void menu(Scanner scanner) {
        if (currentUser == null) {
            String username;
            String password;
            while(currentUser == null){
                System.out.print("Введите логин: ");
                username = scanner.nextLine();
                System.out.print("Введите пароль: ");
                password = scanner.nextLine();
                authenticate(username, password);
            }
        } else {
            logout();
        }
    }
}
