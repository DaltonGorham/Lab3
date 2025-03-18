package GUI;
import HappinessData.CountryHappiness;
import java.util.List;
public interface DataUpdateListener {
    void onDataUpdated(List<CountryHappiness> data);
}
