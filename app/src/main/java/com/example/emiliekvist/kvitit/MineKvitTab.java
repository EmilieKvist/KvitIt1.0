package com.example.emiliekvist.kvitit;

/**
 * Created by katrinethoft on 13/06/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public class MineKvitTab extends Fragment {
    private KvitItExpandableListAdapter kvitItExpandableListAdapter;
    private ExpandableListView expListView;

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

        //Laver et ExpandableListView og en ExpandableListAdapter
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        kvitItExpandableListAdapter = new KvitItExpandableListAdapter(getContext(), false);

        expListView.setAdapter(kvitItExpandableListAdapter);


        //Sætter billederne ind i ExpandableListView
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ImageView child = (ImageView) v.findViewById(R.id.imgListItem);
                String path = child.getTag().toString();
                Intent showPicIntent = new Intent(getActivity(), ShowPicActivity.class);
                showPicIntent.putExtra("path", path);
                Log.i("ExpL", "going to showPic");
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
