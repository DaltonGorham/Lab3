package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DataVisualizationTool extends JFrame {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Country Happiness Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));


        // set components
        TablePanel tablePanel = new TablePanel();
        DetailsPanel detailsPanel = new DetailsPanel();
        StatsPanel statsPanel = new StatsPanel();
        DataChartPanel chartPanel = new DataChartPanel();

        // set listeners
        tablePanel.setDetailsPanel(detailsPanel);
        tablePanel.setDataChangeListener(statsPanel);
        tablePanel.setDataChangeListener(chartPanel);


        // add to frame
        frame.add(chartPanel, BorderLayout.EAST);
        frame.add(tablePanel, BorderLayout.WEST);
        bottomPanel.add(detailsPanel);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(statsPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
