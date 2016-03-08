package com.pyshankov.lab1.ui;

import com.pyshankov.lab1.aspect.SecurityAspect;
import com.pyshankov.lab1.dao.UserDao;
import com.pyshankov.lab1.domain.User;
import com.pyshankov.lab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by pyshankov on 08.03.2016.
 */
@Component(value = "entrance")
public class EntranceDesktop {

    @Autowired
    private Scanner scanner;

    @Autowired
    ApplicationContext context;

    @Autowired
    SecurityAspect securityAspect;

    @Resource(name = "securityDecorator")
    private UserService userServiceSecure;

    @Autowired
    private UserDao userDao;

    public void showMenu(){

        UiHelper.showMenu();

        int chose=scanner.nextInt();
        scanner.nextLine();
        switch (chose){

            case 1: login();

            case 2: userServiceSecure.exit();

                default:
                {
                    System.out.println("invalid format");
                    showMenu();
                }
        }
    }

    private void login(){

        int counter = 0;
        String name;
        String psd;
        do {

            if(counter==3) {
                userServiceSecure.exit();
            }

            System.out.println("input your name");
            name = scanner.nextLine();
            System.out.println(name);

            System.out.println("input your password");
            psd = scanner.nextLine();
            System.out.println(psd);

//            psd =  new BigInteger(1, DigestUtils.md5Digest(psd.getBytes())).toString(16);

            if (userServiceSecure.login(name,psd)) break;
            else {
                counter++;
                if (counter!=3)
                System.out.println("введите правельный пароль или имя, колво попыток:"+(3-counter));
                if (counter==3)
                System.out.println("пока!");
            }


        }
         while (true);

        if (securityAspect.isBlocked(userServiceSecure.getFromCache())){
            System.out.println("you are blocked");
            userServiceSecure.exit();
        }

        if (securityAspect.isAdmin(userServiceSecure.getFromCache())){
            showForAdmin();
        }
        if (securityAspect.isUser(userServiceSecure.getFromCache())){
            showForUser();
        }


    }

    private void showForAdmin(){
        UiHelper.showForAdmin();
        int chose=scanner.nextInt();
        scanner.nextLine();

        switch (chose){
            case 1:{
                ( userServiceSecure.getAllUsers()).values().stream().forEach(System.out::println);
                showForAdmin();
            }


            case 2: addUser() ;

            case 3:blockUser();

            case 4:changePassword();

            case 5:restrictions();

            case 6: {
                userServiceSecure.removeFromCache();
                showMenu();
            }

            default:
            {
                System.out.println("invalid format");
                showForAdmin();
            }


        }
    }

    private void restrictions(){
        System.out.println("введите id");
        int id = scanner.nextInt();
        scanner.nextLine();
        User user = userDao.getUser(id);
        user.setHasRestrictions(true);
        userDao.updateUser(user);
        showForAdmin();
    }

    private void showForUser(){
        UiHelper.showForUser();
        int chose=scanner.nextInt();
        scanner.nextLine();

        switch (chose){

            case 1: changePassword();

            case 2: showData();

            case 3: {
                userServiceSecure.removeFromCache();
                showMenu();
            }

            default:
            {
                System.out.println("invalid format");
                showForUser();
            }
        }
    }

    private void changePassword(){
        User user = userServiceSecure.getFromCache();
        String pwd;
        System.out.println("input your old password");
        pwd = scanner.nextLine();
//        new BigInteger(1, DigestUtils.md5Digest(pwd.getBytes())).toString(16)
        if( pwd.equals(user.getHashedPassword()) ){
            System.out.println("input your new password");
            pwd = scanner.nextLine();
            if (!checkRestrictions(user,pwd))
            {user.setHashedPassword(pwd);
            userDao.updateUser(user);}
            else System.out.println("огпаничения на пароль!");
        }
        else {
            System.out.println("вы ввели не правельный пароль!");
        }
        if (user.getRole()== User.Role.ADMIN) showForAdmin();
        if (user.getRole()== User.Role.USER) showForUser();


    }

    private void showData(){
        System.out.println(userServiceSecure.getFromCache());
        showForUser();
    }

    private void addUser(){
        System.out.println("введите id");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("введите имя пользователя");
        String name = scanner.nextLine();

        userServiceSecure.addUser(new User(id,name));
        System.out.println("новый юзер был добавлен");

        showForAdmin();
    }

    private void blockUser(){
        System.out.println("введите id");
        int id = scanner.nextInt();
        scanner.nextLine();
        User user = userDao.getUser(id);
        user.setIsBlocked(true);
        userDao.updateUser(user);
        showForAdmin();
    }

    private boolean checkRestrictions(User u,String pwd){
            if(!u.getIsBlocked()) return true;
            else
            return pwd.equals(removeDublicate(pwd));
    }

    private  String removeDublicate(String s){
        char[] chars = s.toCharArray();
        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : chars) {
            charSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }
       return sb.toString();
    }

}
