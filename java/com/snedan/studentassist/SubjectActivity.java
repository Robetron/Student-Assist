package com.snedan.studentassist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SubjectActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {
    public static int newPercent, minPercent = 75;
    private static ArrayList<String> Timetable = new ArrayList<String>();
    int totalpresent, totalabsent;
    float totalavg;
    private CardArrayAdapter cardAdapter;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30, t31, t32, t33, t34, t35, t36, t37, t38, t39, t40, t41, t42, t43, t44, t45, t46, t47, t48, t49, t50, t51, t52, t53, t54, t55, t56;
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<Integer> present = new ArrayList<Integer>();
    private ArrayList<Integer> absent = new ArrayList<Integer>();

    private ArrayList<String> userId = new ArrayList<String>();
    private ArrayList<String> user_fName = new ArrayList<String>();
    private ArrayList<String> avgPresent = new ArrayList<String>();
    private ArrayList<String> avgAbsent = new ArrayList<String>();

    private Toolbar toolbar, bottombar,tool;
    private TextView toolbarTitle, bb, bavg,toolt;
    private ListView userList;
    private AlertDialog.Builder build;
    private ImageButton FAB, done;
    ProgressBar pg;
    TextView average;
    Button addsubj;
    SeekBar seek1, seek2, seek3, seek4;
    TextView fifth, sixth, seventh, eighth;
    double five, six, seven, eight, total;
    Boolean eng = true;
    Switch myswitch = null;
    final static int check = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();

        TabHost.TabSpec spec = th.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Subjects");
        th.setBackgroundColor(0xff000000);
        th.addTab(spec);

        spec = th.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Timetable");
        th.setBackgroundColor(0xff000000);
        th.addTab(spec);

        spec = th.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Degree Percent");
        th.setBackgroundColor(0xff000000);
        th.addTab(spec);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.miniic);
        //toolbar.setBackgroundColor(0xff000000);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        addsubj =(Button) findViewById(R.id.savebutton);
        userList = (ListView) findViewById(R.id.List);
        CharSequence a[]={"9,10,11,12,13,14,16,17,18,19,20,21,23,24,25,26,27,28,30,31,32,33,34,35,37,38,39,40,41,42,44,45,46,47,48,49,51,52,53,54,55,56"};
        ArrayList<String> index = new ArrayList<String>();
        bavg =(TextView)findViewById(R.id.avgdisplay);
        done = (ImageButton) findViewById(R.id.always);

        mHelper = new DbHelper(this);
        dataBase = mHelper.getWritableDatabase();
        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<Integer> per = new ArrayList<Integer>();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME4, null);
        id.clear();
        per.clear();
        if (mCursor.moveToFirst()) {
            do {

                id.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                per.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_PERCENT)));
            } while (mCursor.moveToNext());
        }
        minPercent=per.get(0);mCursor.close();
        //add new record
        addsubj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);
                builder.setTitle("New Subject");
                builder.setMessage("Enter New Subject Name");
                final EditText input = new EditText(SubjectActivity.this);
                builder.setView(input);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = input.getEditableText().toString();

                        if (str.length() > 0) {
                            dataBase = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            values.put(DbHelper.KEY_SUBJECT, str);

                            System.out.println("");
                            //insert data into database
                            dataBase.insert(DbHelper.TABLE_NAME1, null, values);
                            //close database
                            dataBase.close();
                            displayData();

                        } else {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SubjectActivity.this);
                            alertBuilder.setTitle("Invalid Data");
                            alertBuilder.setMessage("Please Enter a Subject Name");
                            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertBuilder.create().show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a = builder.create();
                a.show();
            }
        });

        //click to update data
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent i = new Intent(getApplicationContext(),
                        AttendanceActivity.class);
                i.putExtra("Subject", user_fName.get(arg2));
                startActivity(i);

            }
        });

        //long click to delete data
        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {
                build = new AlertDialog.Builder(SubjectActivity.this);
                build.setTitle("Delete " + user_fName.get(arg2)+ "?");
                build.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(getApplicationContext(), user_fName.get(arg2) + " deleted.", Toast.LENGTH_SHORT).show();
                                dataBase.delete(
                                        DbHelper.TABLE_NAME1,
                                        DbHelper.KEY_ID + "="
                                                + userId.get(arg2), null);
                                dataBase.execSQL("DELETE FROM " + DbHelper.TABLE_NAME2 + " WHERE sname= '" + user_fName.get(arg2) + "'");
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
        //CODE FOR TAB2
        dataBase = mHelper.getWritableDatabase();
        mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME1+ " ORDER BY sname", null);

        userId.clear();
        user_fName.clear();
        if (mCursor.moveToFirst()) {
            do {
                userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                user_fName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SUBJECT)));
            } while (mCursor.moveToNext());
        }

        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);
        t5 = (TextView) findViewById(R.id.text5);
        t6 = (TextView) findViewById(R.id.text6);
        t7 = (TextView) findViewById(R.id.text7);
        t8 = (TextView) findViewById(R.id.text8);
        t9 = (TextView) findViewById(R.id.text9);
        t10 = (TextView) findViewById(R.id.text10);
        t11 = (TextView) findViewById(R.id.text11);
        t12 = (TextView) findViewById(R.id.text12);
        t13 = (TextView) findViewById(R.id.text13);
        t14 = (TextView) findViewById(R.id.text14);
        t15 = (TextView) findViewById(R.id.text15);
        t16 = (TextView) findViewById(R.id.text16);
        t17 = (TextView) findViewById(R.id.text17);
        t18 = (TextView) findViewById(R.id.text18);
        t19 = (TextView) findViewById(R.id.text19);
        t20 = (TextView) findViewById(R.id.text20);
        t21 = (TextView) findViewById(R.id.text21);
        t22 = (TextView) findViewById(R.id.text22);
        t23 = (TextView) findViewById(R.id.text23);
        t24 = (TextView) findViewById(R.id.text24);
        t25 = (TextView) findViewById(R.id.text25);
        t26 = (TextView) findViewById(R.id.text26);
        t27 = (TextView) findViewById(R.id.text27);
        t28 = (TextView) findViewById(R.id.text28);
        t29 = (TextView) findViewById(R.id.text29);
        t30 = (TextView) findViewById(R.id.text30);
        t31 = (TextView) findViewById(R.id.text31);
        t32 = (TextView) findViewById(R.id.text32);
        t33 = (TextView) findViewById(R.id.text33);
        t34 = (TextView) findViewById(R.id.text34);
        t35 = (TextView) findViewById(R.id.text35);
        t36 = (TextView) findViewById(R.id.text36);
        t37 = (TextView) findViewById(R.id.text37);
        t38 = (TextView) findViewById(R.id.text38);
        t39 = (TextView) findViewById(R.id.text39);
        t40 = (TextView) findViewById(R.id.text40);
        t41 = (TextView) findViewById(R.id.text41);
        t42 = (TextView) findViewById(R.id.text42);
        t43 = (TextView) findViewById(R.id.text43);
        t44 = (TextView) findViewById(R.id.text44);
        t45 = (TextView) findViewById(R.id.text45);
        t46 = (TextView) findViewById(R.id.text46);
        t47 = (TextView) findViewById(R.id.text47);
        t48 = (TextView) findViewById(R.id.text48);
        t49 = (TextView) findViewById(R.id.text49);
        t50 = (TextView) findViewById(R.id.text50);
        t51 = (TextView) findViewById(R.id.text51);
        t52 = (TextView) findViewById(R.id.text52);
        t53 = (TextView) findViewById(R.id.text53);
        t54 = (TextView) findViewById(R.id.text54);
        t55 = (TextView) findViewById(R.id.text55);
        t56 = (TextView) findViewById(R.id.text56);

        displayTable();

        t1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t1, 1);
                return true;
            }
        });

        t9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t9, 9);
                return true;
            }
        });
        t10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t10, 10);
                return true;
            }
        });
        t11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t11, 11);
                return true;
            }
        });
        t12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t12, 12);
                return true;
            }
        });
        t13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t13, 13);
                return true;
            }
        });
        t14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t14, 14);
                return true;
            }
        });
        t16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t16, 16);
                return true;
            }
        });
        t17.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t17, 17);
                return true;
            }
        });
        t18.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t18, 18);
                return true;
            }
        });
        t19.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t19, 19);
                return true;
            }
        });
        t20.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t20, 20);
                return true;
            }
        });
        t21.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t21, 21);
                return true;
            }
        });
        t23.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t23, 23);
                return true;
            }
        });
        t24.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t24, 24);
                return true;
            }
        });
        t25.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t25, 25);
                return true;
            }
        });
        t26.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t26, 26);
                return true;
            }
        });
        t27.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t27, 27);
                return true;
            }
        });
        t28.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t28, 28);
                return true;
            }
        });
        t30.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t30, 30);
                return true;
            }
        });
        t31.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t31, 31);
                return true;
            }
        });
        t32.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t32, 32);
                return true;
            }
        });
        t33.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t33, 33);
                return true;
            }
        });
        t34.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t34, 34);
                return true;
            }
        });
        t35.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t35, 35);
                return true;
            }
        });
        t37.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t37, 37);
                return true;
            }
        });
        t38.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t38, 38);
                return true;
            }
        });
        t39.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t39, 39);
                return true;
            }
        });
        t40.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t40, 40);
                return true;
            }
        });
        t41.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t41, 41);
                return true;
            }
        });
        t42.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t42, 42);
                return true;
            }
        });
        t44.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t44, 44);
                return true;
            }
        });
        t45.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t45, 45);
                return true;
            }
        });
        t46.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t46, 46);
                return true;
            }
        });
        t47.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t47, 47);
                return true;
            }
        });
        t48.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t48, 48);
                return true;
            }
        });
        t49.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t49, 49);
                return true;
            }
        });
        t51.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t51, 51);
                return true;
            }
        });
        t52.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t52, 52);
                return true;
            }
        });
        t53.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t53, 53);
                return true;
            }
        });
        t54.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t54, 54);
                return true;
            }
        });
        t56.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t56, 56);
                return true;
            }
        });
        t55.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changesubj(v, t55, 55);
                return true;
            }
        });

        //CODE FOR TAB3
        myswitch = (Switch) findViewById(R.id.switch1);
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eng=false;
                    myswitch.setText("ENGINEERING");
                    //	seek3.setProgressBackgroundTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	seek3.setProgressTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	seek3.setThumbTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	seventh.setTextColor(0xcfd8dc);
                    //	seek4.setProgressBackgroundTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	seek4.setProgressTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	seek4.setThumbTintList(ColorStateList.valueOf(0xcfd8dc));
                    //	eighth.setTextColor(0xcfd8dc);
                } else {
                    eng=true;
                    myswitch.setText("BSC / BCOM / BA");
                    //	seek3.setProgressBackgroundTintList(ColorStateList.valueOf(0xff000000));
                    //	seek3.setProgressTintList(ColorStateList.valueOf(0xff1e88e4));
                    //	seek3.setThumbTintList(ColorStateList.valueOf(0xfff09f00));
                    //	seventh.setTextColor(0xff000000);
                    //	seek4.setProgressBackgroundTintList(ColorStateList.valueOf(0xff000000));
                    //	seek4.setProgressTintList(ColorStateList.valueOf(0xff1e88e4));
                    //	seek4.setThumbTintList(ColorStateList.valueOf(0xfff09f00));
                    //	eighth.setTextColor(0xff000000);
                }
            }
        });

        if (eng == true) myswitch.setText("BSC / BCOM / BA");
        else myswitch.setText("ENGINEERING");
        average = (TextView) findViewById(R.id.avg);
        pg = (ProgressBar) findViewById(R.id.progressBar);
        fifth = (TextView) findViewById(R.id.dis1);
        seek1 = (SeekBar) findViewById(R.id.seekBar1);
        seek1.setOnSeekBarChangeListener(this);

        five = seek1.getProgress();
        five = five + 401;
        fifth.setText("Sem V: " + five / 10 + "%");

        sixth = (TextView) findViewById(R.id.dis2);
        seek2 = (SeekBar) findViewById(R.id.seekBar2);
        seek2.setOnSeekBarChangeListener(this);
        six = seek2.getProgress();
        six = six + 401;
        sixth.setText("Sem VI: " + six / 10 + "%");

        seventh = (TextView) findViewById(R.id.dis3);
        seek3 = (SeekBar) findViewById(R.id.seekBar3);
        seek3.setOnSeekBarChangeListener(this);
        seven = seek3.getProgress();
        seven = seven + 401;
        seventh.setText("Sem VII: " + seven / 10 + "%");

        eighth = (TextView) findViewById(R.id.dis4);
        seek4 = (SeekBar) findViewById(R.id.seekBar4);
        seek4.setOnSeekBarChangeListener(this);
        eight = seek4.getProgress();
        eight = eight + 401;
        eighth.setText("Sem VIII: " + eight / 10 + "%");

        if (eng == true)total = (0.33 * (five + six) + 0.67 * (seven + eight)) / 2;
        else total = (five + six) / 2;
        average.setText(((int) total / 10) + "%");
        pg.setProgress((int) total / 10);



    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        ArrayList<String> date = new ArrayList<String>();

        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME1+ " ORDER BY ", null);
        userId.clear();
        user_fName.clear();
        cardAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        if (mCursor.moveToFirst()) {
            do {

                userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                user_fName.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SUBJECT)));
            } while (mCursor.moveToNext());
        }
        userList.setAdapter(cardAdapter);
        mCursor.close();
        int pre, abs, count, avg;
        totalabsent = 0;
        totalpresent = 0;
        totalavg = 0;
        for (int i = 0; i < userId.size(); i++) {
            mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME2 + " WHERE " + DbHelper.KEY_SUBJECT + " = '" + user_fName.get(i) + "' ORDER BY " + DbHelper.KEY_DATE+ " DESC", null);
            id.clear();
            date.clear();
            subject.clear();
            present.clear();
            absent.clear();
            pre = 0;
            abs = 0;
            count = 0;
            if (mCursor.moveToFirst()) {
                do {
                    count++;
                    id.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                    date.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_DATE)));
                    subject.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SUBJECT)));
                    present.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_PRESENT)));
                    absent.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ABSENT)));
                    pre = pre + mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_PRESENT));
                    abs = abs + mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ABSENT));

                } while (mCursor.moveToNext());
            }
            totalabsent = totalabsent + abs;
            totalpresent = totalpresent + pre;
            if (count > 0) {
                Card card = new Card((int) (100 * ((float) pre / (float) (pre + abs))), user_fName.get(i));
                cardAdapter.add(card);
            } else {
                Card card = new Card(999, user_fName.get(i));
                cardAdapter.add(card);
            }
        }
        totalavg = ((float) (totalpresent) / (float) (totalpresent + totalabsent)) * 100;
        if (totalavg > 0) bavg.setText("Average=" + totalavg + "%");
        else bavg.setText("N.A.");
       // bavg.setText("Average=" + (int) totalavg + "%");

    }

    @Override
    public void onProgressChanged(SeekBar S, int progress, boolean fromuser) {
        if (eng == false)
            switch (S.getId()) {
                case R.id.seekBar1:
                    five = progress;
                    five = (five + 400);
                    fifth.setText("Sem V: " + five / 10 + "%");
                    total = (0.33 * (five + six) + 0.67 * (seven + eight)) / 2;
                    average.setText(((int) total / 10) + "%");
                    pg.setProgress((int) total / 10);
                    break;
                case R.id.seekBar2:
                    six = progress;
                    six = (six + 400);
                    sixth.setText("Sem VI: " + six / 10 + "%");
                    total = (0.33 * (five + six) + 0.67 * (seven + eight)) / 2;
                    average.setText(((int) total / 10) + "%");
                    pg.setProgress((int) total / 10);
                    break;
                case R.id.seekBar3:
                    seven = progress;
                    seven = (seven + 400);
                    seventh.setText("Sem VII: " + seven / 10 + "%");
                    total = (0.33 * (five + six) + 0.67 * (seven + eight)) / 2;
                    average.setText(((int) total / 10) + "%");
                    pg.setProgress((int) total / 10);
                    break;
                case R.id.seekBar4:
                    eight = progress;
                    eight = (eight + 400);
                    eighth.setText("Sem VIII: " + eight / 10 + "%");
                    total = (0.33 * (five + six) + 0.67 * (seven + eight)) / 2;
                    average.setText(((int) total / 10) + "%");
                    pg.setProgress((int) total / 10);
                    break;
            }
        else switch (S.getId()) {
            case R.id.seekBar1:
                five = progress;
                five = (five + 400);
                fifth.setText("Sem V: " + five / 10 + "%");
                total = (five + six) / 2;
                average.setText(((int) total / 10) + "%");
                pg.setProgress((int) total / 10);
                break;
            case R.id.seekBar2:
                six = progress;
                six = (six + 400);
                sixth.setText("Sem VI: " + six / 10 + "%");
                total = (five + six) / 2;
                average.setText(((int) total / 10) + "%");
                pg.setProgress((int) total / 10);
                break;
        }
    }

    public void onStartTrackingTouch(SeekBar seek1) {
    }

    public void onStopTrackingTouch(SeekBar seek1) {
    }

    public void changetile(View v, final TextView t) {
        final CharSequence[] color = {"Red", "Pink", "Purple", "Indigo", "Blue", "Cyan", "Green", "Yellow", "Amber", "Orange"};
        AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
        builder1.setTitle("Select Colour");
        builder1.setItems(color, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        t.setBackgroundColor(0xffef5350);
                        break;
                    case 1:
                        t.setBackgroundColor(0xfff48fb1);
                        break;
                    case 2:
                        t.setBackgroundColor(0xffce93d8);
                        break;
                    case 3:
                        t.setBackgroundColor(0xff3f51b5);
                        break;
                    case 4:
                        t.setBackgroundColor(0xff42a5f5);
                        break;
                    case 5:
                        t.setBackgroundColor(0xff26c6da);
                        break;
                    case 6:
                        t.setBackgroundColor(0xff8bc34a);
                        break;
                    case 7:
                        t.setBackgroundColor(0xffffeb3b);
                        break;
                    case 8:
                        t.setBackgroundColor(0xffffc107);
                        break;
                    case 9:
                        t.setBackgroundColor(0xffff9800);
                        break;
                    //	case 10:
                    //		t.setBackgroundColor(0xffcfd8dc);
                    //		break;
                }
            }
        });
        AlertDialog alert1 = builder1.create();
        alert1.setInverseBackgroundForced(true);
        alert1.show();

    }

    public void changesubj(View v, final TextView t, final int num ) {
        final EditText input = new EditText(SubjectActivity.this);
        final CharSequence[] items = user_fName.toArray(new CharSequence[user_fName.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Select Subject");
        builder.setView(input);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                t.setText(items[item]);
                saveTable();
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = input.getEditableText().toString();
                if (str.length() > 0) {
                    t.setText(str);
                    saveTable();
                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SubjectActivity.this);
                    alertBuilder.setTitle("Invalid Data");
                    alertBuilder.setMessage("Please, Enter valid data");
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertBuilder.create().show();
                }
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                t.setText(" ");
                t.setBackgroundColor(0xffcfd8dc);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void displayTable()
    {
        dataBase = mHelper.getWritableDatabase();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Integer> colours = new ArrayList<Integer>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME3+" ORDER BY "+DbHelper.KEY_ID, null);
        titles.clear();
        ids.clear();
        colours.clear();
        int i=0;
        if (mCursor.moveToFirst())
        {
            do {
                ids.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ID)));
                titles.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_NAME)));
                colours.add(mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_COLOR)));
                //Toast.makeText(getApplicationContext(),"length = "+mCursor.getInt(mCursor.getColumnIndex(DbHelper.KEY_ID)),Toast.LENGTH_SHORT).show();
            } while (mCursor.moveToNext());
        }
        if(titles.size()==1)createTable();
        else {
            t9.setBackgroundColor(colours.get(i));
            t9.setText(titles.get(i++));
            t10.setBackgroundColor(colours.get(i));
            t10.setText(titles.get(i++));
            t11.setBackgroundColor(colours.get(i));
            t11.setText(titles.get(i++));
            t12.setBackgroundColor(colours.get(i));
            t12.setText(titles.get(i++));
            t13.setBackgroundColor(colours.get(i));
            t13.setText(titles.get(i++));
            t14.setBackgroundColor(colours.get(i));
            t14.setText(titles.get(i++));
            t16.setBackgroundColor(colours.get(i));
            t16.setText(titles.get(i++));
            t17.setBackgroundColor(colours.get(i));
            t17.setText(titles.get(i++));
            t18.setBackgroundColor(colours.get(i));
            t18.setText(titles.get(i++));
            t19.setBackgroundColor(colours.get(i));
            t19.setText(titles.get(i++));
            t20.setBackgroundColor(colours.get(i));
            t20.setText(titles.get(i++));
            t21.setBackgroundColor(colours.get(i));
            t21.setText(titles.get(i++).toString());
            t23.setBackgroundColor(colours.get(i));
            t23.setText(titles.get(i++).toString());
            t24.setBackgroundColor(colours.get(i));
            t24.setText(titles.get(i++).toString());
            t25.setBackgroundColor(colours.get(i));
            t25.setText(titles.get(i++).toString());
            t26.setBackgroundColor(colours.get(i));
            t26.setText(titles.get(i++).toString());
            t27.setBackgroundColor(colours.get(i));
            t27.setText(titles.get(i++).toString());
            t28.setBackgroundColor(colours.get(i));
            t28.setText(titles.get(i++).toString());
            t30.setBackgroundColor(colours.get(i));
            t30.setText(titles.get(i++).toString());
            t31.setBackgroundColor(colours.get(i));
            t31.setText(titles.get(i++).toString());
            t32.setBackgroundColor(colours.get(i));
            t32.setText(titles.get(i++).toString());
            t33.setBackgroundColor(colours.get(i));
            t33.setText(titles.get(i++).toString());
            t34.setBackgroundColor(colours.get(i));
            t34.setText(titles.get(i++).toString());
            t35.setBackgroundColor(colours.get(i));
            t35.setText(titles.get(i++).toString());
            t37.setBackgroundColor(colours.get(i));
            t37.setText(titles.get(i++).toString());
            t38.setBackgroundColor(colours.get(i));
            t38.setText(titles.get(i++).toString());
            t39.setBackgroundColor(colours.get(i));
            t39.setText(titles.get(i++).toString());
            t40.setBackgroundColor(colours.get(i));
            t40.setText(titles.get(i++).toString());
            t41.setBackgroundColor(colours.get(i));
            t41.setText(titles.get(i++).toString());
            t42.setBackgroundColor(colours.get(i));
            t42.setText(titles.get(i++).toString());
            t44.setBackgroundColor(colours.get(i));
            t44.setText(titles.get(i++).toString());
            t45.setBackgroundColor(colours.get(i));
            t45.setText(titles.get(i++).toString());
            t46.setBackgroundColor(colours.get(i));
            t46.setText(titles.get(i++).toString());
            t47.setBackgroundColor(colours.get(i));
            t47.setText(titles.get(i++).toString());
            t48.setBackgroundColor(colours.get(i));
            t48.setText(titles.get(i++).toString());
            t49.setBackgroundColor(colours.get(i));
            t49.setText(titles.get(i++).toString());
            t51.setBackgroundColor(colours.get(i));
            t51.setText(titles.get(i++).toString());
            t52.setBackgroundColor(colours.get(i));
            t52.setText(titles.get(i++).toString());
            t53.setBackgroundColor(colours.get(i));
            t53.setText(titles.get(i++).toString());
            t54.setBackgroundColor(colours.get(i));
            t54.setText(titles.get(i++).toString());
            t55.setBackgroundColor(colours.get(i));
            t55.setText(titles.get(i++).toString());
            t56.setBackgroundColor(colours.get(i));
            t56.setText(titles.get(i).toString());
        }
    }
    public void createTable()
    {
        dataBase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        dataBase.execSQL("DELETE FROM " + DbHelper.TABLE_NAME3);

        values.put(DbHelper.KEY_COLOR, getC(t9));
        values.put(DbHelper.KEY_ID, 1);
        values.put(DbHelper.KEY_NAME, getT(t9));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t10));
        values.put(DbHelper.KEY_ID, 2);
        values.put(DbHelper.KEY_NAME, getT(t10));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t11));
        values.put(DbHelper.KEY_ID, 3);
        values.put(DbHelper.KEY_NAME, getT(t11));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t12));
        values.put(DbHelper.KEY_ID, 4);
        values.put(DbHelper.KEY_NAME, getT(t12));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t13));
        values.put(DbHelper.KEY_ID, 5);
        values.put(DbHelper.KEY_NAME, getT(t13));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t14));
        values.put(DbHelper.KEY_ID, 6);
        values.put(DbHelper.KEY_NAME, getT(t14));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t16));
        values.put(DbHelper.KEY_ID, 7);
        values.put(DbHelper.KEY_NAME, getT(t16));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t17));
        values.put(DbHelper.KEY_ID, 8);
        values.put(DbHelper.KEY_NAME, getT(t17));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t18));
        values.put(DbHelper.KEY_ID, 9);
        values.put(DbHelper.KEY_NAME, getT(t18));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t19));
        values.put(DbHelper.KEY_ID, 10);
        values.put(DbHelper.KEY_NAME, getT(t19));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t20));
        values.put(DbHelper.KEY_ID, 11);
        values.put(DbHelper.KEY_NAME, getT(t20));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t21));
        values.put(DbHelper.KEY_ID, 12);
        values.put(DbHelper.KEY_NAME, getT(t21));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t23));
        values.put(DbHelper.KEY_ID, 13);
        values.put(DbHelper.KEY_NAME, getT(t23));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t24));
        values.put(DbHelper.KEY_ID, 14);
        values.put(DbHelper.KEY_NAME, getT(t24));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t25));
        values.put(DbHelper.KEY_ID, 15);
        values.put(DbHelper.KEY_NAME, getT(t25));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t26));
        values.put(DbHelper.KEY_ID, 16);
        values.put(DbHelper.KEY_NAME, getT(t26));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t27));
        values.put(DbHelper.KEY_ID, 17);
        values.put(DbHelper.KEY_NAME, getT(t27));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t28));
        values.put(DbHelper.KEY_ID, 18);
        values.put(DbHelper.KEY_NAME, getT(t28));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t30));
        values.put(DbHelper.KEY_ID, 19);
        values.put(DbHelper.KEY_NAME, getT(t30));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t31));
        values.put(DbHelper.KEY_ID, 20);
        values.put(DbHelper.KEY_NAME, getT(t31));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t32));
        values.put(DbHelper.KEY_ID, 21);
        values.put(DbHelper.KEY_NAME, getT(t32));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t33));
        values.put(DbHelper.KEY_ID, 22);
        values.put(DbHelper.KEY_NAME, getT(t33));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t34));
        values.put(DbHelper.KEY_ID, 23);
        values.put(DbHelper.KEY_NAME, getT(t34));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t35));
        values.put(DbHelper.KEY_ID, 24);
        values.put(DbHelper.KEY_NAME, getT(t35));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t37));
        values.put(DbHelper.KEY_ID, 25);
        values.put(DbHelper.KEY_NAME, getT(t37));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t38));
        values.put(DbHelper.KEY_ID, 26);
        values.put(DbHelper.KEY_NAME, getT(t38));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t39));
        values.put(DbHelper.KEY_ID, 27);
        values.put(DbHelper.KEY_NAME, getT(t39));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t40));
        values.put(DbHelper.KEY_ID, 28);
        values.put(DbHelper.KEY_NAME, getT(t40));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t41));
        values.put(DbHelper.KEY_ID, 29);
        values.put(DbHelper.KEY_NAME, getT(t41));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t42));
        values.put(DbHelper.KEY_ID, 30);
        values.put(DbHelper.KEY_NAME, getT(t42));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t44));
        values.put(DbHelper.KEY_ID, 31);
        values.put(DbHelper.KEY_NAME, getT(t44));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t45));
        values.put(DbHelper.KEY_ID, 32);
        values.put(DbHelper.KEY_NAME, getT(t45));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t46));
        values.put(DbHelper.KEY_ID, 33);
        values.put(DbHelper.KEY_NAME, getT(t46));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t47));
        values.put(DbHelper.KEY_ID, 34);
        values.put(DbHelper.KEY_NAME, getT(t47));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t48));
        values.put(DbHelper.KEY_ID, 35);
        values.put(DbHelper.KEY_NAME, getT(t48));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t49));
        values.put(DbHelper.KEY_ID, 36);
        values.put(DbHelper.KEY_NAME, getT(t49));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t51));
        values.put(DbHelper.KEY_ID, 37);
        values.put(DbHelper.KEY_NAME, getT(t51));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t52));
        values.put(DbHelper.KEY_ID, 38);
        values.put(DbHelper.KEY_NAME, getT(t52));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t53));
        values.put(DbHelper.KEY_ID, 39);
        values.put(DbHelper.KEY_NAME, getT(t53));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t54));
        values.put(DbHelper.KEY_ID, 40);
        values.put(DbHelper.KEY_NAME, getT(t54));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t55));
        values.put(DbHelper.KEY_ID, 41);
        values.put(DbHelper.KEY_NAME, getT(t55));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t56));
        values.put(DbHelper.KEY_ID, 42);
        values.put(DbHelper.KEY_NAME, getT(t56));

        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        dataBase.close();
        displayTable();
        displayTable();
    }
    public void saveTable()
    {
        dataBase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        dataBase.execSQL("DELETE FROM " + DbHelper.TABLE_NAME3);
        values.put(DbHelper.KEY_COLOR, getC(t9));
        values.put(DbHelper.KEY_ID, 1);
        values.put(DbHelper.KEY_NAME, getT(t9));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t10));
        values.put(DbHelper.KEY_ID, 2);
        values.put(DbHelper.KEY_NAME, getT(t10));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t11));
        values.put(DbHelper.KEY_ID, 3);
        values.put(DbHelper.KEY_NAME, getT(t11));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t12));
        values.put(DbHelper.KEY_ID, 4);
        values.put(DbHelper.KEY_NAME, getT(t12));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t13));
        values.put(DbHelper.KEY_ID, 5);
        values.put(DbHelper.KEY_NAME, getT(t13));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t14));
        values.put(DbHelper.KEY_ID, 6);
        values.put(DbHelper.KEY_NAME, getT(t14));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t16));
        values.put(DbHelper.KEY_ID, 7);
        values.put(DbHelper.KEY_NAME, getT(t16));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t17));
        values.put(DbHelper.KEY_ID, 8);
        values.put(DbHelper.KEY_NAME, getT(t17));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t18));
        values.put(DbHelper.KEY_ID, 9);
        values.put(DbHelper.KEY_NAME, getT(t18));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t19));
        values.put(DbHelper.KEY_ID, 10);
        values.put(DbHelper.KEY_NAME, getT(t19));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t20));
        values.put(DbHelper.KEY_ID, 11);
        values.put(DbHelper.KEY_NAME, getT(t20));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t21));
        values.put(DbHelper.KEY_ID, 12);
        values.put(DbHelper.KEY_NAME, getT(t21));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t23));
        values.put(DbHelper.KEY_ID, 13);
        values.put(DbHelper.KEY_NAME, getT(t23));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t24));
        values.put(DbHelper.KEY_ID, 14);
        values.put(DbHelper.KEY_NAME, getT(t24));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t25));
        values.put(DbHelper.KEY_ID, 15);
        values.put(DbHelper.KEY_NAME, getT(t25));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t26));
        values.put(DbHelper.KEY_ID, 16);
        values.put(DbHelper.KEY_NAME, getT(t26));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t27));
        values.put(DbHelper.KEY_ID, 17);
        values.put(DbHelper.KEY_NAME, getT(t27));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t28));
        values.put(DbHelper.KEY_ID, 18);
        values.put(DbHelper.KEY_NAME, getT(t28));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t30));
        values.put(DbHelper.KEY_ID, 19);
        values.put(DbHelper.KEY_NAME, getT(t30));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t31));
        values.put(DbHelper.KEY_ID, 20);
        values.put(DbHelper.KEY_NAME, getT(t31));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t32));
        values.put(DbHelper.KEY_ID, 21);
        values.put(DbHelper.KEY_NAME, getT(t32));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t33));
        values.put(DbHelper.KEY_ID, 22);
        values.put(DbHelper.KEY_NAME, getT(t33));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t34));
        values.put(DbHelper.KEY_ID, 23);
        values.put(DbHelper.KEY_NAME, getT(t34));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t35));
        values.put(DbHelper.KEY_ID, 24);
        values.put(DbHelper.KEY_NAME, getT(t35));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t37));
        values.put(DbHelper.KEY_ID, 25);
        values.put(DbHelper.KEY_NAME, getT(t37));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t38));
        values.put(DbHelper.KEY_ID, 26);
        values.put(DbHelper.KEY_NAME, getT(t38));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t39));
        values.put(DbHelper.KEY_ID, 27);
        values.put(DbHelper.KEY_NAME, getT(t39));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t40));
        values.put(DbHelper.KEY_ID, 28);
        values.put(DbHelper.KEY_NAME, getT(t40));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t41));
        values.put(DbHelper.KEY_ID, 29);
        values.put(DbHelper.KEY_NAME, getT(t41));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t42));
        values.put(DbHelper.KEY_ID, 30);
        values.put(DbHelper.KEY_NAME, getT(t42));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t44));
        values.put(DbHelper.KEY_ID, 31);
        values.put(DbHelper.KEY_NAME, getT(t44));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t45));
        values.put(DbHelper.KEY_ID, 32);
        values.put(DbHelper.KEY_NAME, getT(t45));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t46));
        values.put(DbHelper.KEY_ID, 33);
        values.put(DbHelper.KEY_NAME, getT(t46));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t47));
        values.put(DbHelper.KEY_ID, 34);
        values.put(DbHelper.KEY_NAME, getT(t47));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t48));
        values.put(DbHelper.KEY_ID, 35);
        values.put(DbHelper.KEY_NAME, getT(t48));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t49));
        values.put(DbHelper.KEY_ID, 36);
        values.put(DbHelper.KEY_NAME, getT(t49));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t51));
        values.put(DbHelper.KEY_ID, 37);
        values.put(DbHelper.KEY_NAME, getT(t51));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t52));
        values.put(DbHelper.KEY_ID, 38);
        values.put(DbHelper.KEY_NAME, getT(t52));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t53));
        values.put(DbHelper.KEY_ID, 39);
        values.put(DbHelper.KEY_NAME, getT(t53));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t54));
        values.put(DbHelper.KEY_ID, 40);
        values.put(DbHelper.KEY_NAME, getT(t54));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t55));
        values.put(DbHelper.KEY_ID, 41);
        values.put(DbHelper.KEY_NAME, getT(t55));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        values.put(DbHelper.KEY_COLOR, getC(t56));
        values.put(DbHelper.KEY_ID, 42);
        values.put(DbHelper.KEY_NAME, getT(t56));
        dataBase.insert(DbHelper.TABLE_NAME3, null, values);
        values.clear();
        dataBase.close();

        displayTable();
    }
    public void showColors()
    {
        int colorindex=0;
        for(int i=0;i<=user_fName.size();i++)
        {
            String name=user_fName.get(i);

            if(t9.getText().equals(name))setC(t9, colorindex);
            if(t10.getText().equals(name))setC(t10,colorindex);
            if(t11.getText().equals(name))setC(t11,colorindex);
            if(t12.getText().equals(name))setC(t12,colorindex);
            if(t13.getText().equals(name))setC(t13,colorindex);
            if(t14.getText().equals(name))setC(t14,colorindex);
            if(t16.getText().equals(name))setC(t16,colorindex);
            if(t17.getText().equals(name))setC(t17,colorindex);
            if(t18.getText().equals(name))setC(t18,colorindex);
            if(t19.getText().equals(name))setC(t19,colorindex);
            if(t20.getText().equals(name))setC(t20,colorindex);
            if(t21.getText().equals(name))setC(t21,colorindex);
            if(t23.getText().equals(name))setC(t23,colorindex);
            if(t24.getText().equals(name))setC(t24,colorindex);
            if(t25.getText().equals(name))setC(t25,colorindex);
            if(t26.getText().equals(name))setC(t26,colorindex);
            if(t27.getText().equals(name))setC(t27,colorindex);
            if(t28.getText().equals(name))setC(t28,colorindex);
            if(t30.getText().equals(name))setC(t30,colorindex);
            if(t31.getText().equals(name))setC(t31,colorindex);
            if(t32.getText().equals(name))setC(t32,colorindex);
            if(t33.getText().equals(name))setC(t33,colorindex);
            if(t34.getText().equals(name))setC(t34,colorindex);
            if(t35.getText().equals(name))setC(t35,colorindex);
            if(t37.getText().equals(name))setC(t37,colorindex);
            if(t38.getText().equals(name))setC(t38,colorindex);
            if(t39.getText().equals(name))setC(t39,colorindex);
            if(t40.getText().equals(name))setC(t40,colorindex);
            if(t41.getText().equals(name))setC(t41,colorindex);
            if(t42.getText().equals(name))setC(t42,colorindex);
            if(t44.getText().equals(name))setC(t44,colorindex);
            if(t45.getText().equals(name))setC(t45,colorindex);
            if(t46.getText().equals(name))setC(t46,colorindex);
            if(t47.getText().equals(name))setC(t47,colorindex);
            if(t48.getText().equals(name))setC(t48,colorindex);
            if(t49.getText().equals(name))setC(t49,colorindex);
            if(t51.getText().equals(name))setC(t51,colorindex);
            if(t52.getText().equals(name))setC(t52,colorindex);
            if(t53.getText().equals(name))setC(t53,colorindex);
            if(t54.getText().equals(name))setC(t54,colorindex);
            if(t55.getText().equals(name))setC(t55,colorindex);
            if(t56.getText().equals(name))setC(t56,colorindex);
            colorindex++;
        }
    }
    public void setC(TextView t, int item){
        switch (item) {
            case 0:
                t.setBackgroundColor(0xffef5350);
                break;
            case 1:
                t.setBackgroundColor(0xfff48fb1);
                break;
            case 2:
                t.setBackgroundColor(0xffce93d8);
                break;
            case 3:
                t.setBackgroundColor(0xff3f51b5);
                break;
            case 4:
                t.setBackgroundColor(0xff42a5f5);
                break;
            case 5:
                t.setBackgroundColor(0xff26c6da);
                break;
            case 6:
                t.setBackgroundColor(0xff8bc34a);
                break;
            case 7:
                t.setBackgroundColor(0xffffeb3b);
                break;
            case 8:
                t.setBackgroundColor(0xffffc107);
                break;
            case 9:
                t.setBackgroundColor(0xffff9800);
                break;
        }
    }
    public void writeToSD() throws IOException {
        File f = new File("/data/data/com.snedan.test/databases/" + DbHelper.DATABASE_NAME);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(f);
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/backup.db");
            while (true) {
                int i = fis.read();
                if (i != -1) fos.write(i);
                else break;
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException ioe) {ioe.printStackTrace();}
            catch (java.lang.NullPointerException j) {j.printStackTrace();}
        }
    }
    private void copyFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir =new File(outputPath);
            if(!dir.exists())
            {
                dir.mkdirs();
            }
            in=new FileInputStream(inputPath + inputFile);
            out=new FileOutputStream(outputPath + inputFile);
            byte[] buffer =new byte[1024];
            int read;
            while((read =in.read(buffer))!=-1)
            {
                out.write(buffer,0, read);
            }
            in.close();
            in=null;
            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out=null;
        }
        catch(FileNotFoundException fnfe1)
        {
            Log.e("tag", fnfe1.getMessage());
        }
        catch(Exception e)
        {
            Log.e("tag", e.getMessage());
        }
    }

    public int getC(TextView t)
    {
        try
        {
            int x= Color.parseColor(t.getBackground().toString());
            return (x);
        }
        catch(java.lang.NullPointerException e)
        {
            e.printStackTrace();
            return(0xffcfd8dc);
        }
        finally {
            return(0xffcfd8dc);
        }

    }
    public String getT(TextView t)
    {
        if(t.getText().toString().isEmpty())return "*";
        else return t.getText().toString();
    }

    public int colourToInt(TextView t)
    {
        return t.getBackground().hashCode();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);
                builder.setTitle("Current Minimum  Average Percentage= " + minPercent);
                builder.setMessage("Enter New Minimum Average Percentage");
                final EditText input = new EditText(SubjectActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        newPercent = Integer.parseInt(input.getText().toString());
                        if (newPercent >= 50 && newPercent <= 100) {
                            if (newPercent >= 50 && newPercent <= 100) {

                                dataBase = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(DbHelper.KEY_ID, 1);
                                values.put(DbHelper.KEY_PERCENT, newPercent);
                                //insert data into database
                                dataBase.execSQL("UPDATE percent SET id=1, per="+newPercent+" WHERE id=1");
                                dataBase.close();
                                minPercent = newPercent;
                            }
                            Toast.makeText(getApplicationContext(), "New Minimum Average Percentage set to " + minPercent, Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SubjectActivity.this);
                            alertBuilder.setTitle("Invalid Data");
                            alertBuilder.setMessage("Please Enter a Value Between 50 to 100");
                            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertBuilder.create().show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a = builder.create();
                a.show();
                break;
            case R.id.help:
                String text = "\n *\tUse the ADD Button to add subjects, entries or topics\n" +
                        " *\tPress & Hold to delete subjects or entries.\n" +
                        " *\tPress & Hold to add a Timetable Tile.\n" +
                        " *\tUse the Switch to choose between Engineering or BSc/BCom/BA mode.\n" +
                        " *\tSlide The Sliders based on your acquired marks.\n" +
                        " *\tYou can change Minimum Average Attendance Percentage in Settings.\n" +
                        " *\tWe would love to receive Feedback & Suggestions from you to improve Student Assist.\n" +
                        "\n" +
                        " -\tUninstalling this app will lead to loss of app data. \n" +
                        " -\tAttendance Tracker can be used by \n" +
                        " \t\tStudents:To track Lectures Attended, \n" +
                        " \t\tTeachers/Parents:To track Students Attendance,\n" +
                        " \t\tManagers:To track Employees Attendance.\n" +
                        " -\tYou can add attendance entries either weekly or daily(as per your convenience).\n" +
                        " -\tDo Not Uninstall This App As It Will Lead To Permanent Loss Of Data.\n" +
                        " -\tEnter Accurate Data For Definite Results.";

                AlertDialog.Builder builder1 = new AlertDialog.Builder(SubjectActivity.this);
                builder1.setTitle("Help & Tips");
                final TextView op = new TextView(SubjectActivity.this);

                builder1.setView(op);
                op.setText(text);
                builder1.setIcon(R.drawable.miniic);
                AlertDialog b = builder1.create();
                b.show();
                break;

            case R.id.about:
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
                break;

            case R.id.backup:
                try {
                    writeToSD();
                   // export();
                } catch (IOException e) {

                    Toast.makeText(getApplication(), "Backup Unsuccessful", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    Toast.makeText(getApplication(), "Backup Successful", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}