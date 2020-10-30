package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;
//TODO 바텀 앱 바 상세화
public class CustomBottomAppBar extends BottomAppBar {
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
