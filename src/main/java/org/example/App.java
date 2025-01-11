package org.example;

import org.example.models.User;
import org.example.persistence.PersistenceManager;
import org.example.services.UserService;

public class App
{
    public static void main( String[] args )
    {
        PersistenceManager pm = new PersistenceManager();


        User user = new User("alex","11234");
        pm.saveUser(user);
        System.out.println(pm.loadUser("alex"));
        UserService userService = new UserService(pm);
        userService.authenticate("alex","1134");

    }
}
