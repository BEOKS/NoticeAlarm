package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
//TODO 카테고리 다이얼로그 상세화
public class CategoryBottomDialog extends BottomSheetDialog {

    public CategoryBottomDialog(@NonNull Context context) {
        super(context);
    }

    public CategoryBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected CategoryBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
