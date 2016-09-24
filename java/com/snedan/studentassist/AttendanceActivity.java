package com.snedan.studentassist;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class AttendanceActivity extends ActionBarActivity
{
    private AttendanceAdapter disadpt;
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    private int pre=0, abs=0;
    private float avg=0;
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<Integer> present = new ArrayList<Integer>();
    private ArrayList<Integer> absent = new ArrayList<Integer>();
    private TextView top,average,details,message;
    private ListView subjList;
    private AlertDialog.Builder build;
    private String subj;
    Toolbar toolbar;
    ImageButton FAB;
    Button bb;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //font = Typeface.createFromAsset(getAssets(),"fonts/appFont.ttf");

        //  Transition enterT = new Explode();
        //getWindow().setEnterTransition(enterT);
        //    Transition returnT = new Slide();
        //   getWindow().setEnterTransition(returnT);
        // Transition exitT = new Explode();
        //getWindow().setEnterTransition(exitT);
        //Transition reenterT = new Slide();
        //  getWindow().setEnterTransition(reenterT);

        subj = getIntent().getExtras().getString("Subject");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(subj);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bb=(Button)findViewById(R.id.savebutton);
        top = (TextView)findViewById(R.id.textView);
        average = (TextView)findViewById(R.id.textView1);
        details = (TextView)findViewById(R.id.textView2);
        message = (TextView)findViewById(R.id.textView3);
        subjList = (ListView) findViewById(R.id.AttList);
        subj = getIntent().getExtras().getString("Subject");
        mHelper = new DbHelper(this);

        //average.setTypeface(font);
        //message.setTypeface(font);

        //add new record
        bb.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),
                        AddAttendanceActivity.class);
                i.putExtra("Subject", subj);
                startActivity(i);

            }
        });

        subjList.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {

                build = new AlertDialog.Builder(AttendanceActivity.this);
                build.setTitle("Delete " + date.get(arg2));
                build.setMessage("Delete This Entry?");
                build.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getApplicationContext(),
                                        date.get(arg2) + " " + " Entry deleted.", Toast.LENGTH_SHORT).show();

                                dataBase.delete(
                                        DbHelper.TABLE_NAME2,
                                        DbHelper.KEY_ID + "="
                                                + id.get(arg2), null);
                                disadpt.notifyDataSetChanged();
                                displayData();
                                dialog.cancel();
                            }
                        });

                build.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData()
    {
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME2+ " WHERE "+DbHelper.KEY_SUBJECT+" = '"+subj+"' ORDER BY "+DbHelper.KEY_DATE, null);
        id.clear();
        date.clear();
        subject.clear();
        present.clear();
        absent.clear();
        pre=0;abs=0;avg=0;
        int count=0;
        if (mCursor.moveToFirst()) {
            do {
                count++;
                id.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                date.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_DATE)));
                subject.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SUBJECT)));
                present.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_PRESENT)));
                absent.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ABSENT)));
                pre=pre+mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_PRESENT)) ;
                abs=abs+mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ABSENT)) ;
            } while (mCursor.moveToNext());
        }
        if(count > 0)
        {
            int i;
            float test=0;
            avg=100*((float)pre/(float)(pre+abs));
            if(avg==SubjectActivity.minPercent){
                average.setBackgroundColor(0xff4caf50);
                details.setBackgroundColor(0xff4caf50);
                message.setBackgroundColor(0xff4caf50);
                top.setBackgroundColor(0xff4caf50);
                average.setText(avg + "%");
                details.setText("No. of Lectures Attended ="+pre+"\n\nNo. of Lectures Bunked = "+abs);
                message.setText("Can't Bunk Any Lectures");
            }
            else if(avg>SubjectActivity.minPercent)
            {
                average.setBackgroundColor(0xff4caf50);
                message.setBackgroundColor(0xff4caf50);
                details.setBackgroundColor(0xff4caf50);
                top.setBackgroundColor(0xff4caf50);
                test=100*((float)(pre)/(float)(pre+abs));
                for(i=0;test>SubjectActivity.minPercent;i++)
                {
                    test=100*((float)(pre)/(float)(pre+abs+i));
                    if(test<SubjectActivity.minPercent) break;
                }
                --i;
                if(i==0)
                {
                    average.setText(avg + "%");
                    details.setText("No. of Lectures Attended ="+pre+"\n\nNo. of Lectures Bunked = "+abs);
                    message.setText("Can't Bunk Any Lectures");
                }
                else {
                    average.setText(avg + "%");
                    details.setText("No. of Lectures Attended ="+pre+"\n\nNo. of Lectures Bunked = "+abs);
                    message.setText("Can Safely Bunk "+i+" Lectures");
                }
            }
            else
            {
                average.setBackgroundColor(0xfff44336);
                message.setBackgroundColor(0xfff44336);
                details.setBackgroundColor(0xfff44336);
                top.setBackgroundColor(0xfff44336);
                for(i=0;test<SubjectActivity.minPercent;i++)
                {
                    test=100*((float)(pre+i)/(float)(pre+abs+i));
                    if (test > SubjectActivity.minPercent) break;
                }
                --i;
                average.setText(avg + "%");
                details.setText("No. of Lectures Attended ="+pre+"\n\nNo. of Lectures Bunked = "+abs);
                message.setText("Need To Attend "+i+" Lectures");
            }
        }
        if(count>0){
            disadpt = new AttendanceAdapter(AttendanceActivity.this,date, present, absent);
            subjList.setAdapter(disadpt);
        }
        mCursor.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}