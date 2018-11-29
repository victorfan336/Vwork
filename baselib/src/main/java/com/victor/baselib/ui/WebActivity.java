package com.victor.baselib.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.victor.baselib.R;
import com.victor.baselib.utils.XLog;

/**
 * 搜索网络显示界面
 */
public class WebActivity extends Activity {
    private WebView mContentView;//内容
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setStyle();
        initView();
    }

    private void setStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(getResources().getColor(R.color.lg_green));
            window.setNavigationBarColor(Color.BLACK);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    private void initView() {
        findViewById(R.id.web_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        mUrl = intent.getExtras().getString("url");

        mContentView = (WebView) findViewById(R.id.content_web);
        if (!TextUtils.isEmpty(title)) {
            ((TextView) findViewById(R.id.web_title)).setText(title);
        }
        WebSettings webSettings = mContentView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        mContentView.setWebChromeClient(new WebChromeClient());

        mContentView.loadUrl(mUrl);
        XLog.e(XLog.TAG_GU, "web url: " + mUrl);
    }
}
