package UseCase;

import Entity.Attendee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use case for Attendee related responsibilities.
 */
public class AttendeeManager implements UserAccountManager, Serializable {

    private List<Attendee> attendeeList = new ArrayList<>();
    private HashMap<String, List<String>> attendeeMap = new HashMap<>(); // Maps event IDs to attendee usernames
    private static final long serialVersionUID = 3L;

    /**
     * Creates a User of type Attendee and adds it to attendeeList.
     * @param username username of Attendee
     * @param password password of Attendee
     */
    public void createUser(String username, String password) {
        Attendee a = new Attendee(username, password);
        attendeeList.add(a);
    }

    /**
     * Returns if username corresponds to an Attendee.
     * @param username username of user
     * @return true if username points to an Attendee
     */
    public boolean checkUserExistence(String username) {
        for (Attendee a : attendeeList) {
            if (a.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * From UserAccountManager interface, returns a list of usernames of all attendees.
     * @return list of all attendee usernames
     */
    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        for (Attendee a : attendeeList) {
            users.add(a.getUsername());
        }
        return users;
    }

    // Takes in a list of event IDs and returns all attendees of the events
    public List<String> getAttendeesOfEvents (List<String> eventList) {
        List<String> attendees = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendeeMap.entrySet()) {
            if (eventList.contains(entry.getKey())) {
                attendees.addAll(entry.getValue());
            }
        }
        return attendees;
    }

    /**
     * Gets a list of event IDs of events that user is attending.
     * @param username username of the attendee
     * @return list of event IDs
     */
    public List<String> getAttendeeEvents(String username) {
        List<String> attendeeEvents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendeeMap.entrySet()) {
            if (entry.getValue().contains(username)) {
                attendeeEvents.add(entry.getKey());
            }
        }
        return attendeeEvents;
    }

    /**
     * Signs up the attendee to the event iff they are not signed up to the event already. Assume event is not full and
     * the event exists in the event list. (Only condition we must check for is whether user is already signed up).
     * @param username username of the attendee
     * @param eventID event ID of the event
     * @return boolean value indicating whether sign up was successful
     */
    public boolean signUpAttendee(String username, String eventID) {
        // Mapping already exists
        if (attendeeMap.containsKey(eventID)) {
            if (attendeeMap.get(eventID).contains(username)) {
                return false;
            }
            else {
                attendeeMap.get(eventID).add(username);
                return true;
            }
        }
        // Need to create new mapping
        else {
            List<String> attendees = new ArrayList<>();
            attendees.add(username);
            attendeeMap.put(eventID, attendees);
            return true;
        }
    }

    /**
     * Removes the attendee from the event iff they are attending it, otherwise if they are not already attending it
     * then no removal occurs.
     * @param username username of the attendee
     * @param eventID event ID of the event
     * @return true if attendee was successfully removed
     */
    public boolean cancelEvent(String username, String eventID) {
        if (attendeeMap.containsKey(eventID)) {
            if (attendeeMap.get(eventID).contains(username)) {
                attendeeMap.get(eventID).remove(username);
                return true;
            }
        }
        return false;
    }

}
