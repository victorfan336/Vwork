package com.victor.baselib.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * 监听字符输入情况，控制清楚按钮是否显示,清除文字内容
 * @author fanwentao
 * @Description
 * @date 2018/7/26
 */
public class ClearTextWatcher implements TextWatcher, View.OnClickListener {


    public interface CallBack {
        void onTextChanged(EditText editText, CharSequence s, int start, int before, int count);
    }

    private EditText editText;
    private View clearView;
    private CallBack callBack;

    public ClearTextWatcher(@NonNull EditText editText, View clearView) {
        this(editText, clearView, null);
    }

    public ClearTextWatcher(EditText editText, View clearView, CallBack callBack) {
        this.editText = editText;
        this.clearView = clearView;
        this.callBack = callBack;
        if (this.clearView != null) {
            this.clearView.setOnClickListener(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (callBack != null) {
            callBack.onTextChanged(editText, s, start, before, count);
        }
        if (clearView == null) {
            return;
        }
        if (TextUtils.isEmpty(s)) {
            clearView.setVisibility(View.GONE);
        } else {
            clearView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (editText != null) {
            editText.setText("");
        }
    }

}
