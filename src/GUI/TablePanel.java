package GUI;
import HappinessData.CountryHappiness;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import static HappinessData.FileReader.read;
import java.util.List;


public class TablePanel extends JPanel implements FilterListener{
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<CountryHappiness> data;
    private String[] columnNames;
    private filterPanel filterPanel;
    private DetailsPanel detailsPanel;
    private DataUpdateListener dataChangeListener; // listener for the stats panel

    /**
     * Constructor that initializes the table panel with data from CSV file.
     * @throws IOException if there's an error reading the data file
     */

    public TablePanel() throws IOException {
        // Define all available columns for the table
        columnNames = new String[]{
                "Country",
                "Happiness Rank",
                "Happiness Score",
                "Economy Score",
                "Social Score",
                "Health Score"
        };
        // Read the happiness data from CSV file
        data = read("data/HappinessData.csv");
        initializeTable();
    }

    /**
     * Populates the provided table model with all rows of data.
     * @param model The DefaultTableModel to populate with data
     */
    public void addDataToModel(DefaultTableModel model) {
        for (CountryHappiness countryHappiness : data) {
            // Create a row with all data fields for each country
            Object[] row = {
                    countryHappiness.country(),
                    countryHappiness.happinessRank(),
                    countryHappiness.happinessScore(),
                    countryHappiness.economy(),
                    countryHappiness.socialScore(),
                    countryHappiness.healthScore()
            };
            model.addRow(row);
        }
    }
    /**
     * Sets up the table and filter panel
     * This is called by the constructor.
     */
    private void initializeTable() {

        model = new DefaultTableModel(columnNames, 0); // initialize to  0 rows at first then add data after
        addDataToModel(model);
        this.setLayout(new BorderLayout());

        // Create filter panel and register this class as the listener

        filterPanel = new filterPanel(this);
        add(filterPanel, BorderLayout.NORTH);

        // Create the table with model and add it to a scroll pane
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 500));
        add(scrollPane, BorderLayout.WEST);

        // Mouse listener when clicking a row in the table it sends data to details panel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               int row = table.getSelectedRow();
               if (row >= 0) {
                   updateDetailsPanel(row);
               }
            }
        });
    }

    /**
     * Filters the data based on the specified metric and range values (minimum and maximum),
     * and updates the table model to display only the rows that meet the filter condition.
     *
     * @param metric the metric to filter by (e.g., "Happiness Score", "Economy Score", etc.)
     * @param min the minimum value of the range for the specified metric
     * @param max the maximum value of the range for the specified metric
     */
    @Override
    public void onRangeFilterApplied(String metric, double min, double max) {
        model.setRowCount(0);
        List<CountryHappiness> filteredData = new ArrayList<>();
        for (CountryHappiness country : data) {
            if (meetsRangeFilter(country, metric, min, max)) {
                Object[] row = {
                        country.country(),
                        country.happinessRank(),
                        country.happinessScore(),
                        country.economy(),
                        country.socialScore(),
                        country.healthScore()
                };
                filteredData.add(country);
                model.addRow(row);
            }
        }
        dataChangeListener.onDataUpdated(filteredData);
    }


    // resets back to original table without filters
    @Override
    public void onResetFilters() {
        dataChangeListener.onDataUpdated(data); // if reset change stats back to original table
        model.setRowCount(0);
        addDataToModel(model);
    }


    /**
     * Checks if the given CountryHappiness object meets the specified range filter
     * based on the provided metric, minimum, and maximum values.
     *
     * @param country the country object containing happiness data to evaluate
     * @param metric the metric to filter by (e.g., "Happiness Score", "Economy Score", etc.)
     * @param min the minimum value of the range for the specified metric
     * @param max the maximum value of the range for the specified metric
     * @return true if the country metric's value is within the specified range; false otherwise
     */
    private boolean meetsRangeFilter(CountryHappiness country, String metric, double min, double max) {
        return switch (metric) {
            case "Happiness Score" -> country.happinessScore() >= min && country.happinessScore() <= max;
            case "Economy Score" -> country.economy() >= min && country.economy() <= max;
            case "Social Score" -> country.socialScore() >= min && country.socialScore() <= max;
            case "Health Score" -> country.healthScore() >= min && country.healthScore() <= max;
            default -> true;
        };
    }

    /**
     * Sets the details panel that will display the detailed information
     * for a selected row in the table.
     *
     * @param detailsPanel the DetailsPanel instance to associate with the TablePanel
     */

    public void setDetailsPanel(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

    /**
     * Updates the details panel with information from a specified row in the table model.
     * @param row the index of the row in the table model whose details are to be displayed
     */

    public void updateDetailsPanel(int row) {
        // get the number of columns in case a filter has been applied
        int numColumns = model.getColumnCount();
        String[] details = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                // set the details array here to prevent problems in details panel
                details[i] = model.getColumnName(i) + "  =====>  " + model.getValueAt(row, i).toString() + "    ";

            }
            detailsPanel.onRowClicked(details);
    }

    public void setDataChangeListener(DataUpdateListener listener) {
        this.dataChangeListener = listener;
    }

}
