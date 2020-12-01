import connectors.Corona;
import connectors.StockMarket;
import destinations.email.Email;
import destinations.email.SendGrid;
import java.util.ArrayList;
import models.Filter;

public class Main {
    public static void main(String[] args) {
        Corona corona = new Corona();
        ArrayList<String> coronaProperties = new ArrayList<>();
        coronaProperties.add(Corona.Properties.DAILY_REPORT.name());

        ArrayList<Filter> coronaFilters = new ArrayList<>();
        coronaFilters.add(new Filter(Corona.Properties.DAILY_REPORT.name(), "BritishColumbia", 5));
        coronaFilters.add(new Filter(Corona.Properties.DAILY_REPORT.name(), "Quebec", 5));

        String dataCorona = corona.getData(coronaProperties, coronaFilters);

        // Stock data
        ArrayList<Filter> stockFilters = new ArrayList<>();
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "GOOGL", 0));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "AAPL", 0));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "MSFT", 0));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "VRE.TO", 0));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "VFV.TO", 0));
        stockFilters.add(new Filter(StockMarket.Properties.QUOTE.name(), "XEI.TO", 0));

        ArrayList<String> stockProperties = new ArrayList<>();
        stockProperties.add(StockMarket.Properties.QUOTE.name());

        StockMarket stockMarket = new StockMarket();
        String dataStock = stockMarket.getData(stockProperties, stockFilters);

        Email email = new SendGrid("", "", "Test Carrier Pigeon", "");
        email.sendMessage(dataCorona + "\n-----\n" + dataStock);
    }
}
