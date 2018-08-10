package com.giahan.app.vietskindoctor.utils;

public class NumberToTextUtil {

    String[] strones = {"một", "hai", "na", "bốn", "năm", "dáu", "bảy", "tám",
            "chín", "mười", "mười một", "mười hai", "mười ba", "mười bốn",
            "mười lăm", "mười sáu", "mười bảy", "mười tám", "mười chín",};

    String[] strtens = {"mười", "hai mươi", "ba  mươi", "bốn mươi",
            "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi",
            "trăm", "nghìn"};

    public static String toWord(Long amount) {

        boolean am = false;
        if (amount < 0) {
            amount = -amount;
            am = true;
        } else if (amount == 0) {
            return "Không đồng";
        }
        StringBuilder amountStr = new StringBuilder(amount.toString());
        String[] unit = new String[]{"none", "đồng", "nghìn", "triệu", "tỷ"};
        String returnValue = "";
        int chiSoDonVi;
        while ((amountStr.length() % 3) != 0) {
            amountStr.insert(0, "0");
        }
        chiSoDonVi = amountStr.length() / 3; // 1-Dong, 2-Ngan, 3-Trieu,
        // 4-Ty

        for (int i = 0; i < amountStr.length(); i = i + 3) {
            if (amountStr.length() <= 12) {
                if (Integer.valueOf(amountStr.substring(i, i + 3)) != 0) {
                    if (!"".equalsIgnoreCase(returnValue)) {
                        returnValue = returnValue + " "
                                + docSo3ChuSo(amountStr.substring(i, i + 3))
                                + " " + unit[chiSoDonVi];
                    } else {
                        returnValue = docSo3ChuSo(amountStr.substring(i, i + 3))
                                + " " + unit[chiSoDonVi];
                    }
                } else if (chiSoDonVi == 1) {
                    returnValue = returnValue + " " + unit[1];
                }
                chiSoDonVi = chiSoDonVi - 1;
            } else {
                returnValue = "Số tiền quá lớn";
            }
        }
        // ReturnValue = ReturnValue.replace(/ /g, " ");
        if (returnValue.length() > 0) {
            returnValue = returnValue.substring(0, 1).toUpperCase()
                    + returnValue.substring(1, returnValue.length());
        }
        if (am) {
            returnValue = "Trừ " + returnValue;
        }

        return returnValue;
    }

    public static String docSo3ChuSo(String So) {
        // var DonVi;
        String ReturnValue;

        String[] Dem = new String[]{"none", "một", "hai", "ba", "bốn", "năm",
                "sáu", "bẩy", "tám", "chín"};
        String[] Dem1 = new String[]{"none", "mốt", "hai", "ba", "tư", "lăm",
                "sáu", "bẩy", "tám", "chín"};
        String[] Hang = new String[]{"none", "trăm", "mươi"};
        ReturnValue = "";
        int intSo = Integer.valueOf(So);
        // intSo = CInt(So)
        if (intSo == 0) {
            return "";
        } else if (intSo < 10) {
            return Dem[intSo];
        } else {
            if (Integer.valueOf(So.substring(0, 1)) != 0) {
                if (!"".equalsIgnoreCase(ReturnValue)) {
                    ReturnValue = ReturnValue + " "
                            + Dem[Integer.valueOf(So.substring(0, 1))] + " "
                            + Hang[1];
                } else {
                    ReturnValue = Dem[Integer.valueOf(So.substring(0, 1))]
                            + " " + Hang[1];
                }
            }

            if ((Integer.valueOf(So.substring(1, 2)) != 0)
                    || (Integer.valueOf(So.substring(2, 3)) != 0)) {
                if ((Integer.valueOf(So.substring(1, 2)) == 0)
                        && (Integer.valueOf(So.substring(2, 3)) != 0)) {// 0x
                    ReturnValue = ReturnValue + " linh "
                            + Dem[Integer.valueOf(So.substring(2, 3))];
                } else if ((Integer.valueOf(So.substring(1, 2)) == 1)
                        && (Integer.valueOf(So.substring(2, 3)) == 0)) {// 10
                    if (!"".equalsIgnoreCase(ReturnValue)) {
                        ReturnValue = ReturnValue + " mười";
                    } else {
                        ReturnValue = "mười";
                    }
                } else if ((Integer.valueOf(So.substring(1, 2)) == 1)
                        && (Integer.valueOf(So.substring(2, 3)) > 0)) {// 1x
                    if (!"".equalsIgnoreCase(ReturnValue)) {
                        if (Integer.valueOf(So.substring(2, 3)) == 5) {
                            ReturnValue = ReturnValue + " mười lăm";
                        } else {
                            ReturnValue = ReturnValue + " mười "
                                    + Dem[Integer.valueOf(So.substring(2, 3))];
                        }
                    } else {
                        if (Integer.valueOf(So.substring(2, 3)) == 5) {
                            ReturnValue = ReturnValue + " mười lăm";
                        } else {
                            ReturnValue = "mười "
                                    + Dem[Integer.valueOf(So.substring(2, 3))];
                        }
                    }
                } else if ((Integer.valueOf(So.substring(1, 2)) == 2)
                        && (Integer.valueOf(So.substring(2, 3)) == 5)) {// 25
                    if (!"".equalsIgnoreCase(ReturnValue)) {
                        ReturnValue = ReturnValue + " hai lăm";
                    } else {
                        ReturnValue = "hai lăm";
                    }
                } else if ((Integer.valueOf(So.substring(1, 2)) == 3)
                        && (Integer.valueOf(So.substring(2, 3)) == 5)) {// 35
                    if (!"".equalsIgnoreCase(ReturnValue)) {
                        ReturnValue = ReturnValue + " ba lăm";
                    } else {
                        ReturnValue = "ba lăm";
                    }
                } else {
                    if (!"".equalsIgnoreCase(ReturnValue)) {
                        ReturnValue = ReturnValue + " "
                                + Dem[Integer.valueOf(So.substring(1, 2))]
                                + " mươi";
                    } else {
                        ReturnValue = Dem[Integer.valueOf(So.substring(1, 2))]
                                + " mươi";
                    }
                    if (Integer.valueOf(So.substring(2, 3)) != 0) {
                        if (!"".equalsIgnoreCase(ReturnValue)) {
                            ReturnValue = ReturnValue + " "
                                    + Dem1[Integer.valueOf(So.substring(2, 3))];
                        } else {
                            ReturnValue = Dem1[Integer.valueOf(So.substring(2, 3))];
                        }
                    }
                }
            }
            return ReturnValue;
        }
    }
}
