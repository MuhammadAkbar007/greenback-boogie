package com.example.gyrodraw.home.leaderboard;

import static com.example.gyrodraw.firebase.FbDatabase.getFriend;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.firebase.OnSuccessValueEventListener;
import com.example.gyrodraw.home.FriendsRequestState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

/**
 * Button to show if a user is a friend of current user and to manage friends requests.
 * Needs to be package-private.
 */
class FriendsButton extends AppCompatImageView {

    private static final int SENT = FriendsRequestState.SENT.ordinal();
    private static final int FRIENDS = FriendsRequestState.FRIENDS.ordinal();

    private final Context context;
    private final Account account;
    private final Player player;
    private final int index;
    private final boolean isCurrentUser;

    FriendsButton(final Context context, final Player player, int index, boolean isCurrentUser) {
        super(context);
        this.context = context;
        this.account = Account.getInstance(context);
        this.player = player;
        this.index = index;
        this.isCurrentUser = isCurrentUser;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getFriend(account.getUserId(),
                        player.getUserId(),
                        changeFriendsButtonImageOnClick());
            }
        });
        getFriend(account.getUserId(), player.getUserId(),
                initializeFriendsButton());
        initLayout();
    }

    private void initLayout() {
        LinearLayout.LayoutParams friendsParams =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        setLayoutParams(friendsParams);

        // Give Button unique Tag to test them later
        setTag("friendsButton" + index);

        // Set friendsButton invisible to yourself
        if (isCurrentUser) {
            setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Checks if users are already friends and sets image accordingly.
     *
     * @return listener
     */
    private ValueEventListener initializeFriendsButton() {
        return new OnSuccessValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    initializeImageCorrespondingToFriendsState(
                            dataSnapshot.getValue(int.class));
                } else {
                    setImageResource(R.drawable.add_friend);
                }
            }
        };
    }

    /**
     * Sets the image depending on the friends state.
     *
     * @param state current state of friendship
     */
    @VisibleForTesting
    public void initializeImageCorrespondingToFriendsState(int state) {
        if (state == SENT) {
            setImageResource(R.drawable.pending_friend);
        } else if (state == FRIENDS) {
            setImageResource(R.drawable.remove_friend);
        } else {
            setImageResource(R.drawable.add_friend);
        }
    }

    /**
     * Friends button got clicked, now add/remove friend and modify image.
     *
     * @return listener
     */
    private ValueEventListener changeFriendsButtonImageOnClick() {
        return new OnSuccessValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setImageAndUpdateFriendsState(dataSnapshot.getValue(int.class));
                } else {
                    Account.getInstance(context).addFriend(player.getUserId());
                    setImageResource(R.drawable.pending_friend);
                }
            }
        };
    }

    /**
     * Helper function for changeFriendsButtonImageOnClick.
     * Updates the friends state depending on status.
     *
     * @param state current state of friendship
     */
    @VisibleForTesting
    public void setImageAndUpdateFriendsState(int state) {
        switch (FriendsRequestState.fromInteger(state)) {
            case RECEIVED:
                Account.getInstance(context).addFriend(player.getUserId());
                setImageResource(R.drawable.remove_friend);
                break;
            case FRIENDS:
            case SENT:
                Account.getInstance(context).removeFriend(player.getUserId());
                setImageResource(R.drawable.add_friend);
                break;
            default:
                break;
        }
    }
}