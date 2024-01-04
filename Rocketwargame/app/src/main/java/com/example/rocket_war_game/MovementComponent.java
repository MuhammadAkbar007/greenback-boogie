package com.example.rocket_war_game;

public interface MovementComponent {

    boolean move(long fps,
                 Transform t,
                 Transform playerTransform);
}
