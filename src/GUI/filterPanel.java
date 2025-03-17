package GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class filterPanel extends JPanel {
    private List<JCheckBox> filterCheckboxes;
    private FilterListener listener;
    private JLabel titleLabel;

    /**
     * Constructor that creates the filter panel with checkboxes for each column.
     */
    public filterPanel() {
        filterCheckboxes = new ArrayList<>();
        // Define all columns that can be filtered (Country is always shown so it's not included)
        String[] filterOptions = {"Happiness Rank", "Happiness Score", "Economy Score", "Social Score", "Health Score"};

        titleLabel = new JLabel("Filter By: ");
        add(titleLabel);


        // Create a checkbox for each filter option
        for (String filterOption : filterOptions) {
            JCheckBox checkBox = new JCheckBox(filterOption);
            // Add action listener to handle checkbox selections
            checkBox.addActionListener(e -> causeFilterChange());

            // Add to our list and to the panel
            filterCheckboxes.add(checkBox);
            add(checkBox);
        }
    }
    /**
     * Notify the listener that filter selections have changed.
     * Called when any checkbox is clicked.
     */
    public void causeFilterChange(){
            if (listener != null) {
                listener.onFilterChanged(getFilters());
            }
    }

    /**
     * Gets the current list of selected filters.
     * Returns List of column names that are currently selected
     */
    public List<String> getFilters() {
        return filterCheckboxes.stream()
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());
    }

    /**
     * Sets the listener that will be notified when filters change.
     * @param listener The FilterListener to notify
     */
    public void setFilterListener(FilterListener listener) {
       this.listener = listener;
    }

}
