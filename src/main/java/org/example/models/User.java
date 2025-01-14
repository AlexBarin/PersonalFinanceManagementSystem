package org.example.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private String username;
    private int password;
    private Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password.hashCode();
        this.wallet = new Wallet();//If user new, then new, else download from xml
    }

    public boolean access(String password) {
        return this.password == password.hashCode();
    }
}
