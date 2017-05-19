package ru.antsharapov.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends Activity {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComponentName receiver = new ComponentName(getApplicationContext(), BootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.setAction("ru.antsharapov.alarm.action");
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();

        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmMgr.setExactAndAllowWhileIdle(ALARM_TYPE, calendar.getTimeInMillis(), alarmIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            alarmMgr.setExact(ALARM_TYPE, 60000, alarmIntent);
        else
            alarmMgr.set(ALARM_TYPE, calendar.getTimeInMillis(), alarmIntent);

    }
}
