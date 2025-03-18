package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class filterPanel extends JPanel {
    private  JComboBox<String> metricCombo;
    private  JComboBox<String> rangeCombo;
    private  FilterListener filterListener;
    private final String[] metrics = {
            "Happiness Score",
            "Economy Score",
            "Social Score",
            "Health Score"
    };
    private final String[] ranges = {"High", "Moderate", "Low"};

    // ranges for filter settings
    private final double HAPPINESS_HIGH_MAX = 10.0;
    private final double HAPPINESS_HIGH_MIN = 7.0;
    private final double HAPPINESS_MOD_MAX = 6.99;
    private final double HAPPINESS_MOD_MIN = 5.0;
    private final double HAPPINESS_LOW_MAX = 4.99;
    private final double HAPPINESS_LOW_MIN = 0.0;
    private final double ECONOMY_SOCIAL_HIGH_MAX = 2.0;
    private final double ECONOMY_SOCIAL_HIGH_MIN = 1.5;
    private final double ECONOMY_SOCIAL_MOD_MAX = 1.49;
    private final double ECONOMY_SOCIAL_MOD_MIN = 1.0;
    private final double ECONOMY_SOCIAL_LOW_MAX = 0.99;
    private final double ECONOMY_SOCIAL_LOW_MIN = 0.0;
    private final double HEALTH_HIGH_MAX = 1.0;
    private final double HEALTH_HIGH_MIN = 0.8;
    private final double HEALTH_MOD_MAX = 0.79;
    private final double HEALTH_MOD_MIN = 0.5;
    private final double HEALTH_LOW_MAX = 0.49;
    private final double HEALTH_LOW_MIN = 0.0;





    /**
     * Constructor that creates the filter panel with combo boxes
     */
    public filterPanel(FilterListener listener) {

        this.filterListener = listener;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Create filtering boxes
        metricCombo = new JComboBox<>(metrics);
        rangeCombo = new JComboBox<>(ranges);
        JButton applyButton = new JButton("Apply Filter");
        JButton resetButton = new JButton("Reset");

        // Add to panel
        add(new JLabel("Filter by:"));
        add(metricCombo);
        add(new JLabel("Range:"));
        add(rangeCombo);
        add(applyButton);
        add(resetButton);

        // Add listeners
        applyButton.addActionListener(e -> applyFilter());
        resetButton.addActionListener(e -> filterListener.onResetFilters());
        }


    private void applyFilter() {
        String selectedMetric = (String) metricCombo.getSelectedItem();
        String selectedRange = (String) rangeCombo.getSelectedItem();

        double[] range = getRangeValues(selectedMetric, selectedRange);
        filterListener.onRangeFilterApplied(selectedMetric, range[0], range[1]);
    }


    /**
     * Determines the range of values based on the specified metric and range.
     *
     * @param metric the metric for which the range values are being retrieved
     *               (e.g., "Happiness Score", "Economy Score", "Social Score", or "Health Score")
     * @param range  the range category for the metric (e.g., "High", "Moderate", "Low", or other custom ranges)
     * @return a double array containing the minimum and maximum values of the range
     *         for the specified metric and range category; defaults to generalized
     *         ranges if the metric or range is unrecognized
     */
    private double[] getRangeValues(String metric, String range) {
        return switch (metric) {
            case "Happiness Score" -> switch (range) {
                case "High" -> new double[]{HAPPINESS_HIGH_MIN, HAPPINESS_HIGH_MAX};
                case "Moderate" -> new double[]{HAPPINESS_MOD_MIN, HAPPINESS_MOD_MAX};
                case "Low" -> new double[]{HAPPINESS_LOW_MIN, HAPPINESS_LOW_MAX};
                default -> new double[]{0.0, 10.0};
            };
            case "Economy Score", "Social Score" -> switch (range) {
                case "High" -> new double[]{ECONOMY_SOCIAL_HIGH_MIN, ECONOMY_SOCIAL_HIGH_MAX};
                case "Moderate" -> new double[]{ECONOMY_SOCIAL_MOD_MIN, ECONOMY_SOCIAL_MOD_MAX};
                case "Low" -> new double[]{ECONOMY_SOCIAL_LOW_MIN, ECONOMY_SOCIAL_LOW_MAX};
                default -> new double[]{0.0, 2.0};
            };
            case "Health Score" -> switch (range) {
                case "High" -> new double[]{HEALTH_HIGH_MIN, HEALTH_HIGH_MAX};
                case "Moderate" -> new double[]{HEALTH_MOD_MIN, HEALTH_MOD_MAX};
                case "Low" -> new double[]{HEALTH_LOW_MIN, HEALTH_LOW_MAX};
                default -> new double[]{0.0, 1.0};
            };
            default -> new double[]{0.0, 10.0};
        };
    }

}
