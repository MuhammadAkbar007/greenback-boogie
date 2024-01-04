/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.snake_android.snakeLogic;

/**
 * @author Kuba
 */
public class Position {

    private int r;
    private int c;

    public Position() {
    }

    public Position(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Position other = (Position) obj;
        if (this.r != other.r) {
            return false;
        }
        if (this.c != other.c) {
            return false;
        }
        return true;
    }

}
