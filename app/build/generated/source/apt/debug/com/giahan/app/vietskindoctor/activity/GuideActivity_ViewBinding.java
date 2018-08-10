// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.gw.swipeback.SwipeBackLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GuideActivity_ViewBinding implements Unbinder {
  private GuideActivity target;

  private View view2131689652;

  @UiThread
  public GuideActivity_ViewBinding(GuideActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GuideActivity_ViewBinding(final GuideActivity target, View source) {
    this.target = target;

    View view;
    target.mSwipeBackLayout = Utils.findRequiredViewAsType(source, R.id.swipeBackLayout, "field 'mSwipeBackLayout'", SwipeBackLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.pager_splash_screen, "field 'viewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.tv_skip, "field 'tvSkip' and method 'onSkip'");
    target.tvSkip = Utils.castView(view, R.id.tv_skip, "field 'tvSkip'", TextView.class);
    view2131689652 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSkip();
      }
    });
    target.mIndicator1 = Utils.findRequiredView(source, R.id.indicator_1, "field 'mIndicator1'");
    target.mIndicator2 = Utils.findRequiredView(source, R.id.indicator_2, "field 'mIndicator2'");
  }

  @Override
  @CallSuper
  public void unbind() {
    GuideActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeBackLayout = null;
    target.viewPager = null;
    target.tvSkip = null;
    target.mIndicator1 = null;
    target.mIndicator2 = null;

    view2131689652.setOnClickListener(null);
    view2131689652 = null;
  }
}
