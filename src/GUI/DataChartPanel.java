package GUI;

import HappinessData.CountryHappiness;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import static HappinessData.FileReader.read;


public class DataChartPanel extends JPanel implements DataUpdateListener{

   private DefaultCategoryDataset dataset;
   private List<CountryHappiness> data;
   private TextTitle subtitle;

    /**
     * Constructs a DataChartPanel
     * This panel displays a bar chart visualizing the happiness score and economy score for various countries,
     * using the provided dataset.
     * Throws:
     * - IOException if there is an error reading the CSV file containing the happiness data.
     * The chart is dynamically updated via the {@code onDataUpdated} method when the dataset changes.
     */
    public DataChartPanel() throws IOException {
       setName("Country Happiness Data");
       setLayout(new BorderLayout());
       // create the dataset
       dataset = new DefaultCategoryDataset();
       data = read("data/HappinessData.csv");
       setPreferredSize(new Dimension(720, 800));

       // create the chart as a bar chart
       JFreeChart chart = ChartFactory.createBarChart("Happiness Score vs Economy",
               "Country",
               "Score",
               dataset,
               PlotOrientation.VERTICAL,
               true,
               true,
               false
       );

       // add the subtitle
        subtitle = new TextTitle("Current Data: " + data.size() + " countries"
                , new Font("SansSerif", Font.ITALIC, 14));
        chart.addSubtitle(subtitle);

        // update bar graphs features
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        // add the components and add the initial data to the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel,BorderLayout.CENTER);
        onDataUpdated(data); // start chart with current table


    }

    /**
     * Updates the chart when the data is updated. This method clears and refreshes
     * the dataset using the provided list of {@code CountryHappiness} objects.
     *
     * @param data the new list of {@code CountryHappiness} objects containing the
     *             updated happiness and economy scores for various countries
     */
    @Override
    public void onDataUpdated(List<CountryHappiness> data) {
        updateChart(data);
    }

    /**
     * Updates the chart dataset and subtitle based on the provided list of {@code CountryHappiness}
     * objects. This method clears the current dataset and populates it with the happiness score and
     * economy score corresponding to each country in the list. Updates the chart's subtitle to
     * display the total number of countries in the updated data.
     *
     * @param data the list of {@code CountryHappiness} objects containing the updated happiness
     *             scores, economy scores, and country names
     */
    private void updateChart(List<CountryHappiness> data) {
        dataset.clear();

        for (CountryHappiness countryHappiness : data) {
            dataset.addValue(countryHappiness.happinessScore(), "Happiness Score", countryHappiness.country());
            dataset.addValue(countryHappiness.economy(), "Economy Score", countryHappiness.country());
        }

        subtitle.setText("Current Data: " + data.size() + " countries");
        repaint();
        revalidate();
    }

}


