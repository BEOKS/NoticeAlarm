package com.example.noticealarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    @Override
    public View getView(int position, View mView, ViewGroup viewGroup) {

        View view = mLayoutInflater.inflate(R.layout.temp_layout, null);

        TextView URLDataName = (TextView)view.findViewById(R.id.URLName);
        URLDataName.setText(mData.get(position).getUrlName());

        return view;
    }


}
