// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  private View view2131689646;

  private View view2131689857;

  private View view2131689855;

  private View view2131689856;

  @UiThread
  public SearchFragment_ViewBinding(final SearchFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lnBack, "field 'lnBack' and method 'onBack'");
    target.lnBack = Utils.castView(view, R.id.lnBack, "field 'lnBack'", LinearLayout.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
    target.edtSearch = Utils.findRequiredViewAsType(source, R.id.edtSearch, "field 'edtSearch'", EditText.class);
    target.spDegree = Utils.findRequiredViewAsType(source, R.id.spBangCap, "field 'spDegree'", Spinner.class);
    target.spExperience = Utils.findRequiredViewAsType(source, R.id.spNamKinhNghiem, "field 'spExperience'", Spinner.class);
    target.spSex = Utils.findRequiredViewAsType(source, R.id.spGioiTinh, "field 'spSex'", Spinner.class);
    target.spLanguages = Utils.findRequiredViewAsType(source, R.id.spNgonNgu, "field 'spLanguages'", Spinner.class);
    target.spMajors = Utils.findRequiredViewAsType(source, R.id.spChuyenNganh, "field 'spMajors'", Spinner.class);
    target.spOnline = Utils.findRequiredViewAsType(source, R.id.spTrucTuyen, "field 'spOnline'", Spinner.class);
    target.spCity = Utils.findRequiredViewAsType(source, R.id.spThanhPho, "field 'spCity'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.btnTimKiem, "field 'btnTimKiem' and method 'onTimKiem'");
    target.btnTimKiem = Utils.castView(view, R.id.btnTimKiem, "field 'btnTimKiem'", TextView.class);
    view2131689857 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onTimKiem();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgSearch, "field 'imgSearch' and method 'onSearchName'");
    target.imgSearch = Utils.castView(view, R.id.imgSearch, "field 'imgSearch'", ImageView.class);
    view2131689855 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchName();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgClear, "method 'onClickClear'");
    view2131689856 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickClear();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lnBack = null;
    target.edtSearch = null;
    target.spDegree = null;
    target.spExperience = null;
    target.spSex = null;
    target.spLanguages = null;
    target.spMajors = null;
    target.spOnline = null;
    target.spCity = null;
    target.btnTimKiem = null;
    target.imgSearch = null;

    view2131689646.setOnClickListener(null);
    view2131689646 = null;
    view2131689857.setOnClickListener(null);
    view2131689857 = null;
    view2131689855.setOnClickListener(null);
    view2131689855 = null;
    view2131689856.setOnClickListener(null);
    view2131689856 = null;
  }
}
