/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the helper to convert between DP and Pixels
 */

package com.example.pop_the_balloons_game.utils;

import android.content.Context;
import android.util.TypedValue;

public class PixelHelper {


    /*
    * Convert pixels to dp
    * */
    public static int pixelsToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }


    /*
     * Convert dp to pixles
     * */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}