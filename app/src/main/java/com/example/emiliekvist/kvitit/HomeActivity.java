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
import java.text.DateFormat;
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

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // checks if the picture still exists and deletes kvittering if it doesn't
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Kvittering> kvitteringer = realm.where(Kvittering.class).findAll();
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

        /*
        File myIm = new File(kvitteringer.get(0).photoPath);
        ImageView image;
        image = (ImageView) findViewById(R.id.image_view);
        Bitmap myBitmap = BitmapFactory.decodeFile(myIm.getAbsolutePath());
        image.setImageBitmap(myBitmap);
        */


        //getActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);


        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        // Create a new Tab named "Mine Kvitteringer"
//        TabLayout.Tab mineKvitTab = tabLayout.newTab();
//        mineKvitTab.setText("Mine kvitteringer"); // set the Text for the first Tab

        // mine kvit tab
//        tabLayout.addTab(mineKvitTab); // add  the tab at in the TabLayout
//
//        // Create a new Tab named "Kategorier"
//        TabLayout.Tab kategoriTab = tabLayout.newTab();
//        kategoriTab.setText("Kategorier"); // set the Text for the second Tab
//
//        tabLayout.addTab(kategoriTab); // add  the tab  in the TabLayout

        // Instantierer en ViewPager og en PagerAdapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        // perform setOnTabSelectedListener event on TabLayout
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                // get the current selected tab's position and replace the fragment accordingly
//                 Fragment fragment = null;
//                switch (tab.getPosition()) {
//                    case 0:
//                        fragment = new MineKvitTab();
//                        break;
//                    case 1:
//                        fragment = new KategoriTab();
//                        break;
//                    default:
//
//                }
//
//
//
//
//               // FragmentManager fm = getSupportFragmentManager();
//                FragmentManager fm = getFragmentManager();
//
//               // FragmentTransaction ft = fm.beginTransaction();
//              //  ft.replace(R.id.simpleFrameLayout, fragment);
//            //    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//              //  ft.commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


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

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date today = new Date();
        //Chekker om der er en kvittering der er udløbet:
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