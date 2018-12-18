package com.dldiaz.proyecto.notificationappgrupo08;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class NotificationActivity extends AppCompatActivity {
    private RadioGroup mOptionsGroup;
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mOptionsGroup = (RadioGroup) findViewById(R.id.options_group);
    }
    public void onPostClick(View v) {
        final int noteId = mOptionsGroup.getCheckedRadioButtonId();
        final Notification note;
        switch (noteId) {
            case R.id.option_basic:
            case R.id.option_bigtext:
            case R.id.option_bigpicture:
            case R.id.option_inbox:
                note = buildStyledNotification(noteId);
                break;
            case R.id.option_private:
            case R.id.option_secret:
            case R.id.option_headsup:
                note = buildSecuredNotification(noteId);
                break;
            default:
                throw new IllegalArgumentException("Unknown Type");
        }

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(noteId, note);
    }

    private Notification buildStyledNotification(int type) {


        Intent launchIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, launchIntent, 0);

        // Create notification with the time it was fired
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                NotificationActivity.this,NOTIFICATION_CHANNEL_ID);


        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Something Happened")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Hemos terminado!")
                .setContentText("Click A qui!")
                .setContentIntent(contentIntent);

        switch (type) {
            case R.id.option_basic:
                //Return the simple notification
                return builder.build();
            case R.id.option_bigtext:
                //Include two actions
                builder.addAction(android.R.drawable.ic_menu_call,
                        "Call", contentIntent);
                builder.addAction(android.R.drawable.ic_menu_recent_history,
                        "History", contentIntent);
                //Use the BigTextStyle when expanded
                NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle(builder);
                textStyle.bigText("Aquí hay un texto adicional que se mostrará cuando la notificación este "
                        +"en modo expandido. Puedo incluir mucho más contenido en esta vista!");

                return textStyle.build();
            case R.id.option_bigpicture:
                //Add one additional action
                builder.addAction(android.R.drawable.ic_menu_compass,
                        "View Location", contentIntent);
                //Use the BigPictureStyle when expanded
                NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle(builder);
                pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.dog));

                return pictureStyle.build();
            case R.id.option_inbox:
                //Use the InboxStyle when expanded
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(builder);
                inboxStyle.setSummaryText("4 Nuevas Notificaciones");
                inboxStyle.addLine("Hacer la cena");
                inboxStyle.addLine("LLamar a mama");
                inboxStyle.addLine("Dar de comer a mi perro");
                inboxStyle.addLine("Deber de Optativa");

                return inboxStyle.build();
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    //These properties can be overridden by the user's notification settings
    private Notification buildSecuredNotification(int type) {
        Intent launchIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);

        //Construct the base notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Saldo adeudado actualizada")
                .setContentText("EL saldo adeudado total de su cuenta es -$250.00")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("EL saldo pendiente de su cuenta es -$250.00; acerquese a cancelar "
                                + "hasta la semana entrante!"))
                .setContentIntent(contentIntent);

        switch (type) {
            case R.id.option_private:
                //Provide a unique version for secured lockscreens
                Notification publicNote = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificacion de su cuenta")
                        .setContentText("Ha llegado un aviso importane.")
                        .setContentIntent(contentIntent)
                        .build();

                return builder.setPublicVersion(publicNote)
                        .build();
            case R.id.option_secret:
                //Hide the notification from a secured lockscreen completely
                return builder.setVisibility(Notification.VISIBILITY_SECRET)
                        .build();
            case R.id.option_headsup:
                //Show a heads-up notification when posted
                return builder.setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .build();
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
    }
}
