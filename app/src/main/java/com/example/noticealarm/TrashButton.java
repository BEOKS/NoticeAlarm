package com.example.noticealarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 1. 쓰레기 버튼을 선택하면 삭제 다이얼로그가 실행됨
 * 2. 확인 버튼시 현재 선택된 카테고리이름을 mainActivity.categoryBottomDialog.selectedCategoryname를 통해 가져와서 URLData.removeCategory(String categoryName)를 호출함
 * 3. 취소시 다이얼로그 창 닫기
 */


public class TrashButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {

    MainActivity mainActivity;
    public TrashButton(Context context, AttributeSet attr) {
        super(context,attr);
        setOnClickListener(this);
    }
    public TrashButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        final String SelCategoryName = mainActivity.categoryNameTextView.getText().toString();

        builder.setTitle(SelCategoryName + "를(을) 삭제 하시겠습니까?");

        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(SelCategoryName.equals("모든 공지사항")){
                            mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mainActivity,"모든 공지사항은 삭제 할 수 없습니다",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            URLData.removeCategory(SelCategoryName);
                        }

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
