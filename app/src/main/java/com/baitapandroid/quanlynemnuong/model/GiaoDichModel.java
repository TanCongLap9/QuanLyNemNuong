package com.baitapandroid.quanlynemnuong.model;

import java.util.Date;

public class GiaoDichModel {
    private int mMaGd, mMaKh, mMaNv, mPhiVanChuyen;
    private Date mThoiGianDat, mThoiGianGiao;
    private String mHoTenKh, mHoTenNv;

    public GiaoDichModel(int maGd, int maKh, int maNv, int phiVanChuyen, Date thoiGianDat, Date thoiGianGiao, String hoTenKh, String hoTenNv) {
        this.mMaGd = maGd;
        this.mMaKh = maKh;
        this.mMaNv = maNv;
        this.mPhiVanChuyen = phiVanChuyen;
        this.mThoiGianDat = thoiGianDat;
        this.mThoiGianGiao = thoiGianGiao;
        this.mHoTenKh = hoTenKh;
        this.mHoTenNv = hoTenNv;
    }

    public int getMaGd() {
        return mMaGd;
    }

    public void setMaGd(int maGd) {
        this.mMaGd = maGd;
    }

    public int getMaKh() {
        return mMaKh;
    }

    public void setMaKh(int maKh) {
        this.mMaKh = maKh;
    }

    public int getMaNv() {
        return mMaNv;
    }

    public void setMaNv(int maNv) {
        this.mMaNv = maNv;
    }

    public int getPhiVanChuyen() {
        return mPhiVanChuyen;
    }

    public void setPhiVanChuyen(int phiVanChuyen) {
        this.mPhiVanChuyen = phiVanChuyen;
    }

    public Date getThoiGianDat() {
        return mThoiGianDat;
    }

    public void setThoiGianDat(Date thoiGianDat) {
        this.mThoiGianDat = thoiGianDat;
    }

    public Date getThoiGianGiao() {
        return mThoiGianGiao;
    }

    public void setThoiGianGiao(Date thoiGianGiao) {
        this.mThoiGianGiao = thoiGianGiao;
    }

    public String getHoTenKh() {
        return mHoTenKh;
    }

    public void setHoTenKh(String hoTenKh) {
        this.mHoTenKh = hoTenKh;
    }

    public String getHoTenNv() {
        return mHoTenNv;
    }

    public void setHoTenNv(String hoTenNv) {
        this.mHoTenNv = hoTenNv;
    }
}
