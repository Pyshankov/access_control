package com.pyshankov.lab1.aspect;

import com.pyshankov.lab1.domain.User;

/**
 * Created by pyshankov on 08.03.2016.
 */
public interface SecurityAspect {

     boolean performVerification(User user, String password);

     default boolean isAdmin(User u) {
          return u.getRole() == User.Role.ADMIN;
     }

     default boolean isUser(User u) {
          return u.getRole() == User.Role.USER;
     }

     default boolean isBlocked(User u) {return u.getIsBlocked();}
}
