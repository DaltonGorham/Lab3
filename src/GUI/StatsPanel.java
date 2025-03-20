package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import HappinessData.CountryHappiness;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static HappinessData.FileReader.read;


/** Stats
 * Average Happiness Score (General Insight)
 * Top 3 Happiest Countries (Ranking Insight)
 * Happiness vs. GDP Correlation (Trend Insight)
 */


public class StatsPanel extends JPanel implements DataUpdateListener{
    private ArrayList<CountryHappiness> data;
    private JLabel titleLabel;
    private JTextArea textArea;

    /** Constructor:
     * Constructs a StatsPanel displaying statistical information and updates dynamically.
     * @throws IOException if the specified data file cannot be read.
     */

    public StatsPanel() throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 300));
        setMaximumSize(new Dimension(600, 300));

        // Title
        titleLabel = new JLabel("Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        // For the actual data
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        textArea.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));

        // add the label and text
        add(titleLabel);
        add(textArea);
        setVisible(true);
        data = read("data/HappinessData.csv");
        onDataUpdated(data); // start stats with current table
    }


    /**
     * Calculates the average happiness score from a list of CountryHappiness objects.
     *
     * @param data a list of CountryHappiness objects containing happiness data for various countries
     * @return the average happiness score; returns 0 if the list is empty
     */
    public double calcAverageHappiness(List<CountryHappiness> data){
        return data.stream()
                .mapToDouble(CountryHappiness::happinessScore)
                .average()
                .orElse(0);
    }

    /**
     * Calculates the top three happiest countries based on the happiness rank
     * by sorting the list of CountryHappiness objects in ascending order of their
     * happiness rank and returning the top three results.
     *
     * @param data a list of CountryHappiness objects containing happiness data for various countries
     * @return a list of the top three CountryHappiness objects based on the happiness rank
     */
    public List<CountryHappiness> calcTopThreeHappiest(List<CountryHappiness> data){
        return data.stream()
                .sorted(Comparator.comparingDouble(CountryHappiness::happinessRank))
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Analyzes the relationship between a country's GDP (economy) and its happiness score,
     * returning a value between -1 and +1 that indicates how strongly these factors are related.
     *
     * @param data a list of CountryHappiness objects containing happiness scores and economy scores
     * @return the Pearson correlation coefficient as a double; returns 0.0 if an error occurs
     */
    public double calculateGDPCorrelation(List<CountryHappiness> data){
        double[] happiness = data.stream()
                .mapToDouble(CountryHappiness::happinessScore).toArray();

        double[] gdp = data.stream()
                .mapToDouble(CountryHappiness::economy).toArray();

        PearsonsCorrelation correlation = new PearsonsCorrelation();

        try {
            return correlation.correlation(happiness, gdp);
        }catch (Exception e){
            return 0.0;
        }
    }

    /**
     * Handles the event when the data is updated by recalculating statistical values such as
     * average happiness score, the top three happiest countries, and the correlation between
     * GDP and happiness. Updates the UI with these newly calculated values.
     *
     * @param data a list of CountryHappiness objects containing happiness data for various countries
     */
    @Override
    public void onDataUpdated(List<CountryHappiness> data) {
        double avgHappiness = calcAverageHappiness(data);

        List<CountryHappiness> topThree = calcTopThreeHappiest(data);

        double correlation = calculateGDPCorrelation(data);

        updateStats(avgHappiness, topThree, correlation);

    }

    /**
     * Updates the statistics displayed on the panel, including the average happiness score,
     * the top three happiest countries, and the correlation between GDP and happiness.
     *
     * @param avgHappiness the average happiness score to be displayed
     * @param topThree a list of the top three happiest countries, as CountryHappiness objects
     * @param correlation the correlation coefficient between GDP and happiness
     */
    public void updateStats(double avgHappiness, List<CountryHappiness> topThree, double correlation) {
        // Clear the text area first
        textArea.setText("");

        // Format average happiness score
        String avgHappinessString = String.format("Average Happiness Score: %.2f", avgHappiness);

        // Build top 3 countries string
        StringBuilder top3Happy = new StringBuilder("Top 3 Happiest Countries:\n");
        for (CountryHappiness countryHappiness : topThree) {
            top3Happy.append(String.format("  • %s — Rank %d\n",
                    countryHappiness.country(),
                    countryHappiness.happinessRank()));
        }

        // Format correlation value
        String gdpCorrelation = String.format("Happiness vs. GDP Correlation: %.2f", correlation);
        String gdpCorrelationStringExplanation;
        if (correlation > 0) {
            gdpCorrelationStringExplanation = "The happiness of these countries are higher if they have a higher GDP.";
        }
        else if (correlation < 0) {
            gdpCorrelationStringExplanation = "The happiness of these countries are higher if they have a lower GDP.";
        }
        else {
            gdpCorrelationStringExplanation = "The happiness rank of these countries are the same regardless of GDP.";
        }

        // Build output
        StringBuilder output = new StringBuilder();
        output.append(avgHappinessString).append("\n\n");
        output.append(top3Happy).append("\n");
        output.append(gdpCorrelation);
        output.append("\n\n").append(gdpCorrelationStringExplanation);

        // Set the formatted text to the text area
        textArea.setText(output.toString());

        // Update the UI
        repaint();
        revalidate();
    }
}
