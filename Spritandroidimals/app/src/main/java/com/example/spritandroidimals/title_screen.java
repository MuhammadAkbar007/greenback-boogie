package com.example.spritandroidimals;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.Animations.GameStart;
import com.example.spritandroidimals.Animations.Vibration;
import com.example.spritandroidimals.GameEvents.AboutPage;
import com.example.spritandroidimals.GameEvents.InputHighscore;
import com.example.spritandroidimals.GameEvents.MapMaker;
import com.example.spritandroidimals.GameEvents.Settings;


/**
 * Class for starting and displaying the title screen
 */
public class title_screen extends AppCompatActivity
{

  private Vibration cVibration;
  private MediaPlayer introMusic;
  private Boolean mMusicSelection;
  private Boolean mSoundSelection;

  /**
   * Sets the view to the title screen, while setting up vibrations and music
   *
   * @param savedInstanceState : the previous instance of the device
   */
  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_title_screen);

    cVibration = new Vibration((Vibrator) this.getSystemService (Context.VIBRATOR_SERVICE));
    introMusic = MediaPlayer.create (this, R.raw.intro_music);
    introMusic.setLooping (true);

  }

  /**
   * Receives music and sound selections in settings menu, and restarts music if true
   */
  @Override
  protected void onResume (){
    super.onResume ();
    mMusicSelection = getIntent ().getBooleanExtra ("MusicSwitch", true);
    mSoundSelection = getIntent ().getBooleanExtra ("SoundSwitch", true);

    if (mMusicSelection)
    {
      introMusic = MediaPlayer.create (this, R.raw.intro_music);
      introMusic.setLooping (true);
      introMusic.start ();
    }

  }

  /**
   * Pauses the menu music, and pauses the activity
   */
  @Override
  protected void onPause ()
  {
    super.onPause ();

    introMusic.pause ();
  }

  /**
   * Vibrates short, stops music, creates a new instance linked to game start,
   * and passes the extra music and sound switches, and starts the activity
   *
   * @param view : the current view the device is in
   */
  public void startGame (View view)
  {
    cVibration.activateVibrationShort ();
    introMusic.stop ();

    Intent game = new Intent(this, GameStart.class);
    game.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    game.putExtra ("MusicSwitch", mMusicSelection);
    game.putExtra ("SoundSwitch", mSoundSelection);
    startActivity (game);
  }

  /**
   * Vibrates short, creates a new instance linked to high score, and passes
   * the music switch from settings, and starts the activity
   *
   * @param view : the current view the device is in
   */
  public void startScores (View view)
  {
    cVibration.activateVibrationShort ();
    Intent scores = new Intent(this, InputHighscore.class);
    scores.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    scores.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
    scores.putExtra ("MusicSwitch", mMusicSelection);
    startActivity (scores);
  }

  /**
   * Vibrates short, creates a new instance linked to settings, starts the
   * activity
   *
   * @param view : the current view the device is in
   */
  public void settings (View view)
  {
    cVibration.activateVibrationShort ();
    Intent settings = new Intent(this, Settings.class);
    settings.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity (settings);

  }


  public void mapEditor (View view)
  {
    cVibration.activateVibrationShort ();
    Intent settings = new Intent(this, MapMaker.class);
    settings.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //    settings.addFlags (Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity (settings);

  }


  /**
   * Vibrates short, creates a new instance linked to the about page, and passes
   * the extra music switch boolean from settings, and starts the acitivity
   *
   * @param view : the current view the device is in
   */

  public void about (View view)
  {
    cVibration.activateVibrationShort ();
    Intent about = new Intent(this, AboutPage.class);
    about.putExtra ("MusicSwitch", mMusicSelection);
    startActivity (about);
  }

  /**
   * Stops the music, creates a new activity manager, creates a new instance,
   * calls the start main instance, kills all background process from the
   * activity manager for the project, "cs.pacificu.edu.spritandroidimals"
   */
  @Override
  public void onBackPressed() {

    super.onBackPressed();
    introMusic.stop();
    ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

    Intent startMain = new Intent(Intent.ACTION_MAIN);
    startMain.addCategory(Intent.CATEGORY_HOME);
    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.startActivity(startMain);

    am.killBackgroundProcesses("cs.pacificu.edu.spritandroidimals");
  }

  /**
   * Stops the music, creates a new activity manager, creates a new instance,
   * calls the start main instance, kills all background process from the
   * activity manager for the project, "cs.pacificu.edu.spritandroidimals"
   *
   * @param view : the current view the device is in
   */
  public void exitGame (View view)
  {
    introMusic.stop ();
    ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);

    Intent startMain = new Intent(Intent.ACTION_MAIN);
    startMain.addCategory(Intent.CATEGORY_HOME);
    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.startActivity(startMain);

    am.killBackgroundProcesses("cs.pacificu.edu.spritandroidimals");
  }
}
