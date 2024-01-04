package com.example.snake3.mrsnake;

public class Eraser {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public int x, y;
    public int type;

    public Eraser(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
