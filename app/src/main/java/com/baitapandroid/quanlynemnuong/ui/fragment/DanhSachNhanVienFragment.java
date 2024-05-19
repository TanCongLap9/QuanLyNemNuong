package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.ui.adapter.KhachHangAdapter;
import com.baitapandroid.quanlynemnuong.ui.adapter.NhanVienAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class DanhSachNhanVienFragment extends Fragment {
    private GridView mDanhSach;
    private NhanVienAdapter mAdapter;
    private ImageButton mThem, mSua;
    private SearchView mTimKiem;
    private int mSelected = -1;
    private List<KhachHangModel> mModels = new ArrayList<>();

    public DanhSachNhanVienFragment() {
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
        return inflater.inflate(R.layout.fragment_danh_sach_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SqlConnection connection = new SqlConnection(getContext());
        mModels = connection.getAllNhanVien();
        mAdapter = new NhanVienAdapter(getContext(), mModels);
        mDanhSach = getView().findViewById(R.id.danh_sach_nhan_vien_danh_sach);
        mTimKiem = getView().findViewById(R.id.danh_sach_nhan_vien_tim_kiem);
        mThem = getView().findViewById(R.id.danh_sach_nhan_vien_them);
        mSua = getView().findViewById(R.id.danh_sach_nhan_vien_sua);

        mDanhSach.setAdapter(mAdapter);
        mDanhSach.setOnItemClickListener(this::onItemClick);
        mThem.setOnClickListener(this::them);
        mSua.setOnClickListener(this::sua);
        mTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mTimKiem.getQuery().length() == 0) {
                    mAdapter.setModels(mModels);
                    return false;
                }
                String chuoiTimKiem = toSearchText(mTimKiem.getQuery());
                List<KhachHangModel> filteredModelList = new ArrayList<>();
                for (KhachHangModel model : mModels) {
                    if (
                            toSearchText(model.getHoTen()).contains(chuoiTimKiem)
                                    || toSearchText(model.getTaiKhoan()).contains(chuoiTimKiem)
                                    || toSearchText(model.getEmail()).contains(chuoiTimKiem)
                                    || Integer.toString(model.getMaKh()).equals(chuoiTimKiem)
                    ) filteredModelList.add(model);
                }
                mAdapter.setModels(filteredModelList);
                return false;
            }
        });
    }

    public String toSearchText(CharSequence text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .toLowerCase()
                .replaceAll("[\u0300-\u036f]", "");
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mSelected = i;
    }

    public void them(View view) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new QuanLyNhanVienFragment(null, true))
                .addToBackStack("name")
                .commit();
    }

    public void sua(View view) {
        if (mSelected == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn người cần chỉnh sửa.", Toast.LENGTH_SHORT).show();
            return;
        }
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new QuanLyNhanVienFragment(mModels.get(mSelected), false))
                .addToBackStack("name")
                .commit();
    }
}