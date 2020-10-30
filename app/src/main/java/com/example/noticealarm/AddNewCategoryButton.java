package com.example.noticealarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//TODO 카테고리 추가 버튼 상세화

/**
 * 1. 새로운 카테고리를 추가하기 위한 다이얼로그 생성
 * 2. 다이얼로그에서 카테고리 이름을 받고 확인 버튼을 누르면 URLData.addNewCategory(String catgoryName) 호출
 * 3. 취소를 누르면 다이얼로그 닫기
 */
public class AddNewCategoryButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {

    MainActivity mainActivity;
    public AddNewCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }
    private int a=1;
    @Override
    public void onClick(View v) {
        //카테고리 추가 Dialog 실행

    }
}
