package com.baitapandroid.quanlynemnuong.model;

public class ChiTietGiaoDichModel {
    private int mMaGd, mMaSp, mSoLuong, mDonGia;
    private String tenSp;

    public ChiTietGiaoDichModel(int maGd, int maSp, String tenSp, int soLuong, int donGia) {
        this.mMaGd = maGd;
        this.mMaSp = maSp;
        this.tenSp = tenSp;
        this.mSoLuong = soLuong;
        this.mDonGia = donGia;
    }

    public int getMaGd() {
        return mMaGd;
    }

    public void setMaGd(int maGd) {
        this.mMaGd = maGd;
    }

    public int getMaSp() {
        return mMaSp;
    }

    public void setMaSp(int maSp) {
        this.mMaSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getSoLuong() {
        return mSoLuong;
    }

    public void setSoLuong(int soLuong) {
        this.mSoLuong = soLuong;
    }

    public int getDonGia() {
        return mDonGia;
    }

    public void setDonGia(int mDonGia) {
        this.mDonGia = mDonGia;
    }
}
