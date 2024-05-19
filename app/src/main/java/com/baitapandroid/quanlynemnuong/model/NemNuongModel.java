package com.baitapandroid.quanlynemnuong.model;

public class NemNuongModel {
    private int mMaSp, mDonGia, mMaNsx;
    private float mSao;
    private String mTenSp, mHinhAnh, mMoTa, mTenNpp, mDcNpp;

    public NemNuongModel(int maSp, String tenSp, String hinhAnh, String moTa, int donGia, float sao, int maNsx, String tenNpp, String dcNpp) {
        this.mMaSp = maSp;
        this.mDonGia = donGia;
        this.mMaNsx = maNsx;
        this.mSao = sao;
        this.mTenSp = tenSp;
        this.mHinhAnh = hinhAnh;
        this.mMoTa = moTa;
        this.mTenNpp = tenNpp;
        this.mDcNpp = dcNpp;
    }

    public int getMaSp() {
        return mMaSp;
    }

    public void setMaSp(int maSp) {
        this.mMaSp = maSp;
    }

    public int getDonGia() {
        return mDonGia;
    }

    public void setDonGia(int donGia) {
        this.mDonGia = donGia;
    }

    public int getMaNsx() {
        return mMaNsx;
    }

    public void setMaNsx(int maNsx) {
        this.mMaNsx = maNsx;
    }

    public float getSao() {
        return mSao;
    }

    public void setSao(float sao) {
        this.mSao = sao;
    }

    public String getTenSp() {
        return mTenSp;
    }

    public void setTenSp(String tenSp) {
        this.mTenSp = tenSp;
    }

    public String getHinhAnh() {
        return mHinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.mHinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return mMoTa;
    }

    public void setMoTa(String moTa) {
        this.mMoTa = moTa;
    }

    public String getTenNpp() {
        return mTenNpp;
    }

    public void setTenNpp(String tenNpp) {
        this.mTenNpp = tenNpp;
    }

    public String getDcNpp() {
        return mDcNpp;
    }

    public void setDcNpp(String dcNpp) {
        this.mDcNpp = dcNpp;
    }
}
