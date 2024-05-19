package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.activity.MainActivity;
import com.baitapandroid.quanlynemnuong.ui.adapter.NemNuongAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DanhSachGioHangFragment extends Fragment {
    private GridView mDanhSach;
    private NemNuongAdapter mAdapter;
    private ImageButton mThanhToan, mXoa;
    private int mSelected = -1;
    private List<NemNuongModel> mModels;
    private SqlConnection mConnection;
    private int phiVanChuyen = 0;
    private int tongGiaSanPham = 0; // Tổng giá của các sản phẩm chưa tính phí
    private int thanhTien = 0; // Tổng giá của các sản phẩm có tính phí
    private RadioButton giaoHang, giaoHangHoaToc, nhanTaiQuan;
    private TextView tong;
    public DanhSachGioHangFragment() {
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
        return inflater.inflate(R.layout.fragment_danh_sach_gio_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModels = (List<NemNuongModel>)getActivity().getIntent().getSerializableExtra(MainActivity.INTENT_GIOHANG);
        mAdapter = new NemNuongAdapter(getContext(), mModels);
        mDanhSach = getView().findViewById(R.id.danh_sach_gio_hang_danh_sach);
        mThanhToan = getView().findViewById(R.id.danh_sach_gio_hang_thanh_toan);
        mXoa = getView().findViewById(R.id.danh_sach_gio_hang_xoa);

        mDanhSach.setAdapter(mAdapter);
        mDanhSach.setOnItemClickListener(this::onItemClick);
        mThanhToan.setOnClickListener(this::xacNhanThanhToan);
        mXoa.setOnClickListener(this::xoa);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mSelected = i;
    }

    public void xacNhanThanhToan(View view) {
        if (mModels.size() == 0) {
            Toast.makeText(getContext(), "Vui lòng đặt nem nướng để thanh toán.", Toast.LENGTH_SHORT).show();
            return;
        }
        View xacNhanThanhToan = LayoutInflater.from(getContext()).inflate(R.layout.dialog_xac_nhan_thanh_toan, null, false);
        giaoHang = xacNhanThanhToan.findViewById(R.id.xac_nhan_thanh_toan_giao_hang);
        giaoHangHoaToc = xacNhanThanhToan.findViewById(R.id.xac_nhan_thanh_toan_giao_hang_hoa_toc);
        nhanTaiQuan = xacNhanThanhToan.findViewById(R.id.xac_nhan_thanh_toan_nhan_tai_quan);
        tong = xacNhanThanhToan.findViewById(R.id.xac_nhan_thanh_toan_tong);

        tongGiaSanPham = 0;
        for (NemNuongModel model : mModels)
            tongGiaSanPham += model.getDonGia();

        nhanTaiQuan.setOnCheckedChangeListener(this::reloadGia);
        giaoHang.setOnCheckedChangeListener(this::reloadGia);
        giaoHangHoaToc.setOnCheckedChangeListener(this::reloadGia);

        reloadGia(null, false);

        new AlertDialog.Builder(getContext())
                .setTitle("Chọn phương thức thanh toán")
                .setView(xacNhanThanhToan)
                .setNegativeButton("Huỷ", null)
                .setPositiveButton("Thanh toán", this::thanhToan)
                .show();
    }

    public void reloadGia(CompoundButton compoundButton, boolean checked) {
        phiVanChuyen = (int)(giaoHang.isChecked()
                ? 0.05 * tongGiaSanPham
                : giaoHangHoaToc.isChecked()
                ? 0.15 * tongGiaSanPham
                : 0);
        thanhTien = tongGiaSanPham + phiVanChuyen;
        tong.setText(String.format("%,d", thanhTien));
    }

    public void thanhToan(DialogInterface dialogInterface, int i) {
        KhachHangModel taiKhoan = (KhachHangModel)getActivity().getIntent().getSerializableExtra(MainActivity.INTENT_TAIKHOANHIENTAI);
        mConnection = new SqlConnection(getContext());

        Random random = new Random();
        List<KhachHangModel> cacNhanVien = mConnection.getAllNhanVien();
        int randomMaNv = cacNhanVien.get(random.nextInt(cacNhanVien.size())).getMaKh();

        int maGd = mConnection.insertGiaoDich(new GiaoDichModel(
                -1,
                taiKhoan.getMaKh(),
                randomMaNv,
                phiVanChuyen,
                new Date(),
                new Date(System.currentTimeMillis() + 30L * 60L * 1000L), // Thời gian giao là 30 phút sau khi thanh toán
                "",
                ""
        ));

        List<ChiTietGiaoDichModel> ctgdModels = new ArrayList<>();
        for (NemNuongModel model : mModels) {
            boolean hasMaGd = false;
            for (ChiTietGiaoDichModel ctgdModel : ctgdModels)
                if (ctgdModel.getMaSp() == model.getMaSp()) {
                    ctgdModel.setSoLuong(ctgdModel.getSoLuong() + 1);
                    hasMaGd = true;
                    break;
                }
            if (!hasMaGd) {
                ctgdModels.add(new ChiTietGiaoDichModel(maGd, model.getMaSp(), "", 1, 0));
            }
        }
        for (ChiTietGiaoDichModel model : ctgdModels)
            mConnection.insertChiTietGiaoDich(model);

        mModels.clear();
        mAdapter.notifyDataSetChanged();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
        Toast.makeText(getContext(), "Thanh toán thành công.", Toast.LENGTH_SHORT).show();
        mConnection.close();
    }

    public void xoa(View view) {
        if (mSelected == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn nem nướng cần loại bỏ.", Toast.LENGTH_SHORT).show();
            return;
        }
        mModels.remove(mSelected);
        mSelected = -1;
        mAdapter.notifyDataSetChanged();
    }
}