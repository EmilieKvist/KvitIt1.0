package com.example.emiliekvist.kvitit;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by EmilieKvist on 19-06-2017.
 */

public class SearchActivity extends Activity {

    TextView searchTxt;
    String search;
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Kvittering> kvitteringer = realm.where(Kvittering.class).findAll();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchTxt = (TextView) findViewById(R.id.search_txt);
        search  = getIntent().getStringExtra("search");
        searchTxt.setText(search);

        List<Kvittering> soegeKvitteringer = new ArrayList<Kvittering>();

        for (Kvittering k : kvitteringer) {
            String tags = k.tags.toLowerCase();
            if (tags.contains(search.toLowerCase())) {
                soegeKvitteringer.add(k);
            }
        }

        if (!soegeKvitteringer.isEmpty()) {
            Log.i("ExpL", "we have receipts");
        } else {
            TextView noPics = (TextView) findViewById(R.id.no_kvit_txt);
            noPics.setText("ingen kvitteringer matcher din søgning");
        }


    }
}
