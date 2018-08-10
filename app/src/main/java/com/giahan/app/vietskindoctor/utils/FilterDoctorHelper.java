package com.giahan.app.vietskindoctor.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.domains.Filter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 03/07/2018.
 */
public class FilterDoctorHelper {
    public Context mContext;
    public PrefHelper_ mPref;
    private List<Filter> mFilterList;
    private Doctor mDoctor;

    public FilterDoctorHelper(Context context, PrefHelper_ pref, Doctor doctor) {
        this.mContext = context;
        this.mPref = pref;
        this.mDoctor = doctor;
    }

    public FilterDoctorHelper(Context context, PrefHelper_ pref) {
        mContext = context;
        mPref = pref;
    }

    public List<Filter> getListFilter(){
        List<Filter> list = new ArrayList<>();
        if (!TextUtils.isEmpty(mPref.filterDoctor().get())) {
            Type type = new TypeToken<List<Filter>>() {
            }.getType();
            list = new Gson().fromJson(mPref.filterDoctor().get(), type);
        }
        return list;
    }

    public void init(LinearLayout lnMajor, TextView tvDegree){
        mFilterList = getListFilter();
        setupData(Constant.KEY_MAJOR, lnMajor, tvDegree);
        setupData(Constant.KEY_DEGREE, lnMajor, tvDegree);
    }

    private void setupData(String key, LinearLayout lnMajor, TextView tvDegree) {
        List<String> listLabel = new ArrayList<>();
        List<String> listValue = new ArrayList<>();
        for (int i = 0; i < mFilterList.size(); i++) {
            if (!mFilterList.get(i).getKey().equals(key)) continue;
            for (int j = 0; j < mFilterList.get(i).getOptions().size(); j++) {
                listLabel.add(mFilterList.get(i).getOptions().get(j).getLabel());
                listValue.add(mFilterList.get(i).getOptions().get(j).getValue());
            }
        }

        switch (key) {
            case Constant.KEY_MAJOR:
                setupListData(listLabel, listValue, mDoctor.getMajors(), null, lnMajor);
                break;
            case Constant.KEY_DEGREE:
                setupListData(listLabel, listValue, null, mDoctor.getDegree(), tvDegree);
                break;
        }
    }
    private void setupListData(List<String> listLabel, List<String> listValue, List<String> list,
            String value, TextView textView) {
        if (value != null && listValue.contains(value)){
            setText(listLabel.get(listValue.indexOf(value)), textView);
        }else if (list != null){
            List<String> listShow = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                listShow.add(listLabel.get(listValue.indexOf(list.get(j))));
            }
            for (int i = 0; i < listShow.size(); i++) {
                setText(listShow.get(i),textView);
            }
        }else textView.setText("Bác sĩ");
    }

    private void setupListData(List<String> listLabel, List<String> listValue, List<String> list,
            String value, LinearLayout lnLayout) {
        if (value != null && listValue.contains(value)){
            setText(listLabel.get(listValue.indexOf(value)), lnLayout);
        }else if (list != null){
            List<String> listShow = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                listShow.add(listLabel.get(listValue.indexOf(list.get(j))));
            }
            for (int i = 0; i < listShow.size(); i++) {
                setText(listShow.get(i),lnLayout);
            }
        }
    }

    private void setText(String value, LinearLayout lnLayout){
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(value);
        textView.setTextSize(15);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        if (lnLayout == null) return;
        lnLayout.addView(textView);
    }

    private void setText(String value, TextView textView){
        textView.setText(TextUtils.isEmpty(value)? "Bác sĩ " : value);
    }
}
