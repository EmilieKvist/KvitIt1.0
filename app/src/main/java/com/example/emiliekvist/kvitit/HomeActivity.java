package com.example.emiliekvist.kvitit;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


public class HomeActivity extends AppCompatActivity {
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getActionBar().setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        // Create a new Tab named "Mine Kvitteringer"
        TabLayout.Tab mineKvitTab = tabLayout.newTab();
        mineKvitTab.setText("Mine kvitteringer"); // set the Text for the first Tab

        // mine kvit tab
        tabLayout.addTab(mineKvitTab); // add  the tab at in the TabLayout

        // Create a new Tab named "Kategorier"
        TabLayout.Tab kategoriTab = tabLayout.newTab();
        kategoriTab.setText("Kategorier"); // set the Text for the second Tab

        tabLayout.addTab(kategoriTab); // add  the tab  in the TabLayout




// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MineKvitTab();
                        break;
                    case 1:
                        fragment = new KategoriTab();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }





}