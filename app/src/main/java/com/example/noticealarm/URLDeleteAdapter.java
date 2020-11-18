package com.example.noticealarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class URLDeleteAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Data> mData;

    public URLDeleteAdapter(Context context, ArrayList<Data> data) {
        mContext = context;
        mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    TextView URLDataName;
    Button button;
    @Override
    public View getView(int position, View mView, ViewGroup viewGroup) {

        View view = mLayoutInflater.inflate(R.layout.temp_layout, null);

        URLDataName = (TextView) view.findViewById(R.id.URLName);
        URLDataName.setText(mData.get(position).urlName);
        button=(Button)view.findViewById(R.id.URLtrashButtonIcon);
        final String string=URLDataName.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(string + "를(을) 삭제 하시겠습니까?");

                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                URLData.removeURL(string);         //쓰레기통 버튼 누를경우 remove URL 호출해
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
        });

        return view;
    }

}
