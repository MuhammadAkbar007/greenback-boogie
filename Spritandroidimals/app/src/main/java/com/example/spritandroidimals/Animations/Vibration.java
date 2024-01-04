package com.example.spritandroidimals.Animations;

/**
 * The vibration class allows us to call vibration on collisions in any view
 */
import android.os.Vibrator;

public class Vibration
{
  final int mShort = 500;
  final int mLong = 1000;
  Vibrator mVibrator;

  /**
   * Initializes the vibration services for the class
   * @param vib
   */
  public Vibration (Vibrator vib)
  {
    mVibrator = vib;
  }

  /**
   * Activates a short half second vibration
   */
  public void activateVibrationShort ()
  {
    mVibrator.vibrate (mShort);
  }

  /**
   * Activates a long 1 second vibration
   */
  public void activateVibrationLong ()
  {
    mVibrator.vibrate (mLong);
  }

  /**
   * Activates a vibration for how ever many milliseconds is passed in
   *
   * @param vibrationLength
   */
  public void activateVibrationSet (int vibrationLength)
  {
    mVibrator.vibrate (vibrationLength);
  }
}
