package com.giahan.app.vietskindoctor.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.gw.swipeback.SwipeBackLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by NamVT on 7/23/2018.
 */

public class WebviewActivity extends BaseActivity {

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.loading)
    View loading;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_swipe;
    }

    @Override
    protected void createView() {
        Toolbox.setStatusBarColor(this, getResources(), R.color.color_header_bar);
        url = getIntent().getStringExtra("url");
        showLoad();
        initViews();
        initControl();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        webview.getSettings().setJavaScriptEnabled(true); // enable javascript

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                showLoad();
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebviewActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webview.loadUrl(url);
    }

    private void initControl() {
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_TOP);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {

            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                if (isEnd) {
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.btn_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.btnImgUpload)
    void shareLink() {
        if (!Toolbox.isEmpty(url)) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ liên kết"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VietSkinDoctorApplication.setIsOpenDetailScreen(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_top, R.anim.exit_to_bottom);
    }
}
