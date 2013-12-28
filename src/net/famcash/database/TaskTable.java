package net.famcash.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskTable {

  // Database table
  public static final String TABLE_TASK = "task";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TASKNAME ="taskName";
  public static final String COLUMN_ONETIME = "oneTime";
  public static final String COLUMN_TASKVALUE = "taskValue";

  // Database creation SQL statement
  private static final String TABLE_CREATE = "create table " 
      + TABLE_TASK
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_TASKNAME + " text not null, " 
      + COLUMN_TASKVALUE + " float not null, "
      + COLUMN_ONETIME + " integer not null," 
      + ");";
  
  private static final String ADD_DEFAULT_TASK_1 = "insert into task (taskName,oneTime,taskValue) values ('Brush Teeth Twice',0,0.10);";
  private static final String ADD_DEFAULT_TASK_2 = "insert into task (taskName,oneTime,taskValue) values ('Clean Basement',0,0.10);";
  private static final String ADD_DEFAULT_TASK_3 = "insert into task (taskName,oneTime,taskValue) values ('Clean Bathroom',0,0.10);";
  private static final String ADD_DEFAULT_TASK_4 = "insert into task (taskName,oneTime,taskValue) values ('Clean Bedroom',0,0.10);";
  private static final String ADD_DEFAULT_TASK_5 = "insert into task (taskName,oneTime,taskValue) values ('Clean Familyroom',0,0.10);";
  private static final String ADD_DEFAULT_TASK_6 = "insert into task (taskName,oneTime,taskValue) values ('Clean Lego Room',0,0.10);";
  private static final String ADD_DEFAULT_TASK_7 = "insert into task (taskName,oneTime,taskValue) values ('Clean Windows',0,0.10);";
  private static final String ADD_DEFAULT_TASK_8 = "insert into task (taskName,oneTime,taskValue) values ('Empty Dishwasher',0,0.10);";
  private static final String ADD_DEFAULT_TASK_9 = "insert into task (taskName,oneTime,taskValue) values ('Fill Dishwasher',0,0.10);";
  private static final String ADD_DEFAULT_TASK_10 = "insert into task (taskName,oneTime,taskValue) values ('Fruit of the Spirit',0,0.10);";
  private static final String ADD_DEFAULT_TASK_11 = "insert into task (taskName,oneTime,taskValue) values ('Make Bed',0,0.10);";
  private static final String ADD_DEFAULT_TASK_12 = "insert into task (taskName,oneTime,taskValue) values ('Set Table',0,0.10);";
  private static final String ADD_DEFAULT_TASK_13 = "insert into task (taskName,oneTime,taskValue) values ('Sweep Kitchen',0,0.10);";
  private static final String ADD_DEFAULT_TASK_14 = "insert into task (taskName,oneTime,taskValue) values ('Turn off lights',0,0.10);";
  
  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(TABLE_CREATE);
    database.execSQL(ADD_DEFAULT_TASK_1);
    database.execSQL(ADD_DEFAULT_TASK_2);
    database.execSQL(ADD_DEFAULT_TASK_3);
    database.execSQL(ADD_DEFAULT_TASK_4);
    database.execSQL(ADD_DEFAULT_TASK_5);
    database.execSQL(ADD_DEFAULT_TASK_6);
    database.execSQL(ADD_DEFAULT_TASK_7);
    database.execSQL(ADD_DEFAULT_TASK_8);
    database.execSQL(ADD_DEFAULT_TASK_9);
    database.execSQL(ADD_DEFAULT_TASK_10);
    database.execSQL(ADD_DEFAULT_TASK_11);
    database.execSQL(ADD_DEFAULT_TASK_12);
    database.execSQL(ADD_DEFAULT_TASK_13);
    database.execSQL(ADD_DEFAULT_TASK_14);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.w(TaskTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
    onCreate(database);
  }
} //end of class TaskTable