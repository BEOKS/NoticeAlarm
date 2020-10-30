package com.example.noticealarm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
//TODO 카테고리 삭제 버튼 상세화


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
