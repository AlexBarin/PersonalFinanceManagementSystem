package org.example;

import java.util.Scanner;

public interface PersonAction {
    void registration(Scanner sc);
    boolean login(Scanner sc);
    void logout();

}
