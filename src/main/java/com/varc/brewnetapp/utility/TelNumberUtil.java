package com.varc.brewnetapp.utility;

import com.varc.brewnetapp.exception.InvalidDataException;

public class TelNumberUtil {
    public static String formatTelNumber(String tel) {
        tel = tel.replaceAll("[^0-9]", "");
        if (tel.length() >= 9 && tel.length() <= 11) {
            String regEx = "(\\d{3})(\\d{4})(\\d{4})";

            if (tel.length() == 9) {
                regEx = "(\\d{2})(\\d{3})(\\d{4})";
            } else if (tel.length() == 10) {
                String chkTel = tel.substring(0, 2);
                if (chkTel.equals("02")) {
                    regEx = "(\\d{2})(\\d{4})(\\d{4})";
                } else {
                    regEx = "(\\d{3})(\\d{3})(\\d{4})";
                }
            }
            return tel.replaceAll(regEx, "$1-$2-$3");
        } else {
            throw new InvalidDataException("잘못된 전화번호입니다");
        }
    }
}
