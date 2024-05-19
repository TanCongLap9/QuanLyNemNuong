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
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;

import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<KhachHangModel> {
    private List<KhachHangModel> mModels;
    public NhanVienAdapter(@NonNull Context context, List<KhachHangModel> models) {
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
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_nhanvien, parent, false);

        TextView hoTenNv = convertView.findViewById(R.id.listitem_nhanvien_hotennv);
        TextView email = convertView.findViewById(R.id.listitem_nhanvien_email);
        TextView maNv = convertView.findViewById(R.id.listitem_nhanvien_manv);
        TextView sdt = convertView.findViewById(R.id.listitem_nhanvien_sdt);

        hoTenNv.setText(getItem(position).getHoTen());
        email.setText(getItem(position).getEmail());
        maNv.setText(Integer.toString(getItem(position).getMaKh()));
        sdt.setText(getItem(position).getSdt());
        return convertView;
    }
}
