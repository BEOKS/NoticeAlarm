package com.example.noticealarm.URLDataShow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

/**
 * 이건 제가 할게요!
 * setCategory(String categoryName)이 호출되면 그에 따라서 데이터를 변경함
 * 1. URLData 클래스에서 데이터를 다운받음
 * 2. 데이터를 탭레이아웃내부에 삽입
 */
public class DataShowViewPager extends ViewPager {
    private TabLayout tabLayout;
    public DataShowViewPager(@NonNull Context context) {
        super(context);
        setData(context);
    }

    public DataShowViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setData(context);
    }
    public void setData(Context context){
        tabLayout=new TabLayout(context);
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(tabLayout);
    }
}
