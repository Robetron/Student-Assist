package com.snedan.studentassist;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.Calendar;


public class AddAttendanceActivity extends ActionBarActivity
{
    ImageButton FAB;
    EditText edit_first;
    private TextView present,absent;
    private DbHelper mHelper;
    private SQLiteDatabase dataBase;
    private String id,fname,date;
    private boolean isUpdate;
    private int year, day, month;
    private EditText p,a;
    private int y,m,d;
    Button datebutton;

    private Calendar calendar;
    private Toolbar toolbar, bottombar;
    private TextView toolbarTitle;
    // public final String subj = getIntent().getExtras().getString("Subject");
    Button b1,b2,b3,b4,bb;
    int pre=1,abs=0;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        //  toolbar.setTitle("New Entry");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("New Entry");
        String subj = getIntent().getExtras().getString("Subject");
        bb = (Button) findViewById(R.id.savebutton);

        datebutton = (Button) findViewById(R.id.DateButton);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        showDate(year, month, day);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        present=(TextView)findViewById(R.id.Text1);
        absent=(TextView)findViewById(R.id.Text2);
        present.setText(""+pre);
        absent.setText(""+abs);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre<8)++pre;
                present.setText(""+pre);

            }
        });
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(abs<8)++abs;
                absent.setText(""+abs);

            }
        });
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre>0)--pre;
                present.setText(""+pre);

            }
        });
        b4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(abs>0)--abs;
                absent.setText(""+abs);

            }
        });
        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            id=getIntent().getExtras().getString("ID");
            fname=getIntent().getExtras().getString("Fname");
            edit_first.setText(fname);

        }

        bb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String subj = getIntent().getExtras().getString("Subject");

                if(Integer.parseInt(present.getText().toString())>=0 && Integer.parseInt(absent.getText().toString())>=0)
                {

                    dataBase=mHelper.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put(DbHelper.KEY_DATE, datebutton.getText().toString());
                    values.put(DbHelper.KEY_SUBJECT, subj);
                    values.put(DbHelper.KEY_PRESENT, present.getText().toString());
                    values.put(DbHelper.KEY_ABSENT, absent.getText().toString());

                    System.out.println("");
                    try {

                        dataBase.insert(DbHelper.TABLE_NAME2, null, values);

                    }
                    catch(SQLiteException e)
                    {
                        e.printStackTrace();
                    }
                    //close database
                    dataBase.close();
                    finish();
                }
                else
                {
                    AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddAttendanceActivity.this);
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

        mHelper=new DbHelper(this);

        datebutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==999)
        {

            return new DatePickerDialog(this,myDateListener,year,month,day);
        }
        return  null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            y=year;
            m=monthOfYear;
            d=dayOfMonth;
            showDate(year, monthOfYear, dayOfMonth);
        }
    };

    private void showDate(int y, int m, int d)
    {
        if(m<10 && d<10)datebutton.setText(new StringBuilder().append(y).append(" - 0").append(m + 1).append(" - 0").append(d));
        else if(m<10 && d>10)datebutton.setText(new StringBuilder().append(y).append(" - 0").append(m + 1).append(" - ").append(d));
        else if(m>10 && d<10)datebutton.setText(new StringBuilder().append(y).append(" - ").append(m + 1).append(" - 0").append(d));
        else datebutton.setText(new StringBuilder().append(y).append(" - ").append(m + 1).append(" - ").append(d));


    }
}
