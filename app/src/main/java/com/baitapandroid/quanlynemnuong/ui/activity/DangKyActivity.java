package com.baitapandroid.quanlynemnuong.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DangKyActivity extends AppCompatActivity {
    private EditText hoTen, email, sdt, taiKhoan, matKhau, ngaySinh;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private RadioButton nam, nu;
    private Date ngaySinhValue;
    private Button dangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        loadViews();
    }

    private void loadViews() {
        hoTen = findViewById(R.id.dang_ky_ho_ten);
        ngaySinh = findViewById(R.id.dang_ky_ngay_sinh);
        nam = findViewById(R.id.dang_ky_nam);
        nu = findViewById(R.id.dang_ky_nu);
        email = findViewById(R.id.dang_ky_email);
        sdt = findViewById(R.id.dang_ky_sdt);
        taiKhoan = findViewById(R.id.dang_ky_tai_khoan);
        matKhau = findViewById(R.id.dang_ky_mat_khau);
        dangKy = findViewById(R.id.dang_ky_dang_ky);

        dangKy.setOnClickListener(this::dangKy);
    }

    private void dangKy(View view) {
        if (!validateInput()) return;
        KhachHangModel model = new KhachHangModel(
                -1,
                hoTen.getText().toString(),
                nam.isChecked(),
                ngaySinhValue,
                email.getText().toString(),
                sdt.getText().toString(),
                taiKhoan.getText().toString(),
                matKhau.getText().toString()
        );
        SqlConnection connection = new SqlConnection(this);
        try {
            connection.insertKhachHang(model, false);
        }
        catch (Exception exc) {
            Toast.makeText(this, "Có lỗi xảy ra: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        finally {
            connection.close();
        }
        new AlertDialog.Builder(this)
                .setTitle("Đăng ký thành công")
                .setMessage("Quý khách vui lòng sử dụng tài khoản đã đăng ký để đăng nhập vào hệ thống.")
                .setPositiveButton("Ok", this::dangKyXong)
                .show();
    }

    private void dangKyXong(DialogInterface dialogInterface, int i) {
        finish();
    }

    private boolean validateInput() {
        boolean valid = true;

        // Check not blank
        for (EditText edt : new EditText[] {hoTen, ngaySinh, email, sdt, taiKhoan, matKhau}) {
            if (edt.getText().toString().trim().isEmpty()) {
                edt.setError("Thông tin này là bắt buộc.");
                valid = false;
            }
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
        if (!valid) return false;

        return true;
    }
}