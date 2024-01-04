package com.example.spritandroidimals.GameEvents;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.R;


import static com.example.spritandroidimals.R.layout.settings_menu;
import com.example.spritandroidimals.title_screen;
/**
 * Class for displaying settings submenu
 */
public class Settings extends AppCompatActivity
{
  private boolean mMusicSelection;
  private boolean mSoundSelection;

  private Switch mMusicSwitch;
  private Switch mSoundSwitch;
  private MediaPlayer introMusic;

  /**
   * Declares music and sound setting switches to true, and sets the view
   *
   * @param savedInstanceState : the previous instance of the device
   */
  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate (savedInstanceState);
    setContentView(settings_menu);

    mMusicSwitch = (Switch) findViewById (R.id.swtMusic);
    mMusicSwitch.setChecked (true);
    mMusicSelection = true;

    mSoundSwitch = (Switch) findViewById (R.id.swtSound);
    mSoundSwitch.setChecked (true);
    mSoundSelection = true;

  }

  /**
   * Calls overriden onPause by pressing back button
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    onPause();
  }

  /**
   * Gets the music file, if the music selection is true: starts music,
   * sets switches based on the boolean true/false statements
   */
  @Override
  protected void onStart ()
  {
    super.onStart ();

    introMusic = MediaPlayer.create (this, R.raw.intro_music);
    if (mMusicSelection)
    {
      introMusic.setLooping (true);
      introMusic.start ();
    }

    mMusicSwitch.setChecked (mMusicSelection);
    mSoundSwitch.setChecked (mSoundSelection);
  }

  /**
   * Creates 2 setOnCheckedChangeListeners, one for music, one for sound
   * and sets the boolean true/false statements to what is switched
   */
  @Override
  protected void onResume ()
  {
    super.onResume ();

    mMusicSwitch.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
    {
      @Override
      public void onCheckedChanged (CompoundButton buttonView,
          boolean isChecked)
      {

        mMusicSelection = isChecked;
        if (isChecked)
        {
          introMusic.start ();
        }
        else if (!isChecked)
        {
          introMusic.pause ();
        }
      }
    });

    mSoundSwitch.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ()
    {
      @Override
      public void onCheckedChanged (CompoundButton buttonView,
          boolean isChecked)
      {
        mSoundSelection = isChecked;
      }
    });
  }

  /**
   * Stops music, creates new instance to title screen, while passing the
   * extra true/false statements, and launches the title screen
   */
  @Override
  protected void onPause ()
  {
    super.onPause ();

    introMusic.stop ();
    Intent intent = new Intent (getApplicationContext(), title_screen.class);
    intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra ("MusicSwitch", mMusicSelection);
    intent.putExtra ("SoundSwitch", mSoundSelection);
    startActivity (intent);
  }


}
