package com.bitoasis.websocket.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class AppUtils {

    public static boolean isInValidString(String strData) {
        return strData == null || strData.trim().length() == 0;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
