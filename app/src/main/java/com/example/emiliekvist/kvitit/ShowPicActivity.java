package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by EmilieKvist on 16-06-2017.
 */

public class ShowPicActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pic);

        Bundle extras = getIntent().getExtras();

        String path = extras.get("path").toString();

        ImageView picture = (ImageView) findViewById(R.id.vis_billede);

        File myIm = new File(path);
        Bitmap myBitmap;

        if (myIm.exists()) {
            myBitmap = BitmapFactory.decodeFile(myIm.getAbsolutePath());
        } else {
            myBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.blank_bitmap);
        }

        picture.setImageBitmap(myBitmap);

    }
}
