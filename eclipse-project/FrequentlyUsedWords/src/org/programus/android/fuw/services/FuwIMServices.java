package org.programus.android.fuw.services;

import org.programus.android.fuw.R;
import org.programus.android.fuw.activities.KeySettingsDialogActivity;
import org.programus.android.fuw.views.FuwKeyboard;
import org.programus.android.fuw.views.FuwKeyboardView;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author programus
 *
 */
public class FuwIMServices extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
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
        // TODO Auto-generated method stub
        
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
