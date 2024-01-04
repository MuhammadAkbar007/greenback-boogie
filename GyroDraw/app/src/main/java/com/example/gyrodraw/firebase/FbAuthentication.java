package com.example.gyrodraw.firebase;

import android.content.Context;

import com.example.gyrodraw.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;


/**
 */
public final class FbAuthentication {

    private FbAuthentication() {
    }

    /**
     * Starts the sign in flow.
     *
     * @param activity the activity calling the method
     * @param requestCode the code denoting the request
     */
    public static void signIn(BaseActivity activity, int requestCode) {
        final List<IdpConfig> providers = Collections.singletonList(new GoogleBuilder().build());
        activity.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.LoginTheme)
                .setLogo(R.mipmap.ic_launcher_round)
                .build(), requestCode);
    }

    /**
     * Signs the user out.
     *
     * @param context the context calling the method
     * @param onCompleteListener the {@link OnCompleteListener} to be added to the task
     */
    public static void signOut(Context context, OnCompleteListener<Void> onCompleteListener) {
        AuthUI.getInstance().signOut(context).addOnCompleteListener(onCompleteListener);
    }
}
