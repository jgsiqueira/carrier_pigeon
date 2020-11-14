package models;

public class Filter {
    private String property;
    private String value;
    private int rows;

    public Filter(String property, String value, int rows) {
        this.property = property;
        this.value = value;
        this.rows = rows;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public int getRows() {
        return rows;
    }
}
