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
        KeySettingsDialog dialog = new KeySettingsDialog(this, id);
        Runnable finishThis = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        dialog.setPositiveCallback(finishThis);
        dialog.setNegativeCallback(finishThis);
        return dialog.getDialog();
    }
    
    public static void showDialog(Context context, int id) {
        Intent intent = new Intent(context, KeySettingsDialogActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop");
    }
}
