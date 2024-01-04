package com.example.snake3.mrsnake;


import com.example.snake3.framework.Screen;
import com.example.snake3.framework.impl.AndroidGame;

public class MrSnakeGame extends AndroidGame {
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
} 
