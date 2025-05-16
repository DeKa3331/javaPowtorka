import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{

    private final Map<String, String> codeToDescription;

    public ICDCodeTabularOptimizedForTime(String filePath) {
        codeToDescription = new HashMap<>();
        loadCodes(filePath);
    }


    private void loadCodes(String filePath) {
        int startLine = 88;
        int currentLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                currentLine++;
                if (currentLine < startLine) continue;

                line = line.trim();
                if (line.isEmpty()) continue;

                int firstSpaceIndex = line.indexOf(' ');
                if (firstSpaceIndex == -1) {
                    continue;
                }

                String code = line.substring(0, firstSpaceIndex).trim();
                String description = line.substring(firstSpaceIndex + 1).trim();

                if (!code.isEmpty() && !description.isEmpty()) {
                    codeToDescription.put(code, description);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        String description = codeToDescription.get(code);
        if (description == null) {
            throw new IndexOutOfBoundsException("Kod nie został znaleziony: " + code);
        }
        return description;
    }
}
