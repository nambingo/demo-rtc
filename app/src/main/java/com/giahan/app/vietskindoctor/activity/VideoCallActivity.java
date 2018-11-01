package com.giahan.app.vietskindoctor.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import butterknife.BindView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.utils.Constant;

public class VideoCallActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_call;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void createView() {
        //Request Permissions
        this.requestPermissionForCameraAndMicrophone();
        //Setting
        this.setUpWebViewDefaults(webview);
        //JS Interface for Viewers Count
        webview.addJavascriptInterface(this, "Android");

        //Load WebRTC Page
        webview.loadUrl(Constant.URL_RTC);

        //Grant WebView Permissions
        final VideoCallActivity context = this;
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("----", "onPermissionRequest");
                context.runOnUiThread(() -> {
                    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                        if (request.getOrigin().toString().equals(Constant.URL_RTC)) {
                            request.grant(request.getResources());
                        } else {
                            request.deny();
                        }
                    }
                });
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
            }
        });
//        setup();
//        webview.getSettings().setJavaScriptEnabled(true); // enable javascript

//        webview.setWebChromeClient(new WebChromeClient(){
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onPermissionRequest(final PermissionRequest request) {
//                request.grant(request.getResources());
//            }
//        });

//        webview.setOnKeyListener((v, keyCode, event) -> {
//            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                WebView webView = (WebView) v;
//
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_BACK:
//                        if (webView.canGoBack()) {
//                            webView.goBack();
//                            return true;
//                        }
//                        break;
//                }
//            }
//
//            return false;
//        });

//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                showLoad();
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                hideLoading();
//            }
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
////                Toast.makeText(LoadWebviewActivity.this, description, Toast.LENGTH_SHORT).show();
//            }
//
//            @TargetApi(android.os.Build.VERSION_CODES.M)
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//                // Redirect to deprecated method, so you can use it in all SDK versions
//                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//            }
//        });
//
//        webview.loadUrl(Constant.URL_RTC);
    }

    private void requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this,
                    "WE NEED CAMERA AND MIC PERMISSIONS",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                    CAMERA_MIC_PERMISSION_REQUEST_CODE);
        }
    }
    private void setup(){
        setUpWebViewDefaults(webview);

        webview.loadUrl(Constant.URL_RTC);

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.e("----", "onPermissionRequest");
                runOnUiThread(() -> {
                    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                        if(request.getOrigin().toString().equals(Constant.URL_RTC)) {
                            request.grant(request.getResources());
                        } else {
                            request.deny();
                        }
                    }
                });
            }

        });
    }

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if(VERSION.SDK_INT > VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setWebViewClient(new WebViewClient());

        // AppRTC requires third party cookies to work
        CookieManager cookieManager = CookieManager.getInstance();
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }
}
