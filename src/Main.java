public class Main {
    public static void main(String[] args) {
        String csvFilePath = "zgony.csv";
        String icdCodesFilePath = "ICD.txt";

        DeathCauseStatisticList deathList = new DeathCauseStatisticList();
        deathList.repopulate(csvFilePath);
        System.out.println("Załadowano " + deathList.getDeathCauseStatistics().size() + " linii.");

        int testAge = 3;
        System.out.println("\nZgony dla wieku " + testAge + " w wybranych chorobach:");
        for (int i = 0; i < Math.min(3, deathList.getDeathCauseStatistics().size()); i++) {
            DeathCauseStatistic stat = deathList.getDeathCauseStatistics().get(i);
            DeathCauseStatistic.AgeBracketDeaths abd = stat.getAgeBracketDeaths(testAge);
            System.out.printf("Kod: %s, zgonów w grupie: %d\n", stat.getIcd10Code(), abd.deathCount);
        }

        int n = 5;
        System.out.println("\n" + n + " najgroźniejszych chorób dla wieku " + testAge + ":");
        var mostDeadly = deathList.mostDeadlyDiseases(testAge, n);
        for (DeathCauseStatistic stat : mostDeadly) {
            DeathCauseStatistic.AgeBracketDeaths abd = stat.getAgeBracketDeaths(testAge);
            System.out.printf("Kod: %s, zgonów: %d\n", stat.getIcd10Code(), abd.deathCount);
        }

        System.out.println("\nTest ICDCodeTabularOptimizedForTime");
        ICDCodeTabular icdFast = new ICDCodeTabularOptimizedForTime(icdCodesFilePath);
        testICDCode(icdFast, "J12.81");
        testICDCode(icdFast, "A00.0");

        System.out.println("\nTest ICDCodeTabularOptimizedForMemory");
        ICDCodeTabular icdMemory = new ICDCodeTabularOptimizedForMemory(icdCodesFilePath);
        testICDCode(icdMemory, "J12.81");
        testICDCode(icdMemory, "A00.0");
        testICDCode(icdMemory, "OGÓŁEM");
    }

    private static void testICDCode(ICDCodeTabular icdTabular, String code) {
        try {
            String description = icdTabular.getDescription(code);
            System.out.printf("Opis %s: %s\n", code, description);
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Kod %s nie znaleziony.\n", code);
        }
    }
}
