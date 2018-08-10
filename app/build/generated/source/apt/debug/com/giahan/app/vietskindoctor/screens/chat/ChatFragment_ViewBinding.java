// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.chat;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatFragment_ViewBinding implements Unbinder {
  private ChatFragment target;

  private View view2131689774;

  private View view2131689646;

  private View view2131689772;

  private View view2131689911;

  private View view2131689914;

  private View view2131689768;

  @UiThread
  public ChatFragment_ViewBinding(final ChatFragment target, View source) {
    this.target = target;

    View view;
    target.mMessagesView = Utils.findRequiredViewAsType(source, R.id.messages, "field 'mMessagesView'", RecyclerView.class);
    target.mInputMessageView = Utils.findRequiredViewAsType(source, R.id.message_input, "field 'mInputMessageView'", EditText.class);
    view = Utils.findRequiredView(source, R.id.send_button, "field 'sendButton' and method 'onSend'");
    target.sendButton = Utils.castView(view, R.id.send_button, "field 'sendButton'", ImageView.class);
    view2131689774 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSend();
      }
    });
    target.tvNameDoctor = Utils.findRequiredViewAsType(source, R.id.tvNameDoctor, "field 'tvNameDoctor'", TextView.class);
    target.imgMenu = Utils.findRequiredViewAsType(source, R.id.imgMenu, "field 'imgMenu'", ImageView.class);
    target.drawer_layout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer_layout'", DrawerLayout.class);
    target.lnRight = Utils.findRequiredViewAsType(source, R.id.lnRight, "field 'lnRight'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.lnBack, "field 'lnBack' and method 'onBack'");
    target.lnBack = Utils.castView(view, R.id.lnBack, "field 'lnBack'", LinearLayout.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgAttachPhoto, "field 'imgAttachPhoto' and method 'onAttachPhoto'");
    target.imgAttachPhoto = Utils.castView(view, R.id.imgAttachPhoto, "field 'imgAttachPhoto'", ImageView.class);
    view2131689772 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAttachPhoto();
      }
    });
    target.rvPhoto = Utils.findRequiredViewAsType(source, R.id.rvPhoto, "field 'rvPhoto'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.rlMauXN, "field 'rlMauXN' and method 'clickMauXN'");
    target.rlMauXN = Utils.castView(view, R.id.rlMauXN, "field 'rlMauXN'", RelativeLayout.class);
    view2131689911 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickMauXN();
      }
    });
    view = Utils.findRequiredView(source, R.id.rlDonThuoc, "field 'rlDonThuoc' and method 'clickDonThuoc'");
    target.rlDonThuoc = Utils.castView(view, R.id.rlDonThuoc, "field 'rlDonThuoc'", RelativeLayout.class);
    view2131689914 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickDonThuoc();
      }
    });
    target.lvMauXN = Utils.findRequiredViewAsType(source, R.id.lvMauXN, "field 'lvMauXN'", RecyclerView.class);
    target.lvDonThuoc = Utils.findRequiredViewAsType(source, R.id.lvDonThuoc, "field 'lvDonThuoc'", RecyclerView.class);
    target.nsv = Utils.findRequiredViewAsType(source, R.id.nsv, "field 'nsv'", NestedScrollView.class);
    view = Utils.findRequiredView(source, R.id.llMenu, "method 'onMenu'");
    view2131689768 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMenu();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMessagesView = null;
    target.mInputMessageView = null;
    target.sendButton = null;
    target.tvNameDoctor = null;
    target.imgMenu = null;
    target.drawer_layout = null;
    target.lnRight = null;
    target.lnBack = null;
    target.imgAttachPhoto = null;
    target.rvPhoto = null;
    target.rlMauXN = null;
    target.rlDonThuoc = null;
    target.lvMauXN = null;
    target.lvDonThuoc = null;
    target.nsv = null;

    view2131689774.setOnClickListener(null);
    view2131689774 = null;
    view2131689646.setOnClickListener(null);
    view2131689646 = null;
    view2131689772.setOnClickListener(null);
    view2131689772 = null;
    view2131689911.setOnClickListener(null);
    view2131689911 = null;
    view2131689914.setOnClickListener(null);
    view2131689914 = null;
    view2131689768.setOnClickListener(null);
    view2131689768 = null;
  }
}
