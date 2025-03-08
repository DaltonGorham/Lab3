package HappinessData;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader {
    public static Object[][] read(String path) throws IOException {
        String content = Files.readString(Path.of(path), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(new StringReader(content));
        final int ROW_AMOUNT = 6;

       List<Object[]> countryHappinessDataStr =
               reader.lines()
                       .skip(1) // skips the header row
                       .map(line -> {
                           String[] row = line.split(",");

                           if (row.length != ROW_AMOUNT) {
                               return null; // skip the rows with no values
                           }

                           return new CountryHappiness(
                                   row[0],
                                   Integer.parseInt(row[1]),
                                   parseWithDefaultIfNull(row[2], 0.0),
                                   parseWithDefaultIfNull(row[3], 0.0),
                                   parseWithDefaultIfNull(row[4], 0.0),
                                   parseWithDefaultIfNull(row[5], 0.0)
                           );
                       }).filter(Objects::nonNull) // filter out the rows that returned null
                       .map(CountryHappiness::toObjectArray).toList();


       return countryHappinessDataStr.toArray(new Object[0][]);


    }

    // TODO: MOVE THIS TO ITS OWN SEPARATE FILE
    // TODO: THIS IS JUST FOR TESTING PURPOSES
    public static void main(String[] args) throws IOException {
        Object[][] objects = read("data/2024.csv");
        // Print Out the Items in the first row
        System.out.println(Arrays.toString(new Object[]{objects[0]}));
        // Print Out the Items in the 10th row
        System.out.println(Arrays.toString(objects[9]));
        }

        // if rows contain empty values just default to 0.0
        public static double parseWithDefaultIfNull(String s, double defaultValue){
        if(s == null || s.trim().isEmpty()){
                return defaultValue;
            }
            return Double.parseDouble(s);
        }
    }

