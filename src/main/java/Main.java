import connectors.Corona;
import connectors.StockMarket;
import java.util.ArrayList;
import models.Filter;

public class Main {
    public static void main(String[] args) {
        Corona corona = new Corona();
        ArrayList<String> coronaProperties = new ArrayList<>();
        coronaProperties.add(Corona.Properties.DAILY_REPORT.name());

        ArrayList<Filter> coronaFilters = new ArrayList<>();
        coronaFilters.add(new Filter(Corona.Properties.DAILY_REPORT.name(), "BritishColumbia", 5));
        coronaFilters.add(new Filter(Corona.Properties.DAILY_REPORT.name(), "Quebec", 3));

        String dataCorona = corona.getData(coronaProperties, coronaFilters);

        // Stock data
        ArrayList<Filter> stockFilters = new ArrayList<>();
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "GOOGL", 5));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "AAPL", 5));
        stockFilters.add(new Filter(StockMarket.Properties.STATS.name(), "GOOGL", 5));
        stockFilters.add(new Filter(StockMarket.Properties.STATS.name(), "AAPL", 5));

        ArrayList<String> stockProperties = new ArrayList<>();
        stockProperties.add(StockMarket.Properties.QUOTE.name());

        StockMarket stockMarket = new StockMarket();
        String dataStock = stockMarket.getData(stockProperties, stockFilters);

        // Print
        System.out.println(dataCorona);
        System.out.println("---------------\n\n");
        System.out.println(dataStock);
    }
}
