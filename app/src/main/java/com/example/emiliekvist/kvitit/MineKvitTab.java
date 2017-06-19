package com.example.emiliekvist.kvitit;

/**
 * Created by katrinethoft on 13/06/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MineKvitTab extends Fragment {
    private KvitItExpandableListAdapter kvitItExpandableListAdapter;
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
        kvitItExpandableListAdapter = new KvitItExpandableListAdapter(getContext(), false);

        expListView.setAdapter(kvitItExpandableListAdapter);



        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ImageView child = (ImageView) v.findViewById(R.id.imgListItem);
                String path = child.getTag().toString();
                Intent showPicIntent = new Intent(getActivity(), ShowPicActivity.class);
                showPicIntent.putExtra("path", path);
                startActivity(showPicIntent);
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        kvitItExpandableListAdapter.notifyDataSetInvalidated();
    }

}
