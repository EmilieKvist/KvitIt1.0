package com.example.emiliekvist.kvitit;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

//


public class HomeActivity extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    //private ImageView image;
    static final int CAM_REQUEST = 1;
    private PendingIntent pendingIntent;
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    String mCurrentPhotoPath;

    Realm realm = Realm.getDefaultInstance();
    RealmResults<Kvittering> kvitteringer = realm.where(Kvittering.class).findAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // checks if the picture still exists and deletes kvittering if it doesn't
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


        //getActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);


        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);


        // Instantierer en ViewPager og en PagerAdapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //Adding onClickListener for add receipt button
        FloatingActionButton addReceipt = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HomeAct", "were in onclick");
                // Click action
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(HomeActivity.this,
                            "com.example.emiliekvist.fileprovider",
                            photoFile);
                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(camera_intent, CAM_REQUEST);
                }

            }
        });


        //Chekker om der er en kvittering der er udløbet:
        Date today = new Date();
        for (Kvittering k : kvitteringer) {
            if (today.after(k.udløbsDato)) {
                sendNotification(k);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.i("HomeAct", "storage: " + storageDir.toString());

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Log.i("HomeAct", "current photo path: " + mCurrentPhotoPath);
            Intent addRecIntent = new Intent(HomeActivity.this, AddReceiptActivity.class);
            addRecIntent.putExtra("current_photo", mCurrentPhotoPath);
            startActivity(addRecIntent);
        }


    }

    //Sender en notifikation om at en kvittering er udløbet
    private void sendNotification(Kvittering k){
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Kvittering udløbet")
                        .setContentText("Kvittering tilføjet den: " + k.dato + "er udløbet");

        Intent notificationIntent = new Intent(this,HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}