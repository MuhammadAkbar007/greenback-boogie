package com.example.spritandroidimals.GameEvents;

import android.graphics.Rect;

import com.example.spritandroidimals.R;
import com.example.spritandroidimals.Sprites.Player;
import com.example.spritandroidimals.Sprites.SpriteObjects;


/**
 * Class for handling collisions between sprite objects
 */

public class Collision
{
  /**
   * Takes a player sprite, and another sprite object and checks for collision.
   *
   * @param cPlayer : the player sprite object
   * @param cObject : the sprite object to check against
   * @return true if the objects collided, false otherwise
   */
  public boolean collisionDetection (Player cPlayer, SpriteObjects cObject)
  {
    final int FEET_BUFFER = 20;
    final int TAIL_BUFFER = 60;
    final int PIT_SIDE_BUFF = 80;
    int newPlayerTop = cPlayer.getSpriteXTop () + TAIL_BUFFER;
    int newPlayerHeight = cPlayer.getFrameHeight () - FEET_BUFFER;

    Rect playerRect = new Rect (newPlayerTop, cPlayer.getSpriteYLeft (),
        cPlayer.getSpriteXTop () + cPlayer.getFrameWidth (),
        (cPlayer.getSpriteYLeft () + newPlayerHeight));
    Rect objectRect = new Rect (cObject.getSpriteXTop (),
        cObject.getSpriteYLeft (),
        cObject.getSpriteXTop () + cObject.getFrameWidth (),
        cObject.getSpriteYLeft () + cObject.getFrameHeight ());
    

    if (R.drawable.pit == cObject.getSpriteID ())
    {
      return (((cPlayer.getSpriteXTop () + cPlayer.getFrameWidth ()) >= (cObject.getSpriteXTop () + PIT_SIDE_BUFF))
          && ((cPlayer.getSpriteXTop () + cPlayer.getFrameWidth ()) <= (cObject.getSpriteXTop () + cObject.getFrameWidth () - PIT_SIDE_BUFF))
          && ((cPlayer.getSpriteYLeft () + newPlayerHeight) >= (cObject.getSpriteYLeft ())))
          || (((newPlayerTop) >= (cObject.getSpriteXTop () + PIT_SIDE_BUFF))
          && (newPlayerTop <= (cObject.getSpriteXTop () - PIT_SIDE_BUFF))
          && ((cPlayer.getSpriteYLeft () + newPlayerHeight) >= (cObject.getSpriteYLeft ())));
    }

    else
    {
      return objectRect.intersect (playerRect);
    }
  }
}
