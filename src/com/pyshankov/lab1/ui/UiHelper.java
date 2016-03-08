package com.pyshankov.lab1.ui;

/**
 * Created by pyshankov on 06.03.2016.
 */
public class UiHelper {
    public static void showMenu(){
        System.out.println("============================");
        System.out.println("|           MENU           |");
        System.out.println("============================");
        System.out.println("|        1. Войти          |");
        System.out.println("|        2. Exit           |");
        System.out.println("============================");
    }
    public static void showForUser(){
        System.out.println("============================");
        System.out.println("|           MENU           |");
        System.out.println("============================");
        System.out.println("|   1. Изменить пароль     |");
        System.out.println("|  2. Посмотреть данные    |");
        System.out.println("|        3. Exit           |");
        System.out.println("============================");
    }
    public static void showForAdmin(){
        System.out.println("============================");
        System.out.println("|           MENU           |");
        System.out.println("============================");
        System.out.println("|1.Посмотреть пользователей|");
        System.out.println("|2.Добавить пользователя   |");
        System.out.println("|3.Блокировать Пользователя|");
        System.out.println("|   4. Изменить пароль     |");
        System.out.println("|  5.включить ограничения  |");
        System.out.println("|        6. Exit           |");
        System.out.println("============================");
    }
}
