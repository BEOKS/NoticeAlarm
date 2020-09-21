package com.example.noticealarm;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Requirement
 * 1. Set network permission in manifest
 * <uses-permission android:name="android.permission.INTERNET" />
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *
 * 2. Allow http connection
 * android:usesCleartextTraffic="true"
 *
 *
 */
public class NoticeCrawling {
    private String url=null;
    public NoticeCrawling(String url){
        this.url=url;
    }
    public Elements getTableElements(){
        if(url==null){
            Log.e("NoticeCrawling","getHtmlInfo : url is null");
            return  null;
        }
        Document document=null;
        try {
            document=new ConnetAsyncTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(document==null){
            return null;
        }
        Elements elements=document.getElementsByTag("table");
        if(elements.size()==0){
            return null;
        }
        return elements;
    }
}
class ConnetAsyncTask extends AsyncTask<String, Void, Document> {

    @Override
    protected Document doInBackground(String... strings) {
        Document document=null;
        try {
             document=Jsoup.connect(strings[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
