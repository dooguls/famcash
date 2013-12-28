package net.famcash.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventTable {

  // Database table
  public static final String TABLE_EVENT = "event";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TASKITEM ="taskItem";
  public static final String COLUMN_KIDNAME = "kidName";
  public static final String COLUMN_KIDRUNNINGTOTOAL = "kidRunningTotal";
  public static final String COLUMN_DATEDONE = "dateDone";
  //I know keeping a running total in this table is dumb, and I should 
  // make another table for kids and their current total, but I'm going
  // for simple here first.
  // don't forget SQLITE expects dates stored like this: YYYY-MM-DD HH:MM:SS

  // Database creation SQL statement
  private static final String TABLE_CREATE = "create table " 
      + TABLE_EVENT
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_TASKITEM + " text not null, " 
      + COLUMN_KIDNAME + " text not null,"
      + COLUMN_KIDRUNNINGTOTOAL + " float not null,"
      + COLUMN_DATEDONE  + " datetime default current_timestamp,"
      + ");";

  private static final String DEFAULT_ROW_1 = "insert into event"
      + "(taskItem,kidName,kidRunningTotal) "
      + "values('first row','Owen',0);";
  private static final String DEFAULT_ROW_2 = "insert into event"
      + "(taskItem,kidName,kidRunningTotal) "
      + "values('first row','Caroline',0);";

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(TABLE_CREATE);
    database.execSQL(DEFAULT_ROW_1);
    database.execSQL(DEFAULT_ROW_2);
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
