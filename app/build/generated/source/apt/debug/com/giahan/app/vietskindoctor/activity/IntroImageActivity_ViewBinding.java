// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.gw.swipeback.SwipeBackLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroImageActivity_ViewBinding implements Unbinder {
  private IntroImageActivity target;

  private View view2131689670;

  private View view2131689672;

  @UiThread
  public IntroImageActivity_ViewBinding(IntroImageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IntroImageActivity_ViewBinding(final IntroImageActivity target, View source) {
    this.target = target;

    View view;
    target.mSwipeBackLayout = Utils.findRequiredViewAsType(source, R.id.swipeBackLayout, "field 'mSwipeBackLayout'", SwipeBackLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.pager_splash_screen, "field 'viewPager'", ViewPager.class);
    target.tvIndex = Utils.findRequiredViewAsType(source, R.id.tv_index, "field 'tvIndex'", TextView.class);
    target.mIndicator1 = Utils.findRequiredViewAsType(source, R.id.indicator_1, "field 'mIndicator1'", ImageView.class);
    target.mIndicator2 = Utils.findRequiredViewAsType(source, R.id.indicator_2, "field 'mIndicator2'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.imgClose, "method 'onClose'");
    view2131689670 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClose();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgUpload, "method 'onUpload'");
    view2131689672 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUpload();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    IntroImageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeBackLayout = null;
    target.viewPager = null;
    target.tvIndex = null;
    target.mIndicator1 = null;
    target.mIndicator2 = null;

    view2131689670.setOnClickListener(null);
    view2131689670 = null;
    view2131689672.setOnClickListener(null);
    view2131689672 = null;
  }
}
