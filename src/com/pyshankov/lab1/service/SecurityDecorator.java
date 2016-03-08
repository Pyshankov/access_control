package com.pyshankov.lab1.service;

import com.pyshankov.lab1.aspect.SecurityAspect;
import com.pyshankov.lab1.dao.UserDao;
import com.pyshankov.lab1.domain.User;


import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by pyshankov on 08.03.2016.
 */

public class SecurityDecorator implements UserService {

    private UserService userService;

    private SecurityAspect securityAspect;

    private UserDao userDao;

    public SecurityDecorator(UserService userService,SecurityAspect securityAspect,UserDao dao){
        this.userService=userService;
        this.securityAspect=securityAspect;
        this.userDao=dao;
    }

    @Override
    public void changePassword(String password) {
        User user = this.getFromCache();
        if (securityAspect.isAdmin(user)||securityAspect.isUser(user))   userService.changePassword(password);
        else throw new SecurityException("permission denied");
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        if (securityAspect.isAdmin(this.getFromCache())) return userService.getAllUsers();
        else throw new SecurityException("permission denied");
    }

    @Override
    public void addUser(User user) {
        if(securityAspect.isAdmin(this.getFromCache()))
            userService.addUser(user);
        else throw new SecurityException("permission denied");
    }

    @Override
    public void userToCache(User user,String pwd) {
        if(securityAspect.performVerification(user,pwd))
            userService.userToCache(user,pwd);
        else throw new SecurityException("permission denied");
    }

    @Override
    public void removeFromCache() {
        userService.removeFromCache();
    }

    @Override
    public User getFromCache() {
        return userService.getFromCache();
    }

    @Override
    public boolean login(String user, String password) {
        try {
            userToCache(userDao.getByName(user),password);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
        catch (SecurityException e){
            return false;
        }
    }

}
