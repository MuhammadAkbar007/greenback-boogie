package com.example.snake3.framework.impl;

import android.graphics.Bitmap;

import com.example.snake3.framework.Graphics;
import com.example.snake3.framework.Pixmap;


public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    Graphics.PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public Graphics.PixmapFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }      
}
