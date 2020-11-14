package models;

import java.util.List;

public class Report {
    private String message;
    private Schedule schedule;
    private List<Destination> destinations;

    /**
     * Creates a single report.
     *
     * @param message A String composed by raw text and Connectors.
     * @param schedule Defines the frequency the report will be sent.
     * @param destinations Who will receive the report.
     */
    public Report(String message, Schedule schedule, List<Destination> destinations) {
        this.message = message;
        this.schedule = schedule;
        this.destinations = destinations;
    }

    public String getMessage() {
        return message;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }
}
