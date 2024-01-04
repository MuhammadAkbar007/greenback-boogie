package com.example.spritandroidimals.GameEvents;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

/**
 * Created by pinc2380 on 1/23/2017.
 */

public class OutputHighscores extends AppCompatActivity
{
  private FileOutputStream fos = null;
  private static final String FILE_NAME = "highscore";
  public static final int MAX_NUM_SCORES = 10;
  public static final int NUM_DATA = 3;

  public OutputHighscores (Context c, String name, int mins, int secs)
  {
    String s = Integer.toString (mins);
    try
    {
      fos = openFileOutput (FILE_NAME, Context.MODE_PRIVATE);
      fos.write (name.getBytes ());
      fos.write (",".getBytes ());
      fos.write (s.getBytes ());
      fos.write (",".getBytes ());
      s = Integer.toString (secs);
      fos.write (s.getBytes ());
      fos.write (",".getBytes ());
      fos.close ();

    }
    catch (Exception e)
    {
      e.getStackTrace ();
    }
  }
}
