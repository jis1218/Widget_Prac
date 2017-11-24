package com.project.widget_prac;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 정인섭 on 2017-11-24.
 */

public class WidgetProvider extends AppWidgetProvider {

    final static int INTERVAL = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                int appWidgetIds[] = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                for(int appWidgetId : appWidgetIds){
                    updateWidget(context, AppWidgetManager.getInstance(context), appWidgetId);
                }
                long next = System.currentTimeMillis() + INTERVAL;
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent targetintent = new Intent("AppWidgetManager.ACTION_APPWIDGET_UPDATE");
                PendingIntent pintent = PendingIntent.getBroadcast(context, 0, targetintent, 0);
                alarmManager.set(AlarmManager.RTC, next, pintent);
            }
        }
    }
    /*
    위젯 갱신 주기에 따라 호출된다
     */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds); //appWidgetIds - 바탕에 떠있는 widget 개수
        int count = appWidgetIds.length;
        for(int i=0; i<count; i++){

            updateWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetIds){
        //위젯 레이아웃 가져오기
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        remoteViews.setTextViewText(R.id.textView, sdf.format(now));

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    //

}
