package com.example.gyrodraw.home.leaderboard;

import static com.example.gyrodraw.firebase.AccountAttributes.LEAGUE;
import static com.example.gyrodraw.firebase.AccountAttributes.TROPHIES;
import static com.example.gyrodraw.firebase.AccountAttributes.USERNAME;
import static com.example.gyrodraw.firebase.AccountAttributes.USER_ID;
import static com.example.gyrodraw.home.FriendsRequestState.FRIENDS;
import static com.example.gyrodraw.home.FriendsRequestState.fromInteger;
import static com.example.gyrodraw.utils.LayoutUtils.getLeagueImageId;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.firebase.AccountAttributes;
import com.example.gyrodraw.firebase.FbDatabase;
import com.example.gyrodraw.firebase.OnSuccessValueEventListener;
import com.example.gyrodraw.utils.OnlineStatus;
import com.example.gyrodraw.utils.TestUsers;
import com.example.gyrodraw.utils.TypefaceLibrary;
import com.google.firebase.database.DataSnapshot;

import java.util.LinkedList;

/**
 * Helper class to manage and display user data from Firebase. Needs to be package-private.
 */
class Player implements Comparable {

    private final Context context;
    private final String userId;
    private final String username;
    private final Long trophies;
    private final String league;
    private int rank;
    private final boolean isFriend;
    private final boolean isCurrentUser;

    Player(Context context, String userId, String username, Long trophies, String league,
           boolean isFriend, boolean isCurrentUser) {
        this.context = context;
        this.userId = userId;
        this.username = username;
        this.trophies = trophies;
        this.league = league;
        this.isFriend = isFriend;
        this.isCurrentUser = isCurrentUser;
    }

    String getUserId() {
        return userId;
    }

    /**
     * Allows one to call sort on a collection of Players. Sorts collection in descending order.
     *
     * @param object Player to compare
     * @return 1 if this is larger, -1 if this is smaller, 0 else
     */
    @Override
    public int compareTo(Object object) {
        int compareTrophies = -this.trophies.compareTo(((Player) object).trophies);
        if (compareTrophies == 0) {
            return this.username.compareTo(((Player) object).username);
        }
        return compareTrophies;
    }

    /**
     * Returns true if the player name contains the given string, false otherwise.
     *
     * @param query the string to search for in the player name
     * @return true if the player name contains the given string, false otherwise
     */
    boolean playerNameContainsString(String query) {
        return username.toUpperCase().contains(query.toUpperCase());
    }

    /**
     * Converts this player into a LinearLayout that will be displayed on the leaderboard.
     *
     * @param index of the player
     * @return LinearLayout that will be displayed
     */
    LinearLayout toLayout(int index) {
        TextView usernameView = new TextView(context);
        Resources res = context.getResources();
        styleView(usernameView, rank + ". " + username, res.getColor(
                isCurrentUser ? R.color.colorPrimaryDark : R.color.colorDrawYellow),
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5));
        usernameView.setPadding(0, 10, 0, 10);

        TextView trophiesView = new TextView(context);
        styleView(trophiesView, trophies.toString(),
                res.getColor(R.color.colorPrimaryDark),
                new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        trophiesView.setTextAlignment(RelativeLayout.TEXT_ALIGNMENT_TEXT_END);

        ImageView leagueView = new ImageView(context);
        leagueView.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        leagueView.setImageResource(getLeagueImageId(league));

        final FriendsButton friendsButton =
                new FriendsButton(context, this, index, isCurrentUser);

        LinearLayout entry = addViews(new LinearLayout(context),
                new View[]{usernameView, trophiesView, leagueView, friendsButton});

        if (isFriend && !isCurrentUser) {
            createAndAddOnlineView(entry, usernameView);
        }

        entry.setBackgroundColor(res.getColor(
                isCurrentUser ? R.color.colorDrawYellow : R.color.colorLightGrey));
        entry.setPadding(30, 10, 30, 10);

        return entry;
    }

    private void createAndAddOnlineView(final LinearLayout entry, final TextView usernameView) {
        FbDatabase.getUserOnlineStatus(userId, new OnSuccessValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer data = dataSnapshot.getValue(Integer.class);
                if (data != null) {
                    OnlineStatus onlineStatus = OnlineStatus.fromInteger(data);
                    if (onlineStatus == OnlineStatus.ONLINE) {
                        ImageView onlineView = new ImageView(context);
                        onlineView.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        onlineView.setImageResource(R.drawable.online);
                        onlineView.setPadding(0, 10, 0, 10);

                        usernameView.setLayoutParams(new LinearLayout.LayoutParams(0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT, 4));
                        entry.addView(onlineView, 1);
                    }
                }
            }
        });
    }

    private LinearLayout addViews(LinearLayout layout, View[] views) {
        for (View view : views) {
            layout.addView(view);
        }
        return layout;
    }

    private void styleView(TextView view, String text, int color,
                           LinearLayout.LayoutParams layoutParams) {
        view.setText(text);
        view.setTextSize(20);
        view.setMaxLines(1);
        view.setTextColor(color);
        view.setTypeface(TypefaceLibrary.getTypeMuro());
        view.setLayoutParams(layoutParams);
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Checks if the received player is not a test-user and if all values are available. Then adds
     * the player to allPlayers.
     *
     * @param snapshot to convert
     */
    static void convertSnapshotToPlayerAndAddToList(Context context, DataSnapshot snapshot,
                                                    LinkedList<Player> players) {
        String userId = snapshot.child(USER_ID).getValue(String.class);
        String username = snapshot.child(USERNAME).getValue(String.class);
        Long trophies = snapshot.child(TROPHIES).getValue(Long.class);
        String league = snapshot.child(LEAGUE).getValue(String.class);
        if (!TestUsers.isTestUser(snapshot.getKey())
                && userId != null
                && username != null
                && trophies != null
                && league != null) {
            boolean isFriend = false;
            for (DataSnapshot friend : snapshot.child(AccountAttributes.FRIENDS).getChildren()) {
                if (friend.getKey().equals(Account.getInstance(context).getUserId())
                        && fromInteger(friend.getValue(int.class)) == FRIENDS) {
                    isFriend = true;
                }
            }
            Player temp = new Player(context, userId, username, trophies, league, isFriend,
                    username.equals(
                            Account.getInstance(context)
                                    .getUsername()));

            players.add(temp);
        }
    }
}
