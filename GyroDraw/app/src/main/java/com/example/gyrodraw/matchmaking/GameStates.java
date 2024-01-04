package com.example.gyrodraw.matchmaking;

/**
 * Enum representing the states of an online game.
 */
public enum GameStates {
    HOME_STATE, CHOOSE_WORDS_TIMER_START, START_DRAWING_ACTIVITY, WAITING_UPLOAD,
    START_VOTING_ACTIVITY, END_VOTING_ACTIVITY, RANKING_FRAGMENT;

    /**
     * Converts the an integer value into its corresponding {@link GameStates} value.
     *
     * @param value Integer value to be converted
     * @return Returns the game state
     */
    public static GameStates convertValueIntoState(int value) {
        GameStates state;
        switch (value) {
            case 0:
                state = HOME_STATE;
                break;
            case 1:
                state = CHOOSE_WORDS_TIMER_START;
                break;
            case 2:
                state = START_DRAWING_ACTIVITY;
                break;
            case 3:
                state = WAITING_UPLOAD;
                break;
            case 4:
                state = START_VOTING_ACTIVITY;
                break;
            case 5:
                state = END_VOTING_ACTIVITY;
                break;
            case 6:
                state = RANKING_FRAGMENT;
                break;
            default:
                state = HOME_STATE;
        }

        return state;
    }
}
