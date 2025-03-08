package HappinessData;

public record CountryHappiness(
        String country,
        int happinessRank,
        double happinessScore,
        double economy,
        double socialScore,
        double healthScore ) {

    public static final int COUNTRY = 0;
    public static final int HAPPINESS_RANK = 1;
    public static final int HAPPINESS_SCORE = 2;
    public static final int ECONOMY = 3;
    public static final int SOCIAL_SCORE = 4;
    public static final int HEALTH_SCORE = 5;
}
