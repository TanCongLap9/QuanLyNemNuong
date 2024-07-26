package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.ui.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThongTinTaiKhoanFragment extends Fragment {
    private Date ngaySinhValue;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private TextView maKh, hoTen, gioiTinh, email, ngaySinh, sdt, taiKhoan, matKhau;
    private Button doiMatKhau;
    private KhachHangModel model;

    public ThongTinTaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_tin_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hoTen = getView().findViewById(R.id.thong_tin_tai_khoan_ho_ten);
        email = getView().findViewById(R.id.thong_tin_tai_khoan_email);
        sdt = getView().findViewById(R.id.thong_tin_tai_khoan_sdt);
        maKh = getView().findViewById(R.id.thong_tin_tai_khoan_ma);
        ngaySinh = getView().findViewById(R.id.thong_tin_tai_khoan_ngay_sinh);
        taiKhoan = getView().findViewById(R.id.thong_tin_tai_khoan_tai_khoan);
        matKhau = getView().findViewById(R.id.thong_tin_tai_khoan_mat_khau);
        gioiTinh = getView().findViewById(R.id.thong_tin_tai_khoan_gioi_tinh);
        doiMatKhau = getView().findViewById(R.id.thong_tin_tai_khoan_doi_mat_khau);

        doiMatKhau.setOnClickListener(this::doiMatKhau);

        model = MainActivity.taiKhoanHienTai;

        try {
            hoTen.setText(model.getHoTen());
            email.setText(model.getEmail());
            gioiTinh.setText(model.isGioiTinh() ? "Nữ" : "Nam");
            maKh.setText(Integer.toString(model.getMaKh()));
            ngaySinh.setText(dateFormat.format(model.getNgaySinh()));
            sdt.setText(model.getSdt());
            taiKhoan.setText(model.getTaiKhoan());
            matKhau.setText("\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022");
        }
        catch (NullPointerException e) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Thông tin tài khoản.")
                    .setMessage("Có lỗi trong lúc lấy thông tin tài khoản.")
                    .show();
        }
    }

    public void doiMatKhau(View view) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new DoiMatKhauFragment(model))
                .addToBackStack("name")
                .commit();
    }
}