public class DeathCauseStatistic {
    private String icd10Code;
    private int[] deathsByAgeGroup;

    public DeathCauseStatistic(String icd10Code, int[] deathsByAgeGroup) {
        this.icd10Code = icd10Code;
        this.deathsByAgeGroup = deathsByAgeGroup;
    }

    public String getIcd10Code() {
        return icd10Code;
    }
    public static DeathCauseStatistic fromCsvLine(String csvLine) {
        String[] tokens = csvLine.split(",");

        String code = tokens[0].trim();

        int[] ageGroupDeaths = new int[tokens.length - 1];

        for (int i = 2; i < tokens.length; i++) {
            String value = tokens[i].trim();
            ageGroupDeaths[i - 2] = value.equals("-") ? 0 : Integer.parseInt(value);
        }

        return new DeathCauseStatistic(code, ageGroupDeaths);
    }


    public static class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }



    public AgeBracketDeaths getAgeBracketDeaths(int age) {
        int[] lowerBounds = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95};
        int[] upperBounds = {4, 9, 14, 19, 24, 29, 34, 39, 44, 49, 54, 59, 64, 69, 74, 79, 84, 89, 94, Integer.MAX_VALUE};

        for (int i = 0; i < lowerBounds.length; i++) {
            if (age >= lowerBounds[i] && age <= upperBounds[i]) {
                int young = lowerBounds[i];
                int old = upperBounds[i];
                int deathCount = deathsByAgeGroup[i];
                return new AgeBracketDeaths(young, old, deathCount);
            }
        }
        return null;
    }




}
