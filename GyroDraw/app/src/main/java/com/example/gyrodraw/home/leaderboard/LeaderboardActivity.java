package com.example.gyrodraw.home.leaderboard;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gyrodraw.NoBackPressActivity;
import com.example.gyrodraw.R;
import com.example.gyrodraw.utils.GlideUtils;
import com.example.gyrodraw.utils.LayoutUtils;

/**
 * Class representing the leaderboard.
 */
public class LeaderboardActivity extends NoBackPressActivity {

    private Leaderboard leaderboard;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_leaderboard);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        GlideUtils.startBackgroundAnimation(this);

        final EditText searchField = findViewById(R.id.searchField);
        TextView exitButton = findViewById(R.id.exitButton);
        LayoutUtils.setFadingExitHomeListener(exitButton, this);
        exitButton.setTypeface(typeMuro);
        searchField.setTypeface(typeMuro);

        LinearLayout leaderboardView = findViewById(R.id.leaderboard);
        leaderboard = new Leaderboard(getApplicationContext(), leaderboardView);
        setCheckBoxListener(searchField);

        searchField.addTextChangedListener(getTextWatcher());
    }

    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
                // Not what we need.
            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                // Not what we need.
            }

            @Override
            public void afterTextChanged(Editable query) {
                leaderboard.update(query.toString());
            }
        };
    }

    private void setCheckBoxListener(final EditText searchField) {
        final CheckBox friendsFilterCheckbox = findViewById(R.id.friendsFilterCheckBox);
        final TextView friendsFilterText = findViewById(R.id.friendsFilterText);
        friendsFilterText.setTypeface(typeMuro);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 500) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                leaderboard.xorFilterByFriends();
                if (leaderboard.getFilterByFriends()) {
                    leaderboard.update(searchField.getText().toString());
                    friendsFilterCheckbox.setChecked(true);
                } else {
                    leaderboard.update(searchField.getText().toString());
                    friendsFilterCheckbox.setChecked(false);
                }
            }
        };

        friendsFilterCheckbox.setOnClickListener(clickListener);
        friendsFilterText.setOnClickListener(clickListener);
    }

    /**
     * Populates the leaderboard's cache with values from Firebase.
     */
    void initLeaderboard() {
        leaderboard.initLeaderboard();
    }
}
