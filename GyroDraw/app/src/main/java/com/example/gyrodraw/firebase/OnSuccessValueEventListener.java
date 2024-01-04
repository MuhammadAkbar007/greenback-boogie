package com.example.gyrodraw.firebase;

import static com.example.gyrodraw.firebase.FbDatabase.checkForDatabaseError;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * This class represents a {@link ValueEventListener} with a default implementation of {@code
 * onCancelled}.
 */
public abstract class OnSuccessValueEventListener implements ValueEventListener {

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        checkForDatabaseError(databaseError);
    }
}
