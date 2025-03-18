package GUI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DetailsPanel extends JPanel implements RowListener {
    private JTextArea detailsArea;
    private RowListener listener;
    public DetailsPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 300));
        setMaximumSize(new Dimension(600, 300));

        JLabel title = new JLabel("Country Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(title);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setPreferredSize(new Dimension(280, 250));
        detailsArea.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));
        detailsArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(detailsArea);
        setVisible(false);

    }


    /**
     * This method is triggered when a table row is clicked. It updates the details panel
     * with the provided data and makes the panel visible. The method is part of the
     * implementation of the RowListener interface.
     * @param details An array of strings containing data from the clicked row.
     */
    @Override
    public void onRowClicked(String[] details) {
        // clear the text
        detailsArea.setText("");
        for (String detail : details) {
            detailsArea.append(detail + "\n\n");
            detailsArea.setFont(new Font("Arial", Font.BOLD, 16));
        }
        setVisible(true);
        repaint();
        revalidate();
    }

    public void setListener(RowListener listener) {
        this.listener = listener;
    }

}
