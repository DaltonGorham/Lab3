package HappinessData;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import static HappinessData.CountryHappiness.*;

public class FileReader {
    /**
     * Reads data from the specified file path, parses the content, and creates a list of
     * CountryHappiness objects. The method skips the header row in the input file, filters
     * out invalid rows, and initializes missing or empty data values with default values.
     *
     * @param path the file path to read the data from.
     * @return an ArrayList of CountryHappiness objects populated with data from the file.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public static ArrayList<CountryHappiness> read(String path) throws IOException {
        String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
            final int ROW_AMOUNT = 6;
            final int DEFAULT_AMOUNT = 0;

            // for each line in the data place a new CountryHappiness object inside the ArrayList
            // filter out the null values within rows
            return reader.lines()
                    .skip(1) // skips the header row
                    .map(line -> {
                        String[] row = line.split(",");

                        if (row.length != ROW_AMOUNT) {
                            return null; // skip the rows with no values
                        }

                        return new CountryHappiness(
                                row[COUNTRY],
                                parseIntWithDefaultIfNull(row[HAPPINESS_RANK], DEFAULT_AMOUNT),
                                parseDoubleWithDefaultIfNull(row[HAPPINESS_SCORE], DEFAULT_AMOUNT),
                                parseDoubleWithDefaultIfNull(row[ECONOMY], DEFAULT_AMOUNT),
                                parseDoubleWithDefaultIfNull(row[SOCIAL_SCORE], DEFAULT_AMOUNT),
                                parseDoubleWithDefaultIfNull(row[HEALTH_SCORE], DEFAULT_AMOUNT)
                        );
                    }).filter(Objects::nonNull) // filter out the rows that returned null
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }


    // if rows contain empty values just default to 0
    public static double parseDoubleWithDefaultIfNull(String s, double defaultValue){
    if(s == null || s.trim().isEmpty()){
            return defaultValue;
        }
        return Double.parseDouble(s);
    }

    // if rows contain empty values just default to 0
    public static int parseIntWithDefaultIfNull(String s, int defaultValue){
        if(s == null || s.trim().isEmpty()){
            return defaultValue;
        }
        return Integer.parseInt(s);
    }
}

