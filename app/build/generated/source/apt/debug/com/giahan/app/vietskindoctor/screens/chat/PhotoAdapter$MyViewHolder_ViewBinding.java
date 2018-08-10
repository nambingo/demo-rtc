// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private PhotoAdapter.MyViewHolder target;

  @UiThread
  public PhotoAdapter$MyViewHolder_ViewBinding(PhotoAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.ivPhoto = Utils.findRequiredViewAsType(source, R.id.ivPhoto, "field 'ivPhoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivPhoto = null;
  }
}
