package com.example.gyrodraw.home.battleLog;

import android.os.Bundle;
import androidx.annotation.VisibleForTesting;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.gyrodraw.NoBackPressActivity;
import com.example.gyrodraw.R;
import com.example.gyrodraw.localDatabase.LocalDbForGameResults;
import com.example.gyrodraw.localDatabase.LocalDbHandlerForGameResults;
import com.example.gyrodraw.utils.GlideUtils;
import com.example.gyrodraw.utils.LayoutUtils;

import java.util.List;

/**
 * Class representing the battle log.
 */
public class BattleLogActivity extends NoBackPressActivity {

    private LinearLayout battleLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_battle_log);

        battleLogView = findViewById(R.id.battleLog);

        GlideUtils.startBackgroundAnimation(this);

        TextView exitButton = findViewById(R.id.exitButton);
        TextView emptyBattleLogText = findViewById(R.id.emptyBattleLogText);
        LayoutUtils.setFadingExitHomeListener(exitButton, this);

        exitButton.setTypeface(typeMuro);
        emptyBattleLogText.setTypeface(typeMuro);
        ((TextView) findViewById(R.id.battleLogText)).setTypeface(typeMuro);

        fetchGameResults();

        // Hide or display the empty battle log text
        ScrollView scrollBattleLog = findViewById(R.id.scrollBattleLog);
        if (battleLogView.getChildCount() == 0) {
            ((ViewManager) scrollBattleLog.getParent()).removeView(scrollBattleLog);
        } else {
            ((ViewManager) emptyBattleLogText.getParent()).removeView(emptyBattleLogText);
        }
    }

    /**
     * Fetches the latest game results in the local database, converts them to views
     * and adds them to the layout.
     */
    private void fetchGameResults() {
        LocalDbForGameResults localDb =
                new LocalDbHandlerForGameResults(this, null, 1);
        List<GameResult> gameResults = localDb.getGameResultsFromDb(this);

        for (GameResult gameResult : gameResults) {
            if (gameResult != null) {
                battleLogView.addView(gameResult.toLayout(this));
            }
        }
    }

    /**
     * Returns the number of game result currently displayed.
     */
    @VisibleForTesting
    public int getGameResultsCount() {
        return ((LinearLayout) findViewById(R.id.gameResults)).getChildCount();
    }
}
