package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GiaoDichModel;
import com.baitapandroid.quanlynemnuong.ui.adapter.GiaoDichAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TraCuuFragment extends Fragment {
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private GridView mDanhSach;
    private SqlConnection mConnection;
    private GiaoDichAdapter mAdapter;
    private List<GiaoDichModel> mModels = new ArrayList<>();
    private EditText mNgayBatDau, mNgayKetThuc;
    private Date mNgayBatDauValue, mNgayKetThucValue;
    private View mTraCuu;

    public TraCuuFragment() {
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
        return inflater.inflate(R.layout.fragment_tra_cuu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadViews();
    }

    public void loadViews() {
        mDanhSach = getView().findViewById(R.id.tra_cuu_danh_sach);
        mNgayBatDau = getView().findViewById(R.id.tra_cuu_ngay_bat_dau);
        mNgayKetThuc = getView().findViewById(R.id.tra_cuu_ngay_ket_thuc);
        mTraCuu = getView().findViewById(R.id.tra_cuu_tra_cuu);
        mAdapter = new GiaoDichAdapter(getContext(), mModels);
        mDanhSach.setAdapter(mAdapter);

        mDanhSach.setOnItemClickListener(this::onItemClick);
        mTraCuu.setOnClickListener(this::traCuu);
    }

    public boolean validateInput() {
        boolean valid = true;
        // Check not blank
        for (EditText edt : new EditText[] {mNgayBatDau, mNgayKetThuc}) {
            if (edt.getText().toString().trim().isEmpty()) {
                edt.setError("Thông tin này là bắt buộc.");
                valid = false;
            }
        }
        if (!valid) return false;

        // Check pattern
        try {
            mNgayBatDauValue = sDateFormat.parse(mNgayBatDau.getText().toString());
            if (mNgayBatDauValue == null) throw new ParseException("NgayBatDau is null", 0);
        }
        catch (ParseException e) {
            mNgayBatDau.setError("Vui lòng ghi ngày bắt đầu theo dạng dd/MM/yyyy (ngày đứng trước, tháng đứng sau, năm đứng cuối).");
            valid = false;
        }
        try {
            mNgayKetThucValue = sDateFormat.parse(mNgayKetThuc.getText().toString());
            if (mNgayKetThucValue == null) throw new ParseException("NgayKetThuc is null", 0);
        }
        catch (ParseException e) {
            mNgayKetThuc.setError("Vui lòng ghi ngày kết thúc theo dạng dd/MM/yyyy (ngày đứng trước, tháng đứng sau, năm đứng cuối).");
            valid = false;
        }
        if (!valid) return false;

        // Check value
        if (mNgayBatDauValue.equals(mNgayKetThucValue)) { // Bắt đầu bằng kết thúc
            mNgayKetThuc.setError("Vui lòng ghi ngày kết thúc sau ngày bắt đầu.");
            valid = false;
        }
        if (!valid) return false;
        if (mNgayBatDauValue.after(mNgayKetThucValue)) { // Bắt đầu sau kết thúc
            mNgayKetThuc.setError("Vui lòng ghi ngày kết thúc sau ngày bắt đầu.");
            valid = false;
        }
        if (!valid) return false;
        Calendar calDifference = Calendar.getInstance();
        calDifference.setTimeInMillis(mNgayKetThucValue.getTime() - mNgayBatDauValue.getTime());
        if (calDifference.getTimeInMillis() >= 60L * 24L * 60L * 60L * 1000L) { // Quá 60 ngày
            mNgayKetThuc.setError("Vui lòng tra cứu các giao dịch trong tối đa 60 ngày.");
            valid = false;
        }
        if (!valid) return false;

        return true;
    }

    public void traCuu(View view) {
        if (!validateInput()) return;
        mConnection = new SqlConnection(getContext());
        mModels = mConnection.getGiaoDich(mNgayBatDauValue, mNgayKetThucValue);
        mAdapter.setModels(mModels);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mConnection = new SqlConnection(getContext());
        GiaoDichModel gdModel = mModels.get(i);
        List<ChiTietGiaoDichModel> ctgdModels = mConnection.getChiTietGiaoDich(gdModel.getMaGd());
        mConnection.close();
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new XemGiaoDichFragment(gdModel, ctgdModels))
                .addToBackStack("name")
                .commit();
    }
}