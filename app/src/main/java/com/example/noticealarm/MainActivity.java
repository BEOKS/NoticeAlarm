package com.example.noticealarm;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

//TODO 메인 액티비티 상세화
public class MainActivity extends AppCompatActivity {
    public AddNewCategoryButton addNewCategoryButton;
    public CategoryBottomDialog categoryBottomDialog;
    public CustomBottomAppBar customBottomAppBar;
    public DataShowTabLayout dataShowTabLayout;
    public TrashButton trashButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URLData.mainActivity=this;
        URLData.addNewCategory("새로운 카테고리");
        URLData.addNewURL("컴퓨터학부","http://computer.knu.ac.kr/main/index.html",null);
        URLData.addNewURL("컴퓨터학부2","http://computer.knu.ac.kr/06_sub/02_sub.html",null);
        URLData.getDataFromSharedPreference();
    }
}