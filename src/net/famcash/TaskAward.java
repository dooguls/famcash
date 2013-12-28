package net.famcash;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri; 
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import net.famcash.contentprovider.FamCashContentProvider;
import net.famcash.database.*;

public class TaskAward extends Activity {

    //Fields to contain the current position and display contents of the spinners
    protected int mPos_task;
    protected int mPos_kid;
    protected String mSelection_task;
    protected String mSelection_kid;
    private Button mTaskSubmitButton;

    //ArrayAdapter connects the spinner widget to array-based data.
    protected ArrayAdapter<CharSequence> mAdapter_task;
    protected ArrayAdapter<CharSequence> mAdapter_kid;

    //The initial position of the spinner when it is first installed.
    public static final int DEFAULT_POSITION = 2;

    //properties file stores the position and
    //selection when the activity is not loaded.
    public static final String TASK_PREFERENCES_FILE = "TaskSpinnerPrefs";
    public static final String KID_PREFERENCES_FILE = "KidSpinnerPrefs";

    
    //These values are used to read and write the properties file.
    //PROPERTY_DELIMITER delimits the key and value in a Java properties file.
    //The "marker" strings are used to write the properties into the fill
    public static final String PROPERTY_DELIMITER = "=";

    //The key or label for "position" in the preferences file
    public static final String POSITION_KEY = "Position";

    //The key or label for "selection" in the preferences file
    public static final String SELECTION_KEY = "Selection";

    public static final String POSITION_MARKER =
            POSITION_KEY + PROPERTY_DELIMITER;

    public static final String SELECTION_MARKER =
            SELECTION_KEY + PROPERTY_DELIMITER;
	
    private Uri awardUri;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_award);
        
        // Set up taskPicker spinner
        Spinner task_spinner = (Spinner) findViewById(R.id.spinner_taskPicker);
        this.mAdapter_task = ArrayAdapter.createFromResource(this,
             R.array.task_list, android.R.layout.simple_spinner_dropdown_item);
        //task_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        task_spinner.setAdapter(this.mAdapter_task);
        OnItemSelectedListener spinnerListener_task = new myOnItemSelectedListener(this,this.mAdapter_task);
        task_spinner.setOnItemSelectedListener(spinnerListener_task);
        
        // Set up kidPicker spinner
        Spinner kid_spinner = (Spinner) findViewById(R.id.spinner_kidPicker);
        this.mAdapter_kid = ArrayAdapter.createFromResource(this,
             R.array.kid_list, android.R.layout.simple_spinner_dropdown_item);
        //kid_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kid_spinner.setAdapter(this.mAdapter_kid);    
        OnItemSelectedListener spinnerListener_kid = new myOnItemSelectedListener(this,this.mAdapter_kid);
        kid_spinner.setOnItemSelectedListener(spinnerListener_kid);
        
        // Set up button handler
        mTaskSubmitButton = (Button) findViewById(R.id.button_taskSubmit);
        mTaskSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        
        Bundle extras = getIntent().getExtras();
        // check from the saved Instance
        awardUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState.getParcelable(FamCashContentProvider.CONTENT_ITEM_TYPE);
        //or passed from another activity
        if (extras != null) {
        	awardUri = extras.getParcelable(FamCashContentProvider.CONTENT_ITEM_TYPE);
        }
        
        
    }//end of onCreate
    
    public class myOnItemSelectedListener implements OnItemSelectedListener {

        ArrayAdapter<CharSequence> mLocalAdapter;
        Activity mLocalContext;

        /**
         *  Constructor
         *  @param c - The activity that displays the Spinner.
         *  @param ad - The Adapter view that
         *    controls the Spinner.
         *  Instantiate a new listener object.
         */
        public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {

          this.mLocalContext = c;
          this.mLocalAdapter = ad;

        }

        /**
         * When the user selects an item in the spinner, this method is invoked by the callback
         * chain. Android calls the item selected listener for the spinner, which invokes the
         * onItemSelected method.
         *
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *  android.widget.AdapterView, android.view.View, int, long)
         * @param parent - the AdapterView for this listener
         * @param v - the View for this listener
         * @param pos - the 0-based position of the selection in the mLocalAdapter
         * @param row - the 0-based row number of the selection in the View
         */
        public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {

        	if (this.mLocalAdapter == TaskAward.this.mAdapter_task)
        	{
        		TaskAward.this.mPos_task = pos;
        		TaskAward.this.mSelection_task = parent.getItemAtPosition(pos).toString();
        	}
        	else
        	{
        		TaskAward.this.mPos_kid = pos;
        		TaskAward.this.mSelection_kid = parent.getItemAtPosition(pos).toString();
        	}
            /*
             * Set the value of the text field in the UI
             * Want to make this write to the text file storing what was selected
             */
            //TextView resultText = (TextView)findViewById(R.id.SpinnerResult);
            //resultText.setText(TaskAward.this.mSelection);
        }

        /**
         * The definition of OnItemSelectedListener requires an override
         * of onNothingSelected(), even though this implementation does not use it.
         * @param parent - The View for this Listener
         */
        public void onNothingSelected(AdapterView<?> parent) {

            // do nothing

        }
    }// end myOnItemSelectedListener

    private void onSaveButtonClicked() {
        //Log.v(TAG, "Save button clicked");
        recordTaskEntry();
        //finish();
    }
    
    protected void recordTaskEntry() {
    	String taskSpinnerSelection = getTaskSpinnerSelection();
        String kidSpinnerSelection = getKidSpinnerSelection();
        
        //need to write this to a local database, but don't know how to do that yet, so we Toast!
        Context ctx = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(ctx, "User selected task: " + taskSpinnerSelection + " for kid: " + kidSpinnerSelection + "start db work", duration);
        toast.show();
        
        // do insert
        
        //don't record anything if the task and kid are blank
        if (taskSpinnerSelection.length() == 0 && kidSpinnerSelection.length() == 0) {
        	return;
        }
        
        ContentValues values = new ContentValues();
        float taskValue = 0;
        int kidId = 0;
        int taskId = 0;
        //query for kid id based on kid name, then insert that kidID into the event table
        String[] kidProjection = { KidTable.COLUMN_CURRENTCASH, KidTable.COLUMN_ID, KidTable.COLUMN_KIDNAME,
        		KidTable.COLUMN_LASTCASH };
        //Uri kidUri = new Uri ("content://net.famcash.contentprovider/famcash/20/");
        String kidWhere = KidTable.COLUMN_KIDNAME + " like '%" + kidSpinnerSelection + "%'";
        Cursor kidCursor = getContentResolver().query(awardUri, kidProjection, kidWhere, null, null); //don't think this is right uri
        if (kidCursor != null) {
        	kidCursor.moveToFirst();
        	kidId = kidCursor.getInt(kidCursor.getColumnIndexOrThrow(KidTable.COLUMN_ID));
        }
        values.put(EventTable.COLUMN_KIDTABLEID, kidId);
        //query for taskid and task value based on task spinner selection, then insert that stuff into the event table
        String[] taskProjection = { TaskTable.COLUMN_ID, TaskTable.COLUMN_ONETIME, TaskTable.COLUMN_TASKNAME, TaskTable.TABLE_TASK };
        //Uri taskUri = new (Uri ("content://net.famcash.contentprovider/famcash/10/");
        String taskWhere = TaskTable.COLUMN_TASKNAME + " like '%" + taskSpinnerSelection + "%'";
        Cursor taskCursor = getContentResolver().query(awardUri, taskProjection, taskWhere, null, null);//don't think this is the right uri
        if (taskCursor != null) {
        	taskCursor.moveToFirst();
        	taskId = taskCursor.getInt(taskCursor.getColumnIndexOrThrow(TaskTable.COLUMN_ID));
        	taskValue = taskCursor.getFloat(taskCursor.getColumnIndexOrThrow(TaskTable.COLUMN_ID));
        }
        values.put(EventTable.COLUMN_TASKTABLEID, taskId);
        values.put(EventTable.COLUMN_CASHVALUE, taskValue);
        //need to be aware that if the insert explicitly sets DATEDONE to null, then i'll get null for EventTable.COLUMN_DATEDONE
        //hopefully I don't have to do anything and the database will auto insert dates into the rows like I want.
        
        if(awardUri == null) {
        	awardUri = getContentResolver().insert(FamCashContentProvider.CONTENT_URI, values);
        }
        toast = Toast.makeText(ctx, "Finished writing database. kidID: " + kidId + " taskID: " + taskId + " taskValue:" + taskValue, duration);
        toast.show();
    }//end of recordTaskEntry
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_task_award, menu);
        return true;
    }//end onCreateOptionsMenu
    
    // getters and setters for the spinners
    public int getTaskSpinnerPosition() {
        return this.mPos_task;
    }
    public int getKidSpinnerPosition() {
        return this.mPos_kid;
    }

    public void setTaskSpinnerPosition(int pos) {
        this.mPos_task = pos;
    }
    public void setKidSpinnerPosition(int pos) {
        this.mPos_kid = pos;
    }
    
    public String getTaskSpinnerSelection() {
        return this.mSelection_task;
    }
    public String getKidSpinnerSelection() {
        return this.mSelection_kid;
    }
    
    public void setTaskSpinnerSelection(String selection) {
        this.mSelection_task = selection;
    }
    public void setkidSpinnerSelection(String selection) {
        this.mSelection_kid = selection;
    }    

}//end class TaskAward
