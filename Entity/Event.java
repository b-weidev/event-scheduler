package Entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event entity, describes the events of the conference.
 */
public class Event implements Serializable {

    private String eventName;
    private String eventID; // Must be unique for every Event
    private String startTime; // Must be numerical, military time "hours:minutes" => "13:20" means 1:20 pm
    private String endTime; // Same condition as above
    private String date; // Formatted "DD/MM/YYYY" => "01/03/2000" => March 1, 2000
    private int capacity; // Precondition: must be > 0, no empty events allowed
    private int numAttendees; // Must be >= 0
    private List<String> speakers = new ArrayList<>(); // List of usernames of Speakers speaking at event
    private static final long serialVersionUID = 9L;

    /**
     * Default empty constructor for Event.
     */
    public Event() { }

    /**
     * Constructor method for Event. Creates an event object with a name and unique ID, and upon instantiation has
     * no speakers as well as 0 people attending.
     * @param eventName name of the event
     * @param eventID unique ID of the event
     */
    public Event(String eventName, String eventID) {
        this.eventName = eventName;
        this.eventID = eventID;
        this.numAttendees = 0;
        this.speakers = new ArrayList<>();
    }

    /**
     * Returns the event's name.
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Returns the unique event ID.
     * @return the eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Returns the start time in hours and minutes of the event.
     * @return start time of event
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time in hours and minutes of the event.
     * @return end time of the event
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns the date of the event in days, months and years.
     * @return date of the event
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the maximum attendee capacity of the event.
     * @return capacity of the event
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the number of people attending the event.
     * @return number of people attending event
     */
    public int getNumAttendees() {
        return numAttendees;
    }

    /**
     * Returns a list of speaker usernames of the speakers of the event.
     * @return list of speaker usernames
     */
    public List<String> getSpeakers() {
        return speakers;
    }

    /**
     * Sets the start time of the event.
     * @param startTime start time of the event
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the event.
     * @param endTime end time of the event
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /** Sets the date of the event.
     * @param date the date of the event
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the capacity of the event.
     * @param capacity maximum capacity of event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the number of people attending the event.
     * @param numAttendees number of people attending event
     */
    public void setNumAttendees(int numAttendees) {
        this.numAttendees = numAttendees;
    }

    /**
     * Adds a speaker to the list of speakers speaking to the event.
     * @param username username of speaker
     */
    public void addSpeaker(String username) {
        speakers.add(username);
    }

    /**
     * Returns whether there are enough speakers for the event, by default it is 1.
     * @return true if there are enough speakers
     */
    public boolean sufficientSpeakers() {
        return speakers.size() == 1;
    }

    /**
     * Returns whether the event has a conflict with a specified time and date.
     * @param otherStartTime start time to compare
     * @param otherEndTime end time to compare
     * @param otherDate date to compare
     * @return true if the event conflicts with the given time and date
     */
    public boolean hasConflict(String otherStartTime, String otherEndTime, String otherDate) {
        // Check if event on the same day, if not, no conflict, done
        if (!this.date.equals(otherDate)) {
            return false;
        }
        // On same day, check for conflict between times
        else {
            LocalTime startTimeA = LocalTime.parse(this.startTime);
            LocalTime endTimeA = LocalTime.parse(this.endTime);
            LocalTime startTimeB = LocalTime.parse(otherStartTime);
            LocalTime endTimeB = LocalTime.parse(otherEndTime);
            return startTimeA.isBefore(endTimeB) && startTimeB.isBefore(endTimeA);
        }
    }

    /**
     * Overrides method in Object class to get a String representation of Event objects.
     * @return String representation of Event
     */
    public String toString() {
        String event = eventName + " [" + eventID + "] " + startTime + "-" + endTime + " " + date + " CAPACITY: " +
                numAttendees + "/" + capacity + " Speakers: ";
        if (speakers.isEmpty()) {
            return event + "[No one is speaking at the event.]";
        }
        for (String s : speakers) {
            event += s + " ";
        }
        return event;
    }

}
