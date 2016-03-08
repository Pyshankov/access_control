package com.pyshankov.lab1.service;

import com.pyshankov.lab1.domain.User;
import sun.security.util.Password;

import java.util.Map;

/**
 * Created by pyshankov on 07.03.2016.
 */
public interface UserService {

    void changePassword(String password);
    Map<Integer,User> getAllUsers();
    void addUser(User user);
    void userToCache(User user,String pwd);
    void removeFromCache();
    User getFromCache();

    default boolean login(String name,String password){
        //implemented in SecurityDecorator
        throw new UnsupportedOperationException();
    }
    default void blockUser(User u) {u.setIsBlocked(true);}
    default void enableRestriction(User u) {u.setHasRestrictions(true);}
    default void exit() {
        removeFromCache();
        System.exit(0);
    }
}
