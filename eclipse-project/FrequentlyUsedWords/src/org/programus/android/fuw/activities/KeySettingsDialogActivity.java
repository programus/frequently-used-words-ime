package org.programus.android.fuw.activities;

import org.programus.android.fuw.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class KeySettingsDialogActivity extends Activity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showDialog(0);
    }

    @SuppressLint("NewApi")
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View settingView = inflater.inflate(R.layout.key_preference, null);
        // Construct a builder to build dialog
        AlertDialog.Builder builder;
        // prepare different themes for different version
        int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(this);
        } else if (apiLevel < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        } else {
            builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        }
        
        return builder
            .setTitle(R.string.key_settings_dialog_title)
            .setView(settingView)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .create();
    }

    public static void showDialog(Context context, int id) {
        Intent intent = new Intent(context, KeySettingsDialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
