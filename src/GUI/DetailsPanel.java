package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DetailsPanel extends JPanel implements RowListener {
    private JLabel[] textLabels;
    private JLabel titleLabel;
    private final int PANEL_WIDTH = 300;
    private final int PANEL_HEIGHT = 300;
    private final int INFO_PANEL_ROWSIZE = 6;
    private final int INFO_PANEL_COLSIZE = 2;
    private final int HORIZONTAL_GAP = 30;
    private final int VERTICAL_GAP = 10;
    private final int PADDING_SIZE = 10;
    private final int NUM_LABELS = 6;

    /**
     * Constructs a new DetailsPanel object.
     * This is designed to display details about a country. The panel includes a title label
     * and a grid layout containing information fields for specific attributes such as country name,
     * happiness rank, happiness score, economy score, social score, and health score.
     * The panel is initially hidden

     */
    public DetailsPanel() {
        setVisible(false);
       setLayout(new BorderLayout(10,10));
       setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
       setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

       titleLabel = new JLabel("Country Details");
       titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

       add(titleLabel, BorderLayout.NORTH);

       JPanel infoPanel = new JPanel();
       infoPanel.setLayout(new GridLayout(INFO_PANEL_ROWSIZE, INFO_PANEL_COLSIZE, HORIZONTAL_GAP, VERTICAL_GAP));
       // create some padding for the details
       infoPanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
       // set border for looks
       infoPanel.setBorder(new MatteBorder(0, 0, 2, 2, Color.BLACK));

        textLabels = new JLabel[]{
                new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel(),
        };

        // add the labels to the panel with formatting
        for (int i = 0; i < NUM_LABELS; i++) {
            textLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
            textLabels[i].setBackground(Color.LIGHT_GRAY);

            infoPanel.add(textLabels[i]);
        }
        add(infoPanel, BorderLayout.WEST); // after all labels have been added to info panel, and the info panel to details panel
    }

    /**
     * Updates the text labels in the panel with the provided details.
     * @param details An array of strings containing new text values to update the labels.
     */
    public void updateDetails(String[] details) {
        clearDetails();
        for (int i = 0; i < details.length; i++) {
            textLabels[i].setText(details[i]);
        }
    }

    /**
     * This method is triggered when a table row is clicked. It updates the details panel
     * with the provided data and makes the panel visible. The method is part of the
     * implementation of the RowListener interface.
     * @param details An array of strings containing data from the clicked row.
     */
    @Override
    public void onRowClicked(String[] details) {
        updateDetails(details);
        setVisible(true);
        repaint();
        revalidate();
    }

    void clearDetails(){
        for (JLabel label : textLabels) {
            label.setText("");
        }

        revalidate();
        repaint();
    }
}
