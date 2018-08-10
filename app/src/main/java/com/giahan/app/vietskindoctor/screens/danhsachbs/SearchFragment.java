package com.giahan.app.vietskindoctor.screens.danhsachbs;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.DoctorSearch;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.FilterDoctorHelper;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.KeyBoardUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 03/07/2018.
 */
public class SearchFragment extends BaseFragment {
    @BindView(R.id.lnBack)
    LinearLayout lnBack;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.spBangCap)
    Spinner spDegree;
    @BindView(R.id.spNamKinhNghiem)
    Spinner spExperience;
    @BindView(R.id.spGioiTinh)
    Spinner spSex;
    @BindView(R.id.spNgonNgu)
    Spinner spLanguages;
    @BindView(R.id.spChuyenNganh)
    Spinner spMajors;
    @BindView(R.id.spTrucTuyen)
    Spinner spOnline;
    @BindView(R.id.spThanhPho)
    Spinner spCity;
    @BindView(R.id.btnTimKiem)
    TextView btnTimKiem;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;

    private FilterDoctorHelper filterDoctorHelper;

    private String degree, experience, sex, languages, majors, online, city;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void createView(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        new Handler().postDelayed(this::initView, 1000);
    }

    @OnClick(R.id.lnBack)
    public void onBack(){
        getMainActivity().popFragmentNoAnim();
    }

    @OnClick(R.id.imgClear)
    public void onClickClear(){
        edtSearch.setText("");
        KeyBoardUtil.hide(mActivity);
    }

    @OnClick(R.id.btnTimKiem)
    public void onTimKiem(){
        onSearch();
    }

    @OnClick(R.id.imgSearch)
    public void onSearchName(){
        onSearch();
    }

    private void onSearch(){
        mPref.isListDoctor().put(false);
        KeyBoardUtil.hide(getMainActivity());
        putDataToSearch();
        getMainActivity().pushFragmentNoAnim(new DanhSachBSFragment());
    }

    private void putDataToSearch(){
        DoctorSearch doctorSearch = new DoctorSearch();
        doctorSearch.setName(TextUtils.isEmpty(edtSearch.getText().toString()) ? null : edtSearch
                .getText().toString());
        doctorSearch.setExperience(experience);
        doctorSearch.setLanguages(languages);
        doctorSearch.setDegree(degree);
        doctorSearch.setSex(sex);
        doctorSearch.setMajors(majors);
        doctorSearch.setOnline(online);
        doctorSearch.setCity(city);
        mPref.dataSearch().put(GeneralUtil.toJSon(doctorSearch));
    }

    private void initView(){
        filterDoctorHelper = new FilterDoctorHelper(mActivity, mPref);
        setupListFilter(Constant.KEY_SEX, spSex);
        setupListFilter(Constant.KEY_CITY, spCity);
        setupListFilter(Constant.KEY_DEGREE, spDegree);
        setupListFilter(Constant.KEY_EXPERIENCE, spExperience);
        setupListFilter(Constant.KEY_LANGUAGE, spLanguages);
        setupListFilter(Constant.KEY_MAJOR, spMajors);
        setupListFilter(Constant.KEY_ONLINE, spOnline);
        Log.e("SearchFragment", "initView:  -----> ");
        hideLoading();
    }

    private void setupListFilter(String key, Spinner spinner) {
        List<String> listLabel = new ArrayList<>();
        List<String> listValue = new ArrayList<>();
        for (int i = 0; i < filterDoctorHelper.getListFilter().size(); i++) {
            if (!filterDoctorHelper.getListFilter().get(i).getKey().equals(key)) continue;
            for (int j = 0; j < filterDoctorHelper.getListFilter().get(i).getOptions().size(); j++) {
                listLabel.add(filterDoctorHelper.getListFilter().get(i).getOptions().get(j).getLabel());
                listValue.add(filterDoctorHelper.getListFilter().get(i).getOptions().get(j).getValue());
            }
        }

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(mActivity, R.layout.spinner_text, listLabel);
        dataAdapter.insert(getString(R.string.tat_ca), 0);
        listValue.add(0, getString(R.string.tat_ca));
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDataToSearch(key, listValue, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDataToSearch(String key, List<String> list, int position) {
        switch (key) {
            case Constant.KEY_DEGREE:
                degree = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_EXPERIENCE:
                experience = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_SEX:
                sex = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_LANGUAGE:
                languages = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_MAJOR:
                majors = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_ONLINE:
                online = position == 0 ? null : list.get(position);
                break;
            case Constant.KEY_CITY:
                city = position == 0 ? null : list.get(position);
                break;
        }
    }
}
