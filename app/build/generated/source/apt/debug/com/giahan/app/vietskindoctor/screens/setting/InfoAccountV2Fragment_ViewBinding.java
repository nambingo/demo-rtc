// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoAccountV2Fragment_ViewBinding implements Unbinder {
  private InfoAccountV2Fragment target;

  private View view2131689747;

  private View view2131689821;

  private View view2131689757;

  private View view2131689748;

  private View view2131689820;

  private View view2131689674;

  private View view2131689813;

  private View view2131689754;

  @UiThread
  public InfoAccountV2Fragment_ViewBinding(final InfoAccountV2Fragment target, View source) {
    this.target = target;

    View view;
    target.tvProfileName = Utils.findRequiredViewAsType(source, R.id.tv_profile_name, "field 'tvProfileName'", TextView.class);
    target.tvId = Utils.findRequiredViewAsType(source, R.id.tv_id, "field 'tvId'", TextView.class);
    target.tvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmail'", TextView.class);
    target.edtPhone = Utils.findRequiredViewAsType(source, R.id.edt_phone, "field 'edtPhone'", MaterialEditText.class);
    target.edtGender = Utils.findRequiredViewAsType(source, R.id.edt_gender, "field 'edtGender'", MaterialEditText.class);
    view = Utils.findRequiredView(source, R.id.edt_from_date, "field 'edtDate' and method 'clickFromDate'");
    target.edtDate = Utils.castView(view, R.id.edt_from_date, "field 'edtDate'", MaterialEditText.class);
    view2131689747 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickFromDate();
      }
    });
    target.edtAddress = Utils.findRequiredViewAsType(source, R.id.edt_address, "field 'edtAddress'", MaterialEditText.class);
    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profileImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btnSave' and method 'save'");
    target.btnSave = Utils.castView(view, R.id.btn_save, "field 'btnSave'", Button.class);
    view2131689821 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.save();
      }
    });
    view = Utils.findRequiredView(source, R.id.delPhone, "field 'delPhone' and method 'onClick'");
    target.delPhone = view;
    view2131689757 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.delGender = Utils.findRequiredView(source, R.id.delGender, "field 'delGender'");
    view = Utils.findRequiredView(source, R.id.delDate, "field 'delDate' and method 'onClick'");
    target.delDate = view;
    view2131689748 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.delAddress, "field 'delAddress' and method 'onClick'");
    target.delAddress = view;
    view2131689820 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.rlGender = Utils.findRequiredView(source, R.id.rlGender, "field 'rlGender'");
    target.rlUpdateGender = Utils.findRequiredView(source, R.id.rlUpdateGender, "field 'rlUpdateGender'");
    target.llChooseDate = Utils.findRequiredView(source, R.id.llChooseDate, "field 'llChooseDate'");
    target.radioM = Utils.findRequiredViewAsType(source, R.id.radioM, "field 'radioM'", RadioButton.class);
    target.radioF = Utils.findRequiredViewAsType(source, R.id.radioF, "field 'radioF'", RadioButton.class);
    target.radioGrp = Utils.findRequiredViewAsType(source, R.id.radioGrp, "field 'radioGrp'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
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
    view = Utils.findRequiredView(source, R.id.delGender2, "method 'onClick'");
    view2131689754 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoAccountV2Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvProfileName = null;
    target.tvId = null;
    target.tvEmail = null;
    target.edtPhone = null;
    target.edtGender = null;
    target.edtDate = null;
    target.edtAddress = null;
    target.profileImage = null;
    target.btnSave = null;
    target.delPhone = null;
    target.delGender = null;
    target.delDate = null;
    target.delAddress = null;
    target.rlGender = null;
    target.rlUpdateGender = null;
    target.llChooseDate = null;
    target.radioM = null;
    target.radioF = null;
    target.radioGrp = null;

    view2131689747.setOnClickListener(null);
    view2131689747 = null;
    view2131689821.setOnClickListener(null);
    view2131689821 = null;
    view2131689757.setOnClickListener(null);
    view2131689757 = null;
    view2131689748.setOnClickListener(null);
    view2131689748 = null;
    view2131689820.setOnClickListener(null);
    view2131689820 = null;
    view2131689674.setOnClickListener(null);
    view2131689674 = null;
    view2131689813.setOnClickListener(null);
    view2131689813 = null;
    view2131689754.setOnClickListener(null);
    view2131689754 = null;
  }
}
