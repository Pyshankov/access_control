package com.pyshankov.lab1.domain;


import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by pyshankov on 27.02.2016.
 */
public class User  implements Serializable {

    private int id;
    private String userName;
    private boolean isBlocked;
    private String hashedPassword;
    private Role role;
    private boolean hasRestrictions;

    public User(){};

    public User(User u){
        this(u.getId(),u.getUserName(),u.getIsBlocked(),u.getHashedPassword(),u.getRole());
    }

    public User(int id,String userName){
        this(id,userName,false,"",Role.USER);
    }

    public User(int id,String userName, boolean isBlocked, String hashedPassword,Role role,boolean hasRestrictions) {
        this.id = id;
        this.userName = userName;
        this.isBlocked = isBlocked;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.hasRestrictions=hasRestrictions;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(int id, String userName, boolean isBlocked, String hashedPassword, Role role) {
        this.id = id;
        this.userName = userName;
        this.isBlocked = isBlocked;
        this.hashedPassword = hashedPassword;
        this.role = role;
        hasRestrictions=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setStatusBlocked(boolean idBlocked) {
        this.isBlocked = idBlocked;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isHasRestrictions() {
        return hasRestrictions;
    }

    public void setHasRestrictions(boolean restrictions) {
        this.hasRestrictions = restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public enum Role { ADMIN , USER; }

    public String toCsv(){
        return  String.format("%d,%s,%b,%s,%s,%b",
                this.id,
                this.userName,
                this.isBlocked,
                this.hashedPassword,
//                new BigInteger(1,DigestUtils.md5Digest(this.hashedPassword.getBytes())).toString(16) ,
                this.role.toString(),
                this.hasRestrictions
        );
    }
    public static User toUserFromCsv(String values){
        String[] s = values.split(",");
        return new User(Integer.valueOf(s[0]),
                s[1],
                Boolean.valueOf(s[2]),
                s[3],
                Role.valueOf(s[4]),
                Boolean.valueOf(s[5]));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", isBlocked=" + isBlocked +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", role=" + role +
                '}';
    }
}
