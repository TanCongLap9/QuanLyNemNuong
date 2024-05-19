package com.baitapandroid.quanlynemnuong;

import android.content.Context;
import android.content.SharedPreferences;

import com.baitapandroid.quanlynemnuong.model.UserModel;

public class SharedPrefUtils {
    public static final String PREF_NAME = "nemnuong";
    public static final String PREF_USER = "PREF_USER";
    public static final String PREF_PASSWORD = "PREF_PASSWORD";

    public static UserModel getRememberedUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String taiKhoan = sharedPreferences.getString(PREF_USER, "");
        String matKhau = sharedPreferences.getString(PREF_PASSWORD, "");
        if (taiKhoan.isEmpty()) return null;
        else return new UserModel(taiKhoan, matKhau);
    }

    public static void setRememberedUser(Context context, UserModel model) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_USER, model.getTaiKhoan());
        editor.putString(PREF_PASSWORD, model.getMatKhau());
        editor.commit();
    }

    public static void clearRememberedUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(PREF_USER);
        editor.remove(PREF_PASSWORD);
        editor.commit();
    }
}
