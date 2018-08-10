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

public class DocumentAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private DocumentAdapter.MyViewHolder target;

  @UiThread
  public DocumentAdapter$MyViewHolder_ViewBinding(DocumentAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvMtest = Utils.findRequiredViewAsType(source, R.id.tvMtest, "field 'tvMtest'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DocumentAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvMtest = null;
  }
}
