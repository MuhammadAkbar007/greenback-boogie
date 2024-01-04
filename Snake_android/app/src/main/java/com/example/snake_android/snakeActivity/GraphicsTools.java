package com.example.snake_android.snakeActivity;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kuba on 17.07.2017.
 */

public class GraphicsTools {

    private static float WIDTH = 0;
    private static float HEIGHT = 0;

    public void drawLines(Canvas canvas, int linesColor, Paint paint) {
        paint.setColor(linesColor);
        WIDTH = canvas.getWidth() / 12;
        HEIGHT = canvas.getHeight() / 20;

        for (int i = 0; i < 13; i++) {
            canvas.drawLine(i * WIDTH, 0, i * WIDTH, 20 * HEIGHT, paint);
        }
        for (int i = 0; i < 21; i++) {
            canvas.drawLine(0, i * HEIGHT, 12 * WIDTH, i * HEIGHT, paint);
        }
    }
}
