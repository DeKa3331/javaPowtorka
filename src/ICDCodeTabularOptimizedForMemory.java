import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{

    private final String filePath;

    public ICDCodeTabularOptimizedForMemory(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        int startLine = 88;
        int currentLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine < startLine) continue;

                line = line.trim();
                if (line.startsWith(code)) {
                    return line.substring(7).trim();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IndexOutOfBoundsException("Kod nie został znaleziony: " + code);
    }
}
