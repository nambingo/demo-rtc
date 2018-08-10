// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAdapter$ChatTextViewHolder_ViewBinding implements Unbinder {
  private MessageAdapter.ChatTextViewHolder target;

  @UiThread
  public MessageAdapter$ChatTextViewHolder_ViewBinding(MessageAdapter.ChatTextViewHolder target,
      View source) {
    this.target = target;

    target.chat_text_other = Utils.findRequiredViewAsType(source, R.id.chat_text_other, "field 'chat_text_other'", LinearLayout.class);
    target.chat_text_me = Utils.findRequiredViewAsType(source, R.id.chat_text_me, "field 'chat_text_me'", LinearLayout.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", CircleImageView.class);
    target.tvYou = Utils.findRequiredViewAsType(source, R.id.tvYou, "field 'tvYou'", TextView.class);
    target.tvMe = Utils.findRequiredViewAsType(source, R.id.tvMe, "field 'tvMe'", TextView.class);
    target.tvDateMe = Utils.findRequiredViewAsType(source, R.id.tvDateMe, "field 'tvDateMe'", TextView.class);
    target.tvDateYou = Utils.findRequiredViewAsType(source, R.id.tvDateYou, "field 'tvDateYou'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageAdapter.ChatTextViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.chat_text_other = null;
    target.chat_text_me = null;
    target.imgAvatar = null;
    target.tvYou = null;
    target.tvMe = null;
    target.tvDateMe = null;
    target.tvDateYou = null;
  }
}
