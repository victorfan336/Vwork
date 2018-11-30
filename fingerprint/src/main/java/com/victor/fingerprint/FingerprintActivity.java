package com.victor.fingerprint;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.victor.baselib.base.BaseActivity;
import com.victor.baselib.utils.ToastUtil;

/**
 * @author fanwentao
 * @Description 指纹解锁操作
 * @date 2018/7/5
 */
public class FingerprintActivity extends BaseActivity {

    private final static String TAG = "FingerprintActivity";
    private FingerprintUtil fingerprintUtil;
    private View progressParentView;
    private ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fingerprint;
    }

    @Override
    protected String setTitle() {
        return "指纹解锁";
    }

    @Override
    protected void initView() {
        findViewById(R.id.fingerprint_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressParentView.setVisibility(View.VISIBLE);
                fingerprintUtil.showFingerprint(FingerprintActivity.this);
            }
        });

        progressParentView = findViewById(R.id.fingerprint_progress_parent);
        progressBar = findViewById(R.id.fingerprint_progress);
        progressParentView.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fingerprintUtil = new FingerprintUtil(true, mHandler, callback);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "msg.what = " + msg.what + " msg : " + msg.obj + " arg1 = " + msg.arg1 + " arg2 = " + msg.arg2);
        }
    };

    private FingerprintManagerCompat.AuthenticationCallback callback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            ToastUtil.getInstance().showToast(FingerprintActivity.this, errString.toString());
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            ToastUtil.getInstance().showToast(FingerprintActivity.this, helpString.toString());
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            ToastUtil.getInstance().showToast(FingerprintActivity.this, "指纹解锁成功");
            progressParentView.setVisibility(View.GONE);
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            ToastUtil.getInstance().showToast(FingerprintActivity.this, "指纹解锁失败");
        }
    };

}
