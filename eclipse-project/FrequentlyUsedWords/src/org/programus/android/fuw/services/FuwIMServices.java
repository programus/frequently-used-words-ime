package org.programus.android.fuw.services;

import org.programus.android.fuw.R;
import org.programus.android.fuw.settings.AppPreferences;
import org.programus.android.fuw.views.FuwKeyboard;
import org.programus.android.fuw.views.FuwKeyboardView;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

/**
 * @author programus
 *
 */
public class FuwIMServices extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static final String TAG = "FuwIMServices";
    private InputMethodManager mIMManager;
    private FuwKeyboard mKeyboard;
    private FuwKeyboardView mInputView;

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
    public void onFinishInput() {
        super.onFinishInput();
        
        this.setCandidatesViewShown(false);
        
        if (this.mInputView != null) {
            this.mInputView.closing();
        }
    }

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
            this.keyDownUp(KeyEvent.KEYCODE_MOVE_HOME);
        } else if (primaryCode == this.getCode(R.integer.tail_code)) {
            this.keyDownUp(KeyEvent.KEYCODE_MOVE_END);
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
    
    @Override
    public void onPress(int primaryCode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRelease(int primaryCode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onText(CharSequence text) {
        Log.d(TAG, "onText:" + text);
        InputConnection ic = this.getCurrentInputConnection();
        if (ic != null && text.length() > 0) {
            ic.commitText(text, text.length());
        }
    }

    @Override
    public void swipeDown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void swipeLeft() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void swipeRight() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void swipeUp() {
        // TODO Auto-generated method stub
        
    }
}
