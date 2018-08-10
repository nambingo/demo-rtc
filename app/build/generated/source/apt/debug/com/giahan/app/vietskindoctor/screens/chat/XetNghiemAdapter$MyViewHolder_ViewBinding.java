// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class XetNghiemAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private XetNghiemAdapter.MyViewHolder target;

  @UiThread
  public XetNghiemAdapter$MyViewHolder_ViewBinding(XetNghiemAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tvDate, "field 'tvDate'", TextView.class);
    target.tvHours = Utils.findRequiredViewAsType(source, R.id.tvHours, "field 'tvHours'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    XetNghiemAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvName = null;
    target.tvDate = null;
    target.tvHours = null;
  }
}
