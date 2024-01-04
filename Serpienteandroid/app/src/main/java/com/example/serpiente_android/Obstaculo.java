package com.example.serpiente_android;

import android.graphics.Bitmap;

public class Obstaculo {
    Position position;

    Bitmap skin;

    public Obstaculo(Position position, Bitmap skin) {
        this.position = position;
        this.skin = skin;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Bitmap getSkin() {
        return skin;
    }
}
