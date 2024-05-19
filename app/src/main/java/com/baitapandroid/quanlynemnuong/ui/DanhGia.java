package com.baitapandroid.quanlynemnuong.ui;

import android.widget.ImageView;

import com.baitapandroid.quanlynemnuong.R;

public class DanhGia {
    private float danhGia;
    private ImageView[] stars;

    public DanhGia(ImageView danhGia1, ImageView danhGia2, ImageView danhGia3, ImageView danhGia4, ImageView danhGia5) {
        this.stars = new ImageView[]{danhGia1, danhGia2, danhGia3, danhGia4, danhGia5};
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
        for (int i = 1; i < 5; i++)
            stars[i].setImageResource(
                    danhGia >= i - 0.25
                            ? R.drawable.ic_star_24dp
                            : danhGia >= i - 0.75
                            ? R.drawable.ic_star_half_24dp
                            : R.drawable.ic_star_border_24dp
            );
    }

    public float getDanhGia() {
        return danhGia;
    }
}
