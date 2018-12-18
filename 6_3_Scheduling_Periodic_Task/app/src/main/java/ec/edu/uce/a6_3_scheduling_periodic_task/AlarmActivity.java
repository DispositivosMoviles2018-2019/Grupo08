package ec.edu.uce.a6_3_scheduling_periodic_task;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import ec.edu.uce.a6_3_scheduling_periodic_task.servicio.AlarmService;

public class AlarmActivity extends Activity implements View.OnClickListener {

    private PendingIntent mAlarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_service_activity);
//Attach the listener to both buttons
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
//Create the launch sender
        Intent launchIntent = new Intent(this, AlarmService.class);
        mAlarmIntent = PendingIntent.getService(this, 0, launchIntent, 0);
    }

    @Override
    public void onClick(View v) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long interval = 5*1000; //5 seconds

        switch(v.getId()) {
            case R.id.start:
                Toast.makeText(this, "Scheduled", Toast.LENGTH_SHORT).show();
                manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                        SystemClock.elapsedRealtime()+interval,
                        interval,
                        mAlarmIntent);
                break;
            case R.id.stop:
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                manager.cancel(mAlarmIntent);
                break;
            default:
                break;
        }
    }
}
