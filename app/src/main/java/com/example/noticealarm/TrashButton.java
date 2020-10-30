package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * 1. 쓰레기 버튼을 선택하면 삭제 다이얼로그가 실행됨
 * 2. 확인 버튼시 현재 선택된 카테고리이름을 mainActivity.categoryBottomDialog.selectedCategoryname를 통해 가져와서 URLData.removeCategory(String categoryName)를 호출함
 * 3. 취소시 다이얼로그 창 닫기
 */


public class TrashButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {

    public TrashButton(Context context, AttributeSet attr) {
        super(context,attr);
        setOnClickListener(this);
    }
    public TrashButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {

    }
}
