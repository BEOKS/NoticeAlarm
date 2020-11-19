package com.example.noticealarm;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;

/**
 * 0. find view by id 등으로 객체를 호출한 후에 mainactivity를 연결해야
 * 1. 왼쪽 하단 네비게이션 바를 클릭하면 CategoryBottomDialog를 보여줌
 * 2. 오른쪽 하단 메뉴 버튼을 클릭하면 현재 선택한 카테고리이름(String)을 인텐트로 넘겨주면서 URLDeleteActivity 실행
 */
public class CustomBottomAppBar extends BottomAppBar {
    MainActivity mainActivity;
    URLDeleteActivity urlDeleteActivity;
    public CustomBottomAppBar(@NonNull Context context) {
        super(context);
        mainActivity=((MainActivity)context);
    }

    public CustomBottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mainActivity=((MainActivity)context);
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryBottomDialog categoryBottomDialog=new CategoryBottomDialog(mainActivity);
                categoryBottomDialog.show();
            }
        });
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.END);
        Button button=new Button(context);
        button.setBackground(getResources().getDrawable(R.drawable.trash1));
        button.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity,URLDeleteActivity.class);
                intent.putExtra("categoryName",mainActivity.categoryNameTextView.getText().toString());
                mainActivity.startActivity(intent);
            }
        });
        linearLayout.addView(button);
        addView(linearLayout);
    }

    public CustomBottomAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
