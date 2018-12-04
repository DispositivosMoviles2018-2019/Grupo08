package ec.edu.uce.a4_5appcamara;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;



public class Video_Activity extends Camara_Activity {

    private static final int REQUEST_VIDEO = 100;

    Button captureButton;
    TextView text;
    File destination;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);


        captureButton = (Button)findViewById(R.id.capture);
        captureButton.setOnClickListener(listener);

        text = (TextView)findViewById(R.id.file);

        destination = new File(Environment
                .getExternalStorageDirectory(), "myVideo.mpg");



    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO
                && resultCode == Activity.RESULT_OK) {
            String location = data.getData().toString();
            text.setText(location);
        }
    }

    private View.OnClickListener listener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent =
                                new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//Add (optional) extra to save video to our file
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(destination));
//Optional extra to set video quality
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        startActivityForResult(intent, REQUEST_VIDEO);
                    } catch (ActivityNotFoundException e) {
//Handle if no application exists
                    }
                }
            };


}
