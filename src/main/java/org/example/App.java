package org.example;

import org.example.models.User;
import org.example.models.Wallet;
import org.example.persistence.PersistenceManager;
import org.example.services.UserService;
import org.example.services.WalletService;
import org.example.ui.ConsoleUI;


public class App
{
    public static void main( String[] args )
    {
        PersistenceManager pm = new PersistenceManager();
        UserService userService = new UserService(pm);
        WalletService walletService = new WalletService(new Wallet());
        ConsoleUI console = new ConsoleUI(userService, walletService);
        console.start();

    }
}
