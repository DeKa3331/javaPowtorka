1. Podstawowy odczyt pliku linia po linii
try (BufferedReader reader = new BufferedReader(new FileReader("plik.csv"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line); // wypisz każdą linię
    }
} catch (IOException e) {
    e.printStackTrace();
}
2. Pomijanie pierwszych N linii
3. try (BufferedReader reader = new BufferedReader(new FileReader("plik.csv"))) {
    // Pomijamy 2 linie
    reader.readLine(); // linia 1
    reader.readLine(); // linia 2
//wersja dynamiczna:
   int skipLines = 5;
for (int i = 0; i < skipLines; i++) {
    reader.readLine(); // pomiń i-tą linię
}


    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line); // od linijki 3 w dół
    }
} catch (IOException e) {
    e.printStackTrace();
}

id,name,age,city
1,Alice,30,London
2,Bob,25,Berlin


try (BufferedReader reader = new BufferedReader(new FileReader("plik.csv"))) {
    reader.readLine(); // pomiń nagłówek

    String line;
    while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(","); // podziel po przecinku

        // kolumny od 1. włącznie (czyli name, age, city)
        for (int i = 1; i < tokens.length; i++) {
            System.out.print(tokens[i] + " ");
        }
        System.out.println();
    }
} catch (IOException e) {
    e.printStackTrace();
}


wersja z liniami kolos 2024
import java.io.*;
import java.util.*;

public class City {
    private final String name;
    private final int population;
    private final String country;

    public City(String name, int population, String country) {
        this.name = name;
        this.population = population;
        this.country = country;
    }

    // publiczne gettery
    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getCountry() {
        return country;
    }

    // 👉 StringBuilder w toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City{name='").append(name)
          .append("', population=").append(population)
          .append(", country='").append(country)
          .append("'}");
        return sb.toString();
    }

    // 🔒 prywatna metoda do parsowania pojedynczej linii
    private static City parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Zbyt mało danych: " + line);
        }

        String name = parts[0].trim();
        int population = Integer.parseInt(parts[1].trim());
        String country = parts[2].trim();

        return new City(name, population, country);
    }

    // 🌍 publiczna metoda do wczytywania całego pliku
    public static Map<String, City> parseFile(String filePath) {
        Map<String, City> result = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    City city = parseLine(line);
                    result.put(city.getName(), city);
                } catch (IllegalArgumentException e) {
                    System.err.println("Błąd parsowania: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}




