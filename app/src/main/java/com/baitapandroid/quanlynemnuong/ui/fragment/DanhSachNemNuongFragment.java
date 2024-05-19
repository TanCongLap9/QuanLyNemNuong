package com.baitapandroid.quanlynemnuong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.SqlConnection;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.adapter.NemNuongAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class DanhSachNemNuongFragment extends Fragment {
    private GridView mDanhSach;
    private NemNuongAdapter mAdapter;
    private Button mGioHang;
    private SqlConnection mConnection;
    private SearchView mTimKiem;
    private int mSelected = -1;
    private List<NemNuongModel> mModels = new ArrayList<>();

    public DanhSachNemNuongFragment() {
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
        return inflater.inflate(R.layout.fragment_danh_sach_nem_nuong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConnection = new SqlConnection(getContext());
        mModels = mConnection.getAllSanPham();
        mDanhSach = getView().findViewById(R.id.danh_sach_nem_nuong_danh_sach);
        mTimKiem = getView().findViewById(R.id.danh_sach_nem_nuong_tim_kiem);
        mGioHang = getView().findViewById(R.id.danh_sach_nem_nuong_gio_hang);

        mAdapter = new NemNuongAdapter(getContext(), mModels);
        mDanhSach.setAdapter(mAdapter);
        mDanhSach.setOnItemClickListener(this::onItemClick);
        mGioHang.setOnClickListener(this::gioHang);
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
                List<NemNuongModel> filteredModelList = new ArrayList<>();
                for (NemNuongModel model : mModels) {
                    if (
                            toSearchText(model.getTenSp()).contains(chuoiTimKiem)
                                    || toSearchText(model.getMoTa()).contains(chuoiTimKiem)
                                    || Integer.toString(model.getMaSp()).equals(chuoiTimKiem)
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
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new XemNemNuongFragment(mModels.get(i)))
                .addToBackStack("name")
                .commit();
    }

    public void gioHang(View view) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new DanhSachGioHangFragment())
                .addToBackStack("name")
                .commit();
    }
}