package com.example.emiliekvist.kvitit;

/**
 * Created by katrinethoft on 13/06/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MineKvitTab extends Fragment {
    private ExpandableListView expListView;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

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

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        expListView.setAdapter(new KvitItExpandableListAdapter(getContext()));


        return rootView;
    }

}
