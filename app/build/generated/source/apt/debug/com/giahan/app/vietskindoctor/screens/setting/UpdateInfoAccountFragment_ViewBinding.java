// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UpdateInfoAccountFragment_ViewBinding implements Unbinder {
  private UpdateInfoAccountFragment target;

  private View view2131689674;

  private View view2131689813;

  @UiThread
  public UpdateInfoAccountFragment_ViewBinding(final UpdateInfoAccountFragment target,
      View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.edtPhone = Utils.findRequiredViewAsType(source, R.id.edt_phone, "field 'edtPhone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_update, "method 'update'");
    view2131689813 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.update();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateInfoAccountFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.edtPhone = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689813.setOnClickListener(null);
    view2131689813 = null;
  }
}
