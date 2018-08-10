// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebviewFragment_ViewBinding implements Unbinder {
  private WebviewFragment target;

  @UiThread
  public WebviewFragment_ViewBinding(WebviewFragment target, View source) {
    this.target = target;

    target.mWebView = Utils.findRequiredViewAsType(source, R.id.webview, "field 'mWebView'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WebviewFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mWebView = null;
  }
}
