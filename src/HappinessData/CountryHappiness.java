package HappinessData;

public record CountryHappiness(
        String country,
        int happinessRank,
        double happinessScore,
        double economy,
        double socialScore,
        double healthScore ) {


    public Object[] toObjectArray() {
        Object[] objects = new Object[6];
        objects[0] = country;
        objects[1] = happinessRank;
        objects[2] = happinessScore;
        objects[3] = economy;
        objects[4] = socialScore;
        objects[5] = healthScore;
        return objects;
    }

    //TODO: MAKE ENUMS FOR EACH ROW HEADER INSTEAD OF USING ROW[0]
}
