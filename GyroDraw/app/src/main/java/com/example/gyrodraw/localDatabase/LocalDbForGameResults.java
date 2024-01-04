package com.example.gyrodraw.localDatabase;

import android.content.Context;

import com.example.gyrodraw.home.battleLog.GameResult;

import java.util.List;


/**
 * Interface representing a generic handler for the local database, responsible of operations
 */
public interface LocalDbForGameResults {

    /**
     * Adds a game result to the local db.
     *
     * @param gameResult to insert
     */
    void addGameResultToDb(GameResult gameResult);

    /**
     * Retrieves the 10th most recent game results from the table.
     * @param context the context invoking this method
     * @return the newest game results
     */
    List<GameResult> getGameResultsFromDb(Context context);
}
