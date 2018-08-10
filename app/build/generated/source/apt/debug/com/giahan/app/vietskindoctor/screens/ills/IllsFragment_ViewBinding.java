// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.ills;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.hrskrs.instadotlib.InstaDotView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IllsFragment_ViewBinding implements Unbinder {
  private IllsFragment target;

  private View view2131689717;

  private View view2131689843;

  private View view2131689809;

  @UiThread
  public IllsFragment_ViewBinding(final IllsFragment target, View source) {
    this.target = target;

    View view;
    target.rvListIll = Utils.findRequiredViewAsType(source, R.id.rvListIll, "field 'rvListIll'", RecyclerView.class);
    target.instaDotView = Utils.findRequiredViewAsType(source, R.id.instaDotView, "field 'instaDotView'", InstaDotView.class);
    target.viewPagerIll = Utils.findRequiredViewAsType(source, R.id.viewPagerIll, "field 'viewPagerIll'", ViewPager.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.swipeContainer = Utils.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_close, "method 'close'");
    view2131689717 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.close();
      }
    });
    view = Utils.findRequiredView(source, R.id.lnShowDetail, "method 'showDetail'");
    view2131689843 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showDetail();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_register, "method 'goToMainDoctorScreen'");
    view2131689809 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToMainDoctorScreen();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    IllsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvListIll = null;
    target.instaDotView = null;
    target.viewPagerIll = null;
    target.tvName = null;
    target.tvContent = null;
    target.swipeContainer = null;

    view2131689717.setOnClickListener(null);
    view2131689717 = null;
    view2131689843.setOnClickListener(null);
    view2131689843 = null;
    view2131689809.setOnClickListener(null);
    view2131689809 = null;
  }
}
