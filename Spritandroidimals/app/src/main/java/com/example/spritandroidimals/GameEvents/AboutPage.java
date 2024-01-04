package com.example.spritandroidimals.GameEvents;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.R;

import static com.example.spritandroidimals.R.layout.about_page;
/**
 * Class to create about page
 */
public class AboutPage extends AppCompatActivity
{
  private MediaPlayer music;

  /**
   * Declares background music and sets the view
   * @param savedInstanceState : the previous instance of the device
   */
  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);
    setContentView(about_page);

    music = MediaPlayer.create (this, R.raw.intro_music);

  }

  /**
   * Retrieves music switch settings, plays music if true
   */
  @Override
  protected void onStart ()
  {
    super.onStart ();
    boolean mMusicSelection = getIntent ().getBooleanExtra ("MusicSwitch", true);

    if (mMusicSelection)
    {
      music.start ();
    }
  }

  /**
   * Stops music and destroys
   */
  @Override
  public void onDestroy ()
  {
    music.stop ();
    super.onDestroy ();
  }
}
