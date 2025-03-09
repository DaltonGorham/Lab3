package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DataVisualizationTool extends JFrame {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Country Happiness Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.add(new TablePanel());
        frame.setVisible(true);
        frame.pack();
    }
}
