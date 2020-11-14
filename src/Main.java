import connectors.Corona;
import connectors.Corona.Properties;
import java.util.ArrayList;
import models.Filter;

public class Main {
    public static void main(String[] args) {
        Corona corona = new Corona();

        ArrayList<String> properties = new ArrayList<>();
        properties.add(Properties.DAILY_REPORT.name());

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter(Properties.DAILY_REPORT.name(), "BritishColumbia", 5));
        filters.add(new Filter(Properties.DAILY_REPORT.name(), "Quebec", 3));

        String data = corona.getData(properties, filters);
        System.out.println(data);
    }
}
