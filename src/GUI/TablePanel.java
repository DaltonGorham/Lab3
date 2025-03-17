package GUI;
import HappinessData.CountryHappiness;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import static HappinessData.FileReader.read;

import java.util.Arrays;
import java.util.List;


public class TablePanel extends JPanel implements FilterListener{
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<CountryHappiness> data;
    private String[] columnNames;
    private filterPanel filterPanel;
    private DetailsPanel detailsPanel;

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
        filterPanel = new filterPanel();
        filterPanel.setFilterListener(this);
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
     * Implementation of FilterListener interface.
     * Called when checkbox selection changes in the filter panel.
     * Updates the table to show only selected columns.
     * @param filters List of column names that should be displayed
     */
    @Override
    public void onFilterChanged(List<String> filters) {

        model.setRowCount(0);

        if (filters.isEmpty()) {
            model.setColumnIdentifiers(columnNames);
            addDataToModel(model);
            return;
        }
        // Always include Country column (add it at the beginning)
        filters.add(0, "Country");
        // Update the column headers in the table
        model.setColumnIdentifiers(filters.toArray());

        for (CountryHappiness countryHappiness : data) {
            Object[] row = new Object[filters.size()];

            // Populate each cell based on the column filter
            for (int i = 0; i < filters.size(); i++) {
                // Use switch expression to get the right data for each column
                String filter = filters.get(i);
                row[i] = switch (filter) {
                    case "Country" -> countryHappiness.country();
                    case "Happiness Rank" -> countryHappiness.happinessRank();
                    case "Happiness Score" -> countryHappiness.happinessScore();
                    case "Economy Score" -> countryHappiness.economy();
                    case "Social Score" -> countryHappiness.socialScore();
                    case "Health Score" -> countryHappiness.healthScore();
                    default -> "";
                };
            }
            model.addRow(row);
        }
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
     * Updates the details panel to display data from the specified row in the table.
     * The data is retrieved from the table model for the given row and passed
     * to the associated DetailsPanel instance.
     *
     * @param row The index of the row in the table whose details should be displayed in the details panel.
     */

    public void updateDetailsPanel(int row) {
        int numColumns = model.getColumnCount();
        String[] details = new String[numColumns];
            for (int i = 0; i < numColumns; i++) {
                details[i] = model.getColumnName(i) + "  ->  " + model.getValueAt(row, i).toString() + "    ";

            }

            detailsPanel.onRowClicked(details);
    }
}
