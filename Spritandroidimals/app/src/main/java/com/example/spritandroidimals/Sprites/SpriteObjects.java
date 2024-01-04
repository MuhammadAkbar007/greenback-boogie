package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.view.Display;
import android.widget.ImageView;

/**
 * Class for moving the ground beneath the player
 */

public class SpriteObjects extends androidx.appcompat.widget.AppCompatImageView
{
  protected final static int END_SCREEN = 0;
  protected final static int NONE = 0;

  private boolean mbDraw;
  private int mTimeToDraw;
  protected Bitmap mBitmapSprite;
  protected Display mDisplay;
  private int mSpriteID;
  private int mSpriteXTop;
  private int mSpriteYLeft;
  private int mSpriteWidth;
  private int mSpriteHeight;
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
  public SpriteObjects (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int time)
  {
    super (context);
    BitmapFactory.Options opts = new BitmapFactory.Options ();
    opts.inJustDecodeBounds = true;
    mBitmapSprite = BitmapFactory
        .decodeResource (context.getResources (), drawableSprite);
    mDisplay = display;
    mSpriteXTop = SpriteX;
    mSpriteYLeft = SpriteY;
    mSpriteWidth = mBitmapSprite.getWidth ();
    mSpriteHeight = mBitmapSprite.getHeight ();
    mSpriteID = drawableSprite;
    mTimeToDraw = time;
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
  public SpriteObjects (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth, int time)
  {
    super (context);
    //Sets up a standard bitmap
    BitmapFactory.Options opts = new BitmapFactory.Options ();
    opts.inJustDecodeBounds = true;
    mBitmapSprite = BitmapFactory
        .decodeResource (context.getResources (), drawableSprite);
    mDisplay = display;
    mSpriteXTop = SpriteX;
    mSpriteYLeft = SpriteY;
    mSpriteWidth = mBitmapSprite.getWidth ();
    mSpriteHeight = mBitmapSprite.getHeight ();
    mSpriteID = drawableSprite;
    mTimeToDraw = time;

    //If you don't call it in it's own method, it will throw a error
    //Why? because the mSpriteWidth and mSpriteHeight aren't set before it
    // tries to rescale the bitmap
    scaleBitmap (SpriteX, SpriteY, newHeight, newWidth);
  }

  /**
   * Rescales the bitmap
   *
   * @param newHeight - New height for bitmap
   * @param newWidth  - New Width for bitmap
   */
  public void scaleBitmap (int X, int Y, int newHeight, int newWidth)
  {
    Matrix matrix = new Matrix ();
    float scaleWidth = ((float) newWidth) / mSpriteWidth;
    float scaleHeight = ((float) newHeight) / mSpriteHeight;

    //Rescale Bitmap
    matrix.postScale (scaleWidth, scaleHeight);
    mBitmapSprite = Bitmap
        .createBitmap (mBitmapSprite, 0, 0, mSpriteWidth, mSpriteHeight,
            matrix, false);
  }

  /**
   * Updates the sprite object, if it's time is up, move it accross the screen
   * @param startTime - The started time of the frame
   */
  public void update (long startTime)
  {
    long currentTime = SystemClock.currentThreadTimeMillis () - startTime;

    if (currentTime >= mTimeToDraw)
    {
      mbDraw = true;
    }

    if (mbDraw)
    {
      mSpriteXTop -= mSpeed;

      if (END_SCREEN >= (mSpriteYLeft + mSpriteWidth))
      {
        mbDraw = false;
      }
    }
  }

  /**
   * Draws the drawable on the display
   *
   * @param canvas - The screen we are drawing on
   */
  public void draw (Canvas canvas)
  {
    if (mbDraw)
    {
      canvas.drawBitmap (mBitmapSprite, getSpriteXTop (), getSpriteYLeft (),
          null);
    }
  }

  /**
   * Gets the sprites Left coord (Y)
   *
   * @return - int
   */
  public int getSpriteYLeft ()
  {
    return mSpriteYLeft;
  }

  /**
   * Gets the sprites Left coord (X)
   *
   * @return - int
   */
  public int getSpriteXTop ()
  {
    return mSpriteXTop;
  }

  /**
   * Gets the sprites
   *
   * @return - int
   */
  public int getSpriteWidth ()
  {
    return mBitmapSprite.getWidth ();
  }

  /**
   * Gets the sprites height
   *
   * @return - int
   */
  public int getSpriteHeight ()
  {
    return mSpriteHeight;
  }

  /**
   * Sets the top left x position of the sprite
   *
   * @param mSpriteXTop : the new x position
   */
  public void setSpriteXTop (int mSpriteXTop)
  {
    this.mSpriteXTop = mSpriteXTop;
  }

  /**
   * Sets the top left y position of the sprite
   *
   * @param mSpriteYLeft : the new t position
   */
  public void setSpriteYLeft (int mSpriteYLeft)
  {
    this.mSpriteYLeft = mSpriteYLeft;
  }

  /**
   * gets the displays height
   *
   * @return - display height
   */
  public int getDisplayHeight ()
  {
    return getResources ().getDisplayMetrics ().heightPixels;
  }

  /**
   * Gets the display width
   *
   * @return - display width
   */
  public int getDisplayWidth ()
  {
    return getResources ().getDisplayMetrics ().widthPixels;
  }

  /**
   * Sets if the object should be drawn
   *
   * @param mbDraw : true if the sprite should be drawn, false otherwise
   */
  public void setDraw (boolean mbDraw)
  {
    this.mbDraw = mbDraw;
  }

  /**
   * Sets the sprite's speed
   *
   * @param mSpeed : the speed
   */
  public static void setSpeed (int mSpeed)
  {
    SpriteObjects.mSpeed = mSpeed;
  }

  /**
   * Gets the speed of the sprite
   *
   * @return the speed
   */
  public static int getSpeed ()
  {
    return mSpeed;
  }

  /**
   * Gets the frame width of the sprite
   *
   * @return the frame width
   */
  public int getFrameWidth ()
  {
    return mBitmapSprite.getWidth ();
  }

  /**
   * Gets the frame height of the sprite
   *
   * @return the frame height
   */
  public int getFrameHeight ()
  {
    return mBitmapSprite.getHeight ();
  }

  /**
   * Gets if the sprite should be drawn or not
   * @return true if the sprite should be drawn, false otherwise
   */
  public boolean getDraw()
  {
    return mbDraw;
  }

  /**
   * Gets when to draw the sprite
   *
   * @return the time
   */
  public int getTimeToDraw()
  {
    return mTimeToDraw;
  }

  /**
   * Gets the id of the sprite
   *
   * @return the id
   */
  public int getSpriteID()
  {
    return mSpriteID;
  }
}
