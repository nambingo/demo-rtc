package com.giahan.app.vietskindoctor.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham.duc.nam on 29/10/2018.
 */
public class ActionsDialog extends BaseBottomDialog{
    public BottomActionFragmentCallback callback;
    public Context mContext = VietSkinDoctorApplication.getInstance();
    private List<String> listString = new ArrayList<>();

    public void getData(List<String> list){
        this.listString = list;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_bottom_action;
    }

    @Override
    public void bindView(View view) {
        ListView listActions = (ListView) view.findViewById(R.id.list_buttons);
        listActions.setDivider(new ColorDrawable(Color.DKGRAY));
        listActions.setDividerHeight(1);
        ListAdapter listAdapter =
                new ArrayAdapter<String>(mContext, R.layout.item_custom_list_center, listString) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView tvTemp;
                        tvTemp = view.findViewById(android.R.id.text1);
                        if (tvTemp != null) {
                            if (position == 0) {
                                tvTemp.setTextColor(Color.RED);
                                view.setClickable(false);
                            } else {
                                tvTemp.setTextColor(Color.DKGRAY);
                            }
                        }
                        return view;
                    }
                };
        listActions.setAdapter(listAdapter);
        listActions.setOnItemClickListener((parent, view1, position, id) -> {

            callback.onClickButton(position);
            dismiss();
        });
        //setup button Cancel
        Button btnCancel = view.findViewById(R.id.button_cancel);
        btnCancel.setText(getString(R.string.cancel));
        btnCancel.setTransformationMethod(null);
        btnCancel.setTextSize(16);
        btnCancel.setOnClickListener(v -> dismiss());
    }

    public interface BottomActionFragmentCallback {
        void onClickButton(int index);
    }
}
