package HappinessData;

import java.io.IOException;
import java.util.ArrayList;
import static HappinessData.FileReader.read;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<CountryHappiness> data = read("data/HappinessData.csv");
        printCountryHappinessData(data, 0); // Print out the attributes of the first data item in the file.
        printCountryHappinessData(data, 9); // Print out the attributes of the 10th item in the file.
        System.out.println("Total number of entries in the data: " + data.size());
    }

    public static void printCountryHappinessData(ArrayList<CountryHappiness> data, int index) {
        if (index >= data.size()) {
            System.out.println("index out of bounds");
            return;
        }
        System.out.println("Happiness Data for Item " + (index + 1));
        System.out.println("==================================");
        System.out.println("Country: " + data.get(index).country());
        System.out.println("Happiness Rank: " + data.get(index).happinessRank());
        System.out.println("Happiness Score: " + data.get(index).happinessScore());
        System.out.println("Economy Score: " + data.get(index).economy());
        System.out.println("Social Score: " + data.get(index).socialScore());
        System.out.println("Health Score: " + data.get(index).healthScore());
        System.out.println("==================================\n");
    }
}
