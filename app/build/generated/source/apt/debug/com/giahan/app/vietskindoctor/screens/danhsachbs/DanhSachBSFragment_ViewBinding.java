// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class DanhSachBSFragment_ViewBinding implements Unbinder {
  private DanhSachBSFragment target;

  private View view2131689646;

  private View view2131689647;

  private View view2131689793;

  @UiThread
  public DanhSachBSFragment_ViewBinding(final DanhSachBSFragment target, View source) {
    this.target = target;

    View view;
    target.rvListBacSi = Utils.findRequiredViewAsType(source, R.id.rvListBacSi, "field 'rvListBacSi'", RecyclerView.class);
    target.swipeContainer = Utils.findRequiredViewAsType(source, R.id.swipeContainer, "field 'swipeContainer'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.lnBack, "field 'lnBack' and method 'onBack'");
    target.lnBack = Utils.castView(view, R.id.lnBack, "field 'lnBack'", LinearLayout.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    target.imgSearch2 = Utils.findRequiredViewAsType(source, R.id.imgSearch2, "field 'imgSearch2'", ImageView.class);
    target.tvSoTien = Utils.findRequiredViewAsType(source, R.id.tvSoTien, "field 'tvSoTien'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cardView, "field 'cardView' and method 'onGoToRechargeScreen'");
    target.cardView = Utils.castView(view, R.id.cardView, "field 'cardView'", CardView.class);
    view2131689647 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onGoToRechargeScreen();
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rlSearch, "field 'rlSearch' and method 'onSearch2'");
    target.rlSearch = Utils.castView(view, R.id.rlSearch, "field 'rlSearch'", RelativeLayout.class);
    view2131689793 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearch2();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DanhSachBSFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvListBacSi = null;
    target.swipeContainer = null;
    target.lnBack = null;
    target.imgSearch2 = null;
    target.tvSoTien = null;
    target.cardView = null;
    target.tvTitle = null;
    target.rlSearch = null;

    view2131689646.setOnClickListener(null);
    view2131689646 = null;
    view2131689647.setOnClickListener(null);
    view2131689647 = null;
    view2131689793.setOnClickListener(null);
    view2131689793 = null;
  }
}
