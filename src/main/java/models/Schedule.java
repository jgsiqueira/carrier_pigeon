package models;

import java.security.InvalidParameterException;

public class Schedule {
    private String minute;
    private String hour;
    private String dayMonth;
    private String month;
    private String dayWeek;

    public Schedule(String schedule) {
        String[] parameters = schedule.split(" ");

        if(parameters.length != 5) {
            throw new InvalidParameterException(String.format("Invalid cron string {}", schedule));
        }

        this.minute = parameters[0];
        this.hour = parameters[1];
        this.dayMonth = parameters[2];
        this.month = parameters[3];
        this.dayWeek = parameters[4];
    }

    public Schedule(String minute, String hour, String dayMonth, String month, String dayWeek) {
        this.minute = minute;
        this.hour = hour;
        this.dayMonth = dayMonth;
        this.month = month;
        this.dayWeek = dayWeek;
    }

    public String getMinute() {
        return minute;
    }

    public String getHour() {
        return hour;
    }

    public String getDayMonth() {
        return dayMonth;
    }

    public String getMonth() {
        return month;
    }

    public String getDayWeek() {
        return dayWeek;
    }
}
