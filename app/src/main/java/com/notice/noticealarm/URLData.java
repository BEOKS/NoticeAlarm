package com.notice.noticealarm;
//TODO 파이어 베이스와 연결 필


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

     public static Context mContext;

     interface DataChangeListener{
          public void onDataChange(ArrayList<Data> urlDataList,ArrayList<String> categoryNameList);
     }
     public static ArrayList<Data> urlDataList=new ArrayList<Data>();
     public static ArrayList<String> categoryNameList=new ArrayList<String>();
     private static ArrayList<DataChangeListener> dataChangeListenerArrayList=new ArrayList<DataChangeListener>();
     URLDeleteActivity urlDeleteActivity;

     private static String urlDataListFileName="urlDataList",categoryNameListFileName="categoryNameList";
     public static void init(MainActivity mainActivity){
          activity=mainActivity;
          URLData.getDataFromSharedPreference();
          for(int i=0;i<urlDataList.size();i++){
               final int index=i;
               FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlDataList.get(index).urlAddress)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         try {
                              urlDataList.get(index).htmlData=new HtmlDataDownloader().execute(urlDataList.get(index).urlAddress).get();
                              Long data=(Long) dataSnapshot.getValue();
                              if(data<0){
                                   //알림
                                   Intent intent = new Intent(URLData.activity, MainActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   PendingIntent pendingIntent = PendingIntent.getActivity(URLData.activity, 0, intent, 0);
                                   NotificationCompat.Builder builder = new NotificationCompat.Builder(URLData.activity, "공지사항 채널")
                                           .setSmallIcon(R.drawable.app_icon)
                                           .setContentTitle(urlDataList.get(index).urlName)
                                           .setContentText("새로운 공지사항이 등록되었습니다!!")
                                           .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                           .setContentIntent(pendingIntent)
                                           .setAutoCancel(true);
                                   NotificationManagerCompat notificationManager = NotificationManagerCompat.from(URLData.activity);
                                   notificationManager.notify('1', builder.build());
                              }
                              URLData.onDataChanged();
                         }
                         catch (ExecutionException e) {
                              e.printStackTrace();
                         } catch (InterruptedException e) {
                              e.printStackTrace();
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
               });
          }
     }
     public static MainActivity activity;
     public static void getDataFromSharedPreference(){
          SharedPreferences sharedPreferences= activity.getSharedPreferences("Data",Context.MODE_PRIVATE);
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
          SharedPreferences sharedPreferences= activity.getSharedPreferences("Data",Context.MODE_PRIVATE);
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
          getDataFromSharedPreference();
     }
     public static final int ALREADY_EXIST=0,ADD_SUCCESS=1;
     /**
      * 카테고리 추가시 성공하면 ADD_SUCCESS반환하지만 이미 존재하면 ALERADY_EXIST를 반환
      * @param categoryName
      * @return
      */
     public static int addNewCategory(String categoryName){
          if(categoryNameList.indexOf(categoryName)!=-1){
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         Toast.makeText(activity,"이미 존재하는 카테고리입니다.",Toast.LENGTH_SHORT).show();
                    }
               });
               return ALREADY_EXIST;
          }
          categoryNameList.add(categoryName);
          onDataChanged();
          return ADD_SUCCESS;
     }
     public static void removeCategory(String categoryName){
          categoryNameList.remove(categoryName);
          for(int i=0;i<urlDataList.size();i++){
               if(urlDataList.get(i).categoryName.equals(categoryName)){
                    urlDataList.remove(i);
               }
          }
          onDataChanged();
     }
     public static final int URL_NOT_CORRECT=3;
     /**
      * URL이 타당하지 않은 형식이면 URL_NOT_CORRECT반환
      * 파이어베이스에 해당 URL을 검색
      *   없으면 value를 0으로하고 key를 url을 변환한 스트링을 입력
      *   있으면 value++을 함
      * 데이터 추가시 성공하면 ADD_SUCCESS반환하지만 urlname이나 urlAddress가 중복되면 ALERADY_EXIST를 반환
      * @param urlName
      * @param urlAddress
      * @param categoryName
      */
     public static int addNewURL(final String urlName, final String urlAddress, String categoryName){
          if(!URLUtil.isValidUrl(urlAddress)){
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         Toast.makeText(activity,"올바른 주소를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
               });
               return URL_NOT_CORRECT;
          }
          for(Data data:urlDataList){
               if(data.urlName.equals(urlName)||data.urlAddress.equals(urlAddress)){
                    activity.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                              Toast.makeText(activity,"이미 존재하는 주소 또는 이름입니다.",Toast.LENGTH_SHORT).show();
                         }
                    });
                    return ALREADY_EXIST;
               }
          }
          FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                         FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).setValue((Long)dataSnapshot.getValue()+1);
                    }
                    else{
                         FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).setValue(1);
                    }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          });
          FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long data=(Long)dataSnapshot.getValue();
                    if(data<0){
                         //알림
                         Intent intent = new Intent(URLData.activity, MainActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         PendingIntent pendingIntent = PendingIntent.getActivity(URLData.activity, 0, intent, 0);
                         NotificationCompat.Builder builder = new NotificationCompat.Builder(URLData.activity, "공지사항 채널")
                                 .setSmallIcon(R.drawable.app_icon)
                                 .setContentTitle(urlName)
                                 .setContentText("새로운 공지사항이 등록되었습니다!")
                                 .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                 .setContentIntent(pendingIntent)
                                 .setAutoCancel(true);
                         NotificationManagerCompat notificationManager = NotificationManagerCompat.from(URLData.activity);
                         notificationManager.notify('1', builder.build());


                    }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          });
          Data data= null;
          try {
               String htmlData=new HtmlDataDownloader().execute(urlAddress).get();
               if(htmlData==null){
                    activity.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                              Toast.makeText(activity,"해당 주소에서 공지사항을 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                         }
                    });
                    return URL_NOT_CORRECT;
               }
               data = new Data(urlName,urlAddress,categoryName,htmlData);
          } catch (ExecutionException e) {
               e.printStackTrace();
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          urlDataList.add(data);
          onDataChanged();
          return ADD_SUCCESS;
     }

     /**
      * url을 내부 리스트에서 삭제함
      * 파이어베이스 데이터베이스에서 해당 URL에 대해 value--을 진행
      * @param urlName
      */
     public static void removeURL(String urlName){
          for(Data data:urlDataList){
               if(data.urlName==urlName){
                    urlDataList.remove(data);
                    final String urlAddress=data.urlAddress;
                    FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              if(dataSnapshot.exists()){
                                   FirebaseDatabase.getInstance().getReference(Data.parseURLtoDatabaseKey(urlAddress)).setValue((Long)dataSnapshot.getValue()-1);
                              }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                    });
                    break;
               }
          }
          onDataChanged();
     }
     public static ArrayList<Data> getURLinCategory(String categoryName) {
          ArrayList<Data> arrayList = new ArrayList<Data>();
          if(categoryName.equals("모든 공지사항")){
               for (Data data : urlDataList) {
                    arrayList.add(data);
               }
          }
          else{
               for (Data data : urlDataList) {
                    if (data.categoryName.equals(categoryName)) {
                         arrayList.add(data);
                    }
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

     public Data(String urlName) {
          this.urlName = urlName;
     }

     public static String parseURLtoDatabaseKey(String urlAddress){
          return urlAddress.replace('.','_');
     }
     public static String parseDatabaseKeyToURL(String urlAddress){
          return urlAddress.replace('_','.');
     }
     public String getUrlName()
     {
          return this.urlName;
     }

}
class HtmlDataDownloader extends AsyncTask<String,Void,String> {
     public static String text=null;
     @Override
     protected String doInBackground(String... strings) {
          try {

               Document doc = Jsoup.connect(strings[0]).get();
               Elements contents = doc.getElementsByTag("table");
               if(contents.size()==0){
                    text=null;
               }
               else{
                    text = contents.get(0).toString();
               }


          } catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
               URLData.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         Toast.makeText(URLData.activity,"연결할 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
               });
               e.printStackTrace();

          }
          return text;
     }
}