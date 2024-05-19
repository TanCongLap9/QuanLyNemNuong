package com.baitapandroid.quanlynemnuong.model;

public class UserModel {
    private String mTaiKhoan, mMatKhau;

    public UserModel(String username, String password) {
        this.mTaiKhoan = username;
        this.mMatKhau = password;
    }

    public String getTaiKhoan() {
        return mTaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.mTaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return mMatKhau;
    }

    public void setMatKhau(String matKhau) {
        this.mMatKhau = matKhau;
    }
}
