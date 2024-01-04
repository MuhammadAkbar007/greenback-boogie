package com.example.spritandroidimals.GameEvents;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.Animations.GameStart;
import com.example.spritandroidimals.title_screen;


public class EndGame extends AppCompatActivity
{
  private String mName;
  private GameStart gamestart = new GameStart ();
  private OutputHighscores highscores;

  public void endGame (Context context)
  {
    final int mSeconds = (int) SystemClock.currentThreadTimeMillis () / 1000;
    final int mMinutes = mSeconds / 60;
    final Context mContext = context;

    runOnUiThread (new Runnable ()
    {
      @Override
      public void run ()
      {
        //Create pop up
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder (mContext);

        alertBuilder.setTitle ("Please Enter Name:");
        alertBuilder.setMessage ("Time: " + mMinutes + ":" + mSeconds);

        final EditText nameTextBox = new EditText (mContext);
        nameTextBox.setInputType (InputType.TYPE_CLASS_TEXT);
        alertBuilder.setView (nameTextBox)
            .setPositiveButton ("Done", new DialogInterface.OnClickListener ()
            {
              @Override
              public void onClick (DialogInterface dialog, int which)
              {
                mName = nameTextBox.getText ().toString ();
                highscores = new OutputHighscores (mContext, mName, mMinutes, mSeconds);
                Intent homeScreen = new Intent (mContext, title_screen.class);
                mContext.startActivity (homeScreen);
              }
            }).show ();
      }
    });

  }

}
