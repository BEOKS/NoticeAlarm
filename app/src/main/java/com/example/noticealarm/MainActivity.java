package com.example.noticealarm;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        URLData.addNewURL("컴퓨터학부","http://computer.knu.ac.kr/06_sub/02_sub.html","");
        URLData.addNewURL("컴퓨터학부 취업정보","http://computer.knu.ac.kr/06_sub/03_sub.html","");
        dataShowViewPager=(DataShowViewPager)findViewById(R.id.dataShowViewPager);
        dataShowViewPager.setData("");


    }
    public void init(){
        URLData.init(this);

        categoryBottomDialog=new CategoryBottomDialog(getApplicationContext());

    }
}