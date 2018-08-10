// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoAccountFragment_ViewBinding implements Unbinder {
  private InfoAccountFragment target;

  private View view2131689959;

  private View view2131689674;

  @UiThread
  public InfoAccountFragment_ViewBinding(final InfoAccountFragment target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_update, "field 'llUpdate' and method 'openScreenUpdateInfo'");
    target.llUpdate = view;
    view2131689959 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openScreenUpdateInfo();
      }
    });
    target.tvProfileName = Utils.findRequiredViewAsType(source, R.id.tv_profile_name, "field 'tvProfileName'", TextView.class);
    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profileImage'", ImageView.class);
    target.tvCredits = Utils.findRequiredViewAsType(source, R.id.tv_credits_account, "field 'tvCredits'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone_account, "field 'tvPhone'", TextView.class);
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
    InfoAccountFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.llUpdate = null;
    target.tvProfileName = null;
    target.profileImage = null;
    target.tvCredits = null;
    target.tvPhone = null;

    view2131689959.setOnClickListener(null);
    view2131689959 = null;
    view2131689674.setOnClickListener(null);
    view2131689674 = null;
  }
}
