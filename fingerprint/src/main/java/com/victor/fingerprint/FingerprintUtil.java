package com.victor.fingerprint;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.CryptoObject;
import android.support.v4.os.CancellationSignal;

import com.victor.baselib.utils.ToastUtil;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/5
 */
public class FingerprintUtil {

    private final String defaultKeyName = "victor";
    private KeyStore mKeyStore;

    private Cipher cipher;
    private Handler handler;
    private FingerprintManagerCompat.AuthenticationCallback callback;
    /**
     * 是否对称加密
     */
    private boolean symmertricEncryption;

    /**
     * 创建并初始化指纹对象，对称加密已验证，非对称加密未验证
     * @param symmetricEncryption true,对称加密；false，非对称加密
     * @param handler 处理回调，其实没发现有回调
     * @param callback 处理指纹回调结果
     *
     */
    public FingerprintUtil(boolean symmetricEncryption, @Nullable Handler handler, @NonNull FingerprintManagerCompat.AuthenticationCallback callback) {
        this.symmertricEncryption = symmetricEncryption;
        this.handler = handler;
        this.callback = callback;
        init();
    }

    /**
     * 开始验证指纹加密
     * @param context
     * @return
     */
    public boolean showFingerprint(Context context) {
        FingerprintManagerCompat managerCompat = FingerprintManagerCompat.from(context);
        if (managerCompat.isHardwareDetected()) {
            if (managerCompat.hasEnrolledFingerprints()) {
                managerCompat.authenticate(new CryptoObject(cipher), 0,
                        new CancellationSignal(), callback, handler);
            } else {
                ToastUtil.getInstance().showToast(context, "您还没有录入指纹, 请在设置界面录入至少一个指纹");
            }
        } else {
            ToastUtil.getInstance().showToast(context, "您的硬件不支持指纹解锁功能，或没有设置密码锁屏");
        }

        return true;
    }

    private void init() {
        KeyGenerator mKeyGenerator = null;
        KeyPairGenerator mKeyPairGenerator = null;
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }

        // 对称加密， 创建 KeyGenerator 对象
        try {
            if (symmertricEncryption) {
                mKeyGenerator = KeyGenerator
                        .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            } else {
                // 非对称加密，创建 KeyPairGenerator 对象
                mKeyPairGenerator =  KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (symmertricEncryption) {
                    mKeyStore.load(null);
                    KeyGenParameterSpec.Builder builder = null;

                    builder = new KeyGenParameterSpec.Builder(defaultKeyName,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setUserAuthenticationRequired(true)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        builder.setInvalidatedByBiometricEnrollment(true);
                    }
                    mKeyGenerator.init(builder.build());
                    mKeyGenerator.generateKey();
                } else {
                    mKeyPairGenerator.initialize(
                            new KeyGenParameterSpec.Builder(defaultKeyName,
                                    KeyProperties.PURPOSE_SIGN)
                                    .setDigests(KeyProperties.DIGEST_SHA256)
                                    .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                                    .setUserAuthenticationRequired(true)
                                    .build());
                    mKeyPairGenerator.generateKeyPair();
                }
            } catch (CertificateException | NoSuchAlgorithmException | IOException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("创建Cipher对象失败", e);
        }

        try {
            if (symmertricEncryption) {
                mKeyStore.load(null);
                SecretKey key = (SecretKey) mKeyStore.getKey(defaultKeyName, null);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } else {
                // 使用私钥签名
                try {
                    mKeyStore.load(null);
                    PrivateKey key = (PrivateKey) mKeyStore.getKey(defaultKeyName, null);
                    Signature mSignature = Signature.getInstance("SHA256withECDSA");
                    mSignature.initSign(key);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to use Cipher", e);
                }
            }
        } catch (IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException | InvalidKeyException e) {
            throw new RuntimeException("初始化 cipher 失败", e);
        }

    }

    private void createConfirmDeviceCredentialIntent() {

    }

}
