package com.example.gyrodraw.game.drawing.items;


import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.game.drawing.PaintView;

/**
 * Class representing a bonus item which gives 3 stars to the player.
 */
public class AddStarsItem extends Item {

    private static final int ADD_STARS = 3;

    public AddStarsItem(int posX, int posY, int radius) {
        super(posX, posY, radius);
    }

    @Override
    public void activate(final PaintView paintView) {
        vibrate(paintView);
        Account.getInstance(paintView.getContext()).changeStars(ADD_STARS);
    }

    @Override
    public String getTextFeedback() {
        return "+" + ADD_STARS + " STARS ! ";
    }

    @Override
    public int getColorId() {
        return R.color.colorGreen;
    }
}
