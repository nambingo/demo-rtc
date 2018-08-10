// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.khamonline;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListSessionAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ListSessionAdapter.MyViewHolder target;

  @UiThread
  public ListSessionAdapter$MyViewHolder_ViewBinding(ListSessionAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.tvDescription = Utils.findRequiredViewAsType(source, R.id.tvDescription, "field 'tvDescription'", TextView.class);
    target.tvDoctor = Utils.findRequiredViewAsType(source, R.id.tvDoctor, "field 'tvDoctor'", TextView.class);
    target.rlItemSession = Utils.findRequiredViewAsType(source, R.id.rlItemSession, "field 'rlItemSession'", RelativeLayout.class);
    target.lnCancel = Utils.findRequiredViewAsType(source, R.id.lnCancel, "field 'lnCancel'", LinearLayout.class);
    target.lnRemain = Utils.findRequiredViewAsType(source, R.id.lnRemain, "field 'lnRemain'", LinearLayout.class);
    target.tvRemain = Utils.findRequiredViewAsType(source, R.id.tvRemain, "field 'tvRemain'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListSessionAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTime = null;
    target.tvDescription = null;
    target.tvDoctor = null;
    target.rlItemSession = null;
    target.lnCancel = null;
    target.lnRemain = null;
    target.tvRemain = null;
  }
}
