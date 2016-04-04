package edu.bucknell.binvolved;

import java.util.Date;

/**
 * Created by gilbertkim on 4/4/16.
 */
public class Event {

    String name;
    Date startDate; // includes time
    Date endDate; // includes time
    String location;
    Organization host;
    String description;

    public Event(String name, Date startDate, Date endDate, String location, Organization host, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.host = host;
        this.description = description;
    }

    public void stuff() {

    }

}
