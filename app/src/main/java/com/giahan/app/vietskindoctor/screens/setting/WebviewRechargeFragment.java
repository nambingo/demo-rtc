//package com.giahan.app.vietskindoctor.screens.setting;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.os.Handler;
//import android.view.KeyEvent;
//import android.view.View;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebResourceError;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import com.giahan.app.vietskindoctor.R;
//import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
//import com.giahan.app.vietskindoctor.base.BaseFragment;
//import com.giahan.app.vietskindoctor.model.event.RechargeResponseEvent;
//import com.giahan.app.vietskindoctor.utils.Constant;
//import com.giahan.app.vietskindoctor.utils.DialogUtils;
//import com.giahan.app.vietskindoctor.utils.Toolbox;
//
//import org.greenrobot.eventbus.EventBus;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by NamVT on 6/5/2018.
// */
//
//public class WebviewRechargeFragment extends BaseFragment {
//    public static final int TIME_LOADING = 100;
//
//    @BindView(R.id.webview)
//    WebView webview;
//    @BindView(R.id.loading)
//    View loading;
//
//    private String url = "";
//    private int money = 0;
//    private int moneyRecharge = 0;
//    private boolean isRecharged = false;
//    private boolean isShowed = true;
//    private boolean isShowedDialog = false;
//    private Handler handler = null;
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public void setMoney(int money, int moneyRecharge) {
//        this.money = money;
//        this.moneyRecharge = moneyRecharge;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_webview;
//    }
//
//    @Override
//    protected void createView(View view) {
//        handler = new Handler();
//        showLoading();
//        initViews();
//        initControl();
//    }
//
//    @SuppressLint("SetJavaScriptEnabled")
//    private void initViews() {
//        webview.getSettings().setJavaScriptEnabled(true); // enable javascript
//
//        /* Register a new JavaScript interface called HTMLOUT */
//        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
//
//        webview.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    WebView webView = (WebView) v;
//
//                    switch (keyCode) {
//                        case KeyEvent.KEYCODE_BACK:
//                            if (isRecharged) {
//                                backAfterRecharge();
//                            } else {
//                                if (webView.canGoBack()) {
//                                    webView.goBack();
//                                    return true;
//                                }
//                            }
//                            break;
//                    }
//                }
//
//                return false;
//            }
//        });
//
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (!isShowed) {
//                    showLoading();
//                    isShowed = true;
//                }
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                hideLoading();
//                if (url.contains(Constant.URL_PAYMENT)) {
//                    webview.setVisibility(View.GONE);
//                    webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                }
//                isShowed = false;
//            }
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(getMainActivity(), description, Toast.LENGTH_SHORT).show();
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
//        webview.loadUrl(url);
//    }
//
//    private void initControl() {
//
//    }
//
//    @OnClick(R.id.btn_back)
//    void back() {
//        if (isRecharged) {
//            backAfterRecharge();
//        } else {
//            if (webview.canGoBack()) {
//                webview.goBack();
//            } else {
//                getMainActivity().popFragment();
//            }
//        }
//    }
//
//    void backToSession() {
//        getMainActivity().goToSessionScreen();
//        VietSkinDoctorApplication.setIsBackToSession(false);
//    }
//
//    void backAfterRecharge() {
//        getMainActivity().popFragment();
//    }
//
//    /* An instance of this class will be registered as a JavaScript interface */
//    class MyJavaScriptInterface {
//        @JavascriptInterface
//        @SuppressWarnings("unused")
//        public void processHTML(String html) {
//            // process the html as needed by the app
//            isRecharged = true;
//            handler.postDelayed(WebviewRechargeFragment.this::back, TIME_LOADING);
//            boolean isTwoChoice = VietSkinDoctorApplication.isIsBackToSession();
//            if (!isShowedDialog) {
//                isShowedDialog = true;
//                if (html.contains("{\"success\":1}")) {
//                    EventBus.getDefault().post(new RechargeResponseEvent());
//                    String textAsk = getString(R.string.message_recharge_success);
//                    String textContent = getString(R.string.title_recharge, Toolbox.formatMoney(moneyRecharge));
//                    String textMoney = getString(R.string.money, Toolbox.formatMoney(money + moneyRecharge));
//                    DialogUtils.showDialogRechargeResult(getActivity(), false, isTwoChoice, textAsk, textContent
//                            , textMoney, getString(R.string.close), getString(R.string.title_back)
//                            , view2 -> {
//                                DialogUtils.hideAlert();
//                            }, view2 -> {
//                                DialogUtils.hideAlert();
//                                handler.postDelayed(WebviewRechargeFragment.this::backToSession, TIME_LOADING);
//                            }, view2 -> {
//                                DialogUtils.hideAlert();
//                            });
//                } else {
//                    String textAsk = getString(R.string.message_recharge_fail);
//                    String textContent = getString(R.string.title_recharge_fail, Toolbox.formatMoney(moneyRecharge));
//                    String textMoney = getString(R.string.money, Toolbox.formatMoney(money));
//                    DialogUtils.showDialogRechargeResult(getActivity(), true, isTwoChoice, textAsk, textContent
//                            , textMoney, getString(R.string.close), getString(R.string.title_back)
//                            , view2 -> {
//                                DialogUtils.hideAlert();
//                            }, view2 -> {
//                                DialogUtils.hideAlert();
//                                handler.postDelayed(WebviewRechargeFragment.this::backToSession, TIME_LOADING);
//                            }, view2 -> {
//                                DialogUtils.hideAlert();
//                            });
//                }
//            }
//        }
//    }
//}
