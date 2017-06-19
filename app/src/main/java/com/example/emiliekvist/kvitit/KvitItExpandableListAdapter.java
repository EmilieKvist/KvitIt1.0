package com.example.emiliekvist.kvitit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.Realm;

/**
 * Created by Thoke on 2017-06-15.
 */

public class KvitItExpandableListAdapter extends BaseExpandableListAdapter {

    private final boolean isTag;
    private String[] tagsArray;
    private Context _context;
    Realm realm;
    DateFormat DF;


    public KvitItExpandableListAdapter(Context context, boolean isTag) {
        DF = new SimpleDateFormat("dd-MM-yyyy");
        this._context = context;
        this.isTag = isTag;
        realm = Realm.getDefaultInstance();
        tagsArray = _context.getResources().getStringArray(R.array.tags_array);

    }

    @Override
    public int getGroupCount() {
        // finds number of distinct dates
        if (!isTag) {
            return realm.where(Kvittering.class).distinct("dato").sort("dato").size();
        }
        else {
            return tagsArray.length;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // finds number of receipts in each date (how many receipts in each group
        if (!isTag) {
            return realm.where(Kvittering.class).equalTo("dato", realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato).findAll().size();
        }
        else {
            return realm.where(Kvittering.class).contains("tags", tagsArray[groupPosition]).findAll().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        // finds all distinct dates
        if (!isTag) {
            return realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato;
        }
        else {
            return tagsArray[groupPosition];
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // finds all receipts and sorts them in dates
        if (!isTag) {
            return realm.where(Kvittering.class).equalTo("dato", realm.where(Kvittering.class).distinct("dato").sort("dato").get(groupPosition).dato).findAllSorted("dato").get(childPosition);
        }
        else {
            return realm.where(Kvittering.class).contains("tags", tagsArray[groupPosition]).findAll().get(childPosition);
        }
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

    // Method that shows all distinct dates as groups
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle;
        if (!isTag) {
            headerTitle = DF.format(getGroup(groupPosition));
        }
        else {
            headerTitle = (String) getGroup(groupPosition);
        }
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        // sets up the view for the dates
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle + "  ( "+ getChildrenCount(groupPosition) + " )");

        return convertView;
    }

    // method that adds children to groups
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //final String childText = (String) getChild(groupPosition, childPosition).toString();

        // get tags as text
        final String childText = "" + ((Kvittering) getChild(groupPosition, childPosition)).tags;

        // save image as bitmap
        Log.i("ExpL", ((Kvittering)getChild(groupPosition, childPosition)).photoPath);

        String path = ((Kvittering)getChild(groupPosition, childPosition)).photoPath;
        File myIm = new File(path);
        Bitmap myBitmap;

        if (myIm.exists()) {
            myBitmap = BitmapFactory.decodeFile(myIm.getAbsolutePath());
        } else {
            myBitmap = BitmapFactory.decodeResource(_context.getResources(), R.drawable.blank_bitmap);
        }

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgListItem);
        txtListChild.setText(childText);
        img.setImageBitmap(myBitmap);
        img.setTag(path);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
