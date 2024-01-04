package com.example.pop_the_balloons_game;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;

public class ShapeView extends View {
    Shape shape;

    public ShapeView(Context context) {
        super(context);
        init(context);
    }


    /*
     * Initialize the shape
     *
     * */
    public void init(Context context){

    }

    public ShapeView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
