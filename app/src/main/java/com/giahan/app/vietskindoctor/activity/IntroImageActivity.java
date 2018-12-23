package com.giahan.app.vietskindoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.adapter.IntroImageAdapter;
import com.giahan.app.vietskindoctor.base.BaseActivity;
import com.giahan.app.vietskindoctor.domains.Photo;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.gw.swipeback.SwipeBackLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NamVT on 6/14/2018.
 */

public class IntroImageActivity extends BaseActivity {

    @BindView(R.id.swipeBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @BindView(R.id.pager_splash_screen)
    ViewPager viewPager;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.indicator_1)
    ImageView mIndicator1;
    @BindView(R.id.indicator_2)
    ImageView mIndicator2;

    private List<Photo> photos = new ArrayList<>();
    private int indexPos = 0;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_intro;
    }

    @Override
    protected void createView() {
        String data = getIntent().getStringExtra("data");
        if (!Toolbox.isEmpty(data)) {
            photos = Arrays.asList(Toolbox.gson().fromJson(data, Photo[].class));
        }
        indexPos = getIntent().getIntExtra("index", 0);
        initControl();

        if (indexPos == 0 && photos.size() > 1) {
            mIndicator1.setImageResource(R.drawable.ic_chevron_left_gray_24dp);
            mIndicator2.setImageResource(R.drawable.ic_chevron_right_white_24dp);
            tvIndex.setVisibility(View.VISIBLE);
            mIndicator1.setVisibility(View.VISIBLE);
            mIndicator2.setVisibility(View.VISIBLE);
        } else {
            if (indexPos == 0 && photos.size() == 1) {
                mIndicator1.setImageResource(R.drawable.ic_chevron_left_gray_24dp);
                mIndicator2.setImageResource(R.drawable.ic_chevron_right_gray_24dp);
                tvIndex.setVisibility(View.GONE);
                mIndicator1.setVisibility(View.GONE);
                mIndicator2.setVisibility(View.GONE);
            }
        }
        IntroImageAdapter mAdapter = new IntroImageAdapter(this, photos);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(indexPos);
        tvIndex.setText(String.valueOf(indexPos + 1) + "/" + photos.size());
    }

    private void initControl() {
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_RIGHT);
        mSwipeBackLayout.setMaskAlpha(125);
        mSwipeBackLayout.setSwipeBackFactor(0.5f);
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {

            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                if (isEnd) {
                    onClose();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                indexPos = arg0;
                tvIndex.setText(String.valueOf(arg0 + 1) + "/" + photos.size());
                int maxIndex = photos.size() - 1;
                if (arg0 == 0) {
                    mIndicator1.setImageResource(R.drawable.ic_chevron_left_gray_24dp);
                    mIndicator2.setImageResource(R.drawable.ic_chevron_right_white_24dp);
                } else {
                    if (arg0 == maxIndex) {
                        mIndicator1.setImageResource(R.drawable.ic_chevron_left_white_24dp);
                        mIndicator2.setImageResource(R.drawable.ic_chevron_right_gray_24dp);
                    } else {
                        mIndicator1.setImageResource(R.drawable.ic_chevron_left_white_24dp);
                        mIndicator2.setImageResource(R.drawable.ic_chevron_right_white_24dp);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @OnClick(R.id.imgClose)
    void onClose() {
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @OnClick(R.id.imgUpload)
    void onUpload() {
        if (!isOpen) {
            isOpen = true;
            shareImage(this);
        } else {
            showToastMsg(getString(R.string.please_wait));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    public void shareImage(final BaseActivity context) {
        if (photos.get(indexPos).getBitmap() != null) {
            showToastMsg(getString(R.string.please_wait2));
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/png");
            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(photos.get(indexPos).getBitmap(), context));
            startActivityForResult(Intent.createChooser(i, "Chia sẻ ảnh"), 1);
        } else {
            showToastMsg(getString(R.string.please_wait2));
            Glide.with(this)
                    .asBitmap()
                    .load(photos.get(indexPos).getUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull final Bitmap resource,
                                @Nullable final Transition<? super Bitmap> transition) {
                            int nh = (int) (resource.getHeight() * (512.0 / resource.getWidth()));
                            Bitmap scaled = Bitmap.createScaledBitmap(resource, 512, nh, true);
                            photos.get(indexPos).setBitmap(scaled);
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/png");
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(scaled, context));
                            startActivityForResult(Intent.createChooser(i, "Chia sẻ ảnh"), 1);
                        }
                    });
        }
    }

    public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "vietskin_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
//            bmpUri = Uri.fromFile(file);
            bmpUri = FileProvider.getUriForFile(this, "com.giahan.app.vietskin.fileprovider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            isOpen = false;
        }
    }//onActivityResult

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VietSkinDoctorApplication.setIsOpenDetailScreen(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
}
