package com.example.emiliekvist.kvitit;

/**
 * Created by katrinethoft on 13/06/2017.
 */

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MineKvitTab extends Fragment {
    public MineKvitTab() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mine_kvit_tab, container, false);
        return rootView;
    }

}
