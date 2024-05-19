package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoiMatKhauFragment extends Fragment {
    private KhachHangModel model;
    private EditText matKhauCu, matKhauMoi, matKhauXacNhan;
    private Button luu;

    public DoiMatKhauFragment(KhachHangModel model) {
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
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadViews();
    }

    private void loadViews() {
        matKhauCu = getView().findViewById(R.id.doi_mat_khau_mat_khau_cu);
        matKhauMoi = getView().findViewById(R.id.doi_mat_khau_mat_khau_moi);
        matKhauXacNhan = getView().findViewById(R.id.doi_mat_khau_mat_khau_xac_nhan);
        luu = getView().findViewById(R.id.doi_mat_khau_luu);
        luu.setOnClickListener(this::onButtonClick);
    }

    public void onButtonClick(View view) {
        if (!validateInput()) return;
        doiMatKhau();
    }

    public boolean validateInput() {
        boolean valid = true;

        // Check not blank
        for (EditText edt : new EditText[] {matKhauCu, matKhauMoi, matKhauXacNhan}) {
            if (edt.getText().toString().trim().isEmpty()) {
                edt.setError("Thông tin này là bắt buộc.");
                valid = false;
            }
        }
        if (!valid) return false;

        // Check equal
        if (!matKhauMoi.getText().toString().equals(matKhauXacNhan.getText().toString())) {
            matKhauXacNhan.setError("Mật khẩu ở phần này phải trùng với Mật khẩu mới.");
            valid = false;
        }
        if (!valid) return false;

        if (!matKhauCu.getText().toString().equals(model.getMatKhau())) {
            matKhauCu.setError("Mật khẩu không đúng.");
            valid = false;
        }
        if (!valid) return false;

        return true;
    }

    public void doiMatKhau() {
        String newPassword = matKhauMoi.getText().toString();
        model.setMatKhau(newPassword);
        SqlConnection connection = new SqlConnection(getContext());
        connection.updatePassword(model, newPassword);
        connection.close();
        new AlertDialog.Builder(getContext())
                .setTitle("Đổi mật khẩu")
                .setMessage("Đổi mật khẩu thành công.")
                .setPositiveButton("OK", this::thoat)
                .show();
    }

    public void thoat(DialogInterface dialogInterface, int i) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }
}