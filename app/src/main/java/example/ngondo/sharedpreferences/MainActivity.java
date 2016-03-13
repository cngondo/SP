package example.ngondo.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText notesEditText;
    Button btnSettings;
    //when activity calls for an intent to execute
    private static final int SETTINGS_INFO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesEditText = (EditText) findViewById(R.id.notesEditText);
        /*
        * Bundle creates a key-value pair that saves data whenever the orientation of the device
        * changes or android OS shuts down the app.
        *
        * Let's check if bundle has any data first. If it has any then we set the edit text to
        * save that data
        * */
        if(savedInstanceState != null){
            String notes = savedInstanceState.getString("NOTES");
            notesEditText.setText(notes);

        }
        /*
        * Retrieving data when we reopen the app
        * */
        String sPNotes = getPreferences(Context.MODE_PRIVATE).getString("NOTES","EMPTY");

        if(!sPNotes.equals("EMPTY")){
            notesEditText.setText(sPNotes);
        }

    }
    /*
    * Now let's capture data saved on bundle using the key "NOTES" whenever android shuts down
    * the app.
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NOTES",notesEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /*
    * Saving data when the user shuts down the app
    * */
    private void saveSettings(){
        SharedPreferences.Editor sPEditor = getPreferences(Context.MODE_PRIVATE).edit();
        sPEditor.putString("NOTES", notesEditText.getText().toString());
        //commit changes made
        sPEditor.commit();
    }
    //Method called when user stops the app
    @Override
    protected void onStop() {

        saveSettings();
        super.onStop();
    }
}
