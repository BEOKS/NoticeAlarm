package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;

/**
 * 1. 왼쪽 하단 네비게이션 바를 클릭하면 CategoryBottomDialog를 보여줌
 * 2. 오른쪽 하단 메뉴 버튼을 클릭하면 현재 선택한 카테고리이름(String)을 인텐트로 넘겨주면서 URLDeleteActivity 실행
 */
public class CustomBottomAppBar extends BottomAppBar {
    MainActivity mainActivity;
    public CustomBottomAppBar(@NonNull Context context) {
        super(context);

    }

    public CustomBottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
