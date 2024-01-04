package com.example.gyrodraw.game.drawing;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gyrodraw.R;
import com.example.gyrodraw.game.drawing.items.Item;
import com.example.gyrodraw.utils.TypefaceLibrary;

/**
 * Helper class that defines the style of the text feedback.
 */
final class FeedbackTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final int TEXT_SIZE = 1;

    private FeedbackTextView(Context context, String text, int colorId) {
        super(context);
        setTextColor(context.getResources().getColor(colorId));
        setShadowLayer(10, 0, 0, context.getResources().getColor(R.color.colorPrimaryDark));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setTextSize(TEXT_SIZE);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        setLayoutParams(layoutParams);

        Typeface typeMuro = TypefaceLibrary.getTypeMuro();
        setTypeface(typeMuro, Typeface.ITALIC);

        setText(text);
    }

    /**
     * Creates a text feedback to inform the player about which item has been picked up.
     *
     * @param item            item that was activated
     * @param paintViewHolder the holder of the view
     * @param context         the context where we want to put the TextView
     * @return a {@link TextView} containing the feedback text
     */
    static TextView itemTextFeedback(Item item, final RelativeLayout paintViewHolder,
                                     Context context) {
        final FeedbackTextView feedback = new FeedbackTextView(context, item.getTextFeedback(),
                item.getColorId());

        new CountDownTimer(800, 40) {

            public void onTick(long millisUntilFinished) {
                feedback.setTextSize(60 - millisUntilFinished / 15);
            }

            public void onFinish() {
                paintViewHolder.removeView(feedback);
            }
        }.start();

        return feedback;
    }

    /**
     * Creates a text feedback to inform the player that the time is over.
     *
     * @param context the context where we want to put the TextView
     * @return a {@link TextView} containing the feedback text
     */
    static TextView timeIsUpTextFeedback(Context context) {
        final FeedbackTextView feedback = new FeedbackTextView(context, "TIME'S UP ! ",
                R.color.colorDrawYellow);

        new CountDownTimer(800, 40) {

            public void onTick(long millisUntilFinished) {
                feedback.setTextSize(60 - millisUntilFinished / 15);
            }

            public void onFinish() {
                // Nothing to do, the time's up message stays until the end
            }
        }.start();
        return feedback;
    }
}
