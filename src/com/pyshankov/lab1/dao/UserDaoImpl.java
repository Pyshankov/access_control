package com.pyshankov.lab1.dao;

import com.pyshankov.lab1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by pyshankov on 05.03.2016.
 */
@Repository( value = "userDaoRepository")
public class UserDaoImpl implements UserDao{

    @Resource(name ="file")
    private File storage;



    @Override
    public void addUser(User user) {
        try(FileWriter fileWriter = new FileWriter(storage,true)){
            fileWriter.append(user.toCsv()+"\n");
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public User getUser(int id) {
        try(Stream<String> st = Files.lines(storage.toPath())) {
            return st.distinct().map(User::toUserFromCsv).filter((user)-> user.getId()==id).findFirst().get();
        }catch (IOException e){
            e.printStackTrace();
        }
        throw new NoSuchElementException("could not find a user in database!");
    }

    @Override
    public void updateUser(User user) {
        try {
            List<String> lines= Files.readAllLines(storage.toPath());
            List<String> lines2 = new ArrayList<>();
                    lines.stream()
                    .map(User::toUserFromCsv).forEach(
                        (user1)->
                            {
                                if(user1.getId()==user.getId()) user1=user;
                                lines2.add(user1.toCsv());
                            }
                        );
            Files.write(storage.toPath(), lines2);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            List<String> lines= Files.readAllLines(storage.toPath());

            List<String> lines2 = new ArrayList<>();
            lines.stream()
                    .map(User::toUserFromCsv).forEach(
                    (user)->
                    {
                        if(user.getId()!=id) lines2.add(user.toCsv());
                    }
            );
            Files.write(storage.toPath(), lines2);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, User> getAll() {
        try(Stream<String> st = Files.lines(storage.toPath())) {
            return st.distinct().map(User::toUserFromCsv).sorted(Comparator.comparing(User::getId)).collect(Collectors.toMap(User::getId, user -> user));
        }catch (IOException e){
           e.printStackTrace();
        }
        throw new  UnknownFormatConversionException("some problems while retrieving");
    }
}