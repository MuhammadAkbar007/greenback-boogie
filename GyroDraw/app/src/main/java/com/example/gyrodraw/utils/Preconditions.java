package com.example.gyrodraw.utils;

/**
 * Utility class containing methods for checking preconditions.
 */
public final class Preconditions {

    private Preconditions() {
    }

    /**
     * Checks if the given precondition is true; if not, an {@link IllegalArgumentException} with
     * the given error message is thrown.
     *
     * @param precondition the precondition to assert
     * @param errorMessage the error message to show
     * @throws IllegalArgumentException if the precondition is false
     */
    public static void checkPrecondition(boolean precondition, String errorMessage) {
        if (!precondition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Checks if the given precondition is true; if not, an {@link IllegalArgumentException} is
     * thrown.
     *
     * @param precondition the precondition to assert
     * @throws IllegalArgumentException if the precondition is false
     */
    public static void checkPrecondition(boolean precondition) {
        if (!precondition) {
            throw new IllegalArgumentException();
        }
    }
}
