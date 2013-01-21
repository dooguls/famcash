package net.famcash.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventTable {

  // Database table
  public static final String TABLE_EVENT = "event";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TASKTABLEID ="taskTableID";
  public static final String COLUMN_KIDTABLEID = "kidTableID";
  public static final String COLUMN_CASHVALUE = "cashValue";

  // Database creation SQL statement
  private static final String TABLE_CREATE = "create table " 
      + TABLE_EVENT
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_TASKTABLEID + " integer not null, " 
      + COLUMN_KIDTABLEID + " integer not null,"
      + COLUMN_CASHVALUE + " float not null,"
      + ");";

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(TABLE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.w(EventTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
    onCreate(database);
  }
} //end of class EventTable