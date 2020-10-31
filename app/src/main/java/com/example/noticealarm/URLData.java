package com.example.noticealarm;
//TODO 파이어 베이스와 연결 필


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * 사용을 위해서는 mainActivity의 값을 설정해주어야한다.
 */
public class URLData {
     interface DataChangeListener{
          public void onDataChange(ArrayList<Data> urlDataList,ArrayList<String> categoryNameList);
     }
     public static ArrayList<Data> urlDataList=new ArrayList<Data>();
     public static ArrayList<String> categoryNameList=new ArrayList<String>();
     private static ArrayList<DataChangeListener> dataChangeListenerArrayList=new ArrayList<DataChangeListener>();

     private static String urlDataListFileName="urlDataList",categoryNameListFileName="categoryNameList";
     public static void init(){
          FirebaseDatabase.getInstance().getReference("URL_DATA").addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               }

               @Override
               public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

               }

               @Override
               public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          });
     }
     public static MainActivity mainActivity;
     public static void getDataFromSharedPreference(){
          SharedPreferences sharedPreferences=mainActivity.getSharedPreferences("Data",Context.MODE_PRIVATE);
          String json=sharedPreferences.getString(urlDataListFileName,"");
          if(!json.equals("")){
               Gson gson=new Gson();
               urlDataList=gson.fromJson(json,new TypeToken<ArrayList<Data>>(){}.getType());
          }
          json=sharedPreferences.getString(categoryNameListFileName,"");
          if(!json.equals("")){
               Gson gson=new Gson();
               categoryNameList=gson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
          }

     }
     public static void updateDataToSharedPreference(){
          SharedPreferences sharedPreferences=mainActivity.getSharedPreferences("Data",Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sharedPreferences.edit();
          Gson gson=new Gson();
          editor.putString(urlDataListFileName,gson.toJson(urlDataList));
          editor.putString(categoryNameListFileName,gson.toJson(categoryNameList));
          editor.commit();
     }
     public static void addDataChangeListener(DataChangeListener dataChangeListener){
          dataChangeListenerArrayList.add(dataChangeListener);
     }
     private static void onDataChanged(){
          for(DataChangeListener dataChangeListener:dataChangeListenerArrayList){
               dataChangeListener.onDataChange(urlDataList,categoryNameList);
          }
          updateDataToSharedPreference();
     }
     public static final int ALREADY_EXIST=0,ADD_SUCCESS=1;
     /**
      * 카테고리 추가시 성공하면 ADD_SUCCESS반환하지만 이미 존재하면 ALERADY_EXIST를 반환
      * @param categoryName
      * @return
      */
     public static int addNewCategory(String categoryName){
          if(categoryNameList.indexOf(categoryName)!=-1){
               return ALREADY_EXIST;
          }
          categoryNameList.add(categoryName);
          onDataChanged();
          return ADD_SUCCESS;
     }
     public static void removeCategory(String categoryName){
          categoryNameList.remove(categoryName);
          onDataChanged();
     }
     public static final int URL_NOT_CORRECT=3;
     /**
      * URL이 타당하지 않은 형식이면 URL_NOT_CORRECT반환하
      * 데이터 추가시 성공하면 ADD_SUCCESS반환하지만 urlname이나 urlAddress가 중복되면 ALERADY_EXIST를 반환
      * @param urlName
      * @param urlAddress
      * @param categoryName
      */
     public static int addNewURL(String urlName,String urlAddress,String categoryName){
          if(!URLUtil.isValidUrl(urlAddress)){
               return URL_NOT_CORRECT;
          }
          for(Data data:urlDataList){
               if(data.urlName.equals(urlName)||data.urlAddress.equals("urlAddress")){
                    return ALREADY_EXIST;
               }
          }
          Data data= null;
          try {
               data = new Data(urlName,urlAddress,categoryName,new HtmlDataDownloader().execute(urlAddress).get());
          } catch (ExecutionException e) {
               e.printStackTrace();
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          urlDataList.add(data);
          onDataChanged();
          return ADD_SUCCESS;
     }
     public static void removeURL(String urlName){
          for(Data data:urlDataList){
               if(data.urlName==urlName){
                    urlDataList.remove(data);
                    return;
               }
          }
          onDataChanged();
     }
     public static ArrayList<Data>  getURLinCategory(String categoryName){
          ArrayList<Data> arrayList=new ArrayList<Data>();
          for(Data data:urlDataList){
               if(data.categoryName.equals(categoryName)){
                    arrayList.add(data);
               }
          }
          return arrayList;
     }
}
class Data{
     String urlName,urlAddress,categoryName=null,htmlData=null;
     public Data(String urlName, String urlAddress, String categoryName,String htmlData) {
          this.urlName = urlName;
          this.urlAddress = urlAddress;
          this.categoryName = categoryName;
          this.htmlData=htmlData;
     }
}
class HtmlDataDownloader extends AsyncTask<String,Void,String> {
     public static String text=null;
     @Override
     protected String doInBackground(String... strings) {
          try {

               Document doc = Jsoup.connect(strings[0]).get();
               Elements contents = doc.getElementsByTag("table");
               text = contents.html();


          } catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.

               e.printStackTrace();

          }
          return text;
     }
}