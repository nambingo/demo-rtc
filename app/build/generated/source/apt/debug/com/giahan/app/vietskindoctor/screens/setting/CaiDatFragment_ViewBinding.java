// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CaiDatFragment_ViewBinding implements Unbinder {
  private CaiDatFragment target;

  private View view2131689762;

  private View view2131689763;

  private View view2131689764;

  private View view2131689765;

  private View view2131689858;

  private View view2131689766;

  @UiThread
  public CaiDatFragment_ViewBinding(final CaiDatFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_info, "field 'llInfo' and method 'openInfoAccount'");
    target.llInfo = view;
    view2131689762 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openInfoAccount();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_recharge, "field 'llRecharge' and method 'openRecharge'");
    target.llRecharge = view;
    view2131689763 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openRecharge();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_login, "field 'llLogin' and method 'login'");
    target.llLogin = view;
    view2131689764 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_quide, "field 'llGuide' and method 'guide'");
    target.llGuide = view;
    view2131689765 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.guide();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_table_fee, "field 'llTableFee' and method 'openTableFee'");
    target.llTableFee = view;
    view2131689858 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openTableFee();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_logout, "field 'llLogout' and method 'logout'");
    target.llLogout = view;
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.logout();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CaiDatFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llInfo = null;
    target.llRecharge = null;
    target.llLogin = null;
    target.llGuide = null;
    target.llTableFee = null;
    target.llLogout = null;

    view2131689762.setOnClickListener(null);
    view2131689762 = null;
    view2131689763.setOnClickListener(null);
    view2131689763 = null;
    view2131689764.setOnClickListener(null);
    view2131689764 = null;
    view2131689765.setOnClickListener(null);
    view2131689765 = null;
    view2131689858.setOnClickListener(null);
    view2131689858 = null;
    view2131689766.setOnClickListener(null);
    view2131689766 = null;
  }
}
