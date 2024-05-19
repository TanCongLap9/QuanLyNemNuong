package com.baitapandroid.quanlynemnuong.model;

import java.io.Serializable;
import java.util.Date;

public class KhachHangModel implements Serializable {
    private String mHoTen, mEmail, mSdt, mTaiKhoan, mMatKhau;
    private Date mNgaySinh;
    private boolean mGioiTinh, mNhanVien;
    private int mMaKh;

    public KhachHangModel(int maKh, String hoTen, boolean gioiTinh, Date ngaySinh, String email, String sdt, String taiKhoan, String matKhau) {
        this.mMaKh = maKh;
        this.mHoTen = hoTen;
        this.mGioiTinh = gioiTinh;
        this.mEmail = email;
        this.mNgaySinh = ngaySinh;
        this.mSdt = sdt;
        this.mTaiKhoan = taiKhoan;
        this.mMatKhau = matKhau;
    }

    public KhachHangModel(int maKh, String hoTen, boolean gioiTinh, Date ngaySinh, String email, String sdt, String taiKhoan, String matKhau, boolean nhanVien) {
        this(maKh, hoTen, gioiTinh, ngaySinh, email, sdt, taiKhoan, matKhau);
        setNhanVien(nhanVien);
    }

    public boolean isNhanVien() {
        return mNhanVien;
    }

    public void setNhanVien(boolean nhanVien) {
        this.mNhanVien = nhanVien;
    }

    public int getMaKh() {
        return mMaKh;
    }

    public void setMaKh(int makh) {
        this.mMaKh = makh;
    }

    public String getHoTen() {
        return mHoTen;
    }

    public void setHoTen(String hoTen) {
        this.mHoTen = hoTen;
    }

    public Date getNgaySinh() {
        return mNgaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.mNgaySinh = ngaySinh;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getSdt() {
        return mSdt;
    }

    public void setSdt(String sdt) {
        this.mSdt = sdt;
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

    public boolean isGioiTinh() {
        return mGioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.mGioiTinh = gioiTinh;
    }
}
