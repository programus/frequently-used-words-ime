package org.programus.android.fuw.services;

import org.programus.android.fuw.R;
import org.programus.android.fuw.views.FuwKeyboard;
import org.programus.android.fuw.views.FuwKeyboardView;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author programus
 *
 */
public class FuwIMServices extends InputMethodService {
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
}
