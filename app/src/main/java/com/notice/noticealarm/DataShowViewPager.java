package com.notice.noticealarm;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * 완성!
 * setCategory(String categoryName)이 호출되면 그에 따라서 데이터를 변경함
 * 1. URLData 클래스에서 데이터를 다운받음
 * 2. 데이터를 탭레이아웃내부에 삽입
 */
public class DataShowViewPager extends LinearLayout {
    private TabLayout tabLayout;
    public String categoryName;
    DataShowPagerAdapter dataShowPagerAdapter;
    ViewPager viewPager;
    TextView initTextView;
    MainActivity mainActivity;
    public DataShowViewPager(@NonNull Context context) {
        super(context);
        mainActivity=(MainActivity)context;
    }

    public DataShowViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mainActivity=(MainActivity)context;
        categoryName="모든 공지사항";
        init(categoryName);
        URLData.addDataChangeListener(new URLData.DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Data> urlDataList, ArrayList<String> categoryNameList) {
                setData(categoryName);
            }
        });
    }
    public void init(String categoryName){
        initTextView=(TextView)mainActivity.getLayoutInflater().inflate(R.layout.init_textview,null);
        initTextView.setText("게시판의 URL을 추가하세요.");
        addView(initTextView);
        setGravity(Gravity.CENTER);
        initTextView.setVisibility(GONE);

        this.categoryName=categoryName;
        tabLayout=null;
        //탭 레이아웃 세팅
        setOrientation(LinearLayout.VERTICAL);
        tabLayout=new TabLayout(getContext());
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(tabLayout);

        viewPager=new ViewPager(getContext());
        dataShowPagerAdapter=new DataShowPagerAdapter(categoryName);
        viewPager.setAdapter(dataShowPagerAdapter);
        addView(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setData(String categoryName){
        if(URLData.getURLinCategory(categoryName).size()==0){
            initTextView.setVisibility(VISIBLE);
        }
        else{
            initTextView.setVisibility(GONE);
        }

        removeView(viewPager);
        viewPager=new ViewPager(getContext());
        dataShowPagerAdapter.data=URLData.getURLinCategory(categoryName);
        dataShowPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(dataShowPagerAdapter);
        addView(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class DataShowPagerAdapter extends PagerAdapter {
    ArrayList<Data> data;
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