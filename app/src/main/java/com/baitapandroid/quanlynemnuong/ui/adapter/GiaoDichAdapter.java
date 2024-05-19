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
import com.baitapandroid.quanlynemnuong.model.GiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;

import java.util.List;

public class GiaoDichAdapter extends ArrayAdapter<GiaoDichModel> {
    private List<GiaoDichModel> mModels;
    public GiaoDichAdapter(@NonNull Context context, List<GiaoDichModel> models) {
        super(context, 0);
        this.mModels = models;
    }

    public void setModels(List<GiaoDichModel> models) {
        this.mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GiaoDichModel getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_giaodich, parent, false);

        TextView maGd = convertView.findViewById(R.id.listitem_giaodich_magd);
        TextView maKh = convertView.findViewById(R.id.listitem_giaodich_makh);
        TextView hoTenKh = convertView.findViewById(R.id.listitem_giaodich_hotenkh);
        TextView maNv = convertView.findViewById(R.id.listitem_giaodich_manv);
        TextView hoTenNv = convertView.findViewById(R.id.listitem_giaodich_hotennv);

        hoTenKh.setText(getItem(position).getHoTenKh());
        hoTenNv.setText(getItem(position).getHoTenNv());
        maKh.setText(Integer.toString(getItem(position).getMaKh()));
        maNv.setText(Integer.toString(getItem(position).getMaNv()));
        maGd.setText(Integer.toString(getItem(position).getMaGd()));
        return convertView;
    }
}
