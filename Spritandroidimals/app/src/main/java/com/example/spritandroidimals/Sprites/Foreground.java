package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.os.SystemClock;
import android.view.Display;

import com.example.spritandroidimals.R;


/**
 * Class for drawing and displaying foreground images
 */

public class Foreground extends Ground
{
  private final static int mMIDGROUND_MOD = 2;
  private final static int mFOREGROUND_MOD = 3;
  private final static int mMIDGROUND_FREQ = 1;
  private final static int mFOREGROUND_FREQ = 50;
  private int mSpeed = getSpeed ();
  private int mUpdateFreq;
  private int mSpeedMod;
  private boolean mbRandom = true;

  /**
   * Constructor for foreground all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprite ID
   * @param SpriteX        - The sprite starting X
   * @param SpriteY        - The sprite starting Y
   */
  public Foreground (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, 0, 0);
  }

  /**
   * Constructor for foreground scaling all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprite ID
   * @param SpriteX        - The sprite starting X
   * @param SpriteY        - The sprite starting Y
   */
  public Foreground (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, newHeight,
        newWidth, 0, 0);
  }

  /**
   * Updates the foreground images at different frequencies,
   * and moves them along the screen at different speeds
   * depending on what the image is
   */
  public void update ()
  {
    // Middleground images
    if (this.getSpriteID () == R.drawable.snowcliff_1
      || this.getSpriteID () == R.drawable.snowcliff_2)
    {
      mUpdateFreq = mMIDGROUND_FREQ;
      mSpeedMod = mMIDGROUND_MOD;
    }

    // Foreground images
    else
    {
      mUpdateFreq = mFOREGROUND_FREQ;
      mSpeedMod = mFOREGROUND_MOD;
    }

    // Image is off screen
    if (0 >= (getSpriteXTop () + getFrameWidth ())
        && (0 == (SystemClock.currentThreadTimeMillis () % mUpdateFreq)))
    {
      setSpriteXTop (getDisplayWidth ());
      mbRandom = true;
    }

    setSpriteXTop (getSpriteXTop () - (mSpeed * mSpeedMod));
  }

  /**
   * Sets private member variable to false when images have been randomized
   */
  public void randomize ()
  {
    mbRandom = false;
  }

  /**
   * Returns the current randomize status
   * @return true if the images need to be randomized, false otherwise
   */
  public boolean getRandom()
  {
    return mbRandom;
  }
}
