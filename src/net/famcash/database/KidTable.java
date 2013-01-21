package net.famcash.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class KidTable {

  // Database table
  public static final String TABLE_KID = "kid";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_KIDNAME ="kidName";
  public static final String COLUMN_CURRENTCASH = "currentCash";
  public static final String COLUMN_LASTCASH = "lastCash";

  // Database creation SQL statement
  private static final String TABLE_CREATE = "create table " 
      + TABLE_KID
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_KIDNAME + " text not null, " 
      + COLUMN_CURRENTCASH + " float not null,"
      + COLUMN_LASTCASH + " float not null,"
      + ");";

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(TABLE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.w(KidTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_KID);
    onCreate(database);
  }
} // end of class KidTable