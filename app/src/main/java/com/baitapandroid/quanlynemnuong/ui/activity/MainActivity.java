package com.baitapandroid.quanlynemnuong.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.baitapandroid.quanlynemnuong.R;
import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.ui.fragment.DanhSachKhachHangFragment;
import com.baitapandroid.quanlynemnuong.ui.fragment.DanhSachNemNuongFragment;
import com.baitapandroid.quanlynemnuong.ui.fragment.DanhSachNhanVienFragment;
import com.baitapandroid.quanlynemnuong.ui.fragment.ThongTinTaiKhoanFragment;
import com.baitapandroid.quanlynemnuong.ui.fragment.ThongTinUngDungFragment;
import com.baitapandroid.quanlynemnuong.ui.fragment.TraCuuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String INTENT_TAIKHOANHIENTAI = "INTENT_TAIKHOANHIENTAI";
    public static String INTENT_GIOHANG = "INTENT_GIOHANG";
    private Animator fadeIn, fadeOut, expand, shrink;
    private boolean menuShown, menuShowing;
    private List<View> navigationListItems = new ArrayList<>();
    private View dangXuat, drawerBackground, menuToggler, menuToggler2;
    private ViewGroup drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        getIntent().putExtra(INTENT_GIOHANG, new ArrayList<NemNuongModel>());
    }

    private void loadViews() {
        navigationListItems.add(findViewById(R.id.main_khach_hang));
        navigationListItems.add(findViewById(R.id.main_nhan_vien));
        navigationListItems.add(findViewById(R.id.main_ung_dung));
        navigationListItems.add(findViewById(R.id.main_tai_khoan));
        navigationListItems.add(findViewById(R.id.main_tra_cuu));
        navigationListItems.add(findViewById(R.id.main_nem_nuong));
        menuToggler = findViewById(R.id.main_menu_toggler);
        menuToggler2 = findViewById(R.id.main_menu_toggler2);
        drawer = findViewById(R.id.main_drawer);
        drawerBackground = findViewById(R.id.main_drawer_background);
        dangXuat = findViewById(R.id.main_dang_xuat);

        findViewById(R.id.main_quan_ly).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);findViewById(R.id.main_khach_hang).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);
        findViewById(R.id.main_khach_hang).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);findViewById(R.id.main_khach_hang).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);
        findViewById(R.id.main_nhan_vien).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);findViewById(R.id.main_khach_hang).setVisibility(((KhachHangModel)getIntent().getSerializableExtra(INTENT_TAIKHOANHIENTAI)).isNhanVien() ? View.VISIBLE : View.GONE);

        drawer.setVisibility(View.GONE);
        drawerBackground.setVisibility(View.GONE);

        for (View navigationListItem : navigationListItems)
            navigationListItem.setOnClickListener(this::openFragment);
        menuToggler.setOnClickListener(this::toggleMenu);
        menuToggler2.setOnClickListener(this::toggleMenu);
        drawerBackground.setOnClickListener(this::toggleMenu);
        dangXuat.setOnClickListener(this::dangXuat);

        fadeIn = AnimatorInflater.loadAnimator(this, R.animator.fade_in);
        fadeOut = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
        expand = AnimatorInflater.loadAnimator(this, R.animator.expand_width);
        shrink = AnimatorInflater.loadAnimator(this, R.animator.shrink_width);
        fadeIn.setTarget(drawerBackground);
        fadeOut.setTarget(drawerBackground);
        expand.setTarget(drawer);
        shrink.setTarget(drawer);
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                drawerBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuShowing = false;
            }
        });
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                drawerBackground.setVisibility(View.GONE);
                menuShowing = false;
            }
        });
        expand.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {
                drawer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuShowing = false;
            }
        });
        shrink.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                drawer.setVisibility(View.GONE);
                menuShowing = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && ((List<ChiTietGiaoDichModel>)getIntent().getSerializableExtra(INTENT_GIOHANG)).size() != 0)
            new AlertDialog.Builder(this)
                    .setTitle("Huỷ bỏ ")
                    .setMessage("Bạn có muốn huỷ bỏ giỏ hàng hiện tại không?")
                    .setNegativeButton("Không", null)
                    .setPositiveButton("Có", this::huyGioHang)
                    .show();
        else super.onBackPressed();
    }

    public void huyGioHang(DialogInterface dialogInterface, int i) {
        getIntent().putExtra(INTENT_GIOHANG, new ArrayList<NemNuongModel>());
        super.onBackPressed();
    }

    private void dangXuat(View view) {
        onBackPressed();
    }

    private void toggleMenu(View view) {
        if (menuShowing) return;
        menuShowing = true;
        if (menuShown) {
            shrink.start();
            fadeOut.start();
        } else {
            expand.start();
            fadeIn.start();
        }
        menuShown = !menuShown;
    }

    private void openFragment(View view) {
        Fragment fragment;
        if (view.getId() == R.id.main_khach_hang)
            fragment = new DanhSachKhachHangFragment();
        else if (view.getId() == R.id.main_nhan_vien)
            fragment = new DanhSachNhanVienFragment();
        else if (view.getId() == R.id.main_ung_dung)
            fragment = new ThongTinUngDungFragment();
        else if (view.getId() == R.id.main_tai_khoan)
            fragment = new ThongTinTaiKhoanFragment();
        else if (view.getId() == R.id.main_tra_cuu)
            fragment = new TraCuuFragment();
        else if (view.getId() == R.id.main_nem_nuong)
            fragment = new DanhSachNemNuongFragment();
        else return;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
        toggleMenu(view);
    }
}