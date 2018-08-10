// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WellcomeActivity_ViewBinding implements Unbinder {
  private WellcomeActivity target;

  private View view2131689681;

  @UiThread
  public WellcomeActivity_ViewBinding(WellcomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WellcomeActivity_ViewBinding(final WellcomeActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnCont, "method 'cont'");
    view2131689681 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cont();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131689681.setOnClickListener(null);
    view2131689681 = null;
  }
}
