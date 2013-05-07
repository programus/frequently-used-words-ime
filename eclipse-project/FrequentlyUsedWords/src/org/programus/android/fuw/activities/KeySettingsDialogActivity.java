package org.programus.android.fuw.activities;

import org.programus.android.fuw.parts.KeySettingsDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class KeySettingsDialogActivity extends Activity {
    
    private final static String TAG = KeySettingsDialogActivity.class.getCanonicalName();
    private final static String KEY_ID = "Key.Id";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle data = intent.getExtras();
        this.showDialog(data.getInt(KEY_ID));
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        return new KeySettingsDialog(this, id).getDialog();
    }
    
    public static void showDialog(Context context, int id) {
        Intent intent = new Intent(context, KeySettingsDialogActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.d(TAG, "pause");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d(TAG, "resume");
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.d(TAG, "start");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.d(TAG, "stop");
    }
}
