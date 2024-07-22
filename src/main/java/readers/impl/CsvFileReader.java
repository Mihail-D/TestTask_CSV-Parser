package readers.impl;

import readers.FilesReader;
import processors.DataProcessor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFileReader implements FilesReader {

    private static final Map<String, List<Integer>> estateRegister = new HashMap<>();

    @Override
    public void readFromFile(String path) throws IOException {
        if (!Files.exists(Path.of(path))) {
            throw new RuntimeException("Файл отсутствует. Укажите корректный путь к файлу.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.contains("city")) {
                    String[] data = line.split(";");
                    String key = data[0] + ";" + data[1] + ";" + data[2];
                    int value = Integer.parseInt(data[3]);

                    estateRegister.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
                }
            }
        }
        DataProcessor.processEstateRegister(estateRegister);
    }
}