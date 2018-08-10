// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.ills;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ImageAdapter.MyViewHolder target;

  @UiThread
  public ImageAdapter$MyViewHolder_ViewBinding(ImageAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAvatar = null;
  }
}
