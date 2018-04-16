package com.tianluoayi.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tianluoayi.R;
import com.tianluoayi.util.Constants;
import com.tianluoayi.util.PrefsUtil;

/**
 * @author king
 */
public class MainActivity extends AppCompatActivity {
    private WebView mTbWebview;
    private String URL_Baidu = "https://www.baidu.com";
    private String url = "http://mall.tianluoayi.com/";
    //微信支付api
    private IWXAPI iwxapi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mTbWebview = findViewById(R.id.tb_webview);
        final WebSettings webSettings = mTbWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + "tlay");
        mTbWebview.loadUrl(url);
        mTbWebview.addJavascriptInterface(new WebViewJsFunction() {
            @JavascriptInterface
            @Override
            public void jumpToNewPage(String title, String url) {
                Toast.makeText(MainActivity.this, "title " + title + "  url  " + url, Toast.LENGTH_SHORT).show();
                mTbWebview.loadUrl(url);
            }

            @JavascriptInterface
            public void setHeader(String name, String value) {
                PrefsUtil.getPrefs().setString(name, value);
            }

            @JavascriptInterface
            public String getHeader(String name) {
                return PrefsUtil.getPrefs().getString(name, "");
            }
        }, "tlay");
    }

    @Override
    protected void onResume() {
        //激活WebView为活跃状态，能正常执行网页的响应
        mTbWebview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        mTbWebview.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mTbWebview != null) {
            //先让webview加载null内容
            mTbWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mTbWebview.clearHistory();

            //父布局移除webview
            ((ViewGroup) mTbWebview.getParent()).removeView(mTbWebview);
            //最后webview在销毁
            mTbWebview.destroy();
            mTbWebview = null;
        }
        super.onDestroy();
    }

    public void load(View view) {
        mTbWebview.loadUrl(URL_Baidu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && mTbWebview.canGoBack()) {
            mTbWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getRequestFromServer() {
        OkGo.<PayReq>post("").
                tag(this).
                params("param1", "param1").
                execute(new Callback<PayReq>() {
                    @Override
                    public void onStart(Request<PayReq, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<PayReq> response) {
                        Log.e("huihui", "response " + response.body().appId);
                    }

                    @Override
                    public void onCacheSuccess(Response<PayReq> response) {

                    }

                    @Override
                    public void onError(Response<PayReq> response) {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public PayReq convertResponse(okhttp3.Response response) throws Throwable {
                        return null;
                    }
                });
    }


    /**
     * 调起微信支付的方法
     **/
    private void toWXPay(final PayReq payReq) {
        //初始化微信api
        iwxapi = WXAPIFactory.createWXAPI(this, null);
        //注册appid  appid可以在开发平台获取
        iwxapi.registerApp(Constants.APP_ID);
        //这里注意要放在子线程
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //调起微信APP的对象
                PayReq request = new PayReq();
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = payReq.appId;
                request.partnerId = payReq.partnerId;
                request.prepayId = payReq.prepayId;
                request.packageValue = "Sign=WXPay";
                request.nonceStr = payReq.nonceStr;
                request.timeStamp = payReq.timeStamp;
                request.sign = payReq.sign;
                //发送调起微信的请求
                iwxapi.sendReq(request);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
