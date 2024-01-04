package com.example.rocket_war_game;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public interface InputObserver {
    void handleInput(MotionEvent event, GameState gs, ArrayList<Circle> controls);
}
