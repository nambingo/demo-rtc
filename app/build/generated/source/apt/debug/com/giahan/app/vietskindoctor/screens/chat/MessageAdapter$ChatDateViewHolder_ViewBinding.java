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

public class MessageAdapter$ChatDateViewHolder_ViewBinding implements Unbinder {
  private MessageAdapter.ChatDateViewHolder target;

  @UiThread
  public MessageAdapter$ChatDateViewHolder_ViewBinding(MessageAdapter.ChatDateViewHolder target,
      View source) {
    this.target = target;

    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tvDate, "field 'tvDate'", TextView.class);
    target.tvDefault = Utils.findRequiredViewAsType(source, R.id.tvDefault, "field 'tvDefault'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageAdapter.ChatDateViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDate = null;
    target.tvDefault = null;
  }
}
