// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.gw.swipeback.SwipeBackLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebviewActivity_ViewBinding implements Unbinder {
  private WebviewActivity target;

  private View view2131689674;

  private View view2131689678;

  @UiThread
  public WebviewActivity_ViewBinding(WebviewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WebviewActivity_ViewBinding(final WebviewActivity target, View source) {
    this.target = target;

    View view;
    target.mSwipeBackLayout = Utils.findRequiredViewAsType(source, R.id.swipeBackLayout, "field 'mSwipeBackLayout'", SwipeBackLayout.class);
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
    view = Utils.findRequiredView(source, R.id.btnImgUpload, "method 'shareLink'");
    view2131689678 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.shareLink();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WebviewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeBackLayout = null;
    target.webview = null;
    target.loading = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689678.setOnClickListener(null);
    view2131689678 = null;
  }
}
