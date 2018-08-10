// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebviewRechargeFragment_ViewBinding implements Unbinder {
  private WebviewRechargeFragment target;

  private View view2131689674;

  @UiThread
  public WebviewRechargeFragment_ViewBinding(final WebviewRechargeFragment target, View source) {
    this.target = target;

    View view;
    target.webview = Utils.findRequiredViewAsType(source, R.id.webview, "field 'webview'", WebView.class);
    target.loading = Utils.findRequiredView(source, R.id.loading, "field 'loading'");
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WebviewRechargeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.webview = null;
    target.loading = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
  }
}
