package com.giahan.app.vietskindoctor.screens.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by NamVT on 5/23/2018.
 */

public class SettingContainerFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_container;
    }

    @Override
    protected void createView(View view) {
        CaiDatFragment caiDatFragment = new CaiDatFragment();
        getMainActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, caiDatFragment)
                .addToBackStack(null)
                .commit();
    }
}
