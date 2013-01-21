package net.famcash.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "famcashtable.db";
  private static final int DATABASE_VERSION = 1;

  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Method is called during creation of the database
  @Override
  public void onCreate(SQLiteDatabase database) {
    TaskTable.onCreate(database);
    KidTable.onCreate(database);
    EventTable.onCreate(database);
  }

  // Method is called during an upgrade of the database,
  // e.g. if you increase the database version
  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    TaskTable.onUpgrade(database, oldVersion, newVersion);
    KidTable.onUpgrade(database, oldVersion, newVersion);
    EventTable.onUpgrade(database, oldVersion, newVersion);
  }
} // end of class DBHelper