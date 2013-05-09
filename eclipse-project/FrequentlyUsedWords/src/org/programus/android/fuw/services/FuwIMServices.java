package org.programus.android.fuw.services;

import java.util.List;

import org.programus.android.fuw.R;
import org.programus.android.fuw.settings.AppPreferences;
import org.programus.android.fuw.views.FuwKeyboard;
import org.programus.android.fuw.views.FuwKeyboardView;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * @author programus
 *
 */
public class FuwIMServices extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static final String TAG = "FuwIMServices";
    private InputMethodManager mIMManager;
    private FuwKeyboard mKeyboard;
    private FuwKeyboardView mInputView;
    private boolean mPasswordInputing;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mIMManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onInitializeInterface() {
        AppPreferences.getInstance(this);
        this.mKeyboard = new FuwKeyboard(this, R.xml.num_like);
    }

    @Override
    public View onCreateInputView() {
        this.mInputView = (FuwKeyboardView) this.getLayoutInflater().inflate(R.layout.keyboard, null);
        this.mInputView.setKeyboard(mKeyboard);
        this.mInputView.setOnKeyboardActionListener(this);
        return this.mInputView;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Log.d(TAG, "InputType: " + attribute.inputType);
        this.mPasswordInputing = (attribute.inputType & InputType.TYPE_MASK_VARIATION) == InputType.TYPE_TEXT_VARIATION_PASSWORD;
        this.updateKeyboard();
    }
    
    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        this.mInputView.setKeyboard(mKeyboard);
    }

    private void updateKeyboard() {
        AppPreferences ap = AppPreferences.getInstance(this);
        int updatedFlag = ap.getUpdatedFlag();
        ap.resetUpdateFlag();
        int s = this.getResources().getInteger(R.integer.fuwS);
        int e = this.getResources().getInteger(R.integer.fuwE);
        List<Key> keys = this.mKeyboard.getKeys();
        for (Key key : keys) {
            int c = key.codes[0];
            if (c <= e && c >= s) {
                int id = c - s;
                if ((updatedFlag & (1 << id)) != 0) {
                    Log.d(TAG, "refresh key:" + id);
                    this.mKeyboard.refreshContentKey(key, id);
                }
            }
        }
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        
        this.setCandidatesViewShown(false);
        
        if (this.mInputView != null) {
            this.mInputView.closing();
        }
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.d(TAG, "onKey");
        if (primaryCode == this.getCode(R.integer.backspace_code)) {
            this.keyDownUp(KeyEvent.KEYCODE_DEL);
        } else if (primaryCode == this.getCode(R.integer.return_code)) {
            this.keyDownUp(KeyEvent.KEYCODE_ENTER);
        } else if (primaryCode == this.getCode(R.integer.left_code)) {
            this.keyDownUp(KeyEvent.KEYCODE_DPAD_LEFT);
        } else if (primaryCode == this.getCode(R.integer.right_code)) {
            this.keyDownUp(KeyEvent.KEYCODE_DPAD_RIGHT);
        } else if (primaryCode == this.getCode(R.integer.head_code)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                this.keyDownUp(KeyEvent.KEYCODE_MOVE_HOME);
            } else {
                CharSequence s = this.getCurrentInputConnection().getTextBeforeCursor(Integer.MAX_VALUE, 0);
                for (int i = 0; i < s.length(); i++) {
                    this.keyDownUp(KeyEvent.KEYCODE_DPAD_LEFT);
                }
            }
        } else if (primaryCode == this.getCode(R.integer.tail_code)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                this.keyDownUp(KeyEvent.KEYCODE_MOVE_END);
            } else {
                CharSequence s = this.getCurrentInputConnection().getTextAfterCursor(Integer.MAX_VALUE, 0);
                for (int i = 0; i < s.length(); i++) {
                    this.keyDownUp(KeyEvent.KEYCODE_DPAD_RIGHT);
                }
            }
        } else if (primaryCode == this.getCode(R.integer.hide_code)) {
            this.handleClose();
        }
    }
    
    private int getCode(int resid) {
        return this.getResources().getInteger(resid);
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
    
    private void handleClose() {
        this.requestHideSelf(0);
        this.mInputView.closing();
    }
    
    private void switchIme() {
        if (this.mIMManager != null) {
            this.mIMManager.showInputMethodPicker();
        }
    }
    
    @Override
    public void onPress(int primaryCode) {
        Log.d(TAG, "Key Pressed:" + primaryCode);
        Resources res = this.getResources();
        int s = res.getInteger(R.integer.fuwS);
        int e = res.getInteger(R.integer.fuwE);
        int c = primaryCode;
        if (!(c <= e && c >= s)) {
            Log.d(TAG, "preview disabled");
            this.mInputView.setPreviewEnabled(false);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        this.mInputView.setPreviewEnabled(true);
    }

    @Override
    public void onText(CharSequence text) {
        Log.d(TAG, "onText:" + text);
        InputConnection ic = this.getCurrentInputConnection();
        if (ic != null && text.length() > 0) {
            char cmd = text.charAt(0);
            if (text.length() > 1) {
                if (!this.mPasswordInputing && (cmd & FuwKeyboard.CMD_PASSWORD) != 0) {
                    Toast.makeText(getApplicationContext(), R.string.password_only, Toast.LENGTH_LONG).show();
                } else {
                    ic.beginBatchEdit();
                    ic.commitText(text.subSequence(1, text.length()), text.length() - 1);
                    if ((cmd & FuwKeyboard.CMD_AUTO_RETURN) != 0) {
                        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                    }
                    ic.endBatchEdit();
                }
            }
        }
    }

    @Override
    public void swipeDown() {
        this.handleClose();
    }

    @Override
    public void swipeLeft() {
        
    }

    @Override
    public void swipeRight() {
        
    }

    @Override
    public void swipeUp() {
        this.switchIme();
    }
}
