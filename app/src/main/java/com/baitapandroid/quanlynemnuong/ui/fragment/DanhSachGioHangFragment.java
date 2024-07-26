package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.baitapandroid.quanlynemnuong.model.GioHangModel;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.activity.DangNhapActivity;
import com.baitapandroid.quanlynemnuong.ui.activity.MainActivity;
import com.baitapandroid.quanlynemnuong.ui.adapter.GioHangAdapter;
import com.baitapandroid.quanlynemnuong.ui.adapter.NemNuongAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DanhSachGioHangFragment extends Fragment {
    private GridView mDanhSach;
    private GioHangAdapter mAdapter;
    private ImageButton mThanhToan, mXoa, mChinhSoLuong;
    private int mSelected = -1;
    private List<GioHangModel> mModels;
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
        mConnection = new SqlConnection(getContext());
        mModels = mConnection.getGioHangByNguoiDung(MainActivity.taiKhoanHienTai.getMaKh());
        mAdapter = new GioHangAdapter(getContext(), mModels);
        mDanhSach = getView().findViewById(R.id.danh_sach_gio_hang_danh_sach);
        mThanhToan = getView().findViewById(R.id.danh_sach_gio_hang_thanh_toan);
        mChinhSoLuong = getView().findViewById(R.id.danh_sach_gio_hang_chinh_so_luong);
        mXoa = getView().findViewById(R.id.danh_sach_gio_hang_xoa);

        mDanhSach.setAdapter(mAdapter);
        mDanhSach.setOnItemClickListener(this::onItemClick);
        mThanhToan.setOnClickListener(this::xacNhanThanhToan);
        mChinhSoLuong.setOnClickListener(this::chinhSoLuong);
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
        for (GioHangModel model : mModels)
            tongGiaSanPham += model.getDonGia() * model.getSoLuong();

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
        mConnection = new SqlConnection(getContext());

        Random random = new Random();
        List<KhachHangModel> cacNhanVien = mConnection.getAllNhanVien();
        int randomMaNv = cacNhanVien.get(random.nextInt(cacNhanVien.size())).getMaKh();

        int maGd = mConnection.insertGiaoDich(new GiaoDichModel(
                -1,
                MainActivity.taiKhoanHienTai.getMaKh(),
                randomMaNv,
                phiVanChuyen,
                new Date(),
                new Date(System.currentTimeMillis() + 30L * 60L * 1000L), // Thời gian giao là 30 phút sau khi thanh toán
                "",
                ""
        ));

        List<GioHangModel> cacGioHang = mConnection.getGioHangByNguoiDung(MainActivity.taiKhoanHienTai.getMaKh());
        for (GioHangModel gioHangModel : cacGioHang) {
            mConnection.insertChiTietGiaoDich(new ChiTietGiaoDichModel(maGd, gioHangModel.getMaSp(), gioHangModel.getTenSp(), gioHangModel.getSoLuong(), gioHangModel.getDonGia()));
        }


        /*List<ChiTietGiaoDichModel> ctgdModels = new ArrayList<>();
        for (GioHangModel model : mModels) {
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
            mConnection.insertChiTietGiaoDich(model);*/

        mModels.clear();
        mConnection.clearGioHang(cacGioHang.get(0));
        mAdapter.notifyDataSetChanged();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
        Toast.makeText(getContext(), "Thanh toán thành công.", Toast.LENGTH_SHORT).show();
        mConnection.close();
    }

    public void chinhSoLuong(View view) {
        if (mSelected == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn nem nướng cần loại bỏ.", Toast.LENGTH_SHORT).show();
            return;
        }
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(Integer.toString(mModels.get(mSelected).getSoLuong()));
        new AlertDialog.Builder(getContext()).setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            if (Integer.parseInt(input.getText().toString()) < -1)
                                Toast.makeText(getContext(), "Vui lòng nhập số lượng lớn hơn 0.", Toast.LENGTH_SHORT).show();
                            else {
                                mModels.get(mSelected).setSoLuong(Integer.parseInt(input.getText().toString()));
                                mConnection.updateGioHang(mModels.get(mSelected));
                                mSelected = -1;
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (Exception e) {
                            Toast.makeText(getContext(), "Vui lòng nhập số vào đây.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void xoa(View view) {
        if (mSelected == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn nem nướng cần loại bỏ.", Toast.LENGTH_SHORT).show();
            return;
        }
        mModels.remove(mSelected);
        mConnection.deleteGioHang(mModels.get(mSelected));
        mSelected = -1;
        mAdapter.notifyDataSetChanged();
    }
}