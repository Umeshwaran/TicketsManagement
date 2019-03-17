package com.example.android.ticketsmanagement.Otherjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.android.ticketsmanagement.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Android on 5/2/2018.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    public String recent_token;

    @Override
    public void onTokenRefresh() {
        recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.w("notification", recent_token);
        // Toast.makeText(this, "Recent Token  "+recent_token, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recent_token);

        editor.commit();

    }

    public String srrr() {
        Toast.makeText(this, "Check  " + recent_token, Toast.LENGTH_SHORT).show();
        return recent_token;
    }
}
