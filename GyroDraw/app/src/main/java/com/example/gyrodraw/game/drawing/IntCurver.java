package com.example.gyrodraw.game.drawing;

import static com.example.gyrodraw.utils.Preconditions.checkPrecondition;

import java.util.Arrays;


/**
 * This class helps smoothing new cursor position values by remembering older values and returning
 * an average.
 */
final class IntCurver {

    private int index;
    private int curveIntensity;
    private int sum;
    private int[] values;

    /**
     * This constructor initializes the array with a given size and fills it with a given value.
     *
     * @param curveIntensity amount of values that should be remembered
     * @param initValue initial value of the curver
     */
    IntCurver(int curveIntensity, int initValue) {
        checkPrecondition(curveIntensity > 0, "CurveIntensity was not positive.");

        this.curveIntensity = curveIntensity;
        values = new int[curveIntensity];
        Arrays.fill(values, initValue);

        sum = curveIntensity * initValue;
        index = 0;
    }

    /**
     * Resets the values to a given point.
     *
     * @param value the value at which the existing values should be reset
     */
    void setValue(int value) {
        Arrays.fill(values, value);
        sum = curveIntensity * value;
    }

    /**
     * Adds a value to the list of entries.
     *
     * @param value the value to add
     */
    void addValue(int value) {
        sum += value - values[index];
        values[index] = value;
        index = (index + 1) % curveIntensity;
    }

    /**
     * Returns the average of the stored values.
     */
    int getValue() {
        return sum / curveIntensity;
    }
}
