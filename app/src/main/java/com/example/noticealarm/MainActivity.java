package com.example.noticealarm;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

//TODO 메인 액티비티 상세화
public class MainActivity extends AppCompatActivity {
    public AddNewCategoryButton addNewCategoryButton;
    public CategoryBottomDialog categoryBottomDialog;
    public CustomBottomAppBar customBottomAppBar;
    public DataShowViewPager dataShowViewPager;
    public TrashButton trashButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        customBottomAppBar=(CustomBottomAppBar)findViewById(R.id.customBottomAppBar);
        customBottomAppBar.mainActivity=this;


    }
    public void init(){
        URLData.init(this);
        URLData.addNewCategory("모든 공지사항");

    }
}