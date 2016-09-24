package com.snedan.studentassist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Snedan on 19-01-2016.
 */
public class DbHelper extends SQLiteOpenHelper
{
    static String DATABASE_NAME="endsdata";
    public static final String TABLE_NAME1="subj";
    public static final String TABLE_NAME2="attend";
    public static final String TABLE_NAME3="timetable";
    public static final String TABLE_NAME4="percent";

    public static final String KEY_ID="id";
    public static final String KEY_PERCENT="per";
    public static final String KEY_SUBJECT="sname";
    public static final String KEY_PRESENT="present";
    public static final String KEY_ABSENT="absent";
    public static final String KEY_DATE="dat";
    public static final String KEY_COLOR="colour";
    public static final String KEY_NAME="name";
    public static final String ALL_COLUMN_KEYS="id";
    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)	{
        db.execSQL("CREATE TABLE "+TABLE_NAME1+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_SUBJECT+" TEXT NOT NULL UNIQUE)");
        db.execSQL("CREATE TABLE " +TABLE_NAME2+ " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " DATE NOT NULL, " + KEY_SUBJECT + " TEXT NOT NULL, " + KEY_PRESENT + " INTEGER NOT NULL, " + KEY_ABSENT + " INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_NAME3 + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT NOT NULL, "+KEY_COLOR+" INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE "+TABLE_NAME4+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_PERCENT+" INTEGER)");
        db.execSQL("INSERT INTO timetable VALUES(44,'test',123)");
        db.execSQL("INSERT INTO percent VALUES(1,75)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP "+TABLE_NAME1+" IF EXISTS");
        db.execSQL("DROP "+TABLE_NAME2+" IF EXISTS");
        db.execSQL("DROP "+TABLE_NAME3+" IF EXISTS");
        db.execSQL("DROP "+TABLE_NAME4+" IF EXISTS");
        onCreate(db);
    }
}

