package org.drogan.simplecalculatortaxi;

import android.view.View;
import android.widget.EditText;

public class EditTextViewCleanerListener implements View.OnFocusChangeListener {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            EditText et = (EditText) v;
            String s = et.getText().toString();
            et.setText("");
            et.setHint(s);
        }
    }
}
