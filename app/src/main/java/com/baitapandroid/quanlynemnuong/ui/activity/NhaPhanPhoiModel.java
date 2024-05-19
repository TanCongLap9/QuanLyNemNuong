package com.baitapandroid.quanlynemnuong.ui.activity;

import androidx.annotation.NonNull;

public class NhaPhanPhoiModel {
    private int mMaNsx;
    private String mTenNsx, mDiaChi;

    public NhaPhanPhoiModel(int maNsx, String tenNsx, String diaChi) {
        this.mMaNsx = maNsx;
        this.mTenNsx = tenNsx;
        this.mDiaChi = diaChi;
    }

    public int getMaNsx() {
        return mMaNsx;
    }

    public void setMaNsx(int maNsx) {
        this.mMaNsx = maNsx;
    }

    public String getTenNsx() {
        return mTenNsx;
    }

    public void setTenNsx(String tenNsx) {
        this.mTenNsx = tenNsx;
    }

    public String getDiaChi() {
        return mDiaChi;
    }

    public void setDiaChi(String diaChi) {
        this.mDiaChi = diaChi;
    }

    @NonNull
    @Override
    public String toString() {
        return mTenNsx;
    }
}
