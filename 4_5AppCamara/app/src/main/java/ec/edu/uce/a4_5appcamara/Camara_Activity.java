package ec.edu.uce.a4_5appcamara;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;

public class Camara_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 100;

    Button captureButton;
    ImageView imageView;
    File destination;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camara_activity);


        captureButton = (Button)findViewById(R.id.capture);//
        captureButton.setOnClickListener(listener);

        imageView = (ImageView)findViewById(R.id.image);//

        destination = new File(Environment.getExternalStorageDirectory(),"image.jpg");// memoria interna
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            //Bitmap userImage = (Bitmap)data.getExtras().get("data");
            try {
                FileInputStream in = new FileInputStream(destination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10; //Downsample 10x
                Bitmap userImage = BitmapFactory.decodeStream(in, null, options);
                imageView.setImageBitmap(userImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Add extra to save full-image somewhere
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
            Toast.makeText(Camara_Activity.this, Uri.fromFile(destination).toString(), Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, REQUEST_IMAGE);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.tomarfoto:
                Intent intent = new Intent(this,Camara_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            case R.id.tomarvideo:
                Intent videointent = new Intent(this,Video_Activity.class);
                videointent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(videointent);
                return true;
            case R.id.salir:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}