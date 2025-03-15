package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DataVisualizationTool extends JFrame {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Country Happiness Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLayout(new BorderLayout());

        TablePanel tablePanel = new TablePanel();
        DetailsPanel detailsPanel = new DetailsPanel();
        tablePanel.setDetailsPanel(detailsPanel);


        frame.add(tablePanel, BorderLayout.WEST);
        frame.add(detailsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.pack();
    }
}
