package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GioHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.DanhGia;
import com.baitapandroid.quanlynemnuong.ui.activity.MainActivity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XemNemNuongFragment extends Fragment {
    private NemNuongModel model;
    private TextView tenSp, dcNpp, moTa, donGia, tenNpp, soDanhGia;
    private ImageView hinhAnh;
    private DanhGia danhGia;
    private Button nutSua;
    private SqlConnection mConnection;

    public XemNemNuongFragment(NemNuongModel model) {
        this.model = model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xem_nem_nuong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConnection = new SqlConnection(getContext());
        loadViews();
    }

    private void loadViews() {
        tenSp = getView().findViewById(R.id.xem_nem_nuong_ten_sp);
        dcNpp = getView().findViewById(R.id.xem_nem_nuong_dia_chi_npp);
        donGia = getView().findViewById(R.id.xem_nem_nuong_don_gia);
        hinhAnh = getView().findViewById(R.id.xem_nem_nuong_hinh_anh);
        moTa = getView().findViewById(R.id.xem_nem_nuong_mo_ta);
        tenNpp = getView().findViewById(R.id.xem_nem_nuong_tennpp);
        soDanhGia = getView().findViewById(R.id.xem_nem_nuong_so_danh_gia);
        danhGia = new DanhGia(
                getView().findViewById(R.id.xem_nem_nuong_danh_gia_1),
                getView().findViewById(R.id.xem_nem_nuong_danh_gia_2),
                getView().findViewById(R.id.xem_nem_nuong_danh_gia_3),
                getView().findViewById(R.id.xem_nem_nuong_danh_gia_4),
                getView().findViewById(R.id.xem_nem_nuong_danh_gia_5)
        );
        nutSua = getView().findViewById(R.id.xem_nem_nuong_mua);
        nutSua.setOnClickListener(this::mua);

        if (model == null) return;
        tenSp.setText(model.getTenSp());
        tenNpp.setText(model.getTenNpp());
        Glide.with(getContext()).load(model.getHinhAnh()).into(hinhAnh);
        moTa.setText(model.getMoTa());
        donGia.setText(String.format("%,d", model.getDonGia()));
        soDanhGia.setText(Float.toString(model.getSao()));
        danhGia.setDanhGia(model.getSao());
        tenNpp.setText(model.getTenNpp());
        dcNpp.setText(model.getDcNpp());
    }

    private void mua(View view) {
        GioHangModel gioHangModel = mConnection.getGioHang(MainActivity.taiKhoanHienTai.getMaKh(), model.getMaSp());
        if (gioHangModel != null) { // Tăng số lượng
            gioHangModel.setSoLuong(gioHangModel.getSoLuong() + 1);
            mConnection.updateGioHang(gioHangModel);
        }
        else { // Thêm sản phẩm mới vào giỏ hàng
            mConnection.insertGioHang(new GioHangModel(
                    model.getMaSp(),
                    model.getTenSp(),
                    model.getHinhAnh(),
                    model.getMoTa(),
                    model.getDonGia(),
                    model.getSao(),
                    model.getMaNsx(),
                    model.getTenNpp(),
                    model.getDcNpp(),
                    MainActivity.taiKhoanHienTai.getMaKh(),
                    1
            ));
        }
        Toast.makeText(getContext(), "Đặt vào giỏ hàng thành công.", Toast.LENGTH_SHORT).show();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }
}