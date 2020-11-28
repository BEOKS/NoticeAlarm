package com.notice.noticealarm;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

import static com.notice.noticealarm.URLData.urlDataList;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(URLData.activity, "공지사항 채널")
                .setSmallIcon(R.drawable.app_icon)
                .setContentText("공지사항 알림이 실행중입니다")
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(URLData.activity);
        startForeground('9', builder.build());

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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}