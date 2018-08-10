// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoAccountV3Fragment_ViewBinding implements Unbinder {
  private InfoAccountV3Fragment target;

  private View view2131689674;

  private View view2131689823;

  private View view2131689813;

  @UiThread
  public InfoAccountV3Fragment_ViewBinding(final InfoAccountV3Fragment target, View source) {
    this.target = target;

    View view;
    target.tvSoTien = Utils.findRequiredViewAsType(source, R.id.tvSoTien, "field 'tvSoTien'", TextView.class);
    target.tvProfileName = Utils.findRequiredViewAsType(source, R.id.tv_profile_name, "field 'tvProfileName'", TextView.class);
    target.tvId = Utils.findRequiredViewAsType(source, R.id.tv_id, "field 'tvId'", TextView.class);
    target.tvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmail'", TextView.class);
    target.tvCoin = Utils.findRequiredViewAsType(source, R.id.tv_coin, "field 'tvCoin'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    target.tvGender = Utils.findRequiredViewAsType(source, R.id.tv_gender, "field 'tvGender'", TextView.class);
    target.tvBirthday = Utils.findRequiredViewAsType(source, R.id.tv_birthday, "field 'tvBirthday'", TextView.class);
    target.tvWeight = Utils.findRequiredViewAsType(source, R.id.tv_weight, "field 'tvWeight'", TextView.class);
    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profileImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
    view = Utils.findRequiredView(source, R.id.wallet, "method 'openRecharge'");
    view2131689823 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openRecharge();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_update, "method 'openScreenUpdateInfo'");
    view2131689813 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openScreenUpdateInfo();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoAccountV3Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSoTien = null;
    target.tvProfileName = null;
    target.tvId = null;
    target.tvEmail = null;
    target.tvCoin = null;
    target.tvPhone = null;
    target.tvGender = null;
    target.tvBirthday = null;
    target.tvWeight = null;
    target.profileImage = null;

    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689823.setOnClickListener(null);
    view2131689823 = null;
    view2131689813.setOnClickListener(null);
    view2131689813 = null;
  }
}
