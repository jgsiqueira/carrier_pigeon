package connectors;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import models.Connector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Corona implements Connector {
    private final String URL = "https://www.ctvnews.ca/health/coronavirus/tracking-every-case-of-covid-19-in-canada-1.4852102";
    private final String ERROR_FETCHING_HTML = "Error when fetching the HTML";
    private final HashMap<String, String> provinceCSS = new HashMap<>();

    public Corona() {
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
    }

    @Override
    public String getData(List<String> properties, List<String> filters) {
        StringBuilder data = new StringBuilder();

        Document document = fetchHTML();

        if(document == null) {
            return ERROR_FETCHING_HTML;
        }

        for(String province : provinceCSS.keySet()) {
            if(filters.contains(province)) {
                data.append(String.format("%s:\n", province));
                data.append(getProvinceData(document, province,5));
                data.append("\n");
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
}
