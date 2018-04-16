package com.tianluoayi.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.securepreferences.SecurePreferences;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @author king
 * @date 2018/1/16
 */

public class NeoApplication extends Application {
    private static volatile NeoApplication mInstance;
    private SharedPreferences mSecurePrefs;

    public static NeoApplication getmInstance() {
        return mInstance;
    }

    public NeoApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        OkGo.getInstance().init(this);
    }

    /**
     * Single point for the app to get the secure prefs object
     *
     * @return
     */
    public SharedPreferences getSharedPreferences() {
        if (mSecurePrefs == null) {
            try {
                mSecurePrefs =
                        new SecurePreferences(this, "basecore@%^&", "basecore.xml");
                SecurePreferences.setLoggingEnabled(true);
            } catch (Exception e) {
                //这里三星特定机型由于没有实现加密算法(三星 GALAXY Style DUOS(GT-I8262D)等)，
                //会报java.security.NoSuchAlgorithmException: SecretKeyFactory PBKDF2WithHmacSHA1 implementation not found
                mSecurePrefs = getSharedPreferences("basecore.xml", MODE_PRIVATE);
            }
        }
        return mSecurePrefs;
    }
}
