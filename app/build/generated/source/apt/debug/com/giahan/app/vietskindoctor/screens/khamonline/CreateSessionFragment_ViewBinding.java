// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.khamonline;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreateSessionFragment_ViewBinding implements Unbinder {
  private CreateSessionFragment target;

  private View view2131689645;

  private View view2131689642;

  private View view2131689646;

  @UiThread
  public CreateSessionFragment_ViewBinding(final CreateSessionFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tvSsCreate, "field 'tvSsCreate' and method 'onCreate'");
    target.tvSsCreate = Utils.castView(view, R.id.tvSsCreate, "field 'tvSsCreate'", TextView.class);
    view2131689645 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCreate();
      }
    });
    target.edtWeight = Utils.findRequiredViewAsType(source, R.id.edtWeight, "field 'edtWeight'", EditText.class);
    target.edtDescription = Utils.findRequiredViewAsType(source, R.id.edtDescription, "field 'edtDescription'", EditText.class);
    view = Utils.findRequiredView(source, R.id.imgAddPhoto, "field 'imgAddPhoto' and method 'onAddPhoto'");
    target.imgAddPhoto = Utils.castView(view, R.id.imgAddPhoto, "field 'imgAddPhoto'", ImageView.class);
    view2131689642 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddPhoto();
      }
    });
    target.rvResultPhoto = Utils.findRequiredViewAsType(source, R.id.rvResultPhoto, "field 'rvResultPhoto'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.lnBack, "field 'lnBack' and method 'onBack'");
    target.lnBack = Utils.castView(view, R.id.lnBack, "field 'lnBack'", LinearLayout.class);
    view2131689646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBack();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CreateSessionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSsCreate = null;
    target.edtWeight = null;
    target.edtDescription = null;
    target.imgAddPhoto = null;
    target.rvResultPhoto = null;
    target.lnBack = null;

    view2131689645.setOnClickListener(null);
    view2131689645 = null;
    view2131689642.setOnClickListener(null);
    view2131689642 = null;
    view2131689646.setOnClickListener(null);
    view2131689646 = null;
  }
}
