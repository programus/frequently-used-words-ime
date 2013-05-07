package org.programus.android.fuw.views;

import java.text.MessageFormat;

import org.programus.android.fuw.R;
import org.programus.android.fuw.settings.AppPreferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.os.Build;

public class FuwKeyboard extends Keyboard {
    private static final MessageFormat defaultLabelFormat = new MessageFormat("[[{0}]]");
    public static final String BLANK_LABEL = "";

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

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = super.createKeyFromXml(res, parent, x, y, parser);
        int s = res.getInteger(R.integer.fuwS);
        int e = res.getInteger(R.integer.fuwE);
        int c = key.codes[0];
        if (c <= e && c >= s) {
            int id = key.codes[0] - s;
            AppPreferences ap = AppPreferences.getInstance(null);
            AppPreferences.KeySettings ks = ap.getKeySettings(id);
            String label = ks.getLabel();
            String content = ks.getContent();
            if (content.length() == 0) {
                label = BLANK_LABEL;
            } else if (label.length() == 0) {
                label = defaultLabelFormat.format(id);
            }
            key.label = label;
            key.text = content;
        }
        return key;
    }

}
