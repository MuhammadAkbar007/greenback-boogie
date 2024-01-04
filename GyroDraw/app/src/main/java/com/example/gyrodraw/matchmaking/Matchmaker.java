package com.example.gyrodraw.matchmaking;

import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.firebase.FbDatabase;
import com.example.gyrodraw.firebase.FbFunctions;
import com.google.android.gms.tasks.Task;


/**
 * Singleton class that represents the matchmaker.
 */
public final class Matchmaker implements MatchmakingInterface {

    private static Matchmaker instance = null;

    private final Account account;

    private Matchmaker(Account account) {
        if (instance != null) {
            throw new IllegalStateException("Already instantiated");
        }

        this.account = account;
    }

    /**
     * Gets (eventually creates) the instance.
     *
     * @return the unique instance.
     */
    public static Matchmaker getInstance(Account account) {
        if (instance == null) {
            instance = new Matchmaker(account);
        }

        return instance;
    }

    @Override
    public Task<String> joinRoom(int gameMode) {
        return FbFunctions.joinRoom(account, gameMode);
    }

    @Override
    public void leaveRoom(String roomId) {
        if (!account.getUsername().isEmpty()) {
            FbDatabase.removeUserFromRoom(roomId, account);
        }
    }
}
