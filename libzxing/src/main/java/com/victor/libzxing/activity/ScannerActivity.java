package com.victor.libzxing.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.libzxing.R;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/10
 */
@Route(path = "/scanner/main")
public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {


    private CaptureFragment captureFragment;
    private PowerManager.WakeLock wakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        initData(savedInstanceState);
    }


    protected void initData(Bundle savedInstanceState) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "Victor");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initScanner();
        }
    }

    private void initScanner() {
        ZXingLibrary.initDisplayOpinion(this);

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scanner_detail);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.scanner_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Toast.makeText(ScannerActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(ScannerActivity.this, "解析结果失败", Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wakeLock.release();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initScanner();
        } else {
            Toast.makeText(this, "照相权限被拒绝，请前往“设置->应用权限”进行设置", Toast.LENGTH_SHORT).show();
        }
    }

}
