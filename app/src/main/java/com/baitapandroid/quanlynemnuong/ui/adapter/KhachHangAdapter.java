package com.baitapandroid.quanlynemnuong.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.R;

import java.util.List;

public class KhachHangAdapter extends ArrayAdapter<KhachHangModel> {
    private List<KhachHangModel> mModels;
    public KhachHangAdapter(@NonNull Context context, List<KhachHangModel> models) {
        super(context, 0);
        this.mModels = models;
    }

    public void setModels(List<KhachHangModel> models) {
        this.mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public KhachHangModel getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_khachhang, parent, false);

        TextView hoTenKh = convertView.findViewById(R.id.listitem_khachhang_hotenkh);
        TextView email = convertView.findViewById(R.id.listitem_khachhang_email);
        TextView maKh = convertView.findViewById(R.id.listitem_khachhang_makh);
        TextView sdt = convertView.findViewById(R.id.listitem_khachhang_sdt);

        hoTenKh.setText(getItem(position).getHoTen());
        email.setText(getItem(position).getEmail());
        maKh.setText(Integer.toString(getItem(position).getMaKh()));
        sdt.setText(getItem(position).getSdt());
        return convertView;
    }
}
