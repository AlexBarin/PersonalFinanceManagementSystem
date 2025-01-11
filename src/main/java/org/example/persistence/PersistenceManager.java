package org.example.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceManager {
    private static final String DATA_DIRECTORY = "data"; // Папка для хранения файлов
    private final Gson gson;

    public PersistenceManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // Убедимся, что папка для хранения данных существует
        Path directoryPath = Paths.get(DATA_DIRECTORY);
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Папка для данных создана: " + DATA_DIRECTORY);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать папку для данных: " + e.getMessage(), e);
        }
    }


    public void saveUser(User user) {
        Path filePath = Paths.get(DATA_DIRECTORY, user.getUsername() + ".json");

        try {
            String json = gson.toJson(user);
            Files.writeString(filePath, json); // Запись строки в файл
            System.out.println("Данные пользователя успешно сохранены: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных пользователя: " + e.getMessage());
        }
    }
    public User loadUser(String username) {
        Path filePath = Paths.get(DATA_DIRECTORY, username + ".json");

        if (!Files.exists(filePath)) {
            System.err.println("Файл для пользователя не найден: " + filePath);
            return null;
        }

        try {
            String json = Files.readString(filePath); // Чтение содержимого файла
            return gson.fromJson(json, User.class);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных пользователя: " + e.getMessage());
            return null;
        }
    }
}
