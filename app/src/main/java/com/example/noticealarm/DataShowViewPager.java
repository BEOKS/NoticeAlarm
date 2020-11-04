package com.example.noticealarm;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.sql.Array;
import java.util.ArrayList;

/**
 * 완성!
 * setCategory(String categoryName)이 호출되면 그에 따라서 데이터를 변경함
 * 1. URLData 클래스에서 데이터를 다운받음
 * 2. 데이터를 탭레이아웃내부에 삽입
 */
public class DataShowViewPager extends LinearLayout {
    private TabLayout tabLayout;
    private String categoryName;
    public DataShowViewPager(@NonNull Context context) {
        super(context);
    }

    public DataShowViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        categoryName="";
        URLData.addDataChangeListener(new URLData.DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Data> urlDataList, ArrayList<String> categoryNameList) {
                setData(categoryName);
            }
        });
    }
    public void setData(String categoryName){
        this.categoryName=categoryName;
        //탭 레이아웃 세팅
        setOrientation(LinearLayout.VERTICAL);
        tabLayout=new TabLayout(getContext());
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(tabLayout);

        ViewPager viewPager=new ViewPager(getContext());
        DataShowPagerAdapter dataShowPagerAdapter=new DataShowPagerAdapter(categoryName);
        viewPager.setAdapter(dataShowPagerAdapter);
        addView(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class DataShowPagerAdapter extends PagerAdapter {
    private ArrayList<Data> data;
    public DataShowPagerAdapter(String category){
        data=URLData.getURLinCategory(category);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        WebView webView=new WebView(container.getContext());
        webView.loadDataWithBaseURL(data.get(position).urlAddress,data.get(position).htmlData,"text/html","UTF-8",null);
        container.addView(webView);
        TextView textView=new TextView(container.getContext());
        textView.setText("asdfasdf");
        container.addView(textView);
        return webView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).urlName;
    }
}
class DataShowFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_data_show,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        ((WebView)view.findViewById(R.id.webView)).loadDataWithBaseURL(args.getString("baseURL"),args.getString("html"),"text/html","UTF-8",args.getString("baseURL"));
    }
}