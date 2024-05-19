package com.baitapandroid.quanlynemnuong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NemNuongModelArray extends ArrayList<NemNuongModel> {
    /*public List<ChiTietGiaoDichModel> toChiTietGiaoDichList(int maGd) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (NemNuongModel model : this) {
            if (hash.get(model.getMaSp()) == null) hash.put(model.getMaSp(), 1);
            else hash.put(model.getMaSp(), hash.get(model.getMaSp()) + 1);
        }
        List<ChiTietGiaoDichModel> list = new ArrayList<>();
        for (Integer maSp : hash.keySet()) {
            list.add(maSp, new ChiTietGiaoDichModel(maGd, maSp, hash.get(maSp)));
        }
        return list;
    }*/
}
