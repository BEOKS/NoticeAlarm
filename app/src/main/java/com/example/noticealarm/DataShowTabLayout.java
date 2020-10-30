package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

/**
 * 이건 제가 할게요!
 * setCategory(String categoryName)이 호출되면 그에 따라서 데이터를 변경함
 * 1. URLData 클래스에서 데이터를 다운받음
 * 2. 데이터를 탭레이아웃내부에 삽입
 */
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
