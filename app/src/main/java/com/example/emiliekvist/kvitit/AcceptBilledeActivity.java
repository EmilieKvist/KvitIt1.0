package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by EmilieKvist on 14-06-2017.
 */

public class AcceptBilledeActivity extends Activity {

    Button accepter;
    Button afvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepter_billede);

        ImageView image = (ImageView) findViewById(R.id.billede_view);

        /*
        File imgFile = new  File("");

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        } else {
            Log.i("AccB", "no pic");
        }

        // Image url
        //String image_url = "https://www.google.dk/url?sa=i&rct=j&q=&esrc=s&source=imgres&cd=&ved=0ahUKEwikx-7ox73UAhXIApoKHVT4DOcQjRwIBw&url=http%3A%2F%2Fwww.businessinsider.com%2Fcelebrities-who-support-trump-2016-10&psig=AFQjCNHmnfqubBA4Au9ng53mmXyUBzhZLQ&ust=1497537436028057";
        */

        accepter = (Button) findViewById(R.id.acceptér_button);
        accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tilføjIntent = new Intent(AcceptBilledeActivity.this, AddReceiptActivity.class);
                startActivity(tilføjIntent);
            }
        });

        afvis = (Button) findViewById(R.id.afvis_button);
        afvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
