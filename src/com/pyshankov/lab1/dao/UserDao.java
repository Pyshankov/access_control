package com.pyshankov.lab1.dao;

import com.pyshankov.lab1.domain.User;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyshankov on 05.03.2016.
 */
public interface UserDao {

    void addUser(User user);
    User getUser(int id);
    void updateUser(User user);
    void deleteUser(int id);
    Map<Integer,User> getAll();
    default User getByName(String name){
        return ((HashMap<Integer,User>) getAll()).values().stream().filter(u->u.getUserName().equalsIgnoreCase(name)).findFirst().get();
    }

}
