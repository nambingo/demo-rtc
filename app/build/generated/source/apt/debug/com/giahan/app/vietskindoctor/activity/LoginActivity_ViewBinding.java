// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.facebook.login.widget.LoginButton;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131689656;

  private View view2131689658;

  private View view2131689659;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'loginButton'", LoginButton.class);
    view = Utils.findRequiredView(source, R.id.btn_cancel, "method 'cancel' and method 'onCancel'");
    view2131689656 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancel();
        target.onCancel();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login_fb, "method 'startLoginFb'");
    view2131689658 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startLoginFb();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login_google, "method 'accessGoogle'");
    view2131689659 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.accessGoogle();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginButton = null;

    view2131689656.setOnClickListener(null);
    view2131689656 = null;
    view2131689658.setOnClickListener(null);
    view2131689658 = null;
    view2131689659.setOnClickListener(null);
    view2131689659 = null;
  }
}
