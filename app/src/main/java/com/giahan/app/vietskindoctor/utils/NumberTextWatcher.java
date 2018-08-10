package com.giahan.app.vietskindoctor.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";
    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;
    private EditText et;
    private int trailingZeroCount = 0;

    public NumberTextWatcher(EditText et) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df = new DecimalFormat("#,###.##", symbols);
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###", symbols);
        this.et = et;
        hasFractionalPart = false;
    }

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);
        if (s != null && !s.toString().isEmpty()) {
            try {
                int inilen, endlen;
                inilen = et.getText().length();

                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if (hasFractionalPart) {
                    StringBuilder trailingZeros = new StringBuilder();
                    while (trailingZeroCount-- > 0) trailingZeros.append('0');
                    et.setText(df.format(n) + trailingZeros.toString());
                } else {
                    et.setText(dfnd.format(n));
                }

                hasFractionalPart = s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));

                if (hasFractionalPart) {
                    et.setText(df.format(n));
                } else {
                    et.setText(dfnd.format(n));
                }
                endlen = et.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel < 0) {
                    sel = 0;
                }
                if (sel >= 0 && sel <= et.getText().length()) {
                    et.setSelection(sel);
                } else {
                    et.setSelection(et.getText().length() - 1);
                }
            } catch (NumberFormatException | ParseException nfe) {
                nfe.printStackTrace();
            }
        }
        et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int index = s.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
        trailingZeroCount = 0;
        if (index > -1) {
            for (index++; index < s.length(); index++) {
                if (s.charAt(index) == '0')
                    trailingZeroCount++;
                else {
                    trailingZeroCount = 0;
                }
            }

            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }

    }
}