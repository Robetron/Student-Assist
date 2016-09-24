package com.snedan.studentassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * adapter to populate listview with data
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class AttendanceAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> date;
    private ArrayList<String> subject;
    private ArrayList<Integer> present;
    private ArrayList<Integer> absent;



    public AttendanceAdapter(Context c, ArrayList<String> date, ArrayList<Integer> present, ArrayList<Integer> absent) {
        this.mContext = c;
        this.date = date;

        this.present = present;
        this.absent = absent;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return date.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell1, null);
            mHolder = new Holder();
            mHolder.txt_date = (TextView) child.findViewById(R.id.txt_date);
            mHolder.txt_present = (TextView) child.findViewById(R.id.txt_present);
            mHolder.txt_absent = (TextView) child.findViewById(R.id.txt_absent);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_date.setText(date.get(pos));
        mHolder.txt_present.setText(present.get(pos).toString());
        mHolder.txt_absent.setText(absent.get(pos).toString());
        return child;
    }

    public class Holder
    {
        TextView txt_date;
        TextView txt_present;
        TextView txt_absent;
    }
}
