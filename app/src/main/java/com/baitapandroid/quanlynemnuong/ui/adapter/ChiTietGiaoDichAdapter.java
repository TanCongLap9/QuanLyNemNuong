package com.baitapandroid.quanlynemnuong.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;

import java.util.List;

public class ChiTietGiaoDichAdapter extends ArrayAdapter<ChiTietGiaoDichModel> {
    private List<ChiTietGiaoDichModel> mModels;
    public ChiTietGiaoDichAdapter(@NonNull Context context, List<ChiTietGiaoDichModel> models) {
        super(context, 0);
        this.mModels = models;
    }

    public void setModels(List<ChiTietGiaoDichModel> models) {
        this.mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ChiTietGiaoDichModel getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_chitietgiaodich, parent, false);

        TextView tenSp = convertView.findViewById(R.id.listitem_chitietgiaodich_tensp);
        TextView soLuong = convertView.findViewById(R.id.listitem_chitietgiaodich_soluong);

        tenSp.setText(getItem(position).getTenSp());
        soLuong.setText(Integer.toString(getItem(position).getSoLuong()));
        return convertView;
    }
}
