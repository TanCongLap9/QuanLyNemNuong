package com.baitapandroid.quanlynemnuong.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SharedPrefUtils;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.UserModel;

public class DangNhapActivity extends AppCompatActivity {
    private EditText taiKhoan, matKhau;
    private CheckBox nhoDangNhap;
    private Button dangKy, dangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        loadViews();
        UserModel userModel;
        if ((userModel = SharedPrefUtils.getRememberedUser(this)) != null) {
            taiKhoan.setText(userModel.getTaiKhoan());
            matKhau.setText(userModel.getMatKhau());
            nhoDangNhap.setChecked(true);
        }
    }

    private void loadViews() {
        taiKhoan = findViewById(R.id.dang_nhap_tai_khoan);
        matKhau = findViewById(R.id.dang_nhap_mat_khau);
        nhoDangNhap = findViewById(R.id.dang_nhap_nho_dang_nhap);
        dangKy = findViewById(R.id.dang_nhap_dang_ky);
        dangNhap = findViewById(R.id.dang_nhap_dang_nhap);

        dangNhap.setOnClickListener(this::dangNhap);
        dangKy.setOnClickListener(this::dangKy);
    }

    private boolean validateInput() {
        boolean valid = true;

        // Check not blank
        for (EditText edt : new EditText[] {taiKhoan, matKhau}) {
            if (edt.getText().toString().trim().isEmpty()) {
                edt.setError("Thông tin này là bắt buộc.");
                valid = false;
            }
        }
        if (!valid) return false;

        return true;
    }

    private void dangNhap(View view) {
        if (!validateInput()) return;
        SqlConnection connection = new SqlConnection(this);
        UserModel model = new UserModel(taiKhoan.getText().toString(), matKhau.getText().toString());
        KhachHangModel khachHangModel;
        if (
                (khachHangModel = connection.getKhachHangByUser(model)) != null
                        || (khachHangModel = connection.getNhanVienByUser(model)) != null
        ) {
            if (nhoDangNhap.isChecked()) SharedPrefUtils.setRememberedUser(this, model);
            else SharedPrefUtils.clearRememberedUser(this);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.INTENT_TAIKHOANHIENTAI, khachHangModel);
            startActivity(intent);

            connection.close();
            return;
        }
        Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
    }

    private void dangKy(View view) {
        startActivity(new Intent(this, DangKyActivity.class));
    }
}