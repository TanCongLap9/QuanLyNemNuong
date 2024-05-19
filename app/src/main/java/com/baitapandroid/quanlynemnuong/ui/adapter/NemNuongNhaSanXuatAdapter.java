package com.baitapandroid.quanlynemnuong.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.baitapandroid.quanlynemnuong.ui.activity.NhaPhanPhoiModel;

import java.util.List;

public class NemNuongNhaSanXuatAdapter extends ArrayAdapter<NhaPhanPhoiModel> {
    public NemNuongNhaSanXuatAdapter(@NonNull Context context, List<NhaPhanPhoiModel> list) {
        super(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
    }

    public NemNuongNhaSanXuatAdapter(@NonNull Context context, NhaPhanPhoiModel[] list) {
        super(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
    }
}
