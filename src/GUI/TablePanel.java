package GUI;
import HappinessData.CountryHappiness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import static HappinessData.FileReader.read;


public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<CountryHappiness> data;
    private String[] columnNames;

    public TablePanel() throws IOException {
       columnNames = new String[]{
               "Country",
               "Happiness Rank",
               "Happiness Score",
               "Economy Score",
               "Social Score",
               "Health Score"
       };

        data = read("data/HappinessData.csv");
        model = new DefaultTableModel(columnNames, 0); // initialize to  0 rows at first then add data after
        addDataToModel(model);

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 600));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addDataToModel(DefaultTableModel model) {
        for (CountryHappiness countryHappiness : data) {
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

}
