/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.snake_android.snakeLogic;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuba
 */
public class Snake {

    private List<Position> position = new ArrayList<Position>();
    public int length = 0;

    public Snake() {
    }

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
