package com.example.gyrodraw.utils.network;

import static com.example.gyrodraw.firebase.RoomAttributes.ONLINE_STATUS;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.VisibleForTesting;

import com.example.gyrodraw.firebase.FbDatabase;
import com.example.gyrodraw.game.WaitingPageActivity;

/**
 * A wrapper class that registers and unregisters the network receiver. It provides
 * also utility methods to update or check connectivity status.
 */
public final class ConnectivityWrapper {

    private static NetworkStateReceiver networkStateReceiver = null;

    private ConnectivityWrapper() {
        if (networkStateReceiver != null) {
            throw new IllegalStateException("NetworkStateReceiver already instantiated");
        }
    }

    private static NetworkStateReceiver getInstanceNetwork() {
        if (networkStateReceiver == null) {
            networkStateReceiver = new NetworkStateReceiver();
        }

        return networkStateReceiver;
    }

    /**
     * Creates a network receiver and sets the corresponding listener to it.
     *
     * @param context Context of the activity
     */
    public static void registerNetworkReceiver(Context context) {
        getInstanceNetwork();
        NetworkStateReceiverListener networkStateReceiverListener =
                new NetworkStatusHandler(context);

        networkStateReceiver.addListener(networkStateReceiverListener);
        context.registerReceiver(networkStateReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Unregisters the listener and deletes the instance of the receiver.
     *
     * @param context Context of the activity
     */
    public static void unregisterNetworkReceiver(Context context) {
        if (networkStateReceiver != null) {
            context.unregisterReceiver(networkStateReceiver);
            networkStateReceiver = null;
        }
    }

    /**
     * Sets the online flag to 1 when in-game. Allows to notify database that the player is still
     * here.
     *
     * @param roomId   Room number of the game
     * @param username Username of the player
     */
    public static void setOnlineStatusInGame(String roomId, String username) {
        FbDatabase.setValueToUserInRoomAttribute(roomId, username, ONLINE_STATUS, 1);
    }

    /**
     * Checks if the device is connected to the Internet.
     *
     * @param context the activity that calls this method
     * @return true if the device is connected, false otherwise
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Test method that allows to directly call the onReceive method with the given parameters.
     *
     * @param context Context of the activity
     * @param intent  Connectivity intent
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public static void callOnReceiveNetwork(final Context context, final Intent intent) {
        for (NetworkStateReceiverListener listener : networkStateReceiver.getListeners()) {
            networkStateReceiver.removeListener(listener);
        }

        ((WaitingPageActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                networkStateReceiver.addListener(new NetworkStatusHandler(context));
                networkStateReceiver.onReceive(context, intent);
            }
        });
    }
}
