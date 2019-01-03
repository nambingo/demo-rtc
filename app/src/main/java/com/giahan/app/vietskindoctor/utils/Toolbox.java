package com.giahan.app.vietskindoctor.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.Gson;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NamVT on 5/18/2018.
 */

public class Toolbox {
    static final String TAG = "Toolbox";
    private static Gson defaultGson;

    static {
        defaultGson = new Gson();
    }

    public static Gson gson() {
        if (defaultGson == null) defaultGson = new Gson();
        return defaultGson;
    }

    public interface EditTextListener {
        void onListen();
    }

    public static <T> boolean contains(final T[] array, final T key) {
        return Arrays.asList(array).contains(key);
    }

    public static boolean contains(final int[] array, final int key) {
        return ArrayUtils.contains(array, key);
    }

    public static String getKeyHashFb(Context context) {
        // Add code to print out the key hash
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.giahan.app.vietskin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("Key hash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || TextUtils.getTrimmedLength(str) == 0 || "null".equalsIgnoreCase(str.toString().trim());
    }

    public static String formatMoney(String strMoney) {
        return formatMoney(strMoney, null, null);
    }

    public static <T> String formatMoney(T money, String unit) {
        if (money instanceof String) return formatMoney((String) money, unit, null);
        else if (money instanceof Integer) return formatMoneyLong((Integer) money, unit);
        else if (money instanceof Long) return formatMoneyLong((Long) money, unit);
        else if (money instanceof Float) return formatMoneyDouble((Float) money, unit);
        else if (money instanceof Double) return formatMoneyDouble((Double) money, unit);
        else if (money != null) return formatMoney(money.toString(), unit);
        else return null;
    }

    public static String formatMoney(String strMoney, String unit, String valueIfEmpty) {
        String moneyFormat;
        if (TextUtils.isEmpty(strMoney)) {
            return isEmpty(valueIfEmpty) ? "" : valueIfEmpty;
        } else {
            String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
            String strDefault = strMoney.replaceAll(replaceable, "");
            strDefault = strDefault.replaceAll("-", "");
            strDefault = strDefault.replace(".", "");
            int length = strDefault.length();
            if (length < 4) {
                moneyFormat = strDefault;
            } else if (length < 7) {
                moneyFormat = strDefault.substring(0, length - 3) + "."
                        + strDefault.substring(length - 3, length);
            } else if (length < 10) {
                moneyFormat = strDefault.substring(0, length - 6) + "."
                        + strDefault.substring(length - 6, length - 3) + "."
                        + strDefault.substring(length - 3, length);
            } else {
                moneyFormat = strDefault.substring(0, length - 9) + "."
                        + strDefault.substring(length - 9, length - 6) + "."
                        + strDefault.substring(length - 6, length - 3) + "."
                        + strDefault.substring(length - 3, length);
            }
            return moneyFormat + (Toolbox.isEmpty(unit) ? "" : unit);
        }
    }

    public static String formatMoney(long value) {
        return formatMoney(value, null);
    }

    public static String formatMoney(double value) {
        return formatMoney(value, null);
    }

    public static String formatMoneyLong(long value, String unit) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("#,###.##", symbols);
        format.setGroupingUsed(true);
        return format.format(value) + (Toolbox.isEmpty(unit) ? "" : unit);
    }

    public static String formatMoneyDouble(double value, String unit) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("#,###.##", symbols);
        format.setGroupingUsed(true);
        return format.format(value) + (Toolbox.isEmpty(unit) ? "" : unit);
    }

    public static void autoNumberToText(EditText editText, TextView tvNumberText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String moneyNumber = Toolbox.getText(s.toString());
                String value = !"".equals(moneyNumber.trim()) ? "Bằng chữ: " + NumberToTextUtil.toWord(Long.parseLong(moneyNumber)) : "";
                tvNumberText.setText(value);
                Toolbox.setVisibility(!Toolbox.isEmpty(value), tvNumberText);
            }
        });
    }

    public static void autoFormatEditTextNumber(EditText edtNumber) {
        edtNumber.addTextChangedListener(new NumberTextWatcher(edtNumber));
    }

    public static String getText(String text) {
        if (text == null || "".equals(text)) {
            return "";
        } else {
            String replaceable = String.format("[%s,.\\-\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
            String textDefault = text.replaceAll(replaceable, "");
            textDefault = setPhoneNumber(textDefault);
            return textDefault;
        }
    }

    public static String getText(EditText edt) {
        if (edt == null) {
            return "";
        } else {
            String text = edt.getText().toString();
            return getText(text);
        }
    }

    public static void setViewError(EditText view, String error) {
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusableInTouchMode(true);
        view.setError(error);
    }

    public static void setViewError(EditText view, @StringRes int error) {
        setViewError(view, view.getContext().getString(error));
    }

    private static String setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replace(" ", "");
        phoneNumber = phoneNumber.replace("+", "");
        phoneNumber = phoneNumber.replace("-", "");
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "");
        phoneNumber = phoneNumber.replace("/", "");
        phoneNumber = phoneNumber.replace("*", "");
        phoneNumber = phoneNumber.replace(",", "");
        phoneNumber = phoneNumber.replace("#", "");
        phoneNumber = phoneNumber.replace(";", "");
        phoneNumber = phoneNumber.replace(".", "");
        phoneNumber = phoneNumber.replace("\\|", "");
        return phoneNumber;
    }

    public static void setVisibility(boolean isVisible, View... view) {
        for (View v : view)
            if (v != null) v.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public static void setOnEditTextInputDone(EditText editText, EditTextDoneAction action) {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                action.onDone();
                return true;
            }
            return false;
        });
    }

    public interface EditTextDoneAction {
        void onDone();
    }

    public static String changeMobileHeadTo0(String msisdn) {
        String strResult = "";
        if (msisdn != null && !"".equals(msisdn)) {
            msisdn = msisdn.trim();
            msisdn = setPhoneNumber(msisdn);
            if (msisdn.startsWith("84")) {
                strResult = msisdn.replaceFirst("84", "0");
            } else if (msisdn.startsWith("0")) {
                strResult = msisdn;
            } else {
                strResult = "0" + msisdn;
            }
        }
        return strResult;
    }

    public static String formatPhone0(String phone) {
        return formatPhoneNumber(changeMobileHeadTo0(phone));
    }

    public static String formatPhoneNumber(String phone) {
        String phoneFormat;
        if (Toolbox.isEmpty(phone)) {
            return "";
        } else {
            String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
            String phoneDefault = phone.replaceAll(replaceable, "");
            phoneDefault = phoneDefault.replaceAll("-", "");
            phoneDefault = phoneDefault.trim();
            int length = phoneDefault.length();
            if (length < 4) {
                phoneFormat = phoneDefault;
            } else if (length < 7) {
                phoneFormat = phoneDefault.substring(0, length - 3) + "."
                        + phoneDefault.substring(length - 3, length);
            } else {
                phoneFormat = phoneDefault.substring(0, length - 6) + "."
                        + phoneDefault.substring(length - 6, length - 3) + "."
                        + phoneDefault.substring(length - 3, length);
            }
            return phoneFormat;
        }
    }

    public static void setStatusBarColor(FragmentActivity activity, Resources res, int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ResourcesCompat.getColor(res, color, null));
        }
    }

    public static void setOnFocusChangeListener(View view, EditTextListener listener) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (listener != null) listener.onListen();
                }
            }
        });
    }

    public static String formatDate(String strDate, SimpleDateFormat fromFormat, SimpleDateFormat toFormat) {
        if (strDate == null)
            return "";

        String formatDate = "";
        try {
            formatDate = toFormat.format(fromFormat.parse(strDate));
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return formatDate;
    }

    public static DatePickerDialog showDatePickerDialog(Context context, @Nullable Date min, @Nullable Date max, DatePickerDialog.OnDateSetListener listener) {
        return showDatePickerDialog(context, min == null ? -1 : min.getTime(), max == null ? -1 : max.getTime(), listener);
    }

    public static DatePickerDialog showDatePickerDialog(Context context, Calendar focusDay, @Nullable Date min, @Nullable Date max, DatePickerDialog.OnDateSetListener listener) {
        return showDatePickerDialog(context, focusDay, min == null ? -1 : min.getTime(), max == null ? -1 : max.getTime(), listener);
    }

    public static DatePickerDialog showDatePickerDialog(Context context, long min, long max, DatePickerDialog.OnDateSetListener listener) {
        return showDatePickerDialog(context, Calendar.getInstance(), min, max, listener);
    }

    public static DatePickerDialog showDatePickerDialog(Context context, Calendar focusDay, long min, long max, DatePickerDialog.OnDateSetListener listener) {
        int year = focusDay.get(Calendar.YEAR);
        int month = focusDay.get(Calendar.MONTH);
        int dayOfMonth = focusDay.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, listener, year, month, dayOfMonth);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Chọn", dialog);
        dialog.show();
        if (min != -1) dialog.getDatePicker().setMinDate(min);
        if (max != -1) dialog.getDatePicker().setMaxDate(max + 24L * 60L * 60L * 1000L);
        return dialog;
    }

    public static void showDatePickerDialog2(Context context, Calendar focusDay, Calendar min, Calendar max, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener listener) {
        int year = focusDay.get(Calendar.YEAR);
        int month = focusDay.get(Calendar.MONTH);
        int dayOfMonth = focusDay.get(Calendar.DAY_OF_MONTH);
        SpinnerDatePickerDialogBuilder dialog = new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(listener)
                .spinnerTheme(R.style.DatePickerSpinner)
                .defaultDate(year, month, dayOfMonth);
        if (max != null) {
            int yearMax = max.get(Calendar.YEAR);
            int monthMax = max.get(Calendar.MONTH);
            int dayOfMonthMax = max.get(Calendar.DAY_OF_MONTH);
            dialog.maxDate(yearMax, monthMax, dayOfMonthMax);
        }
        com.tsongkha.spinnerdatepicker.DatePickerDialog datePickerDialog = dialog.build();
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Chọn", datePickerDialog);
        datePickerDialog.show();
    }

    public static String getYears(String dateTime, String currentFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentFormat, Constant.LOCALE_VN);

        try {
            Date date = dateFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            Date currentDate = new Date();
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);

            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH);
            int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

            int deltaYear = currentYear - year;
            int deltaMonth = currentMonth - month;
            int deltaDay = currentDay - day;

            if (deltaYear > 0) {
                if (deltaMonth < 0) {
                    deltaYear--;
                } else if (deltaDay < 0) {
                    deltaYear--;
                }

                return deltaYear + " năm";
            } else {
                if (deltaYear == 0) {
                    if (deltaDay < 0) {
                        deltaMonth--;
                    }
                    return deltaMonth + " tháng";
                }
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return "Chưa cập nhật";
    }

    public static String getExp(String dateTime, String currentFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentFormat, Constant.LOCALE_VN);
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            Date date = dateFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "Chưa cập nhật";
        }
        Calendar today = Calendar.getInstance();
        int ageYear = 0, ageMonth = 0, ageDays = 0;
        if (today.get(Calendar.YEAR) > year) {
            /**
             *current year > dobYear
             */
            ageYear = today.get(Calendar.YEAR) - year;

            if (today.get(Calendar.MONTH) > month) {
                /**
                 * year > dobYear and current month >dobMonth
                 */
                ageMonth = today.get(Calendar.MONTH) - month;

                if (today.get(Calendar.DAY_OF_MONTH) > day) {
                    /**
                     *current year > dobYear and current month >dobMonth and current day >dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                } else if (today.get(Calendar.DAY_OF_MONTH) < day) {
                    /**
                     *current year > dobYear and current month >dobMonth and current day <dobDay
                     */
                    if (ageMonth > 0) {
                        ageMonth--;
                    }
                    int preMonthTotalDays = today.getActualMaximum(Calendar.DAY_OF_MONTH - 1);
                    int preMonthCountDays = preMonthTotalDays - day;
                    ageDays = preMonthCountDays + today.get(Calendar.DAY_OF_MONTH);

                } else {
                    /**
                     *current year > dobYear and current month >dobMonth and current day=dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                }
                /**
                 *end of year > dobYear and current month >dobMonth
                 */
            } else if (today.get(Calendar.MONTH) < month) {
                /**
                 * current year>dobYear and month <dobMonth
                 */
                if (ageYear > 0) {
                    ageYear--;
                }
                int preYearCompletedMonth = 11 - month;
                int currentYearCompleteMonth = today.get(Calendar.MONTH);
                ageMonth = preYearCompletedMonth + currentYearCompleteMonth;

                if (today.get(Calendar.DAY_OF_MONTH) > day) {
                    /**
                     * current year>dobYear and month < dobMonth and current day > dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;

                } else if (today.get(Calendar.DAY_OF_MONTH) < day) {
                    /**
                     * current year>dobYear and month <dobMonth and current day < dobDay
                     */
                    if (Calendar.MONTH == 2) {
                        /**
                         * if prev month feb and 30-jan birth date
                         */
                        ageDays = today.get(Calendar.DAY_OF_MONTH);
                    } else {
                        /**
                         * if pre month is not feb
                         */
                        if (ageMonth > 0) {
                            ageMonth--;
                        }
                        int preMonthTotalDays = today.getActualMaximum(Calendar.DAY_OF_MONTH - 1);
                        int preMonthCountDays = preMonthTotalDays - day;
                        ageDays = today.get(Calendar.DAY_OF_MONTH) + preMonthCountDays;
                    }


                } else { //if current year>dobYear and month <dobMonth and current day = dobDay
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                }

                /**
                 * end of current year>dobYear and month <dobMonth
                 */
            } else {
                /**
                 *current year>dobYear and month = dobMonth
                 */
                ageMonth = today.get(Calendar.MONTH) - month;

                if (today.get(Calendar.DAY_OF_MONTH) > day) {
                    /**
                     * current year>dobYear and month = dobMonth current Day > dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                } else if (today.get(Calendar.DAY_OF_MONTH) < day) {
                    /**
                     * current year>dobYear and month = dobMonth current Day < dobDay
                     */
                    if (ageYear > 0) {
                        ageYear--;
                    }
                    ageMonth = 11;
                    int preMonthTotalDays = today.getActualMaximum(Calendar.DAY_OF_MONTH - 1);
                    int preMonthCountDays = preMonthTotalDays - day;
                    ageDays = preMonthCountDays + today.get(Calendar.DAY_OF_MONTH);

                } else {
                    /**
                     * current year>dobYear and month = dobMonth current Day = dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                }
                /**
                 *end of current year>dobYear and month = dobMonth
                 */
            }

        } else if (today.get(Calendar.YEAR) == year) {
            /**
             * Same Year
             */
            ageYear = today.get(Calendar.YEAR) - year;


            if (today.get(Calendar.MONTH) > month) {
                /**
                 * Same Year current month is greater than dob
                 */
                ageMonth = today.get(Calendar.MONTH) - month;

                if (today.get(Calendar.DAY_OF_MONTH) > day) {
                    /**
                     *Same Year current month >dobMonth and current day >dobDay
                     */

                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;

                } else if (today.get(Calendar.DAY_OF_MONTH) < day) {
                    /**
                     *Same Year and current month >dobMonth and current day <dobDay
                     */
                    if (ageMonth > 0) {
                        ageMonth--;
                    }

                    if (Calendar.MONTH == 2) {
                        /**
                         * if prev month feb and 30-jan birth date
                         */
                        ageDays = today.get(Calendar.DAY_OF_MONTH);
                    } else {
                        /**
                         * if feb month
                         */

                        int preMonthTotalDays = today.getActualMaximum(Calendar.DAY_OF_MONTH - 1);
                        int preMonthCountDays = preMonthTotalDays - day;
                        ageDays = today.get(Calendar.DAY_OF_MONTH) + preMonthCountDays;
                    }


                } else {
                    /**
                     *Same Year and current month >dobMonth and current day=dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                }
                /**
                 *end of current month is greater than dob
                 */

            } else if (today.get(Calendar.MONTH) < month) {
                /**
                 * Same Year and month < dobMonth
                 */
                //always false
                return "Chưa cập nhật";
            } else {
                /**
                 *Same Year and month = dobMonth
                 */
                ageMonth = today.get(Calendar.MONTH) - month;

                if (today.get(Calendar.DAY_OF_MONTH) > day) {
                    /**
                     *Same Year and month = dobMonth current Day > dobDay
                     */
                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                } else if (today.get(Calendar.DAY_OF_MONTH) < day) {
                    /**
                     *Same Year and month = dobMonth current Day < dobDay
                     */
                    //always false
                } else {
                    /**
                     * Same Year and month = dobMonth current Day = dobDay
                     */

                    ageDays = today.get(Calendar.DAY_OF_MONTH) - day;
                }
                /**
                 *end of Same Year and month = dobMonth
                 */
            }
            /**
             * end of Same Year
             */} else {
            /**
             * current year is less than dob year
             * always false
             */
            return "Chưa cập nhật";
        }

        if (ageYear == 0) {
            if (ageDays > 0) {
                ageMonth++;
            }
            return ageMonth + " tháng";
        } else {
            if (ageDays > 0) {
                ageMonth++;
            }
            return ageYear + " năm " + ageMonth + " tháng";
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null;
    }

    public static void showAlertDialog(Context context, String content) {
        new AlertDialog.Builder(context)
                .setMessage(content)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                })
                .show();
    }

    public static void hideKeyboardInDialog(Context context, Dialog dialog) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
//                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            View view = dialog.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static boolean validatePublicName(String firstName) {
        String regX = "[ A-Za-z\\s]*";
        Pattern p = Pattern.compile(regX);
        Matcher m = p.matcher(firstName);
        return m.matches();
    }

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }
    public static void autoMovingText(EditText edt1, EditText edt2,EditText edt3, EditText edt4,EditText edt5, EditText edt6){
        edt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });
        edt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }

                return false;
            }
        });
        edt6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        edt1.setText("");
                        edt2.setText("");
                        edt3.setText("");
                        edt4.setText("");
                        edt5.setText("");
                        edt6.setText("");
                        edt1.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });

        edt1.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt2.requestFocus();
                        }
                    },10);
                }
            }
        });
        edt2.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt3.requestFocus();
                        }
                    },10);
                }else {
                    edt1.requestFocus();
                }
            }
        });
        edt3.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt4.requestFocus();
                        }
                    },10);
                }else {
                    edt2.requestFocus();
                }
            }
        });
        edt4.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt5.requestFocus();
                        }
                    },10);
                }else {
                    edt3.requestFocus();
                }
            }
        });
        edt5.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt6.requestFocus();
                        }
                    },10);
                }else {
                    edt4.requestFocus();
                }
            }
        });
        edt6.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0){
                    edt6.requestFocus();
                }
            }
        });
    }

}
