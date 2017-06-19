package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by EmilieKvist on 16-06-2017.
 */

public class ShowPicActivity extends Activity {

    Button delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_pic);

        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<Kvittering> kvitteringer = realm.where(Kvittering.class).findAll();

        Bundle extras = getIntent().getExtras();

        final String path = extras.get("path").toString();

        ImageView picture = (ImageView) findViewById(R.id.vis_billede);

        final File myIm = new File(path);
        Bitmap myBitmap;

        if (myIm.exists()) {
            myBitmap = BitmapFactory.decodeFile(myIm.getAbsolutePath());
        } else {
            myBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.blank_bitmap);
        }

        picture.setImageBitmap(myBitmap);

        // deletes files from realm
        delete = (Button) findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                myIm.delete();
                                int index = 0;

                                for (Kvittering k : kvitteringer) {
                                    File temp = new File(k.photoPath);
                                    if (!temp.exists()) {
                                        realm.beginTransaction();
                                        kvitteringer.deleteFromRealm(index);
                                        realm.commitTransaction();
                                    }
                                    index++;
                                }
                                finish();

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ShowPicActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


    }
}
