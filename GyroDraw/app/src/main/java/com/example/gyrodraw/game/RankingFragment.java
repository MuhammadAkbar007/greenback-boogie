package com.example.gyrodraw.game;

import static com.example.gyrodraw.firebase.RoomAttributes.FINISHED;
import static com.example.gyrodraw.firebase.RoomAttributes.RANKING;
import static com.example.gyrodraw.utils.LayoutUtils.bounceButton;
import static com.example.gyrodraw.utils.LayoutUtils.isPointInsideView;
import static com.example.gyrodraw.utils.LayoutUtils.pressButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.firebase.FbDatabase;
import com.example.gyrodraw.firebase.OnSuccessValueEventListener;
import com.example.gyrodraw.home.battleLog.GameResult;
import com.example.gyrodraw.localDatabase.LocalDbForGameResults;
import com.example.gyrodraw.localDatabase.LocalDbHandlerForGameResults;
import com.example.gyrodraw.utils.LayoutUtils;
import com.example.gyrodraw.utils.RankingUtils;
import com.example.gyrodraw.utils.SortUtils;
import com.example.gyrodraw.utils.TypefaceLibrary;
import com.google.firebase.database.DataSnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom {@link ListFragment} used for displaying the final ranking at the end of the game.
 */
public class RankingFragment extends ListFragment {

    private String roomId;

    private Map<String, Integer> finalRanking;

    private Bitmap[] drawings;
    private String[] playerNames;

    private Account account;
    private VotingPageActivity activity;

    public RankingFragment() {
        // Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ranking_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((TextView) getActivity().findViewById(R.id.rankingTitle))
                .setTypeface(TypefaceLibrary.getTypeMuro());

        Button button = getActivity().findViewById(R.id.homeButton);
        button.setTypeface(TypefaceLibrary.getTypeMuro());

        account = Account.getInstance(getActivity().getApplicationContext());

        retrieveFinalRanking();
        setHomeButtonListener();
    }

    /**
     * Sets the attributes of this class.
     *
     * @param roomId      the id of the room.
     * @param drawings    the users drawings.
     * @param playerNames the usernames of the players.
     * @param activity    the activity instantiating the fragment
     */
    public void putExtra(String roomId, Bitmap[] drawings, String[] playerNames,
                         VotingPageActivity activity) {
        this.roomId = roomId;
        this.drawings = drawings.clone();
        this.playerNames = playerNames.clone();
        this.activity = activity;
    }

    private int getIndexForUserName(String username) {
        for (int i = 0; i < this.playerNames.length; i++) {
            if (username.equals(playerNames[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Index not found");
    }

    private void retrieveFinalRanking() {
        FbDatabase.getRoomAttribute(roomId, RANKING,
                new OnSuccessValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        finalRanking = new HashMap<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue(Integer.class) != null && ds.getKey() != null) {
                                finalRanking.put(ds.getKey(), ds.getValue(Integer.class));
                            }
                        }

                        // Sort the rankings (stars)
                        Integer[] rankings = (finalRanking.values().toArray(
                                new Integer[0]));
                        Arrays.sort(rankings, Collections.reverseOrder());

                        int rankForUser = 0;
                        if (dataSnapshot.child(account.getUsername()).getValue(int.class) != null) {
                            rankForUser = dataSnapshot.child(account.getUsername())
                                    .getValue(int.class);
                        }

                        Integer[] trophies = RankingUtils.generateTrophiesFromRanking(rankings);
                        Integer[] positions = RankingUtils.generatePositionsFromRanking(rankings);
                        List<String> usernames = SortUtils.sortByValue(finalRanking);

                        int trophiesForUser = trophies[usernames.indexOf(account.getUsername())];

                        Boolean won = usernames.get(0).equals(account.getUsername());
                        updateUserStats(rankForUser, trophiesForUser, won);
                        createAndStoreGameResult(usernames, usernames.indexOf(
                                account.getUsername()), rankForUser, trophiesForUser);

                        String[] tmpUserNames = usernames.toArray(new String[usernames.size()]);
                        ArrayAdapter<String> adapter = new RankingAdapter(getActivity(),
                                tmpUserNames, rankings, trophies, drawings, positions);
                        setListAdapter(adapter);
                        setFinishedCollectingRanking();
                    }
                });
    }

    private void updateUserStats(int starIncrease, int trophiesIncrease, boolean won) {
        account.changeStars(starIncrease);
        account.changeTrophies(trophiesIncrease);
        account.increaseTotalMatches();
        account.changeAverageRating(starIncrease);
        if (won) {
            account.increaseMatchesWon();
        }
    }

    /**
     * Create a game result and save the result into the local database.
     */
    public void createAndStoreGameResult(List<String> names, int rank, int stars, int trophies) {
        String userNameId = account.getUsername();

        Bitmap drawing = drawings[getIndexForUserName(userNameId)];

        if (drawing != null) {
            GameResult gameResult = new GameResult(names, rank, stars, trophies, drawing);
            LocalDbForGameResults localDb =
                    new LocalDbHandlerForGameResults(this.getActivity(), null, 1);
            localDb.addGameResultToDb(gameResult);
        }
    }

    private void setFinishedCollectingRanking() {
        FbDatabase.setValueToUserInRoomAttribute(roomId, account.getUsername(), FINISHED, 1);
    }

    private class RankingAdapter extends ArrayAdapter<String> {

        private final String[] players;
        private final Integer[] rankings;
        private final String[] trophies;
        private final Bitmap[] drawings;
        private final Integer[] positions;

        private RankingAdapter(Context context, String[] players, Integer[] rankings,
                               Integer[] trophies, Bitmap[] drawings, Integer[] positions) {
            super(context, 0, players);
            this.players = players;
            this.rankings = rankings;
            this.trophies = RankingUtils.addSignToNumberList(trophies);
            this.drawings = drawings;
            this.positions = positions;
        }

        private void setTextToViews(int position, View convertView) {
            ((TextView) convertView.findViewById(R.id.playerName))
                    .setText(String.format("%d. %s", positions[position], players[position]));
            ((TextView) convertView.findViewById(R.id.trophiesWon))
                    .setText(trophies[position]);
            ((TextView) convertView.findViewById(R.id.starsWon))
                    .setText(RankingUtils.addSignToNumber(Math.max(rankings[position], 0)));
        }

        private void setHighlightColors(View convertView) {
            int yellowColor = getResources().getColor(R.color.colorDrawYellow);
            int greyColor = getResources().getColor(R.color.colorGrey);

            ((TextView) convertView.findViewById(R.id.playerName)).setTextColor(yellowColor);
            ((TextView) convertView.findViewById(R.id.trophiesWon)).setTextColor(yellowColor);
            ((TextView) convertView.findViewById(R.id.starsWon)).setTextColor(yellowColor);
            convertView.setBackgroundColor(greyColor);
        }

        private void setTypeFace(Typeface typeface, View... views) {
            for (View view : views) {
                ((TextView) view).setTypeface(typeface);
            }
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.ranking_item, parent, false);

            setTypeFace(TypefaceLibrary.getTypeMuro(), convertView.findViewById(R.id.playerName),
                    convertView.findViewById(R.id.starsWon),
                    convertView.findViewById(R.id.trophiesWon),
                    convertView.findViewById(R.id.disconnectedRanking));

            // Update image
            ImageView drawingView = convertView.findViewById(R.id.drawing);
            TextView disconnectedTextView = convertView.findViewById(R.id.disconnectedRanking);
            if (rankings[position] >= 0) {
                disconnectedTextView.setVisibility(View.GONE);
                drawingView.setVisibility(View.VISIBLE);
                drawingView.setImageBitmap(drawings[getIndexForUserName(players[position])]);
            } else {
                disconnectedTextView.setVisibility(View.VISIBLE);
                drawingView.setVisibility(View.GONE);
            }

            // Set the color
            if (!players[position].equals(account.getUsername())) {
                setHighlightColors(convertView);
            }

            setTextToViews(position, convertView);

            // Return the completed view to render on screen
            return convertView;
        }
    }

    private void setHomeButtonListener() {
        activity.findViewById(R.id.homeButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressButton(view, LayoutUtils.AnimMode.CENTER, activity);
                        break;
                    case MotionEvent.ACTION_UP:
                        bounceButton(view, activity);
                        if (isPointInsideView(event.getRawX(), event.getRawY(), view)) {
                            activity.startHomeActivity();
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }
}
