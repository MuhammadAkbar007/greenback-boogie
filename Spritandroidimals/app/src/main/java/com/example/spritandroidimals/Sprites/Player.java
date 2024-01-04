package com.example.spritandroidimals.Sprites;

import android.content.Context;
import android.view.Display;

/**
 * Class for displaying the player sprite
 */
public class Player extends AnimatedSprite
{
  private final static int ROW = 4;
  private final static int COL = 2;
  private final static int JUMP_ROW = 3;
  private final static int JUMP_COL = 0;
  private final static int JUMP_SPEED = 16;
  private final static int FRAME_SPEED  = 4;
  private final static double JUMP_MULTIPLIER =  1.4;

  private int mJumpTime;
  private int mJumpYStart;
  private int count = 0;
  private boolean bJump;
  private boolean bJumpDown = false;


  /**
   * Constructor for settings_menu all the defaults values for an drawable
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprites ID
   * @param SpriteX        - The sprites starting X
   * @param SpriteY        - The sprites starting Y
   * @param time           - The sprites time stamp
   */
  public Player (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int time, int row, int col)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, time, row, col);
    setDraw (true);
    bJump = false;
  }

  /**
   * Constructor for settings_menu all the defaults values for an drawable
   * Ths on resize's the bitmap
   *
   * @param context        - References the applications resources
   * @param display        - The display
   * @param drawableSprite - The sprites ID
   * @param SpriteX        - The sprites starting X
   * @param SpriteY        - The sprites starting Y
   * @param newHeight      - The sprites new height
   * @param newWidth       - The sprites new width
   * @param time           - The sprites time stamp
   */
  public Player (Context context, Display display, int drawableSprite,
      int SpriteX, int SpriteY, int newHeight, int newWidth, int time, int row, int col)
  {
    super (context, display, drawableSprite, SpriteX, SpriteY, newHeight,
        newWidth, time, row, col);
    setDraw (true);
    bJump = false;
  }

  /**
   * Updates the the players frames
   */
  public void update ()
  {
    if (!bJump)
    {
      if (count % FRAME_SPEED == NONE)
      {
        setCol (getCol () + 1);
        if (getCol () == COL)
        {
          setCol (NONE);
          setRow (getRow () + 1);
        }
        if (getRow () == ROW)
        {
          setRow (NONE);
        }
      }
      count++;
    }
    else
    {
      setCol (JUMP_COL);
      setRow (JUMP_ROW);
      jump ();
    }
  }

  /**
   * Initiates the jump
   */
  public void jump ()
  {
    int newY;

    if (getSpriteYLeft () > mJumpTime && !bJumpDown)
    {
      newY = getSpriteYLeft () - (JUMP_SPEED);
      this.setSpriteYLeft (newY);
    }
    else
    {
      bJumpDown = true;
    }

    if (getSpriteYLeft () < mJumpYStart && bJumpDown)
    {
      newY = getSpriteYLeft () + JUMP_SPEED;
      this.setSpriteYLeft (newY);
    }
    else if (bJumpDown && getSpriteYLeft () >= mJumpYStart)
    {
      this.bJump = false;
      this.bJumpDown = false;
    }
  }
  /**
   * Starts the jump
   * @param bJump - Boolean, if Hero is jumping
   */
  public void setJump (boolean bJump)
  {
    this.bJump = bJump;
    mJumpTime = getSpriteYLeft () - (int)(getFrameHeight () * JUMP_MULTIPLIER);
    mJumpYStart = getSpriteYLeft ();
  }

  /**
   * Gets if jumping or not
   * @return - Boolean
   */
  public boolean getJump ()
  {
    return this.bJump;
  }

  /**
   * Sets where the player sprite starts before a jump
   *
   * @param jumpY : the start position prior to jump
   */
  public void setJumpYstart(int jumpY)
  {
    this.mJumpYStart = jumpY;
  }
}
