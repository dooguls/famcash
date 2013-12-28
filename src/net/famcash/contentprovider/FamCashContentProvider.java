package net.famcash.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import net.famcash.database.DBHelper;
import net.famcash.database.TaskTable;
import net.famcash.database.KidTable;
import net.famcash.database.EventTable;

public class FamCashContentProvider extends ContentProvider {

  // database
  private DBHelper database;

  // Used for the UriMacher
  private static final int TASKS = 10;
  private static final int TASK_ID = 11;
  private static final int KIDS = 20;
  private static final int KID_ID = 21;
  private static final int EVENTS = 30;
  private static final int EVENT_ID = 31;

  private static final String AUTHORITY = "net.famcash.contentprovider";

  private static final String BASE_PATH = "famcash";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
      + "/" + BASE_PATH);

  public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
      + "/famcash";
  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
      + "/famcash"; //not sure this is right.

  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  static {
    sURIMatcher.addURI(AUTHORITY, BASE_PATH, TASKS);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TASK_ID);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH, KIDS);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", KID_ID);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH, EVENTS);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", EVENT_ID);
  }

  @Override
  public boolean onCreate() {
    database = new DBHelper(getContext());
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {

    // Using SQLiteQueryBuilder instead of query() method
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    // Check if the caller has requested a column which does not exists
    checkColumns(projection);

    int uriType = sURIMatcher.match(uri);
    switch (uriType) {
    case TASKS:
    	// Set the table
        queryBuilder.setTables(TaskTable.TABLE_TASK);
      break;
    case TASK_ID:
    	// Set the table
        queryBuilder.setTables(TaskTable.TABLE_TASK);
      // Adding the ID to the original query
      queryBuilder.appendWhere(TaskTable.COLUMN_ID + "="
          + uri.getLastPathSegment());
      break;
    case KIDS:
    	// Set the table
        queryBuilder.setTables(KidTable.TABLE_KID);
      break;
    case KID_ID:
    	// Set the table
        queryBuilder.setTables(KidTable.TABLE_KID);
      // Adding the ID to the original query
      queryBuilder.appendWhere(KidTable.COLUMN_ID + "="
          + uri.getLastPathSegment());
      break;
    case EVENTS:
    	// Set the table
        queryBuilder.setTables(EventTable.TABLE_EVENT);
      break;
    case EVENT_ID:
    	// Set the table
        queryBuilder.setTables(EventTable.TABLE_EVENT);
      // Adding the ID to the original query
      queryBuilder.appendWhere(EventTable.COLUMN_ID + "="
          + uri.getLastPathSegment());
      break;  
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }//end of switch (uriType)

    SQLiteDatabase db = database.getWritableDatabase();
    Cursor cursor = queryBuilder.query(db, projection, selection,
        selectionArgs, null, null, sortOrder);
    // Make sure that potential listeners are getting notified
    cursor.setNotificationUri(getContext().getContentResolver(), uri);

    return cursor;
  }//end public Cursor query()

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    //int rowsDeleted = 0;
    long id = 0;
    switch (uriType) {
    case TASKS:
      id = sqlDB.insert(TaskTable.TABLE_TASK, null, values);
      break;
    case KIDS:
        id = sqlDB.insert(KidTable.TABLE_KID, null, values);
        break;
    case EVENTS:
        id = sqlDB.insert(EventTable.TABLE_EVENT, null, values);
        break;    
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }//end switch (uriType)
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.parse(BASE_PATH + "/" + id);
  }//end public Uri insert()

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    int rowsDeleted = 0;
    String id = uri.getLastPathSegment();
    
    switch (uriType) {
    case TASKS:
      rowsDeleted = sqlDB.delete(TaskTable.TABLE_TASK, selection,
          selectionArgs);
      break;
    case TASK_ID:
      if (TextUtils.isEmpty(selection)) {
        rowsDeleted = sqlDB.delete(TaskTable.TABLE_TASK,
            TaskTable.COLUMN_ID + "=" + id, 
            null);
      } else {
        rowsDeleted = sqlDB.delete(TaskTable.TABLE_TASK,
            TaskTable.COLUMN_ID + "=" + id 
            + " and " + selection,
            selectionArgs);
      }
      break;
    case KIDS:
        rowsDeleted = sqlDB.delete(KidTable.TABLE_KID, selection,
            selectionArgs);
        break;
    case KID_ID:
        if (TextUtils.isEmpty(selection)) {
          rowsDeleted = sqlDB.delete(KidTable.TABLE_KID,
              KidTable.COLUMN_ID + "=" + id, 
              null);
        } else {
          rowsDeleted = sqlDB.delete(KidTable.TABLE_KID,
              KidTable.COLUMN_ID + "=" + id 
              + " and " + selection,
              selectionArgs);
        }
        break;
      case EVENTS:
          rowsDeleted = sqlDB.delete(EventTable.TABLE_EVENT, selection,
              selectionArgs);
          break;
        case EVENT_ID:
          if (TextUtils.isEmpty(selection)) {
            rowsDeleted = sqlDB.delete(EventTable.TABLE_EVENT,
                EventTable.COLUMN_ID + "=" + id, 
                null);
          } else {
            rowsDeleted = sqlDB.delete(EventTable.TABLE_EVENT,
                EventTable.COLUMN_ID + "=" + id 
                + " and " + selection,
                selectionArgs);
          }
          break;    
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }//end switch (uriType)
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsDeleted;
  }//end public int delete()

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {

    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    int rowsUpdated = 0;
    String id = uri.getLastPathSegment();

    switch (uriType) {
    case TASKS:
      rowsUpdated = sqlDB.update(TaskTable.TABLE_TASK, 
          values, 
          selection,
          selectionArgs);
      break;
    case TASK_ID:
      if (TextUtils.isEmpty(selection)) {
        rowsUpdated = sqlDB.update(TaskTable.TABLE_TASK, 
            values,
            TaskTable.COLUMN_ID + "=" + id, 
            null);
      } else {
        rowsUpdated = sqlDB.update(TaskTable.TABLE_TASK, 
            values,
            TaskTable.COLUMN_ID + "=" + id 
            + " and " 
            + selection,
            selectionArgs);
      }
      break;
    case KIDS:
        rowsUpdated = sqlDB.update(KidTable.TABLE_KID, 
            values, 
            selection,
            selectionArgs);
        break;
      case KID_ID:
        if (TextUtils.isEmpty(selection)) {
          rowsUpdated = sqlDB.update(KidTable.TABLE_KID, 
              values,
              KidTable.COLUMN_ID + "=" + id, 
              null);
        } else {
          rowsUpdated = sqlDB.update(KidTable.TABLE_KID, 
              values,
              KidTable.COLUMN_ID + "=" + id 
              + " and " 
              + selection,
              selectionArgs);
        }
        break;  
      case EVENTS:
          rowsUpdated = sqlDB.update(EventTable.TABLE_EVENT, 
              values, 
              selection,
              selectionArgs);
          break;
        case EVENT_ID:
          if (TextUtils.isEmpty(selection)) {
            rowsUpdated = sqlDB.update(EventTable.TABLE_EVENT, 
                values,
                EventTable.COLUMN_ID + "=" + id, 
                null);
          } else {
            rowsUpdated = sqlDB.update(EventTable.TABLE_EVENT, 
                values,
                EventTable.COLUMN_ID + "=" + id 
                + " and " 
                + selection,
                selectionArgs);
          }
          break;      
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }//end switch (uriType)
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsUpdated;
  }//end public int update()

  private void checkColumns(String[] projection) {
    String[] available = { TaskTable.COLUMN_ID, TaskTable.COLUMN_TASKNAME,
        TaskTable.COLUMN_ONETIME,
        KidTable.COLUMN_ID, KidTable.COLUMN_KIDNAME, KidTable.COLUMN_CURRENTCASH,
        KidTable.COLUMN_LASTCASH,
        EventTable.COLUMN_ID, EventTable.COLUMN_TASKTABLEID,
        EventTable.COLUMN_KIDTABLEID, EventTable.COLUMN_CASHVALUE
         };
    if (projection != null) {
      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
      // Check if all columns which are requested are available
      if (!availableColumns.containsAll(requestedColumns)) {
        throw new IllegalArgumentException("Unknown columns in projection");
      }
    }
  }// end of private void checkColumns

} //end of class FamCashContentProvider