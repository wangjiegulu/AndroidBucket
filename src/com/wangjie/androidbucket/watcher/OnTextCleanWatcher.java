package com.wangjie.androidbucket.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-10-14
 * Time: 下午12:31
 */
public class OnTextCleanWatcher implements TextWatcher, View.OnClickListener{
    private EditText editText;
    private ImageButton cleanBtn;

    public OnTextCleanWatcher(EditText editText, ImageButton cleanBtn) {
        this.editText = editText;
        this.cleanBtn = cleanBtn;
        this.cleanBtn.setOnClickListener(this);
        this.editText.addTextChangedListener(this);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cleanBtn.setVisibility(s.toString().equals("") ? View.GONE : View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onClick(View v) {
        editText.setText("");
    }
}
