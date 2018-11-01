package com.giahan.app.vietskindoctor.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.ProgressDialogUtil;

import butterknife.ButterKnife;

/**
 * Created by pham.duc.nam
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    protected View mView;
    protected ProgressDialogUtil mProgressDialogUtil;
    public PrefHelper_ mPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mProgressDialogUtil = new ProgressDialogUtil(mActivity);
        mPref = new PrefHelper_(VietSkinDoctorApplication.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mView);
            createView(mView);
        }
        return mView;
    }

    @Override
    public void onDestroyView() {
        if (mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        super.onDestroyView();
    }

    protected abstract int getLayoutId();

    protected abstract void createView(View view);

    public void makeToast(String msg) {
        mActivity.makeToast(msg);
    }

    public void makeToast(int msgId) {
        String msg = getString(msgId);
        makeToast(msg);
    }

    public void showActivity(Class t) {
        mActivity.showActivity(t);
    }

    public void showActivity(Class t, Bundle bundle) {
        mActivity.showActivity(t, bundle);
    }

    protected void hideLoading() {
        mProgressDialogUtil.hideProgress();
    }

    protected void hideLoading2(){
        if (mProgressDialogUtil != null && mProgressDialogUtil.isShowing()) {
            mProgressDialogUtil.dismiss();
        }
    }

    protected void showLoading() {
        if (mProgressDialogUtil != null && !mProgressDialogUtil.isShowing()) {
            mProgressDialogUtil.show(false);
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().findViewById(android.R.id.content).getWindowToken(), 0);
        }
    }

    public MainActivity getMainActivity() {
        return ((MainActivity) getActivity());
    }

    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

}
