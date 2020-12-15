package com.notice.noticealarm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class AlarmButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {
    MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AlarmButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainActivity=(MainActivity)context;
        Drawable noti_on,noti_off;
        noti_on=mainActivity.getDrawable(R.drawable.ic_baseline_notifications_active_24);
        noti_off=mainActivity.getDrawable(R.drawable.ic_baseline_notifications_off_24);
        if(isMyServiceRunning(NotificationService.class)){
            setBackground(noti_on);
        }
        else{
            setBackground(noti_off);
        }
        setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        Drawable noti_on,noti_off;
        noti_on=mainActivity.getDrawable(R.drawable.ic_baseline_notifications_active_24);
        noti_off=mainActivity.getDrawable(R.drawable.ic_baseline_notifications_off_24);
        Intent intent=new Intent(mainActivity,NotificationService.class);
        if(isMyServiceRunning(NotificationService.class)){
            mainActivity.stopService(intent);
            setBackground(noti_off);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mainActivity.startForegroundService(intent);
            }
            else{
                mainActivity.startService(intent);
            }
            setBackground(noti_on);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mainActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
