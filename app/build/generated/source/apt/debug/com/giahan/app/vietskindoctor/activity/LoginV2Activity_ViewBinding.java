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

public class LoginV2Activity_ViewBinding implements Unbinder {
  private LoginV2Activity target;

  private View view2131689653;

  private View view2131689664;

  private View view2131689661;

  private View view2131689659;

  @UiThread
  public LoginV2Activity_ViewBinding(LoginV2Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginV2Activity_ViewBinding(final LoginV2Activity target, View source) {
    this.target = target;

    View view;
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'loginButton'", LoginButton.class);
    view = Utils.findRequiredView(source, R.id.btnSkip, "method 'cancel' and method 'onCancel'");
    view2131689653 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancel();
        target.onCancel();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRule, "method 'rule'");
    view2131689664 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.rule();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnLoginFb, "method 'startLoginFb'");
    view2131689661 = view;
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
    LoginV2Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginButton = null;

    view2131689653.setOnClickListener(null);
    view2131689653 = null;
    view2131689664.setOnClickListener(null);
    view2131689664 = null;
    view2131689661.setOnClickListener(null);
    view2131689661 = null;
    view2131689659.setOnClickListener(null);
    view2131689659 = null;
  }
}
