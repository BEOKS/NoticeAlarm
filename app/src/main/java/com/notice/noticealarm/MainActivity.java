package com.notice.noticealarm;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URLData.init(this);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        init();
        mainActivity=this;


        // Intent intent_ = new Intent(this,URLDeleteActivity.class);
        // startActivity(intent_);

        //임시로 MainActivity에서 Intent 송신을 해주었습니다

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "공지사항 채널";
            String description = "공지사항 채널";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("공지사항 채널", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private LinearLayout linearLayout;
    public void init(){

        customBottomAppBar=(CustomBottomAppBar)findViewById(R.id.customBottomAppBar);
        customBottomAppBar.mainActivity=this;
        dataShowViewPager=(DataShowViewPager)findViewById(R.id.viewPager);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floattingActionButton);
        floatingActionButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
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
        /**
         * 최초 실행 시 안내문 생성
         */
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        ((ImageButton)findViewById(R.id.help_image_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GuideActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            Intent intent=new Intent(getApplicationContext(),GuideActivity.class);
             URLData.addNewCategory("모든 공지사항");
            startActivity(intent);
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}