// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.idlestar.ratingstar.RatingStarView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChiTietBSFragment_ViewBinding implements Unbinder {
  private ChiTietBSFragment target;

  private View view2131689777;

  private View view2131689791;

  @UiThread
  public ChiTietBSFragment_ViewBinding(final ChiTietBSFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgBack, "field 'imgBack' and method 'onClick'");
    target.imgBack = Utils.castView(view, R.id.imgBack, "field 'imgBack'", ImageView.class);
    view2131689777 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvKinhNghiem = Utils.findRequiredViewAsType(source, R.id.tvKinhNghiem, "field 'tvKinhNghiem'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvDangKy, "field 'tvDangKy' and method 'onDangKy'");
    target.tvDangKy = Utils.castView(view, R.id.tvDangKy, "field 'tvDangKy'", TextView.class);
    view2131689791 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDangKy();
      }
    });
    target.imgSex = Utils.findRequiredViewAsType(source, R.id.imgSex, "field 'imgSex'", ImageView.class);
    target.imgStatus = Utils.findRequiredViewAsType(source, R.id.imgStatus, "field 'imgStatus'", ImageView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'tvStatus'", TextView.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", CircleImageView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", RatingStarView.class);
    target.lnMajor = Utils.findRequiredViewAsType(source, R.id.lnMajor, "field 'lnMajor'", LinearLayout.class);
    target.lnCity = Utils.findRequiredViewAsType(source, R.id.lnCity, "field 'lnCity'", LinearLayout.class);
    target.lnDegree = Utils.findRequiredViewAsType(source, R.id.lnDegree, "field 'lnDegree'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChiTietBSFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBack = null;
    target.tvName = null;
    target.tvKinhNghiem = null;
    target.tvDangKy = null;
    target.imgSex = null;
    target.imgStatus = null;
    target.tvStatus = null;
    target.imgAvatar = null;
    target.rating = null;
    target.lnMajor = null;
    target.lnCity = null;
    target.lnDegree = null;

    view2131689777.setOnClickListener(null);
    view2131689777 = null;
    view2131689791.setOnClickListener(null);
    view2131689791 = null;
  }
}
