package com.example.gyrodraw.home.leagues;


import static com.example.gyrodraw.utils.Preconditions.checkPrecondition;

/**
 * Class representing a league.
 */
public final class League {

    private final String name;
    private final int minTrophies;
    private final int maxTrophies;

    private League(String name, int minTrophies, int maxTrophies) {
        assert name != null : "name is null";
        assert minTrophies >= 0 : "minTrophies is negative";
        assert maxTrophies >= 0 : "maxTrophies is negative";
        assert minTrophies <= maxTrophies : "minTrophies is greater than maxTrophies";

        this.name = name;
        this.minTrophies = minTrophies;
        this.maxTrophies = maxTrophies;
    }

    /**
     * Creates League 1.
     *
     * @return the desired league.
     */
    public static League createLeague1() {
        return new League("league1", 0, 99);
    }

    /**
     * Creates League 2.
     *
     * @return the desired league.
     */
    public static League createLeague2() {
        return new League("league2", 100, 199);
    }

    /**
     * Creates League 3.
     *
     * @return the desired league.
     */
    public static League createLeague3() {
        return new League("league3", 200, Integer.MAX_VALUE);
    }

    /**
     * Checks if the given number of trophies is inside the league's boundaries.
     *
     * @param trophies the number of trophies to check
     * @return true if the league contains the given number of trophies, false otherwise
     */
    public boolean contains(int trophies) {
        checkPrecondition(trophies >= 0, "trophies is negative");

        return minTrophies <= trophies && trophies <= maxTrophies;
    }

    /**
     * Gets the league's name.
     *
     * @return the league's name
     */
    public String getName() {
        return name;
    }
}
