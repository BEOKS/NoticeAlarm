package com.example.noticealarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//TODO 카테고리 추가 버튼 상세화
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
