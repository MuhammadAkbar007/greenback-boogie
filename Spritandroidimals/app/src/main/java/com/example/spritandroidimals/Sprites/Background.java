package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.view.Display;

/**
 * Class for drawing the background
 */
public class Background extends SpriteObjects
{

  /**
   * Constructor for settings all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The Sprites ID
   * @param SpriteX        - The Sprites starting X
   * @param SpriteY        - The Sprites starting Y
   */
  public Background (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int time)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, time);
  }

  /**
   * Constructor for settings all the defaults values for an drawable
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
  public Background (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth, int time)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, newHeight,
        newWidth, time);
    setDraw (true);
  }

  /**
   * Overrides the update method, to not update
   *
   * @param startTime - The started time of the frame
   */
  public void update(long startTime)
  {
  }
}
