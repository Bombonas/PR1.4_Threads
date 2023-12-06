package com.example.pr14_threads;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Button b = (Button) findViewById(R.id.button);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        TextView txt = (TextView) findViewById(R.id.textView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        String urldisplay = "https://randomfox.ca/images/122.jpg";
                        try {
                            InputStream in = new java.net.URL(urldisplay).openStream();
                            bitmap = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }

                        Handler handler = new Handler(Looper.getMainLooper());
                        Bitmap finalBitmap = bitmap;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                txt.setText(urldisplay);
                                img.setImageBitmap(finalBitmap);
                            }
                        });
                    }
                });
            }
        });
    }
}