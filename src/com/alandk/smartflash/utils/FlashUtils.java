/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.smartflash.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.alandk.smartflash.timer.AlarmReceiver;

/**
 *
 * @author phucdk
 */
public class FlashUtils {

    public static void setResultNotification(Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int interval = getIntervalTimeByLevel(5);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        // Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    private static int getIntervalTimeByLevel(int level) {
        return level * 1000;
    }
}
