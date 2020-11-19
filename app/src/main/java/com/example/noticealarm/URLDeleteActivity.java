package com.example.noticealarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * 이건 제가 할게요!
 * 1. intent로 넘겨받은 카테고리 이름을 통해서
 *      ArrayList<Data>  getURLinCategory(String categoryName)를 호출하여 데이터 리스트를 입력받음
 *      각 데이터의 urlName을 textView로 하고 삭제 버튼을 결합한 뷰를 생성하여 레이아웃에 추가함
 * 2. 각 삭제 버튼을 클릭하면 해당 urlName에 대해서 URLData.removeURL(String urlName)을 호출함
 */

public class URLDeleteActivity extends AppCompatActivity {

    ArrayList<Data> data;
    String categoryName;
    MainActivity mainActivity;
    public URLDeleteActivity urlDeleteActivity;
    public CategoryBottomDialog categoryBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.URLDataImit();
        setContentView(R.layout.activity_u_r_l_delete);  /*activity_u_r_l_delete 레이아웃을 불러옵니다*/

        ListView listView = (ListView)findViewById(R.id.listView);
        URLDeleteAdapter urlDeleteAdapter = new URLDeleteAdapter(this,categoryName,listView);
        listView.setAdapter(urlDeleteAdapter);   /*리스트뷰와 어뎁터를 연결시키고 화면에 출력합니다*/

    }

    public void URLDataImit() {

        Intent intents = getIntent();
        categoryName = intents.getExtras().getString("categoryName");  /* 인텐트로 categoryName을 불러옵니다 */
        data = URLData.getURLinCategory(categoryName);          /* getURLinCategory를 통해 불러온 CategoryName과
                                                                   비교하여 URLName을 data에 추가합니다*/
        System.out.println("1");

    }

        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(categoryName + "를(을) 삭제 하시겠습니까?");

            builder.setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            URLData.removeURL(categoryName);         //쓰레기통 버튼 누를경우 remove URL 호출해
                                                                     //리스트뷰의 URLData를 삭제합니다
                        }
                    });

            builder.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();

        }

}