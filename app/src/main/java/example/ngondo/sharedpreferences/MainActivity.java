package example.ngondo.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(i, SETTINGS_INFO);
            }
        });

    }
    //Called whenever we make changes to the SettingsActivity and it's data passed on to the
    // MainActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTINGS_INFO){
            updateNoteText();
        }
    }

    /*
    * handles changes made to the text from the home activity
    * */
    private void updateNoteText(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //CHeck whether the bold checkbox was clicked
        if(sharedPreferences.getBoolean("pref_text_bold", false)){
            notesEditText.setTypeface(null , Typeface.BOLD);
        }
        else{
            notesEditText.setTypeface(null , Typeface.BOLD);
        }

        //Changes for the text size
        String textSizestr = sharedPreferences.getString("pref_text_size","16");
        //convert to float
        float textSizeFloat = Float.parseFloat(textSizestr);
        //Pass the actual value of the float
        notesEditText.setTextSize(textSizeFloat);
    }

    /*
        * Now let's capture data saved on bundle using the key "NOTES" whenever android shuts down
        * the app.
        * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NOTES", notesEditText.getText().toString());
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
