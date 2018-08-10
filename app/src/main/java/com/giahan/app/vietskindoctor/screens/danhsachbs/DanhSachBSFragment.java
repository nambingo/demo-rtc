package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinApplication;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.domains.DoctorSearch;
import com.giahan.app.vietskindoctor.domains.Filter;
import com.giahan.app.vietskindoctor.domains.ListDoctorResult;
import com.giahan.app.vietskindoctor.domains.ListFilterResult;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.Toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pham.duc.nam
 */

public class DanhSachBSFragment extends BaseFragment
        implements DanhSachBSAdapter.OnClickDoctorListener {

    @BindView(R.id.rvListBacSi)
    RecyclerView rvListBacSi;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.lnBack)
    LinearLayout lnBack;
    @BindView(R.id.imgSearch2)
    ImageView imgSearch2;
    @BindView(R.id.tvSoTien)
    TextView tvSoTien;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;

    private List<Doctor> mDoctorList = new ArrayList<>();
    private List<Filter> mFilterList = new ArrayList<>();
    private DanhSachBSAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_danh_sach_bs;
    }

    @Override
    protected void createView(View view) {
        setupView();
        getDataFilter();
        onSwipeRefresh();
    }

    private void setupView() {
        adapter = new DanhSachBSAdapter(getActivity(), mDoctorList, mPref);
        adapter.setOnClickDoctor(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvListBacSi.setLayoutManager(mLayoutManager);
        rvListBacSi.setItemAnimator(new DefaultItemAnimator());
        rvListBacSi.setAdapter(adapter);
        tvTitle.setText(
                TextUtils.isEmpty(mPref.dataSearch().get()) ? getString(R.string.danh_sach_bs)
                        : getString(R.string.title_result));
        imgSearch2.setVisibility(TextUtils.isEmpty(mPref.dataSearch().get()) ? View.VISIBLE : View.GONE);
        //lnBack.setVisibility(mPref.isListDoctor().get() ? View.GONE : View.VISIBLE);
    }

    private void onSwipeRefresh() {
        swipeContainer.setOnRefreshListener(() -> {
            if (!TextUtils.isEmpty(mPref.dataSearch().get())) {
                resultSearch();
            } else {
                getData(null, null, null, null, null, null, null, null);
            }
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        });
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.color_background));
    }

    private void getDataFilter() {
        Call<ListFilterResult> call = RequestHelper.getRequest(false, getActivity()).getFilter();
        call.enqueue(new Callback<ListFilterResult>() {
            @Override
            public void onResponse(Call<ListFilterResult> call,
                                   Response<ListFilterResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null || response.body().getFilters() == null) return;
                mFilterList = response.body().getFilters();
                mPref.filterDoctor().put(GeneralUtil.toJSon(mFilterList));
            }

            @Override
            public void onFailure(Call<ListFilterResult> call, Throwable t) {
                // Todo
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void getData(String name, String degree, String experience, String sex, String
            languages,
                         String majors, String online, String city) {
        mDoctorList.clear();
        showLoading();
        Call<ListDoctorResult> call = RequestHelper.getRequest(false, getActivity())
                .getListDoctorResult(name, degree, experience, sex, languages, majors, online,
                        city);
        call.enqueue(new Callback<ListDoctorResult>() {
            @Override
            public void onResponse(Call<ListDoctorResult> call,
                                   Response<ListDoctorResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null || response.body().getDoctorList() == null) return;
                hideLoading();
                mDoctorList.addAll(response.body().getDoctorList());
                sortFee(mDoctorList);
                adapter.notifyDataSetChanged();
                if (mDoctorList.size() == 0) {
                    DialogUtils.showDialogOneChoice(getActivity(), true, false, getString(R.string
                            .docter_not_found), getString(R.string.close), view -> {
                        DialogUtils.hideAlert();
                        onBackPress();
                    });
                }
            }

            @Override
            public void onFailure(Call<ListDoctorResult> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void sortFee(List<Doctor> list) {
        Collections.sort(list, (doctor, t1) -> Integer.parseInt(t1.getFee()) - Integer.parseInt(doctor.getFee()));
    }

    @OnClick(R.id.rlSearch)
    public void onSearch2() {
        getMainActivity().pushFragmentNoAnim(new SearchFragment());
    }

    @OnClick(R.id.lnBack)
    public void onBack() {
        onBackPress();
    }

    private void onBackPress() {
        mPref.dataSearch().put("");
        getMainActivity().onBackPressed();
    }

    @OnClick(R.id.cardView)
    public void onGoToRechargeScreen() {
        VietSkinApplication.setIsBackToSession(false);
        getMainActivity().goToRechargeScreen();
//        getMainActivity().pushFragment(new RechargeFragment());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mPref.dataSearch().get())) {
            resultSearch();
        } else {
            getData(null, null, null, null, null, null, null, null);
        }
        String mToken = mPref.token().get();
        cardView.setVisibility(TextUtils.isEmpty(mToken) ? View.GONE : View.VISIBLE);
        UserInfoResponse user = UserInfoResponse.getUser(getMainActivity().pref);
        if (user == null) return;
        tvSoTien.setText(TextUtils.isEmpty(user.getCredits()) ? getResources().getText(
                R.string.vi_vietskin_cua_ban) + " 0 VNĐ"
                : getResources().getText(R.string.vi_vietskin_cua_ban) + " " + Toolbox.formatMoney(
                user.getCredits()) + " VNĐ");
    }

    private void resultSearch() {
        DoctorSearch doctorSearch =
                GeneralUtil.fromJSon(DoctorSearch.class, mPref.dataSearch().get());
        getData(doctorSearch.getName(), doctorSearch.getDegree(), doctorSearch.getExperience(),
                doctorSearch.getSex(), doctorSearch.getLanguages(), doctorSearch.getMajors(),
                doctorSearch.getOnline(), doctorSearch.getCity());
    }

    @Override
    public void onClickDoctor(Doctor doctor) {
        mPref.currentDoctor().put(GeneralUtil.toJSon(doctor));
        getMainActivity().pushFragment(new DetailDoctorV2Fragment());
    }
}
