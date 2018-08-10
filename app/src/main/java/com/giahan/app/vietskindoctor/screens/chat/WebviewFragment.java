package com.giahan.app.vietskindoctor.screens.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import butterknife.BindView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.utils.Constant;

/**
 * Created by pham.duc.nam on 02/07/2018.
 */
public class WebviewFragment extends BaseFragment {
    @BindView(R.id.webview)
    WebView mWebView;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview_info;
    }

    @Override
    protected void createView(View view) {
        getData();
        initView();
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle == null) return;
        url = bundle.getString(Constant.TAG_WEBVIEW_INFO) + "?access_token=" + mPref.token().get();
    }

    private void initView(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

    }
}
