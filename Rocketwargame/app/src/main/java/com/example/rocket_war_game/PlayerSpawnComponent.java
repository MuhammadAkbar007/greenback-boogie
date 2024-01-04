package com.example.rocket_war_game;

public class PlayerSpawnComponent implements SpawnComponent{
    @Override
    public void spawn(Transform playerTransform, Transform t) {
        t.setLocation(t.getScreenSize().x /2, t.getScreenSize().y/2);
    }
}
