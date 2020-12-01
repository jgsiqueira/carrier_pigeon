package connectors;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Connector;
import models.Filter;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockMarket implements Connector {
    public enum Properties {
        QUOTE
    }

    @Override
    public String getData(List<String> properties, List<Filter> filters) {
        validateInput(properties, filters);

        StringBuilder data = new StringBuilder();

        String[] symbols = getUniqueSymbols(filters);

        Map<String, Stock> stocks = fetchStockData(symbols);

        if(properties.contains(Properties.QUOTE.name())) {
            for(Filter filter : filters) {
                if (filter.getProperty().equals(Properties.QUOTE.name())) {
                    String symbol = filter.getValue();
                    Stock stock = stocks.get(symbol);

                    data.append(String.format("[%s] %s:\n", symbol, stock.getName().replace("&amp;", "&")));
                    data.append(getStockQuote(stock));
                    data.append("\n");
                }
            }
        }

        return data.toString();
    }

    private Map<String, Stock> fetchStockData(String[] symbols) {
        Map<String, Stock> stock = null;
        try {
            stock = YahooFinance.get(symbols);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stock;
    }

    private String[] getUniqueSymbols(List<Filter> filters) {
        Set<String> symbols = new HashSet<>();
        for (Filter filter : filters) {
            symbols.add(filter.getValue());
        }
        String[] symbolsArray = new String[symbols.size()];
        symbols.toArray(symbolsArray);
        return symbolsArray;
    }

    private void validateInput(List<String> properties, List<Filter> filters) {
        if(properties == null) {
            throw new InvalidParameterException("properties parameter should not be null.");
        }

        if(filters == null) {
            throw new InvalidParameterException("filters parameter should not be null.");
        }
    }

    private String getStockQuote(Stock stock) {
        StringBuilder data = new StringBuilder();

        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal previous = stock.getQuote().getPreviousClose();
        BigDecimal current = stock.getQuote().getPrice();

        BigDecimal yearHigh = stock.getQuote().getYearHigh();
        BigDecimal yearHighChange = stock.getQuote().getChangeFromYearHighInPercent();

        BigDecimal yearLow = stock.getQuote().getYearLow();
        BigDecimal yearLowChange = stock.getQuote().getChangeFromYearLowInPercent();

        BigDecimal avg50Days = stock.getQuote().getPriceAvg50().setScale(2, RoundingMode.CEILING);
        BigDecimal avg50DaysChange = stock.getQuote().getChangeFromAvg50InPercent();

        BigDecimal avg200Days = stock.getQuote().getPriceAvg200().setScale(2, RoundingMode.CEILING);
        BigDecimal avg200DaysChange = stock.getQuote().getChangeFromAvg200InPercent();

        data.append(String.format("* Previous Close: %s %s\n", previous.toPlainString(), stock.getCurrency()));
        data.append(String.format("* Current Price : %s %s (%s %%)\n", current.toPlainString(), stock.getCurrency(), change.toPlainString()));
        data.append(String.format("* Yeah High     : %s %s (%s %%)\n", yearHigh.toPlainString(), stock.getCurrency(), yearHighChange.toPlainString()));
        data.append(String.format("* Yeah Low      : %s %s (%s %%)\n", yearLow.toPlainString(), stock.getCurrency(), yearLowChange.toPlainString()));
        data.append(String.format("* 50 days avg   : %s %s (%s %%)\n", avg50Days.toPlainString(), stock.getCurrency(), avg50DaysChange.toPlainString()));
        data.append(String.format("* 200 days avg  : %s %s (%s %%)\n", avg200Days.toPlainString(), stock.getCurrency(), avg200DaysChange.toPlainString()));

        return data.toString();
    }
}
