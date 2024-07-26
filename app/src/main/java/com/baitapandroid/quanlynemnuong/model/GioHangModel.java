package com.baitapandroid.quanlynemnuong.model;

public class GioHangModel {
    private int maNd, soLuong;
    private int maSp, donGia, maNsx;
    private float sao;
    private String tenSp, hinhAnh, moTa, tenNpp, dcNpp;

    public GioHangModel(int maSp, String tenSp, String hinhAnh, String moTa, int donGia, float sao, int maNsx, String tenNpp, String dcNpp, int maNd, int soLuong) {
        this.maNd = maNd;
        this.maSp = maSp;
        this.donGia = donGia;
        this.maNsx = maNsx;
        this.sao = sao;
        this.tenSp = tenSp;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.tenNpp = tenNpp;
        this.dcNpp = dcNpp;
        this.soLuong = soLuong;
    }

    public int getMaNd() {
        return maNd;
    }

    public void setMaNd(int maNd) {
        this.maNd = maNd;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getMaNsx() {
        return maNsx;
    }

    public void setMaNsx(int maNsx) {
        this.maNsx = maNsx;
    }

    public float getSao() {
        return sao;
    }

    public void setSao(float sao) {
        this.sao = sao;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenNpp() {
        return tenNpp;
    }

    public void setTenNpp(String tenNpp) {
        this.tenNpp = tenNpp;
    }

    public String getDcNpp() {
        return dcNpp;
    }

    public void setDcNpp(String dcNpp) {
        this.dcNpp = dcNpp;
    }
}
