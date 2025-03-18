package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DetailsPanel extends JPanel implements RowListener {
    private JTextArea detailsArea;
    private RowListener listener;
    public DetailsPanel() {

        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(500, 300));

        JLabel title = new JLabel("Country Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setPreferredSize(new Dimension(400, 200));
        detailsArea.setBorder(new MatteBorder(0, 0, 3, 3, Color.BLACK));
        add(detailsArea, BorderLayout.WEST);
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
            detailsArea.setFont(new Font("Arial", Font.BOLD, 20));
        }
        setVisible(true);
        repaint();
        revalidate();
    }

    public void setListener(RowListener listener) {
        this.listener = listener;
    }

}
