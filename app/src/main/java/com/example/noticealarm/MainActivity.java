package com.example.noticealarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoticeCrawling noticeCrawling=new NoticeCrawling("http://computer.knu.ac.kr/06_sub/02_sub.html");
        Elements elements=noticeCrawling.getTableElements();
    }
}