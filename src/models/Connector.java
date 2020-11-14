package models;

import java.util.List;

public interface Connector {
    String getData(List<String> properties, List<String> filters);
}
