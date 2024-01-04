package com.example.spritandroidimals.Animations;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.example.spritandroidimals.GameEvents.EndGame;
import com.example.spritandroidimals.GameEvents.Sounds;
import com.example.spritandroidimals.R;

import java.io.IOException;


/**
 * Class for starting and running the game
 */
public class GameStart extends Activity
{
  private Display mDisplay;
  private GraphicsView mGraphicsView;

  private EndGame gameEnd;

  private Vibration cVibrator;
  private MediaPlayer gameMusic;
  private Sounds cSounds;


  private boolean mbDefaultMap;
  /**
   * Declares all member variables when one instance is created
   *
   * @param savedInstanceState : the previous state of the device
   */

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);

    requestWindowFeature (Window.FEATURE_ACTION_BAR);
    this.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    WindowManager window = getWindowManager ();

    mbDefaultMap = getIntent ().getBooleanExtra ("DefaultMap", true);

    mDisplay = window.getDefaultDisplay ();

    try
    {
      mGraphicsView = new GraphicsView (this, mDisplay, mbDefaultMap);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }

    gameMusic = MediaPlayer.create (this, R.raw.game_music);
    gameMusic.setLooping (true);
    cSounds = new Sounds(this);

    cVibrator = new Vibration (
        (Vibrator) this.getSystemService (Context.VIBRATOR_SERVICE));

    setContentView (mGraphicsView);
  }

  /**
   * Resumes the game onResume
   */
  protected void onResume ()
  {
    super.onResume ();
    Boolean mMusicSelection = getIntent ().getBooleanExtra ("MusicSwitch", true);
    Boolean mSoundSelection = getIntent ().getBooleanExtra ("SoundSwitch", true);
    if (mMusicSelection)
    {
      gameMusic.start ();
    }

    if (mSoundSelection)
    {
      cSounds.setOn (true);
    }
    else if (!mSoundSelection)
    {
      cSounds.setOn (false);
    }
    //Executes the game thread
    mGraphicsView.resume ();
  }

  /**
   * Pauses the game thread when the app isn't in focus
   */
  protected void onPause ()
  {
    super.onPause ();

    gameMusic.stop ();


    //Tries to pause the thread
    mGraphicsView.pause ();
  }

  /**
   * Handles when the back button is pressed
   */
  @Override
  public void onBackPressed()
  {
    super.onBackPressed ();
  }
}
