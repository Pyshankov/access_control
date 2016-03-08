package com.pyshankov.lab1.service;

import com.pyshankov.lab1.dao.UserDao;
import com.pyshankov.lab1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by pyshankov on 07.03.2016.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Resource(name = "cache")
    private File cache;

    @Override
    public void changePassword( String password) {
           User user= userDao.getUser(getFromCache().getId());
            user.setHashedPassword(password);
            userDao.updateUser(user);
    }

    @Override
    public Map<Integer,User> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public void addUser(User user) {
        user.setHashedPassword("");
        userDao.addUser(user);
    }

    @Override
    public void userToCache(User user,String pwd) {
        try(FileWriter fileWriter = new FileWriter(cache)){
            fileWriter.append(String.valueOf(user.getId()));
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public User getFromCache() {
        try (BufferedReader br = new BufferedReader(new FileReader(cache))) {
            String line ;
            User user ;
            while ((line = br.readLine()) != null) {
                user = userDao.getUser(Integer.parseInt(line.trim()));
                return user;
            }
        }catch(IOException e){

        }
        throw new NoSuchElementException();
    }

    @Override
    public void removeFromCache() {
        cache.delete();
    }
}
