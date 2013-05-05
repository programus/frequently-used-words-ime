package org.programus.android.fuw.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Build;

public class FuwKeyboard extends Keyboard {

    public FuwKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FuwKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public FuwKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public FuwKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

}
