/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the activity for adding scores
 * This activity takes user input of Name, Score, Date/Time and validates it. If the save button is pressed the data is sent to the MainActivity
 */

package com.example.pop_the_balloons_game.highscores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pop_the_balloons_game.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddScoreActivity extends AppCompatActivity {

    EditText name;
    EditText score;
    EditText date;

    MenuItem save;

    boolean buttonFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        setTitle("Add Score");

        name = findViewById(R.id.newName);
        score = findViewById(R.id.newScore);
        date = findViewById(R.id.newDate);

        Intent i = getIntent();
        int currentScore = i.getIntExtra("score",0);

        score.setText(String.valueOf(currentScore));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date.setText(sdf.format(new Date()));

        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                validate();
            }
        });


    }

    private void validate(){
        /*Checking mandatory */
        String newName = name.getText().toString();
        String newScore = score.getText().toString();
        String newDate = date.getText().toString();
        boolean nameValid = false;

        if(newName.length() > 0 && newName.length() <=30){
            name.setError(null);
            nameValid = true;
        }else{
            name.setError("Name should be between 1-30 characters");
        }
        buttonFlag = nameValid;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_score, menu);
        save = menu.findItem(R.id.save);
        save.setEnabled(buttonFlag);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.save);

        if (buttonFlag) {
            item.setEnabled(true);
            name.setError(null);
            item.getIcon().setAlpha(255);
        } else {
            // disabled
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.save){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("newName", name.getText().toString());
            returnIntent.putExtra("newScore", score.getText().toString());
            returnIntent.putExtra("newDate", date.getText().toString());

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
            return true;
        }

        return false;
    }
}
