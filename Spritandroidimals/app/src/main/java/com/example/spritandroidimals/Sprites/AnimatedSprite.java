package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.Display;

/**
 * Class for sprites that require animation
 */
public class AnimatedSprite extends SpriteObjects
{
  protected final static int FRAME_TIME = 2;

  private int mStartROW = 0;
  private int mStartCOL = 0;
  private int mFrameHeight;
  private int mFrameWidth;
  private int mCol;
  private int mRow;
  private int count;
  private static int mSpeed;

  /**
   * Constructor for settings_menu all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprite ID
   * @param SpriteX        - The sprite starting X
   * @param SpriteY        - The sprite starting Y
   * @param time           - the time the sprite spawns
   * @param row            - the row in the bitmap
   * @param col            - the column in the bitmap
   */
  public AnimatedSprite (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int time, int row, int col)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, time);
    mStartROW = row;
    mStartCOL = col;
    mRow = row;
    mCol = col;

    if (mRow != NONE)
    {
      this.mFrameHeight = mBitmapSprite.getHeight () / row;
    }
    else
    {
      this.mFrameHeight = mBitmapSprite.getHeight ();
    }

    if (mCol != NONE)
    {
      this.mFrameWidth = mBitmapSprite.getWidth () / col;
    }
    else
    {
      this.mFrameWidth = mBitmapSprite.getWidth ();
    }
  }

  /**
   * * Constructor for settings_menu all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprite ID
   * @param SpriteX        - The sprite starting X
   * @param SpriteY        - The sprite starting Y
   * @param newHeight      - The sprite's new height
   * @param newWidth       - The sprite's new width
   * @param time           - The time the sprite spawns
   * @param row            - The sprite's row in the bitmap
   * @param col            - The sprite's column in the bitmap
   */
  public AnimatedSprite (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth, int time, int row,
      int col)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, newHeight,
        newWidth, time);
    mStartROW = row;
    mStartCOL = col;
    mRow = row;
    mCol = col;

    if (mRow != NONE)
    {
      this.mFrameHeight = newHeight / mRow;
    }
    else
    {
      this.mFrameHeight = newHeight;
    }

    if (mCol != 0)
    {
      this.mFrameWidth = newWidth / mCol;
    }
    else
    {
      this.mFrameWidth = newWidth;
    }

  }

  /**
   * Updates the sprites animation
   *
   * @param startTime - The started time of the frame
   */
  public void update (long startTime)
  {
    long currentTime = SystemClock.currentThreadTimeMillis () - startTime;
    if (currentTime >= getTimeToDraw ())
    {

      setDraw (true);
    }

    if (getDraw ())
    {
      setSpriteXTop (getSpriteXTop () - mSpeed);

      if (END_SCREEN >= (getSpriteYLeft () + getFrameWidth ()))
      {
        setDraw (false);
      }
    }

    if (getDraw ())
    {
      if (count % FRAME_TIME == 0)
      {

        setCol (getCol () + 1);
        if (getCol () >= mStartCOL)
        {
          setCol (0);
          setRow (getRow () + 1);
        }
        if (getRow () >= mStartROW)
        {
          setRow (0);
        }
      }
      count++;
    }

    if (END_SCREEN >= (getSpriteYLeft () + getFrameWidth ()))
    {
      setDraw (false);
    }
  }

  /**
   * Draws the animated sprite
   *
   * @param canvas - The screen we are drawing on
   */
  public void draw (Canvas canvas)
  {
    if (getDraw ())
    {
      int srcX = getCol () * getFrameWidth ();
      int srcY = getRow () * getFrameHeight ();

      Rect src = new Rect (srcX, srcY, srcX + getFrameWidth (),
          srcY + getFrameHeight ());
      Rect dest = new Rect (getSpriteXTop (), getSpriteYLeft (),
          getSpriteXTop () + getFrameWidth (),
          getSpriteYLeft () + getFrameHeight ());

      canvas.drawBitmap (mBitmapSprite, src, dest, null);
    }
  }

  /**
   * Gets the sprite's frame height
   *
   * @return the frame height
   */
  public int getFrameHeight ()
  {
    return mFrameHeight;
  }

  /**
   * Gets the sprite's frame width
   *
   * @return the frame width
   */
  public int getFrameWidth ()
  {
    return mFrameWidth;
  }

  /**
   * Gets the sprite's row in the bitmap
   *
   * @return the row
   */
  public int getRow ()
  {
    return mRow;
  }

  /**
   * Gets the sprite's column in the bitmap
   *
   * @return the column
   */
  public int getCol ()
  {
    return mCol;
  }

  /**
   * Sets the sprite's column in the bitmap
   *
   * @param mCol : the column to set to
   */
  public void setCol (int mCol)
  {
    this.mCol = mCol;
  }

  /**
   * Sets the sprite's row in the bitmap
   *
   * @param mRow : the row to set to
   */
  public void setRow (int mRow)
  {
    this.mRow = mRow;
  }

  /**
   * Sets the sprite's movement speed
   *
   * @param mSpeed : the speed to set to
   */
  public static void setSpeed (int mSpeed)
  {
    AnimatedSprite.mSpeed = mSpeed;
  }

}
