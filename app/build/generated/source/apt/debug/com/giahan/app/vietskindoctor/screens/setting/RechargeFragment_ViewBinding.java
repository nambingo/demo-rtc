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

public class RechargeFragment_ViewBinding implements Unbinder {
  private RechargeFragment target;

  private View view2131689674;

  private View view2131689852;

  @UiThread
  public RechargeFragment_ViewBinding(final RechargeFragment target, View source) {
    this.target = target;

    View view;
    target.tvCoin = Utils.findRequiredViewAsType(source, R.id.tv_coin, "field 'tvCoin'", TextView.class);
    target.tvCoin2 = Utils.findRequiredViewAsType(source, R.id.tv_coin2, "field 'tvCoin2'", TextView.class);
    target.tvMoney = Utils.findRequiredViewAsType(source, R.id.tv_money, "field 'tvMoney'", TextView.class);
    target.edtAmount = Utils.findRequiredViewAsType(source, R.id.edt_amount, "field 'edtAmount'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "method 'confirm'");
    view2131689852 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.confirm();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RechargeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvCoin = null;
    target.tvCoin2 = null;
    target.tvMoney = null;
    target.edtAmount = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689852.setOnClickListener(null);
    view2131689852 = null;
  }
}
