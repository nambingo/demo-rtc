package com.giahan.app.vietskindoctor.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pham.duc.nam
 */

public class DialogUtils {
    public static Dialog dialog;
    private static int fy, fm, fd;
    private static String name, email, phone, gender, birthday;
    private static boolean isCheckUpdate = false;

    public static void hideAlert() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    public static void showDialogAlert(Context context, String message) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout rlOK = dialog.findViewById(R.id.rlOK);
        rlOK.setOnClickListener(v -> dialog.dismiss());
        TextView tvContent = dialog.findViewById(R.id.tvContent);
        tvContent.setTextColor(context.getResources().getColor(R.color.black));
        tvContent.setText(message);
        dialog.show();
    }

    public static void showDialogAlertUpdate(Context context, UserInfoResponse user, onListener
            listener) {
        dialog = new Dialog(context, R.style.Theme_Swipe_Back_NoActionBar);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(R.layout.dialog_update_info);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvAgain = dialog.findViewById(R.id.btn_again);
        tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
                listener.onDismiss(dialog);
            }
        });
        TextView tvCont = dialog.findViewById(R.id.btn_cont);
        EditText edtPhone = dialog.findViewById(R.id.edt_phone);
        EditText edtName = dialog.findViewById(R.id.edt_name);
        RadioButton radioM = dialog.findViewById(R.id.radioM);
        RadioButton radioF = dialog.findViewById(R.id.radioF);
        RadioGroup radioGrp = dialog.findViewById(R.id.radioGrp);
        View delGender2 = dialog.findViewById(R.id.delGender2);
        View delDate = dialog.findViewById(R.id.delDate);
        View delName = dialog.findViewById(R.id.delName);
        View delPhone = dialog.findViewById(R.id.delPhone);
        EditText edtDate = dialog.findViewById(R.id.edt_from_date);
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd", Constant.LOCALE_VN);
        SimpleDateFormat formatShow = new SimpleDateFormat("dd-MM-yyyy", Constant.LOCALE_VN);
        name = user.getName();
        phone = user.getPhone();
        gender = user.getGender();
        birthday = user.getBirthdate();
        edtName.setText(Toolbox.isEmpty(name) ? "" : name);
        edtPhone.setText(Toolbox.isEmpty(phone) ? "" : Toolbox.formatPhone0(phone));
        if (!Toolbox.isEmpty(user.getGender())) {
            radioM.setChecked(user.getGender().equals("male"));
            radioF.setChecked(!user.getGender().equals("male"));
        } else {
            radioM.setChecked(false);
            radioF.setChecked(false);
        }
        if (!Toolbox.isEmpty(user.getBirthdate())) {
            edtDate.setText(Toolbox.formatDate(user.getBirthdate(), formatSend, formatShow));
            try {
                Date date = formatSend.parse(birthday);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                fy = calendar.get(Calendar.YEAR);
                fm = calendar.get(Calendar.MONTH);
                fd = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            fy = -1;
        }
        radioM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
            }
        });
        radioF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
            }
        });
        tvCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
                name = edtName.getText().toString().trim();
                boolean checkError = false;
                if (Toolbox.isEmpty(name)) {
                    checkError = true;
                    edtName.setError(context.getString(R.string.error_input_name));
                } else {
                    if (name.length() < 4 || name.length() > 25) {
                        checkError = true;
                        edtName.setError(context.getString(R.string.error_input_name2));
                    }
                }
                if (Toolbox.isEmpty(birthday)) {
                    checkError = true;
                    edtDate.setError(context.getString(R.string.error_input_birthday));
                }
                if (radioM.isChecked()) {
                    gender = "male";
                } else {
                    if (radioF.isChecked()) {
                        gender = "female";
                    } else {
                        checkError = true;
                        Toolbox.showAlertDialog(context, context.getString(R.string.error_input_gender));
                        gender = "";
                    }
                }
                if (!Toolbox.isEmpty(edtPhone.getText().toString())) {
                    phone = Toolbox.getText(edtPhone);
                    if (phone.length() < 9 || phone.length() > 13) {
                        checkError = true;
                        edtPhone.setError(context.getString(R.string.error_input_phone2));
                        phone = "";
                    }
                } else {
                    checkError = true;
                    edtPhone.setError(context.getString(R.string.error_input_phone));
                    phone = "";
                }

                if (listener != null && !checkError)
                    listener.onListen(name, birthday, gender, phone, dialog);
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
                Calendar fc = Calendar.getInstance();
                Calendar fc2 = Calendar.getInstance();
                fc2.add(Calendar.DAY_OF_MONTH, -1);
                if (fy != -1) {
                    fc.set(Calendar.YEAR, fy);
                    fc.set(Calendar.MONTH, fm);
                    fc.set(Calendar.DAY_OF_MONTH, fd);
                }

                if (android.os.Build.VERSION.SDK_INT >= 18) {
                    // Do something for lollipop and above versions
                    Toolbox.showDatePickerDialog2(context, fc, null, fc2, (view, year, month, dayOfMonth) -> {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        fy = year;
                        fm = month;
                        fd = dayOfMonth;
                        edtDate.setText(formatShow.format(c.getTime()));
                        birthday = formatSend.format(c.getTime());
                    });
                } else {
                    Toolbox.showDatePickerDialog(context, fc, null, fc2.getTime(), (view, year, month, dayOfMonth) -> {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        fy = year;
                        fm = month;
                        fd = dayOfMonth;
                        edtDate.setText(formatShow.format(c.getTime()));
                        birthday = formatSend.format(c.getTime());
                    });
                }
            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
            String phoneCurrent = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(context.getString(R.string.title_not_installed))) {
                    if (s.toString().length() > 3 && !s.toString().equals(phoneCurrent)) {
                        edtPhone.removeTextChangedListener(this);
                        String phoneNumber = Toolbox.formatPhone0(s.toString());
                        int totalSelection = s.toString().length();
                        int currentSelection = edtPhone.getSelectionStart();
                        phoneCurrent = phoneNumber;
                        edtPhone.setText(phoneNumber);
                        int rightSelection = totalSelection - currentSelection;
                        if (rightSelection < 0) rightSelection = 0;
                        try {
                            edtPhone.setSelection(phoneNumber.length() - rightSelection > 0 ? phoneNumber.length() - rightSelection : 0);
                        } catch (Exception e) {
                            edtPhone.setSelection(edtPhone.getText().length());
                        }
                        edtPhone.addTextChangedListener(this);
                    }
                }
            }
        });
        delGender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGrp.clearCheck();
                gender = "";
            }
        });
        delDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDate.setText("");
                birthday = "";
            }
        });
        delName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                name = "";
            }
        });
        delPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPhone.setText("");
                phone = "";
            }
        });
        dialog.show();
    }

    public static void showDialogAlertInputEmail(Context context, onListenerInputEmail
            listener) {
        isCheckUpdate = false;
        dialog = new Dialog(context, R.style.Theme_Swipe_Back_NoActionBar);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(R.layout.dialog_input_email);
        dialog.setCanceledOnTouchOutside(true);
        EditText edtEmail = dialog.findViewById(R.id.edt_email);
        View delEmail = dialog.findViewById(R.id.delEmail);
        TextView tvClose = dialog.findViewById(R.id.btn_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
                listener.onDismiss(dialog);
            }
        });
        TextView tvLogin = dialog.findViewById(R.id.btn_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.hideKeyboardInDialog(context, dialog);
                boolean checkError = false;
                email = edtEmail.getText().toString();
                if (!Toolbox.isValidEmail(email)) {
                    Toolbox.setViewError(edtEmail, R.string.error_invalid_email);
                    checkError = true;
                    email = "";
                }
                if (listener != null && !checkError) {
                    listener.onListen(email, dialog);
                    isCheckUpdate = true;
                }
            }
        });
        delEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setText("");
                email = "";
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog2) {
                if (!isCheckUpdate) {
                    listener.onDismiss(dialog);
                }
            }
        });
        dialog.show();
    }

    public static void showDialogError(Context context, String error) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout rlOK = dialog.findViewById(R.id.rlOK);
        rlOK.setOnClickListener(v -> dialog.dismiss());
        TextView tvError = dialog.findViewById(R.id.tvError);
        tvError.setText(
                TextUtils.isEmpty(error) ? context.getString(R.string.msg_alert_info) : error);
        tvError.setTextColor(context.getResources().getColor(R.color.red));
        dialog.show();
    }

    public static void showDialogWaitDoctor(Context context, View.OnClickListener
            onClickSelectDocter) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_alert_wait_doctor);
        dialog.setCanceledOnTouchOutside(true);

        RelativeLayout rlOtherDocter = dialog.findViewById(R.id.rlOtherDocter);
        rlOtherDocter.setOnClickListener(onClickSelectDocter);

        RelativeLayout rlWait = dialog.findViewById(R.id.rlWait);
        rlWait.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public static void showDialogLogin(Context context, onConfirmClickListener
            listener) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_alert_v2_login);
        dialog.setCanceledOnTouchOutside(true);

        TextView btnClose = dialog.findViewById(R.id.btn_close);
        TextView btnLogin = dialog.findViewById(R.id.btn_login);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListen();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showDialogTwoChoice(Context context, boolean isFace, boolean isCheck, boolean isAsk,
                                           @Nullable String text_ask, String text_content, String text_left,
                                           String text_right, View.OnClickListener onClickLeft,
                                           View.OnClickListener onClickRight) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setCanceledOnTouchOutside(true);
        ImageView imgFace = dialog.findViewById(R.id.imgFace);
        ImageView imgCheck = dialog.findViewById(R.id.imgCheck);
        TextView tvAsk = dialog.findViewById(R.id.tvAsk);
        TextView tvContent = dialog.findViewById(R.id.tvContent);
        TextView btnLeft = dialog.findViewById(R.id.btn_left);
        TextView btnRight = dialog.findViewById(R.id.btn_right);

        imgFace.setVisibility(isFace ? View.VISIBLE : View.GONE);
        imgCheck.setVisibility(isCheck ? View.VISIBLE : View.GONE);
        tvAsk.setVisibility(isAsk ? View.VISIBLE : View.GONE);
        tvAsk.setText(text_ask);
        tvContent.setText(text_content);
        btnLeft.setText(text_left);
        btnRight.setText(text_right);
        btnLeft.setOnClickListener(onClickLeft);
        btnRight.setOnClickListener(onClickRight);

        dialog.show();
    }

    public static void showDialogOneChoice(Context context, boolean isFace, boolean isCheck,
                                           String text_content, String text_choice,
                                           View.OnClickListener onClick) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_one_choice);
        dialog.setCanceledOnTouchOutside(true);
        ImageView imgFace = dialog.findViewById(R.id.imgFace);
        ImageView imgCheck = dialog.findViewById(R.id.imgCheck);
        TextView tvContent = dialog.findViewById(R.id.tvContent);
        TextView btnExit = dialog.findViewById(R.id.btnExit);

        imgFace.setVisibility(isFace ? View.VISIBLE : View.GONE);
        imgCheck.setVisibility(isCheck ? View.VISIBLE : View.GONE);
        tvContent.setText(text_content);
        btnExit.setText(text_choice);
        btnExit.setOnClickListener(onClick);

        dialog.show();
    }

    public static void showDialogRechargeResult(Context context, boolean isFace, boolean isTwoChoice,
                                                @Nullable String text_ask, String text_content, String text_money, String text_left,
                                                String text_right, View.OnClickListener onClickLeft,
                                                View.OnClickListener onClickRight, View.OnClickListener onClickClose) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_recharge_result);
        dialog.setCanceledOnTouchOutside(true);
        ImageView imgFace = dialog.findViewById(R.id.imgFace);
        ImageView imgCheck = dialog.findViewById(R.id.imgCheck);
        TextView tvAsk = dialog.findViewById(R.id.tvAsk);
        TextView tvContent = dialog.findViewById(R.id.tvContent);
        TextView tvMoney = dialog.findViewById(R.id.tvMoney);
        TextView btnLeft = dialog.findViewById(R.id.btn_left);
        TextView btnRight = dialog.findViewById(R.id.btn_right);
        View lnExit = dialog.findViewById(R.id.lnExit);

        imgFace.setVisibility(isFace ? View.VISIBLE : View.GONE);
        imgCheck.setVisibility(!isFace ? View.VISIBLE : View.GONE);
        lnExit.setVisibility(isTwoChoice ? View.GONE : View.VISIBLE);
        tvAsk.setText(text_ask);
        tvContent.setText(text_content);
        tvMoney.setText(text_money);
        btnLeft.setText(text_left);
        btnRight.setText(text_right);
        btnLeft.setOnClickListener(onClickLeft);
        btnRight.setOnClickListener(onClickRight);
        lnExit.setOnClickListener(onClickClose);

        dialog.show();
    }
//    public static void showDialogNotMatch(Context context, String text_content, String text_left,
//                                  View.OnClickListener onClick) {
//        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
//        dialog.setContentView(R.layout.dialog_two_choice);
//        dialog.setCanceledOnTouchOutside(true);
//        ImageView imgFace = dialog.findViewById(R.id.imgFace);
//        ImageView imgCheck = dialog.findViewById(R.id.imgCheck);
//        TextView tvAsk = dialog.findViewById(R.id.tvAsk);
//        TextView tvContent = dialog.findViewById(R.id.tvContent);
//        TextView btnLeft = dialog.findViewById(R.id.btn_left);
//        TextView btnRight = dialog.findViewById(R.id.btn_right);
//
//
//        imgCheck.setVisibility(View.GONE);
//        tvAsk.setVisibility(View.GONE);
//        btnRight.setVisibility(View.GONE);
//
//        imgFace.setVisibility(View.VISIBLE);
//        tvContent.setText(text_content);
//        btnLeft.setText(text_left);
//        btnLeft.setOnClickListener(onClick);
//
//        dialog.show();
//    }

    @SuppressLint("StringFormatInvalid")
    public static void showDialogShowFee(Context context, String amonut, onConfirmClickListener
            listener) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_alert_v2_fee);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvFee = dialog.findViewById(R.id.tvFee);
        TextView btnClose = dialog.findViewById(R.id.btn_close);
        TextView btnCont = dialog.findViewById(R.id.btn_cont);

        tvFee.setText(context.getString(R.string.fee, String.valueOf(Toolbox.formatMoney(amonut))));
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListen();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public interface onListener {
        void onListen(String name, String date, String gender, String phone, Dialog dialog);

        void onDismiss(Dialog dialog);
    }

    public interface onListenerInputEmail {
        void onListen(String email, Dialog dialog);

        void onDismiss(Dialog dialog);
    }

    public interface onConfirmClickListener {
        void onListen();
    }
}
