// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ControlActivity_ViewBinding implements Unbinder {
  private ControlActivity target;

  private View view2131689638;

  private View view2131689639;

  private View view2131689634;

  private View view2131689637;

  private View view2131689636;

  @UiThread
  public ControlActivity_ViewBinding(ControlActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ControlActivity_ViewBinding(final ControlActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnLogin, "field 'btnLogin' and method 'login'");
    target.btnLogin = Utils.castView(view, R.id.btnLogin, "field 'btnLogin'", TextView.class);
    view2131689638 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnIntro, "field 'btnIntro' and method 'intro'");
    target.btnIntro = Utils.castView(view, R.id.btnIntro, "field 'btnIntro'", TextView.class);
    view2131689639 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.intro();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnGuide, "method 'guide'");
    view2131689634 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.guide();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnConsultant, "method 'goToMainScreen'");
    view2131689637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToMainScreen();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDoctor, "method 'goToMainDoctorScreen'");
    view2131689636 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToMainDoctorScreen();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ControlActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnLogin = null;
    target.btnIntro = null;

    view2131689638.setOnClickListener(null);
    view2131689638 = null;
    view2131689639.setOnClickListener(null);
    view2131689639 = null;
    view2131689634.setOnClickListener(null);
    view2131689634 = null;
    view2131689637.setOnClickListener(null);
    view2131689637 = null;
    view2131689636.setOnClickListener(null);
    view2131689636 = null;
  }
}
