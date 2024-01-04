package com.example.gyrodraw.utils;

import static com.example.gyrodraw.firebase.AccountAttributes.STATUS;
import static com.example.gyrodraw.utils.Preconditions.checkPrecondition;

import com.example.gyrodraw.firebase.FbDatabase;
import com.google.firebase.database.DatabaseReference;


/**
 * Enum representing whether the user is online or offline.
 */
public enum OnlineStatus {
    OFFLINE, ONLINE;

    /**
     * Builds a {@link OnlineStatus} value from the given integer.
     *
     * @param integer the integer corresponding to the desired enum value
     * @return an enum value
     * @throws IllegalArgumentException if the given integer does not correspond to a status
     */
    public static OnlineStatus fromInteger(int integer) {
        switch (integer) {
            case 0:
                return OFFLINE;
            case 1:
                return ONLINE;
            default:
                throw new IllegalArgumentException(integer + " does not correspond to a status");
        }
    }

    /**
     * Changes the user online status to the given {@link OnlineStatus} value.
     *
     * @param userId   the userId of the user
     * @param status   the desired status for the user
     * @param listener {@link DatabaseReference.CompletionListener} for
     *                 the operation
     * @throws IllegalArgumentException if the userId string is null or the given status is
     *                                  wrong/unknown
     */
    public static void changeOnlineStatus(String userId, OnlineStatus status,
                                          DatabaseReference.CompletionListener listener) {
        checkPrecondition(userId != null, "userId is null");
        checkPrecondition(status == OFFLINE || status == ONLINE,
                "Wrong status given");

        FbDatabase.setAccountAttribute(userId, STATUS, status.ordinal(), listener);
    }

    /**
     * Changes the user status to offline upon disconnection (app closed).
     *
     * @param userId the userId of the user
     * @throws IllegalArgumentException if the userId string is null
     */
    public static void changeToOfflineOnDisconnect(String userId) {
        checkPrecondition(userId != null, "userId is null");
        FbDatabase.changeToOfflineOnDisconnect(userId);
    }
}
