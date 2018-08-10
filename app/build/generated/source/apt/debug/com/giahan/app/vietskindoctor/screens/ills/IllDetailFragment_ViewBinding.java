// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.ills;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IllDetailFragment_ViewBinding implements Unbinder {
  private IllDetailFragment target;

  private View view2131689674;

  private View view2131689717;

  private View view2131689809;

  @UiThread
  public IllDetailFragment_ViewBinding(final IllDetailFragment target, View source) {
    this.target = target;

    View view;
    target.rvListImg = Utils.findRequiredViewAsType(source, R.id.rvListImg, "field 'rvListImg'", RecyclerView.class);
    target.tvHeader = Utils.findRequiredViewAsType(source, R.id.tv_header, "field 'tvHeader'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.lnImage = Utils.findRequiredView(source, R.id.lnImage, "field 'lnImage'");
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'onBack'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_close, "method 'close'");
    view2131689717 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_register, "method 'goToMainDoctorScreen'");
    view2131689809 = view;
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
    IllDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvListImg = null;
    target.tvHeader = null;
    target.tvName = null;
    target.tvContent = null;
    target.lnImage = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689717.setOnClickListener(null);
    view2131689717 = null;
    view2131689809.setOnClickListener(null);
    view2131689809 = null;
  }
}
