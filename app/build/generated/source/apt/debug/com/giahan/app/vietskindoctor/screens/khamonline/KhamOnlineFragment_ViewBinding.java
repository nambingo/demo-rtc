// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.khamonline;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class KhamOnlineFragment_ViewBinding implements Unbinder {
  private KhamOnlineFragment target;

  private View view2131689828;

  private View view2131689830;

  private View view2131689646;

  @UiThread
  public KhamOnlineFragment_ViewBinding(final KhamOnlineFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnImgAdd, "field 'lnAddSession' and method 'onClickAdd'");
    target.lnAddSession = Utils.castView(view, R.id.btnImgAdd, "field 'lnAddSession'", LinearLayout.class);
    view2131689828 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAdd();
      }
    });
    view = Utils.findRequiredView(source, R.id.tvDangNhap, "field 'tvDangNhap' and method 'onLogin'");
    target.tvDangNhap = Utils.castView(view, R.id.tvDangNhap, "field 'tvDangNhap'", TextView.class);
    view2131689830 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLogin();
      }
    });
    target.rvListSession = Utils.findRequiredViewAsType(source, R.id.rvListSession, "field 'rvListSession'", RecyclerView.class);
    target.rvListSessionWait = Utils.findRequiredViewAsType(source, R.id.rvListSessionWait, "field 'rvListSessionWait'", RecyclerView.class);
    target.rvListSessionComplete = Utils.findRequiredViewAsType(source, R.id.rvListSessionComplete, "field 'rvListSessionComplete'", RecyclerView.class);
    target.lnNotSignIn = Utils.findRequiredViewAsType(source, R.id.lnNotSignIn, "field 'lnNotSignIn'", LinearLayout.class);
    target.lnSignIn = Utils.findRequiredViewAsType(source, R.id.lnSignIn, "field 'lnSignIn'", LinearLayout.class);
    target.cardViewWait = Utils.findRequiredViewAsType(source, R.id.cardViewWait, "field 'cardViewWait'", CardView.class);
    target.cardViewOnline = Utils.findRequiredViewAsType(source, R.id.cardViewOnline, "field 'cardViewOnline'", CardView.class);
    target.cardViewComplete = Utils.findRequiredViewAsType(source, R.id.cardViewComplete, "field 'cardViewComplete'", CardView.class);
    target.swipeContainer = Utils.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
    target.tvEmpty = Utils.findRequiredViewAsType(source, R.id.tvEmpty, "field 'tvEmpty'", TextView.class);
    view = Utils.findRequiredView(source, R.id.lnBack, "field 'lnBack' and method 'onBack'");
    target.lnBack = Utils.castView(view, R.id.lnBack, "field 'lnBack'", LinearLayout.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    target.nsv = Utils.findRequiredViewAsType(source, R.id.nsv, "field 'nsv'", NestedScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    KhamOnlineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lnAddSession = null;
    target.tvDangNhap = null;
    target.rvListSession = null;
    target.rvListSessionWait = null;
    target.rvListSessionComplete = null;
    target.lnNotSignIn = null;
    target.lnSignIn = null;
    target.cardViewWait = null;
    target.cardViewOnline = null;
    target.cardViewComplete = null;
    target.swipeContainer = null;
    target.tvEmpty = null;
    target.lnBack = null;
    target.nsv = null;

    view2131689828.setOnClickListener(null);
    view2131689828 = null;
    view2131689830.setOnClickListener(null);
    view2131689830 = null;
    view2131689646.setOnClickListener(null);
    view2131689646 = null;
  }
}
