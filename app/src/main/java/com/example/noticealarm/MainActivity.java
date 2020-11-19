package com.example.noticealarm;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//TODO 메인 액티비티 상세화
public class MainActivity extends AppCompatActivity {
    public CustomBottomAppBar customBottomAppBar;
    public DataShowViewPager dataShowViewPager;
    public FloatingActionButton floatingActionButton;
    public TextView categoryNameTextView;
    public MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URLData.init(this);
        URLData.addNewCategory("모든 공지사항");
        setContentView(R.layout.activity_main);
        init();
        mainActivity=this;


        // Intent intent_ = new Intent(this,URLDeleteActivity.class);
        // startActivity(intent_);

        //임시로 MainActivity에서 Intent 송신을 해주었습니다

    }
    private LinearLayout linearLayout;
    public void init(){

        customBottomAppBar=(CustomBottomAppBar)findViewById(R.id.customBottomAppBar);
        customBottomAppBar.mainActivity=this;
        dataShowViewPager=(DataShowViewPager)findViewById(R.id.viewPager);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floattingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                linearLayout=new LinearLayout(getApplicationContext());
                getLayoutInflater().inflate(R.layout.add_new_url_dialog,linearLayout,true);
                builder.setTitle("새로운 URL추가하기").setView(linearLayout);

                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String urlName=((TextView)linearLayout.findViewById(R.id.addUrl_name)).getText().toString();
                                String urlAddress=((TextView)linearLayout.findViewById(R.id.addUrl_address)).getText().toString();
                                URLData.addNewURL(urlName,urlAddress,categoryNameTextView.getText().toString());
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
        });
        categoryNameTextView=(TextView)findViewById(R.id.categoryNameTextView);
    }
}