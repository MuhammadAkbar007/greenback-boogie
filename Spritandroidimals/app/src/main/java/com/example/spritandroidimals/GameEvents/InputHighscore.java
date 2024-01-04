package com.example.spritandroidimals.GameEvents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;


import static com.example.spritandroidimals.R.layout.high_scores;
/**
 * Created by kugl1893 on 1/21/2017.
 */

public class InputHighscore extends AppCompatActivity
{
  public static final int MAX_NUM_SCORES = 10;
  public static final int NUM_DATA = 3;
  private static final String FILE_NAME = "highscore";

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);
    setContentView (high_scores);
    String data = "";
    String[] mNamesAndScores = { "" };
    String temp = "";
    int c;
    try
    {
      FileInputStream highscoreinput = this.openFileInput ("highscore");
      while ((c = highscoreinput.read ()) != -1)
      {
        temp = temp + Character.toString ((char) c);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }

    int size = mNamesAndScores.length;

    LinearLayout.LayoutParams linearContainerParams = new LinearLayout.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);

    LinearLayout.LayoutParams linearWidgetParams = new LinearLayout.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

    TableLayout.LayoutParams tableContainerParams = new TableLayout.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);

    TableLayout.LayoutParams tableWidgetParams = new TableLayout.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

    TableRow.LayoutParams rowContainerParams = new TableRow.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);

    TableRow.LayoutParams rowWidgetParams = new TableRow.LayoutParams (
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

    LinearLayout mRoot = new LinearLayout (this);
    mRoot.setOrientation (LinearLayout.VERTICAL);
    mRoot.setBackgroundColor (Color.LTGRAY);
    mRoot.setLayoutParams (linearContainerParams);

    TableLayout mTableLayout = new TableLayout (this);
    mTableLayout.setOrientation (TableLayout.VERTICAL);
    mTableLayout.setBackgroundColor (Color.BLUE);
    mTableLayout.setLayoutParams (tableContainerParams);
    mRoot.addView (mTableLayout);

    TableRow mTableRow = new TableRow (this);
    mTableRow.setOrientation (TableLayout.VERTICAL);
    mTableRow.setBackgroundColor (Color.GREEN);
    mTableRow.setLayoutParams (rowContainerParams);
    mTableLayout.addView (mTableRow);

    TextView mTextView = new TextView (this);
    mTextView.setText ("Name");
    mTextView.setTextColor (Color.BLACK);
    mTextView.setGravity (Gravity.LEFT);
    mTextView.setLayoutParams (rowWidgetParams);
    mTableRow.addView (mTextView);

    mTextView = new TextView (this);
    mTextView.setText ("Score");
    mTextView.setTextColor (Color.BLACK);
    mTextView.setGravity (Gravity.RIGHT);
    mTextView.setLayoutParams (rowWidgetParams);
    mTableRow.addView (mTextView);
/*
    for (int i = 0; i < MAX_NUM_SCORES; i++)
    {
      mTextView = new TextView (this);
      mTextView.setText (mNamesAndScores[i]);
      mTextView.setTextColor (Color.BLACK);
      mTextView.setGravity (Gravity.LEFT);
      mTextView.setLayoutParams (rowWidgetParams);
      mTableRow.addView (mTextView);

      mTextView = new TextView (this);
      mTextView.setText (mNamesAndScores[++i]);
      mTextView.setTextColor (Color.BLACK);
      mTextView.setGravity (Gravity.RIGHT);
      mTextView.setLayoutParams (rowWidgetParams);
      mTableRow.addView (mTextView);
    }
*/
    setContentView (mRoot);

    /*
    printHighScoresToScreen (names, scores, this);
    */
  }
}