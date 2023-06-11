package com.example.helperapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    public MapFragment() {
        // 기본 생성자
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment의 레이아웃을 인플레이트합니다.
        View view = inflater.inflate(R.layout.activity_map, container, false);
        // View를 초기화하거나 다른 작업을 수행합니다.
        return view;
    }

    // 필요한 다른 메서드를 추가할 수 있습니다.
}