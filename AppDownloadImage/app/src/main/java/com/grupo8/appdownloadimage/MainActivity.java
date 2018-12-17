package com.grupo8.appdownloadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    String imagePath = "https://www.almaceneslaganga.com/img/fotosHome/ac33/MXSN4216BTQ.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.image_view);
        //imageDownload image = new imageDownload(this, iv);
        //image.execute(imagePath);
    }

    protected void Enviar(View view){
        imageDownload image = new imageDownload(this, iv);
        image.execute(imagePath);
    }

    class imageDownload extends AsyncTask<String, Integer, Bitmap> {
        Context context;
        ImageView imageView;
        Bitmap bitmap;
        InputStream in = null;
        int responseCode = -1;
        //constructor.
        public imageDownload(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }
        @Override
        protected void onPreExecute() {


        }
        @Override
        protected Bitmap doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    in = httpURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap data) {
            imageView.setImageBitmap(data);
        }
    }
}
