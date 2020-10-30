package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
//TODO 탭레이아웃 상세화
public class DataShowTabLayout extends TabLayout {

    public DataShowTabLayout(@NonNull Context context) {
        super(context);
    }

    public DataShowTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DataShowTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCategory(String categoryName){

    }
}
