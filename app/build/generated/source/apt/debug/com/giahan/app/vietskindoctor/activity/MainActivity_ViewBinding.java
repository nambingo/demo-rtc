// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131689919;

  private View view2131689922;

  private View view2131689925;

  private View view2131689928;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.bottomTab = Utils.findRequiredView(source, R.id.nav, "field 'bottomTab'");
    view = Utils.findRequiredView(source, R.id.rlVietSkin, "field 'rlVietSkin' and method 'onClick'");
    target.rlVietSkin = Utils.castView(view, R.id.rlVietSkin, "field 'rlVietSkin'", RelativeLayout.class);
    view2131689919 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rlDanhSach, "field 'rlDanhSach' and method 'onClick'");
    target.rlDanhSach = Utils.castView(view, R.id.rlDanhSach, "field 'rlDanhSach'", RelativeLayout.class);
    view2131689922 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rlKhamOnline, "field 'rlKhamOnline' and method 'onClick'");
    target.rlKhamOnline = Utils.castView(view, R.id.rlKhamOnline, "field 'rlKhamOnline'", RelativeLayout.class);
    view2131689925 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rlCaiDat, "field 'rlCaiDat' and method 'onClick'");
    target.rlCaiDat = Utils.castView(view, R.id.rlCaiDat, "field 'rlCaiDat'", RelativeLayout.class);
    view2131689928 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.tvVietSkin = Utils.findRequiredViewAsType(source, R.id.tvVietSkin, "field 'tvVietSkin'", TextView.class);
    target.tvDanhSach = Utils.findRequiredViewAsType(source, R.id.tvDanhSach, "field 'tvDanhSach'", TextView.class);
    target.tvKhamOnline = Utils.findRequiredViewAsType(source, R.id.tvKhamOnline, "field 'tvKhamOnline'", TextView.class);
    target.tvCaiDat = Utils.findRequiredViewAsType(source, R.id.tvCaiDat, "field 'tvCaiDat'", TextView.class);
    target.imgBacSi = Utils.findRequiredViewAsType(source, R.id.img_bac_si, "field 'imgBacSi'", ImageView.class);
    target.imgHome = Utils.findRequiredViewAsType(source, R.id.img_home, "field 'imgHome'", ImageView.class);
    target.imgKhamOnl = Utils.findRequiredViewAsType(source, R.id.img_kham_onl, "field 'imgKhamOnl'", ImageView.class);
    target.imgCaiDat = Utils.findRequiredViewAsType(source, R.id.img_cai_dat, "field 'imgCaiDat'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bottomTab = null;
    target.rlVietSkin = null;
    target.rlDanhSach = null;
    target.rlKhamOnline = null;
    target.rlCaiDat = null;
    target.tvVietSkin = null;
    target.tvDanhSach = null;
    target.tvKhamOnline = null;
    target.tvCaiDat = null;
    target.imgBacSi = null;
    target.imgHome = null;
    target.imgKhamOnl = null;
    target.imgCaiDat = null;

    view2131689919.setOnClickListener(null);
    view2131689919 = null;
    view2131689922.setOnClickListener(null);
    view2131689922 = null;
    view2131689925.setOnClickListener(null);
    view2131689925 = null;
    view2131689928.setOnClickListener(null);
    view2131689928 = null;
  }
}
