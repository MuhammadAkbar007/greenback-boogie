package com.example.balloonpopper.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Ganesh on 07/01/18.
 */

public class PixelHelper {
    public static int pixelsToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }
}
