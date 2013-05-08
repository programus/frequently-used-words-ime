package org.programus.android.fuw.parts;

import org.programus.android.fuw.R;
import org.programus.android.fuw.settings.AppPreferences;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class KeySettingsDialog {
    private Context context;
    private int id;
    
    private EditText keyLabelText;
    private ToggleButton passwordFlag;
    private ToggleButton autoReturnFlag;
    private EditText keyContentText;
    private Runnable positiveCallback;
    private Runnable negativeCallback;
    
    public KeySettingsDialog(Context context, int id) {
        this.context = context;
        this.id = id;
    }
    
    public void setPositiveCallback(Runnable positiveCallback) {
        this.positiveCallback = positiveCallback;
    }

    public void setNegativeCallback(Runnable negativeCallback) {
        this.negativeCallback = negativeCallback;
    }

    @SuppressLint("NewApi")
    public Dialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View settingView = inflater.inflate(R.layout.key_preference, null);
        this.bindViewEvents(settingView);
        this.loadKeySettings();
        // Construct a builder to build dialog
        AlertDialog.Builder builder;
        // prepare different themes for different version
        int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(context);
        } else if (apiLevel < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        } else {
            builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        }
        
        return builder
            .setTitle(R.string.key_settings_dialog_title)
            .setView(settingView)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveKeySettings();
                    if (positiveCallback != null) {
                        positiveCallback.run();
                    }
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (negativeCallback != null) {
                        negativeCallback.run();
                    }
                }
            })
            .setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (negativeCallback != null) {
                        negativeCallback.run();
                    }
                }
            })
            .create();
    }
    
    private void saveKeySettings() {
        AppPreferences.KeySettings ks = new AppPreferences.KeySettings();
        ks.setContent(this.keyContentText.getText().toString());
        ks.setPassword(this.passwordFlag.isChecked());
        ks.setAutoReturn(this.autoReturnFlag.isChecked());
        ks.setLabel(this.keyLabelText.getText().toString());
        AppPreferences ap = AppPreferences.getInstance(context);
        ap.setKeySettings(id, ks);
    }
    
    private void loadKeySettings() {
        AppPreferences ap = AppPreferences.getInstance(context);
        AppPreferences.KeySettings ks = ap.getKeySettings(id);
        this.keyLabelText.setText(ks.getLabel());
        this.passwordFlag.setChecked(ks.isPassword());
        this.autoReturnFlag.setChecked(ks.isAutoReturn());
        this.keyContentText.setText(ks.getContent());
    }
    
    private void bindViewEvents(View view) {
        this.keyLabelText = (EditText) view.findViewById(R.id.keyLabelText);
        this.keyContentText = (EditText) view.findViewById(R.id.keyContentEdit);
        this.passwordFlag = (ToggleButton) view.findViewById(R.id.passwordFlag);
        this.autoReturnFlag = (ToggleButton) view.findViewById(R.id.autoReturnFlag);
        
        this.passwordFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // if set the content as password, then the textbox should look like a password inputbox. 
                    keyContentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // switch to password mode to normal mode, clear everything.
                    keyContentText.setText("");
                    keyContentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
            }
        });
    }
}
