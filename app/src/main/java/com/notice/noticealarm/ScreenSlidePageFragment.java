package com.notice.noticealarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {
    int position;
    public ScreenSlidePageFragment(int position){
        this.position=position;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        if(position==0){
            ((ImageView)rootView.findViewById(R.id.page2)).setVisibility(View.GONE);
        }
        else{
            ((ImageView)rootView.findViewById(R.id.page1)).setVisibility(View.GONE);
        }
        return rootView;
    }
}
