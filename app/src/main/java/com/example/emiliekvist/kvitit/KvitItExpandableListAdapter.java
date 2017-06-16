package com.example.emiliekvist.kvitit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Thoke on 2017-06-15.
 */

public class KvitItExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    Realm realm;


    public KvitItExpandableListAdapter(Context context) {
        this._context = context;
        realm = Realm.getDefaultInstance();

    }


    @Override
    public int getGroupCount() {
        return realm.where(Kvittering.class).distinct("dato").sort("dato").size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return realm.where(Kvittering.class).equalTo("dato", realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato).findAll().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return realm.where(Kvittering.class).equalTo("dato", realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato).findAllSorted("dato").get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition).toString();

//        File myIm = new File(((Kvittering)getChild(groupPosition, childPosition)).photoPath);
//        Bitmap myBitmap = BitmapFactory.decodeFile(myIm.getAbsolutePath());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        //ImageView lblListItem = (ImageView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        //lblListItem.setImageBitmap(myBitmap);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
