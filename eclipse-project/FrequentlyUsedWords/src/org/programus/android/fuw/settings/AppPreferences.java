package org.programus.android.fuw.settings;

import java.text.MessageFormat;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private final static String SP_KEY = "FUW.Settings";
    private final static String KEY_SETTINGS_LABEL_FORMAT = "Key.Settings.Label.{0}";
    private final static String KEY_SETTINGS_PWD_FORMAT = "Key.Settings.Password.{0}";
    private final static String KEY_SETTINGS_CONTENT_FORMAT = "Key.Settings.Content.{0}";
    
    private SharedPreferences sp;
    
    private static AppPreferences inst = new AppPreferences();
    private AppPreferences() {}
    public static AppPreferences getInstance(Context context) {
        if (inst.sp == null) {
            inst.sp = context.getApplicationContext().getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        }
        return inst;
    }
    
    private String getKey(String format, int id) {
        return MessageFormat.format(format, id);
    }
    
    public  KeySettings getKeySettings(int id) {
        KeySettings ks = new KeySettings();
        ks.setLabel(sp.getString(getKey(KEY_SETTINGS_LABEL_FORMAT, id), ""));
        ks.setPassword(sp.getBoolean(this.getKey(KEY_SETTINGS_PWD_FORMAT, id), false));
        ks.setContent(sp.getString(this.getKey(KEY_SETTINGS_CONTENT_FORMAT, id), ""));
        return ks;
    }
    
    public  void setKeySettings(int id, KeySettings keySettings) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(this.getKey(KEY_SETTINGS_LABEL_FORMAT, id), keySettings.getLabel());
        editor.putBoolean(this.getKey(KEY_SETTINGS_PWD_FORMAT, id), keySettings.isPassword());
        editor.putString(this.getKey(KEY_SETTINGS_CONTENT_FORMAT, id), keySettings.getContent());
        editor.commit();
    }
    
    public static class KeySettings {
        private String label;
        private boolean password;
        private String content;
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public boolean isPassword() {
            return password;
        }
        public void setPassword(boolean password) {
            this.password = password;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }
}
