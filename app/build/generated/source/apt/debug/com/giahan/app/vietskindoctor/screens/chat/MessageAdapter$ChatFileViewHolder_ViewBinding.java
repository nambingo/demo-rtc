// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAdapter$ChatFileViewHolder_ViewBinding implements Unbinder {
  private MessageAdapter.ChatFileViewHolder target;

  @UiThread
  public MessageAdapter$ChatFileViewHolder_ViewBinding(MessageAdapter.ChatFileViewHolder target,
      View source) {
    this.target = target;

    target.chat_text_other = Utils.findRequiredViewAsType(source, R.id.chat_text_other, "field 'chat_text_other'", LinearLayout.class);
    target.chat_text_me = Utils.findRequiredViewAsType(source, R.id.chat_text_me, "field 'chat_text_me'", LinearLayout.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", CircleImageView.class);
    target.imgYou = Utils.findRequiredViewAsType(source, R.id.imgYou, "field 'imgYou'", ImageView.class);
    target.imgMe = Utils.findRequiredViewAsType(source, R.id.imgMe, "field 'imgMe'", ImageView.class);
    target.tvDateYou = Utils.findRequiredViewAsType(source, R.id.tvDateYou, "field 'tvDateYou'", TextView.class);
    target.tvDateMe = Utils.findRequiredViewAsType(source, R.id.tvDateMe, "field 'tvDateMe'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageAdapter.ChatFileViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.chat_text_other = null;
    target.chat_text_me = null;
    target.imgAvatar = null;
    target.imgYou = null;
    target.imgMe = null;
    target.tvDateYou = null;
    target.tvDateMe = null;
  }
}
