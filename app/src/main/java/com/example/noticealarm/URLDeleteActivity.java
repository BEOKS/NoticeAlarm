package com.example.noticealarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 이건 제가 할게요!
 * 1. intent로 넘겨받은 카테고리 이름을 통해서
 *      ArrayList<Data>  getURLinCategory(String categoryName)를 호출하여 데이터 리스트를 입력받음
 *      각 데이터의 urlName을 textView로 하고 삭제 버튼을 결합한 뷰를 생성하여 레이아웃에 추가함
 * 2. 각 삭제 버튼을 클릭하면 해당 urlName에 대해서 URLData.removeURL(String urlName)을 호출함
 */
public class URLDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_r_l_delete);
    }
}