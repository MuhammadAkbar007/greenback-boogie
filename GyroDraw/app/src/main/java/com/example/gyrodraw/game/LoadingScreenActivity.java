package com.example.gyrodraw.game;


import static com.example.gyrodraw.firebase.RoomAttributes.GAME_MODE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.example.gyrodraw.NoBackPressActivity;
import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.firebase.FbDatabase;
import com.example.gyrodraw.firebase.OnSuccessValueEventListener;
import com.example.gyrodraw.firebase.RoomAttributes;
import com.example.gyrodraw.home.HomeActivity;
import com.example.gyrodraw.matchmaking.Matchmaker;
import com.example.gyrodraw.utils.BooleanVariableListener;
import com.example.gyrodraw.utils.GlideUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Class encapsulating methods necessary for communicating with the backend before showing to the
 * player the {@link WaitingPageActivity}.
 */
public class LoadingScreenActivity extends NoBackPressActivity {

    public static final String WORD_1 = "word1";
    public static final String WORD_2 = "word2";
    public static final String ROOM_ID = "roomID";

    private static boolean enableWaitingAnimation = true;
    private static boolean isTesting = false;

    private String roomID = null;
    private int gameMode = 0;

    @VisibleForTesting
    public BooleanVariableListener isRoomReady = new BooleanVariableListener();
    @VisibleForTesting
    public BooleanVariableListener areWordsReady = new BooleanVariableListener();

    private boolean hasLeft = false;

    private boolean isWord1Ready = false;
    private boolean isWord2Ready = false;

    @VisibleForTesting
    public DatabaseReference wordsVotesRef;

    private String word1 = null;
    private String word2 = null;

    @VisibleForTesting
    public BooleanVariableListener.ChangeListener listenerRoomReady =
            new BooleanVariableListener.ChangeListener() {
                @Override
                public void onChange() {
                    if (areWordsReady.getBool() && isRoomReady.getBool()) {
                        // Start new activity
                        wordsVotesRef.removeEventListener(listenerWords);
                        Intent intent = new Intent(getApplicationContext(),
                                WaitingPageActivity.class);
                        intent.putExtra(WORD_1, word1);
                        intent.putExtra(WORD_2, word2);
                        intent.putExtra(ROOM_ID, roomID);
                        intent.putExtra(GAME_MODE, gameMode);
                        startActivity(intent);
                    }
                }
            };

    @VisibleForTesting
    public final ValueEventListener listenerWords = new OnSuccessValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<String> words = new ArrayList<>();

            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                words.add(snap.getKey());
            }

            if (areWordsReady(words)) {
                areWordsReady.setBool(true);
            }
        }
    };

    @VisibleForTesting
    protected boolean areWordsReady(ArrayList<String> words) {
        try {
            word1 = words.get(0);
            word2 = words.get(1);
        } catch (Exception e) {
            // Values not ready
        }

        if (word1 != null) {
            isWord1Ready = true;
        }

        if (word2 != null) {
            isWord2Ready = true;
        }

        return (isWord1Ready && isWord2Ready);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_loading_screen);

        gameMode = getIntent().getIntExtra(GAME_MODE, 0);

        if (!isTesting) {
            lookingForRoom(gameMode);
        }

        isRoomReady.setListener(listenerRoomReady);
        areWordsReady.setListener(listenerRoomReady);

        if (enableWaitingAnimation) {
            GlideUtils.startDotsWaitingAnimation(this);
            GlideUtils.startBackgroundAnimation(this);
        }

    }

    private void lookingForRoom(int gameMode) {
        Matchmaker.getInstance(Account.getInstance(this))
                .joinRoom(gameMode).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    launchActivity(HomeActivity.class);
                    finish();
                } else {
                    roomID = task.getResult();
                    if (hasLeft) {
                        Matchmaker.getInstance(Account.getInstance(getApplicationContext()))
                                .leaveRoom(roomID);
                        finish();
                    } else {
                        wordsVotesRef = FbDatabase.getRoomAttributeReference(roomID,
                                RoomAttributes.WORDS);
                        wordsVotesRef.addValueEventListener(listenerWords);
                        isRoomReady.setBool(true);
                    }
                }
            }
        });
    }

    @VisibleForTesting
    public static void disableLoadingAnimations() {
        enableWaitingAnimation = false;
    }

    @VisibleForTesting
    public static void setOnTest() {
        isTesting = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        hasLeft = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @VisibleForTesting
    String getRoomId() {
        return roomID;
    }
}
