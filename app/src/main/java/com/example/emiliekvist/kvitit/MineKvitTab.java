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
        prepareListData();
        expListView.setAdapter(new KvitItExpandableListAdapter(getContext(),listDataHeader, listDataChild));


        return rootView;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Smykker");
        listDataHeader.add("Sport og Fritid");
        listDataHeader.add("Spil");

        // Adding child data
        List<String> smykker = new ArrayList<String>();
        smykker.add("The Shawshank Redemption");
        smykker.add("The Godfather");


        List<String> sportFritid = new ArrayList<String>();
        sportFritid.add("Aerobics");
        sportFritid.add("Hest");


        List<String> tasker = new ArrayList<String>();
        tasker.add("East Pack");
        tasker.add("Mulberry");


        listDataChild.put(listDataHeader.get(0), smykker); // Header, Child data
        listDataChild.put(listDataHeader.get(1), sportFritid);
        listDataChild.put(listDataHeader.get(2), tasker);
    }

}
