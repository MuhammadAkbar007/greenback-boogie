package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.view.Display;

/**
 * Class for drawing and animating the ground
 */
public class Ground extends AnimatedSprite
{
  private static int mSpeed;

  /**
   * Constructor for settings_menu all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The Sprites ID
   * @param SpriteX        - The Sprites starting X
   * @param SpriteY        - The Sprites starting Y
   */
  public Ground (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int col, int num)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, 0, 0, col);

    setSpriteXTop ((mBitmapSprite.getWidth ()) * num);
    setDraw (true);
  }

  /**
   * Constructor for settings_menu all the defaults values for an drawable
   * THIS ONE IS USED TO ALSO SCALE THE BITMAP TO A SPECIFIED SIZE
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The Sprites ID
   * @param SpriteX        - The Sprites starting X
   * @param SpriteY        - The Sprites starting Y
   * @param newHeight      - The Sprites starting newHeight
   * @param newWidth       - The Sprites starting newWidth
   */
  public Ground (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth,
      int col, int num)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, newHeight,
        newWidth, 0, 0, col);

    setSpriteXTop ((newWidth) * num);
    setDraw (true);
  }

  /**
   *  Moves the ground object
   */
  public void update ()
  {
    if (0 >= (getSpriteXTop () + getFrameWidth ()))
    {
      setSpriteXTop (getDisplayWidth ());
    }

    setSpriteXTop (getSpriteXTop () - mSpeed);
  }

  /**
   * Sets the object's movement speed
   *
   * @param mSpeed - Sets the game Speed
   */
  public static void setSpeed (int mSpeed)
  {
    Ground.mSpeed = mSpeed;
  }
}
