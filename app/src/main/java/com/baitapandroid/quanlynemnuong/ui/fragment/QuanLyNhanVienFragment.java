package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuanLyNhanVienFragment extends Fragment {
    private Date ngaySinhValue;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private KhachHangModel model;
    private EditText maNv, hoTen, email, ngaySinh, sdt, taiKhoan, matKhau;
    private String matKhauHienTai;
    private RadioButton nam, nu;
    private Button nutSua;
    private boolean insertMode;

    public QuanLyNhanVienFragment(KhachHangModel model, boolean insertMode) {
        this.model = model;
        this.insertMode = insertMode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadViews();
    }

    private void loadViews() {
        hoTen = getView().findViewById(R.id.quan_ly_nhan_vien_ho_ten);
        email = getView().findViewById(R.id.quan_ly_nhan_vien_email);
        sdt = getView().findViewById(R.id.quan_ly_nhan_vien_sdt);
        maNv = getView().findViewById(R.id.quan_ly_nhan_vien_ma);
        ngaySinh = getView().findViewById(R.id.quan_ly_nhan_vien_ngay_sinh);
        taiKhoan = getView().findViewById(R.id.quan_ly_nhan_vien_tai_khoan);
        matKhau = getView().findViewById(R.id.quan_ly_nhan_vien_mat_khau);
        nam = getView().findViewById(R.id.quan_ly_nhan_vien_nam);
        nu = getView().findViewById(R.id.quan_ly_nhan_vien_nu);
        nutSua = getView().findViewById(R.id.quan_ly_nhan_vien_sua);

        if (insertMode) maNv.setEnabled(true);
        nutSua.setOnClickListener(this::onNutSuaClick);

        if (model == null) return;
        hoTen.setText(model.getHoTen());
        email.setText(model.getEmail());
        maNv.setText(Integer.toString(model.getMaKh()));
        ngaySinh.setText(dateFormat.format(model.getNgaySinh()));
        sdt.setText(model.getSdt());
        taiKhoan.setText(model.getTaiKhoan());
        matKhauHienTai = model.getMatKhau();
    }

    private void onNutSuaClick(View view) {
        if (!validateInput()) return;
        model = new KhachHangModel(
                Integer.parseInt(maNv.getText().toString()),
                hoTen.getText().toString(),
                nam.isChecked(),
                ngaySinhValue,
                email.getText().toString(),
                sdt.getText().toString(),
                taiKhoan.getText().toString(),
                matKhau.getText().toString()
        );
        if (insertMode) them(); else sua();
    }

    private void sua() {
        SqlConnection connection = new SqlConnection(getContext());
        connection.updateNhanVien(model.getMaKh(), model);
        connection.close();
        Toast.makeText(getContext(), "Sửa nhân viên thành công.", Toast.LENGTH_SHORT).show();
    }

    private void them() {
        SqlConnection connection = new SqlConnection(getContext());
        try {
            connection.insertKhachHang(model, true);
        }
        catch (Exception exc) {
            Toast.makeText(getContext(), "Có lỗi xảy ra: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        finally {
            connection.close();
        }
        Toast.makeText(getContext(), "Thêm nhân viên thành công.", Toast.LENGTH_SHORT).show();
    }

    public boolean validateInput() {
        boolean valid = true;

        // Check not blank
        for (EditText edt : new EditText[] {maNv, hoTen, ngaySinh, email, sdt, taiKhoan}) {
            if (edt.getText().toString().trim().isEmpty()) {
                edt.setError("Thông tin này là bắt buộc.");
                valid = false;
            }
        }
        if (insertMode && matKhau.getText().toString().trim().isEmpty()) {
            matKhau.setError("Thông tin này là bắt buộc để tạo tài khoản.");
            valid = false;
        }
        if (!valid) return false;

        // Check pattern
        if (!email.getText().toString().matches("^\\w+@\\w+\\.\\w+$")) {
            email.setError("Vui lòng nhập email đúng dạng.");
            valid = false;
        }
        try {
            ngaySinhValue = dateFormat.parse(ngaySinh.getText().toString());
            if (ngaySinhValue == null) throw new ParseException("NgaySinh is null", 0);
        }
        catch (ParseException e) {
            ngaySinh.setError("Vui lòng ghi ngày sinh theo dạng dd/MM/yyyy (ngày đứng trước, tháng đứng sau, năm đứng cuối).");
            valid = false;
        }
        if (!sdt.getText().toString().matches("^\\d{8,11}$")) {
            sdt.setError("Vui lòng nhập SĐT từ 8 tới 11 chữ số.");
            valid = false;
        }

        // Check unique
        if (insertMode) {
            SqlConnection connection = new SqlConnection(getContext());
            for (KhachHangModel model : connection.getAllKhachHang())
                if (taiKhoan.getText().toString().equals(model.getTaiKhoan())) {
                    taiKhoan.setError("Tên tài khoản này đã có người khác sử dụng. Vui lòng nhập tên tài khoản khác.");
                    valid = false;
                    break;
                }
            connection.close();
            if (!valid) return false;
            for (KhachHangModel model : connection.getAllNhanVien())
                if (taiKhoan.getText().toString().equals(model.getTaiKhoan())) {
                    taiKhoan.setError("Tên tài khoản này đã có người khác sử dụng. Vui lòng nhập tên tài khoản khác.");
                    valid = false;
                    break;
                }
            connection.close();
            if (!valid) return false;
        }

        return true;
    }
}