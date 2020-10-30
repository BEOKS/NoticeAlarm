package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
//TODO 카테고리 다이얼로그 상세화

/**
 * 바텀 다이얼로그 호출시 URLData.categoryNameList에서 카테고리 목록을 가져와서 카테고리 선택 버튼을 추가함
 * 현재 선택된 카테고리 버튼은 색깔을 변경
 * 다른 카테고리 버튼을 선택하면
 * 1. mainActivity에서 categoryTextView를 선택한 카테고리 버튼으로 변경
 * 2. DatashowTabLayout.setCategory를 호출
 */
public class CategoryBottomDialog extends BottomSheetDialog {
    public String selectedCategoryname=null;

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
