// Generated code from Butter Knife. Do not modify!
package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.giahan.app.vietskindoctor.R;
import com.idlestar.ratingstar.RatingStarView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DanhSachBSAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private DanhSachBSAdapter.MyViewHolder target;

  @UiThread
  public DanhSachBSAdapter$MyViewHolder_ViewBinding(DanhSachBSAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.imgSex = Utils.findRequiredViewAsType(source, R.id.imgSex, "field 'imgSex'", ImageView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.lnItem = Utils.findRequiredViewAsType(source, R.id.lnItem, "field 'lnItem'", LinearLayout.class);
    target.imgAvatar = Utils.findRequiredViewAsType(source, R.id.imgAvatar, "field 'imgAvatar'", CircleImageView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", RatingStarView.class);
    target.tvRating = Utils.findRequiredViewAsType(source, R.id.tvRating, "field 'tvRating'", TextView.class);
    target.tvBangCap = Utils.findRequiredViewAsType(source, R.id.tvBangCap, "field 'tvBangCap'", TextView.class);
    target.tvBenhVien = Utils.findRequiredViewAsType(source, R.id.tvBenhVien, "field 'tvBenhVien'", TextView.class);
    target.tvGiaTien = Utils.findRequiredViewAsType(source, R.id.tvGiaTien, "field 'tvGiaTien'", TextView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tvStatus, "field 'tvStatus'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DanhSachBSAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgSex = null;
    target.tvName = null;
    target.lnItem = null;
    target.imgAvatar = null;
    target.rating = null;
    target.tvRating = null;
    target.tvBangCap = null;
    target.tvBenhVien = null;
    target.tvGiaTien = null;
    target.tvStatus = null;
  }
}
