package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GiaoDichModel;
import com.baitapandroid.quanlynemnuong.ui.adapter.ChiTietGiaoDichAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class XemGiaoDichFragment extends Fragment {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private int mTongSoTien;
    private GiaoDichModel mGdModel;
    private List<ChiTietGiaoDichModel> mCtgdModels;
    private ChiTietGiaoDichAdapter mAdapter;
    private GridView mDanhSach;
    private SqlConnection mConnection;
    private TextView mHoTenKh, mHoTenNv, mMaGd, mMmaKh, mMaNv, mThanhTien, mPhiVanChuyen, mThoiGianDat, mThoiGianGiao;

    public XemGiaoDichFragment(GiaoDichModel gdModel, List<ChiTietGiaoDichModel> ctgdModels) {
        this.mGdModel = gdModel;
        this.mCtgdModels = ctgdModels;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xem_giao_dich, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDanhSach = getView().findViewById(R.id.xem_giao_dich_danhsach);
        mHoTenKh = getView().findViewById(R.id.xem_giao_dich_hotenkh);
        mHoTenNv = getView().findViewById(R.id.xem_giao_dich_hotennv);
        mMaGd = getView().findViewById(R.id.xem_giao_dich_magd);
        mMmaKh = getView().findViewById(R.id.xem_giao_dich_makh);
        mMaNv = getView().findViewById(R.id.xem_giao_dich_manv);
        mThoiGianDat = getView().findViewById(R.id.xem_giao_dich_thoi_gian_dat);
        mThoiGianGiao = getView().findViewById(R.id.xem_giao_dich_thoi_gian_giao);
        mPhiVanChuyen = getView().findViewById(R.id.xem_giao_dich_phivanchuyen);
        mThanhTien = getView().findViewById(R.id.xem_giao_dich_thanh_tien);
        mAdapter = new ChiTietGiaoDichAdapter(getContext(), mCtgdModels);
        mDanhSach.setAdapter(mAdapter);

        if (mGdModel == null) return;
        mHoTenKh.setText(mGdModel.getHoTenKh());
        mHoTenNv.setText(mGdModel.getHoTenNv());
        mMaNv.setText(Integer.toString(mGdModel.getMaNv()));
        mMmaKh.setText(Integer.toString(mGdModel.getMaKh()));
        mMaGd.setText(Integer.toString(mGdModel.getMaGd()));
        mThoiGianDat.setText(dateFormat.format(mGdModel.getThoiGianDat()));
        mThoiGianGiao.setText(dateFormat.format(mGdModel.getThoiGianGiao()));
        mPhiVanChuyen.setText(String.format("%,d", mGdModel.getPhiVanChuyen()));
        for (ChiTietGiaoDichModel model : mCtgdModels)
            mTongSoTien += model.getSoLuong() * model.getDonGia();
        mTongSoTien += mGdModel.getPhiVanChuyen();
        mThanhTien.setText(String.format("%,d", mTongSoTien));
    }
}