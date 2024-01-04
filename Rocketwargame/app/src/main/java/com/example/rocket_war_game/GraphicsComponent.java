package com.example.rocket_war_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public interface GraphicsComponent {

    void initialize(Context c, ObjectSpec s, PointF objectSize);

    void draw(Canvas canvas, Paint paint, Transform t);
}
