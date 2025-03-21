package GUI;
import HappinessData.CountryHappiness;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import static HappinessData.FileReader.read;
import java.util.Comparator;
import java.util.List;


public class TablePanel extends JPanel implements FilterListener{
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<CountryHappiness> data;
    private String[] columnNames;
    private filterPanel filterPanel;
    private DetailsPanel detailsPanel;
    private List<DataUpdateListener> dataChangeListeners; // listeners for stats and chart panel
    private TableRowSorter sorter;

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
        dataChangeListeners = new ArrayList<>();
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
        allowSorting();

        // Mouse listener when clicking a row in the table it sends data to details panel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               int row = table.getSelectedRow();
               int col = table.getSelectedColumn();
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
        notifyDataChangeListeners(filteredData);
    }


    // resets back to original table without filters
    @Override
    public void onResetFilters() {
        notifyDataChangeListeners(data); // if reset change stats back to original table
        model.setRowCount(0);
        resetSorting();
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

    /**
     * Adds a listener that will be notified when there is a change in the table's data.
     *
     * @param listener the DataUpdateListener instance to be registered for data change events
     */

    public void addDataChangeListener(DataUpdateListener listener) {
        dataChangeListeners.add(listener);
    }

    /**
     * Notifies all registered data change listeners about an update to the dataset.
     *
     * @param data The list of CountryHappiness records representing the updated data.
     */
    private void notifyDataChangeListeners(List<CountryHappiness> data) {
        for (DataUpdateListener listener : dataChangeListeners) {
            listener.onDataUpdated(data);
        }
    }

    private void allowSorting(){

        // initialize the table row sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // listener for clicking a column
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int col = table.getColumnModel().getColumnIndexAtX(e.getX());
                sorter.toggleSortOrder(col);
            }
        });

        // sorting logic:
        // country sorted alphabetically
        // all others sorted by either ascending or descending
        sorter.setComparator(0, String.CASE_INSENSITIVE_ORDER);

        sorter.setComparator(1, (Comparator<Integer>) Integer::compare);

        for (int column = 2; column < columnNames.length; column++) {
            sorter.setComparator(column, (Comparator<Double>) Double::compare);
        }


        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
    }

    // reset all sorts
    private void resetSorting(){
        sorter.setSortKeys(null);
        table.getTableHeader().repaint();
    }

}
