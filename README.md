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

