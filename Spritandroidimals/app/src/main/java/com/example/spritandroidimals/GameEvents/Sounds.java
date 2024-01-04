package com.example.spritandroidimals.GameEvents;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spritandroidimals.R;

/**
 * Class for playing sounds
 */
public class Sounds extends AppCompatActivity
{
  static boolean on = true;
  MediaPlayer log_sound;
  MediaPlayer bear_trap_sound;
  MediaPlayer barb_wire_sound;
  MediaPlayer pit_sound;
  MediaPlayer plessy_sound;
  MediaPlayer jump_sound;

  /**
   * Initializes all sounds to the media player
   *
   * @param context : the application resources
   */
  public Sounds (Context context)
  {
    log_sound = MediaPlayer.create (context, R.raw.hitting_log);
    bear_trap_sound = MediaPlayer.create (context, R.raw.bear_trap);
    barb_wire_sound = MediaPlayer.create (context, R.raw.barb_wire);
    pit_sound = MediaPlayer.create (context, R.raw.pit);
    plessy_sound = MediaPlayer.create (context, R.raw.plessy);
    jump_sound = MediaPlayer.create (context, R.raw.jump);
  }

  /**
   * Sets sound on/off
   *
   * @param temp : true if on, false if off
   */
  public void setOn (boolean temp)
  {
    on = temp;
  }

  /**
   * Plays sound for jump
   */
  public void playJump ()
  {
    if (on)
    {
      jump_sound.start ();
    }
  }

  /**
   * Plays sound for log collision
   */
  public void playLog ()
  {
    if (on)
    {
      log_sound.start ();
    }
  }

  /**
   * Plays sound for trap collision
   */
  public void playTrap ()
  {
    if (on)
    {
      bear_trap_sound.start ();
    }
  }

  /**
   * Plays sound for wire collision
   */
  public void playWire ()
  {
    if (on)
    {
      barb_wire_sound.start ();
    }
  }

  /**
   * Plays sound for pit collision
   */
  public void playPit ()
  {
    if (on)
    {
      pit_sound.start ();
    }
  }

  /**
   * Plays sound for Plessy (enemy) collision
   */
  public void playPlessy ()
  {
    if (on)
    {
      plessy_sound.start ();
    }
  }


}
