package models;

import java.util.List;

public interface Connector {
    String getData(List<String> properties, List<Filter> filters);
}
