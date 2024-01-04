/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is a POJO for storing color and its name
 */

package com.example.pop_the_balloons_game.utils;

public class GameColor{
    public int color;
    public String name;

    public GameColor(int color,String name){
        this.color = color;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}