# Happiness Data Visualization Tool
An application for visualizing and analyzing country happiness data, providing interactive features to explore relationships between happiness scores and various economic and social factors.
## Overview
This tool offers a comprehensive view of global happiness data through multiple interactive components:

## Steps to run:
#### `git clone https://github.com/DaltonGorham/Lab3.git `
#### select on the GUI package and run the `DataVisualizationTool`



### Main Components
1. **Table Panel (`TablePanel`)**
    - Displays raw data in a sortable table format
    - Allows filtering of data based on various metrics
    - Triggers updates to other components when selections change

2. **Details Panel (`DetailsPanel`)**
    - Shows detailed information about a selected country
    - Updates dynamically when a table row is clicked
    - Implements the `RowListener` interface

3. **Statistics Panel (`StatsPanel`)**
    - Calculates and displays key statistics:
        - Average happiness score
        - Top 3 happiest countries
        - GDP-Happiness correlation

    - Updates automatically when data changes
    - Implements `DataUpdateListener` interface

4. **Chart Panel (`DataChartPanel`)**
    - Visualizes happiness and economy scores in a bar chart
    - Updates dynamically with filtered data
    - Implements `DataUpdateListener` interface

5. **Filter Panel (`filterPanel`)**
    - Provides filtering options for data analysis
    - Allows filtering by different metrics and ranges
    - Includes reset functionality

### Data 
- `CountryHappiness` record stores country-specific data:
    - Country name
    - Happiness rank and score
    - Economic indicators
    - Social scores
    - Health scores

### Key Interfaces
- `DataUpdateListener`: Handles data update events
- `RowListener`: Manages row selection events
- `FilterListener`: Handles filtering events


### Dependencies
- JFreeChart for visualization
- Apache Commons for StatsPanel
