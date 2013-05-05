package org.programus.android.fuw.parts;

import org.programus.android.fuw.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class KeyPreference extends DialogPreference {

    public KeyPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public KeyPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setDialogLayoutResource(R.layout.key_preference);
        this.setPositiveButtonText(android.R.string.ok);
        this.setNegativeButtonText(android.R.string.cancel);
    }
}
