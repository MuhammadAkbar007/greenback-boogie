/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.snake_android.snakeLogic;

/**
 *
 * @author Kuba
 */
public class User {

    private String userName;
    private int points;

    public User() {

    }

    public User(String userName, int points) {
        this.userName = userName;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.points != other.points) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", points=" + points + '}';
    }

}
