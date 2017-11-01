package ru.antsharapov.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends Activity {
    AlarmManager mgr;
    PendingIntent pi;
    Calendar myCal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(context, AlarmReceiver.class);
                pi = PendingIntent.getBroadcast(context, 0, i, 0);
                myCal = Calendar.getInstance();
                myCal.setTimeInMillis(System.currentTimeMillis()+6000);
                mgr.set(AlarmManager.RTC_WAKEUP, myCal.getTimeInMillis(), pi);
                Log.i("myTag:", "alarm set for " + myCal.getTime());
                Toast.makeText(getApplicationContext(),"Alarm set for " + myCal.getTime(), Toast.LENGTH_LONG).show();

            }
        });

        Button s = (Button) findViewById(R.id.button1);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgr.cancel(pi);
            }
        });

            }
}
