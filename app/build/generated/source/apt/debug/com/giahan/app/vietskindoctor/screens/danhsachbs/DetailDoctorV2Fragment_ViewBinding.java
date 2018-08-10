// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public class DetailDoctorV2Fragment_ViewBinding implements Unbinder {
  private DetailDoctorV2Fragment target;

  private View view2131689647;

  private View view2131689791;

  private View view2131689674;

  @UiThread
  public DetailDoctorV2Fragment_ViewBinding(final DetailDoctorV2Fragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.cardView, "field 'cardView' and method 'onGoToRechargeScreen'");
    target.cardView = Utils.castView(view, R.id.cardView, "field 'cardView'", CardView.class);
    view2131689647 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onGoToRechargeScreen();
      }
    });
    target.cardViewPhoto = Utils.findRequiredViewAsType(source, R.id.cardViewPhoto, "field 'cardViewPhoto'", CardView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvSoTien = Utils.findRequiredViewAsType(source, R.id.tvSoTien, "field 'tvSoTien'", TextView.class);
    target.tvInfo = Utils.findRequiredViewAsType(source, R.id.tvInfo, "field 'tvInfo'", TextView.class);
    target.tvRate = Utils.findRequiredViewAsType(source, R.id.tvRate, "field 'tvRate'", TextView.class);
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
    target.tvWorkplace = Utils.findRequiredViewAsType(source, R.id.tvWorkplace, "field 'tvWorkplace'", TextView.class);
    target.imgSex = Utils.findRequiredViewAsType(source, R.id.imgSex, "field 'imgSex'", ImageView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'tvStatus'", TextView.class);
    target.tvFee = Utils.findRequiredViewAsType(source, R.id.tvFee, "field 'tvFee'", TextView.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", CircleImageView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", RatingStarView.class);
    target.lnMajor = Utils.findRequiredViewAsType(source, R.id.lnMajor, "field 'lnMajor'", LinearLayout.class);
    target.lnCity = Utils.findRequiredViewAsType(source, R.id.lnCity, "field 'lnCity'", LinearLayout.class);
    target.lnDegree = Utils.findRequiredViewAsType(source, R.id.lnDegree, "field 'lnDegree'", LinearLayout.class);
    target.rvListImg = Utils.findRequiredViewAsType(source, R.id.rvListImg, "field 'rvListImg'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'onClick'");
    view2131689674 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailDoctorV2Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.cardViewPhoto = null;
    target.tvName = null;
    target.tvSoTien = null;
    target.tvInfo = null;
    target.tvRate = null;
    target.tvKinhNghiem = null;
    target.tvDangKy = null;
    target.tvWorkplace = null;
    target.imgSex = null;
    target.tvStatus = null;
    target.tvFee = null;
    target.imgAvatar = null;
    target.rating = null;
    target.lnMajor = null;
    target.lnCity = null;
    target.lnDegree = null;
    target.rvListImg = null;

    view2131689647.setOnClickListener(null);
    view2131689647 = null;
    view2131689791.setOnClickListener(null);
    view2131689791 = null;
    view2131689674.setOnClickListener(null);
    view2131689674 = null;
  }
}
