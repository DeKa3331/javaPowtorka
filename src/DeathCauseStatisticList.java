import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DeathCauseStatisticList {

    public List<DeathCauseStatistic> deathCauseStatistics;

    public DeathCauseStatisticList() {
        deathCauseStatistics = new ArrayList<>();
    }

    public void repopulate(String filePath) {
        deathCauseStatistics.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            reader.readLine();
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    DeathCauseStatistic statistic = DeathCauseStatistic.fromCsvLine(line);
                    deathCauseStatistics.add(statistic);
                } catch (IllegalArgumentException e) {
                    System.err.println("blad: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<DeathCauseStatistic> getDeathCauseStatistics() {
        return deathCauseStatistics;
    }
    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {

        List<DeathCauseStatistic> copy = new ArrayList<>(deathCauseStatistics);
        for (int i = 0; i < copy.size() - 1; i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                int deathsI = copy.get(i).getAgeBracketDeaths(age).deathCount;
                int deathsJ = copy.get(j).getAgeBracketDeaths(age).deathCount;
                if (deathsJ > deathsI) {
                    DeathCauseStatistic temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }
        if (copy.size() > n) {
            return copy.subList(0, n);
        } else {
            return copy;
        }
    }



}
