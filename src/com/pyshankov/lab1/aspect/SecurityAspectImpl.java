package com.pyshankov.lab1.aspect;

import com.pyshankov.lab1.domain.User;


/**
 * Created by pyshankov on 07.03.2016.
 */

public class SecurityAspectImpl implements SecurityAspect {
    public boolean performVerification(User user,String password){
        return user.getHashedPassword().equals(password);
    }
}
