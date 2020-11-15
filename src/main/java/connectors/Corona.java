package connectors;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import models.Connector;
import models.Filter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This connector generates a report for corona virus cases in Canada.
 * It fetches and parses the raw HTML from CTVNews website.
 */
public class Corona implements Connector {
    public enum Properties {
        DAILY_REPORT
    }

    private final String URL = "https://www.ctvnews.ca/health/coronavirus/tracking-every-case-of-covid-19-in-canada-1.4852102";
    private final String ERROR_FETCHING_HTML = "Error when fetching the HTML";

    // Helpers for province data
    private final HashMap<String, String> provinceCSS = new HashMap<>();
    private final HashMap<String, String> provinceName = new HashMap<>();

    public Corona() {
        initializeProvinceHelpers();
    }

    @Override
    public String getData(List<String> properties, List<Filter> filters) {
        validateInput(properties, filters);

        StringBuilder data = new StringBuilder();

        Document document = fetchHTML();
        if(document == null) {
            return ERROR_FETCHING_HTML;
        }

        if(properties.contains(Properties.DAILY_REPORT.name())) {
            for(Filter filter : filters) {
                if (filter.getProperty().equals(Properties.DAILY_REPORT.name())) {
                    String province = filter.getValue();
                    int rows = filter.getRows();
                    data.append(String.format("%s:\n", provinceName.get(province)));
                    data.append(getProvinceData(document, province, rows));
                    data.append("\n");
                }
            }
        }

        return data.toString();
    }

    private Document fetchHTML() {
        Document document = null;
        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private String getProvinceData(Document document, String province, int rows) {
        StringBuilder data = new StringBuilder();

        Elements britishColumbia = document.select(provinceCSS.get(province));

        rows = Math.min(rows, britishColumbia.size());
        for(int i=0; i<rows; i++) {
            if(britishColumbia.get(i).text().split(" ")[0].toLowerCase().startsWith("note")) {
                rows = Math.min(rows+1, britishColumbia.size());
                continue;
            }

            data.append(String.format("* %s\n", britishColumbia.get(i).text()));
        }

        return data.toString();
    }

    private void initializeProvinceHelpers() {
        provinceCSS.put("BritishColumbia", "#british-columbia-collapse div p");
        provinceCSS.put("Alberta", "#alberta-collapse div p");
        provinceCSS.put("Saskatchewan", "#saskatchewan-collapse div p");
        provinceCSS.put("Manitoba", "#manitoba-collapse div p");
        provinceCSS.put("Ontario", "#ontario-collapse div p");
        provinceCSS.put("Quebec", "#quebec-collapse div p");
        provinceCSS.put("NewBrunswick", "#new-brunswick-collapse div p");
        provinceCSS.put("NovaScotia", "#nova-scotia-collapse div p");
        provinceCSS.put("PrinceEdwardIsland", "#prince-edward-island-collapse div p");
        provinceCSS.put("NewfoundlandAndLabrador", "#newfoundland-and-labrador-collapse div p");
        provinceCSS.put("Yukon", "#yukon-collapse div p");
        provinceCSS.put("NorthwestTerritories", "#northwest-territories-collapse div p");
        provinceCSS.put("Nunavut", "#nunavut-collapse div p");

        provinceName.put("BritishColumbia", "British Columbia");
        provinceName.put("Alberta", "Alberta");
        provinceName.put("Saskatchewan", "Saskatchewan");
        provinceName.put("Manitoba", "Manitoba");
        provinceName.put("Ontario", "Ontario");
        provinceName.put("Quebec", "Quebec");
        provinceName.put("NewBrunswick", "New Brunswick");
        provinceName.put("NovaScotia", "Nova Scotia");
        provinceName.put("PrinceEdwardIsland", "Prince Edward Island");
        provinceName.put("NewfoundlandAndLabrador", "Newfoundland and Labrador");
        provinceName.put("Yukon", "Yukon");
        provinceName.put("NorthwestTerritories", "Northwest Territories");
        provinceName.put("Nunavut", "Nunavut");
    }

    private void validateInput(List<String> properties, List<Filter> filters) {
        if(properties == null) {
            throw new InvalidParameterException("properties parameter should not be null.");
        }

        if(filters == null) {
            throw new InvalidParameterException("filters parameter should not be null.");
        }
    }
}
