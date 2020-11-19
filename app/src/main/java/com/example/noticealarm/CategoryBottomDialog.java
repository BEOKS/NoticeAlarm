package com.example.noticealarm;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.AccessControlContext;
import java.util.ArrayList;
//TODO 카테고리 다이얼로그 상세화

/**
 * 바텀 다이얼로그 호출시 URLData.categoryNameList에서 카테고리 목록을 가져와서 카테고리 선택 버튼을 추가함
 * 현재 선택된 카테고리 버튼은 색깔을 변경
 * 다른 카테고리 버튼을 선택하면
 * 1. mainActivity에서 categoryTextView를 선택한 카테고리 이름으로 변경
 * 2. DatashowTabLayout.setCategory(String categoryName)를 호출
 */
public class CategoryBottomDialog extends BottomSheetDialog {
    public String selectedCategoryname="모든 공지사항";
    private LinearLayout categoryLinearLayout;
    private AddNewCategoryButton addNewCategoryButton;
    private TrashButton trashButton;
    private ArrayList<String> categoryArrayList;
    public  MainActivity mainActivity;
    public  URLDeleteActivity urlDeleteActivity;
    public  CategoryBottomDialog categoryBottomDialog;
    public  Context mContext;

    public CategoryBottomDialog(@NonNull Context context) {
        super((MainActivity)context);
        mainActivity=(MainActivity)context;
        categoryArrayList=URLData.categoryNameList;
        setContentView(R.layout.bottom_sheet_dialog_layout);
        categoryLinearLayout=(LinearLayout)findViewById(R.id.category_listView);
        for(String category : categoryArrayList){
            TextView textView=new TextView(context);
            textView.setText(category);
            final String copy=category;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedCategoryname=copy;
                    mainActivity.dataShowViewPager.setData(selectedCategoryname);
                    mainActivity.dataShowViewPager.categoryName=selectedCategoryname;
                    mainActivity.categoryNameTextView.setText(selectedCategoryname);
                }
            });
            categoryLinearLayout.addView(textView);
        }


        addNewCategoryButton=(AddNewCategoryButton)findViewById(R.id.addNewCategoryButton);
        addNewCategoryButton.mainActivity=mainActivity;
        trashButton=(TrashButton)findViewById(R.id.trashButton);
        trashButton.mainActivity=mainActivity;

        URLData.addDataChangeListener(new URLData.DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Data> urlDataList, ArrayList<String> categoryNameList) {
                categoryLinearLayout.removeAllViews();
                for(String category : categoryNameList){
                    TextView textView=new TextView(mainActivity);
                    textView.setText(category);
                    final String copy=category;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedCategoryname=copy;
                            mainActivity.dataShowViewPager.setData(selectedCategoryname);
                            mainActivity.dataShowViewPager.categoryName=selectedCategoryname;
                            mainActivity.categoryNameTextView.setText(selectedCategoryname);
                        }
                    });
                    categoryLinearLayout.addView(textView);
                }
            }
        });
    }



    public void startDeleteActivity(){       //URL삭제 버튼을 누를경우 실행될 수 있도록 따로 메소드를 만들어 놓았습니다

        Intent intent = new Intent(this.mainActivity,URLDeleteActivity.class);
        intent.putExtra("categoryName", selectedCategoryname);
        this.mainActivity.startActivity(intent);
    }

    public CategoryBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
        mainActivity=((MainActivity)context);
    }

    protected CategoryBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mainActivity=((MainActivity)context);
    }
}

