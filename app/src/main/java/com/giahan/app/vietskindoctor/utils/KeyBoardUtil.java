package com.giahan.app.vietskindoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by pham.duc.nam on 01/06/2018.
 */

public class KeyBoardUtil {

    public static void show(Activity activity) {

        View view = activity.getCurrentFocus();
        if (view != null) {

            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hide(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void removeAllFocus(Activity act) {
        View current = act.getCurrentFocus();
        if (current != null) {
            ViewGroup rootView = (ViewGroup) current.getRootView();
            if (rootView != null) {
                int dfValue = rootView.getDescendantFocusability();
                rootView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                current.clearFocus();
                rootView.setDescendantFocusability(dfValue);
            } else {
                Log.d("CHECKLIST CHECK", "Current rootView is null!");
            }
        } else {
            Log.d("CHECKLIST CHECK", "Current Focus is null!");
        }
    }
}
