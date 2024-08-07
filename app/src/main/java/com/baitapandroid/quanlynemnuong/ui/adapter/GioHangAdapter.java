package com.baitapandroid.quanlynemnuong.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.model.GioHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.DanhGia;
import com.bumptech.glide.Glide;

import java.util.List;

public class GioHangAdapter extends ArrayAdapter<GioHangModel> {
    private List<GioHangModel> mModels;
    public GioHangAdapter(@NonNull Context context, List<GioHangModel> models) {
        super(context, 0);
        this.mModels = models;
    }

    public void setModels(List<GioHangModel> models) {
        this.mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GioHangModel getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_giohang, parent, false);

        TextView gia = convertView.findViewById(R.id.listitem_giohang_gia);
        TextView soLuong = convertView.findViewById(R.id.listitem_giohang_soluong);
        ImageView hinhAnh = convertView.findViewById(R.id.listitem_giohang_hinhanh);
        TextView tenSp = convertView.findViewById(R.id.listitem_giohang_tensp);
        TextView danhGiaSo = convertView.findViewById(R.id.listitem_giohang_danh_gia_so);
        DanhGia danhGia = new DanhGia(
                convertView.findViewById(R.id.listitem_giohang_danh_gia_1),
                convertView.findViewById(R.id.listitem_giohang_danh_gia_2),
                convertView.findViewById(R.id.listitem_giohang_danh_gia_3),
                convertView.findViewById(R.id.listitem_giohang_danh_gia_4),
                convertView.findViewById(R.id.listitem_giohang_danh_gia_5)
        );

        gia.setText(String.format("%,d", getItem(position).getDonGia()));
        soLuong.setText(String.format("%d", getItem(position).getSoLuong()));
        Glide.with(getContext()).load(getItem(position).getHinhAnh()).into(hinhAnh);
        tenSp.setText(getItem(position).getTenSp());
        danhGiaSo.setText(Float.toString(getItem(position).getSao()));
        danhGia.setDanhGia(getItem(position).getSao());
        return convertView;
    }
}
