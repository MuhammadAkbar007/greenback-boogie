package com.example.gyrodraw.firebase;

import androidx.annotation.NonNull;

import com.example.gyrodraw.auth.Account;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;


/**
 * Utility wrapper class over {@link FirebaseFunctions}.
 */
public final class FbFunctions {

    private FbFunctions() {
    }

    /**
     * Makes the given {@link Account} join a room for playing in the given game mode.
     *
     * @param account the account of the current user
     * @param gameMode the game mode for which the player is searching a room
     * @return a {@link Task} wrapping the result
     */
    public static Task<String> joinRoom(Account account, int gameMode) {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();

        Map<String, Object> data = new HashMap<>();

        data.put("id", account.getUserId());
        data.put("username", account.getUsername());

        // Use regex to extract the number from the league string
        data.put("league", account.getCurrentLeague().replaceAll("\\D+", ""));

        data.put("mode", gameMode);

        return functions.getHttpsCallable("joinGame")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) {
                        return (String) task.getResult().getData();
                    }
                });
    }
}
