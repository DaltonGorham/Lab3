package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DataVisualizationTool extends JFrame {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Country Happiness Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1000);
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        TablePanel tablePanel = new TablePanel();
        DetailsPanel detailsPanel = new DetailsPanel();
        StatsPanel statsPanel = new StatsPanel();
        tablePanel.setDetailsPanel(detailsPanel);
        tablePanel.setDataChangeListener(statsPanel);


        frame.add(tablePanel, BorderLayout.WEST);
        bottomPanel.add(detailsPanel);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(statsPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
