package org.programus.android.fuw.views;

import org.programus.android.fuw.R;
import org.programus.android.fuw.activities.KeySettingsDialogActivity;

import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

public class FuwKeyboardView extends KeyboardView {

    public FuwKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FuwKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean onLongPress(Key popupKey) {
        int keyCode = popupKey.codes[0];
        if (keyCode < R.integer.fuwS && keyCode > R.integer.fuwE || true) {
            KeySettingsDialogActivity.showDialog(this.getContext(), keyCode);
            return true;
        } else {
            return super.onLongPress(popupKey);
        }
    }

}
