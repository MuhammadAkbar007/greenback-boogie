/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the activity for displaying scores
 */

package com.example.pop_the_balloons_game.highscores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pop_the_balloons_game.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    static final int ADD_SCORE_REQUEST = 1;  // The request code
    List<Score> scoresData; //list of scores
    ListView listView;

    int currentScore;
    int maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_actitvity);

        Intent i = getIntent();
        currentScore = i.getIntExtra("score",0);

        scoresData = new ArrayList<>();
        listView  = findViewById(R.id.scoresList);
        displayScores();
        calculateHighScore();
        //Add score only if it is high score
        if(scoresData != null){
            if(currentScore >= maxScore){
                openAddScoreActivity();
            }
        }

    }

    private void calculateHighScore() {
        for(Score score: scoresData){
            int s = Integer.parseInt(score.getScore());
            if(s > maxScore){
                maxScore = s;
            }
        }
    }


    /*
     * Method to open the add score activity
     * */
    private void openAddScoreActivity() {
        Intent addScoreIntent = new Intent(this,AddScoreActivity.class);
        addScoreIntent.putExtra("score",currentScore);
        startActivityForResult(addScoreIntent, ADD_SCORE_REQUEST);
    }


    /*
     * Helper method to display the high scores in list view after reading from the file
     * */
    private void displayScores(){
        FileIO fio = new FileIO(this);
        scoresData = fio.readFile();
        Collections.sort(scoresData);
        StableArrayAdapter scoreAdapter = new StableArrayAdapter(this,R.layout.scorelayout,scoresData);
        listView.setAdapter(scoreAdapter);
    }

    /*
     * This method is used to get the result from the Add score intent and if any score is added it writes it to file and updates list view
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_SCORE_REQUEST){
            /*If there is any new score added then update the listview with the new scores*/
            if(resultCode==RESULT_OK){

                String newName = data.getStringExtra("newName");
                String newScore = data.getStringExtra("newScore");
                String newDate = data.getStringExtra("newDate");

                Score newScoreObject = new Score(newName,newScore,newDate);

                scoresData.add(newScoreObject);

                FileIO fio = new FileIO(this);
                fio.writeFile(scoresData);
                displayScores();
            }
            if(resultCode==RESULT_CANCELED){
                /*If nothing is added in save score activity then display a message that nothing was added*/
                String message = "Nothing added";
                Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
