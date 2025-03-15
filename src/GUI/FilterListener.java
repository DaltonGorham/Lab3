package GUI;

import java.util.List;
/**
 * FilterListener interface defines a contract
 * that notifies TablePanel when filter selections change.
 */
public interface FilterListener {
    void onFilterChanged(List<String> selectedFilters);
}
