// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.ills;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IllAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private IllAdapter.MyViewHolder target;

  @UiThread
  public IllAdapter$MyViewHolder_ViewBinding(IllAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IllAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvName = null;
    target.tvContent = null;
    target.imgAvatar = null;
  }
}
