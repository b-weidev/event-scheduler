package UseCase;

import Entity.Event;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;

public class EventManager implements Serializable {

    private List<Event> eventList = new ArrayList<>();
    private HashMap<String, List<Event>> conferenceSchedule = new HashMap<>(); // Room to event objects mapping
    private static final long serialVersionUID = 6L;

    /**
     * Creates a new event object in the event list with a unique id.
     * @param event name of the event
     */
    public void createEvent(String event) {
        Random rand = new Random();
        while (true) {
            int randomNum = rand.nextInt(eventList.size() + 100) + 1;
            String id = "id" + randomNum;
            boolean uniqueID = true;
            int counter = 0;
            for (Event e : eventList) {
                if (e.getEventID().equals(id)) {
                    uniqueID = false;
                    break;
                }
            }
            if (uniqueID) {
                Event newEvent = new Event(event, id);
                eventList.add(newEvent);
                break;
            }
        }
    }

    /**
     * Checks to see whether an event can be scheduled by the organizer.
     * @param eventID ID of the event
     * @return true if event can be scheduled
     */
    public boolean canSchedule(String eventID) {
        Event currEvent = new Event();
        boolean eventExists = false;
        for (Event e : eventList) {
            // Check for event ID in the event list
            if (e.getEventID().equals(eventID)) {
                eventExists = true;
                currEvent = e;
            }
        }
        // See if the event object is already scheduled
        for (Map.Entry<String, List<Event>> entry : conferenceSchedule.entrySet()) {
            if (entry.getValue().contains(currEvent)) {
                return false;
            }
        }
        return eventExists;
    }

    // TODO: Sorter function that sorts all events in conferenceSchedule by date

    /**
     * Returns a schedule of the events from given event list in the form of String values to be passed into UI,
     * and if we want the entire schedule the second parameter must be true.
     * @param events list of event IDs
     * @param allEvents boolean value that is true only if we want the entire conference schedule
     * @return schedule of events pertaining to the request of the parameters
     */
    public HashMap<String, List<String>> getSchedule(List<String> events, boolean allEvents) {
        HashMap<String, List<String>> scheduledEvents = new HashMap<>();
        // First iterate over rooms of the conference
        for (Map.Entry<String, List<Event>> entry : conferenceSchedule.entrySet()) {
            // For each room we have a list of event strings
            List<String> eventStrings = new ArrayList<>();
            // If we want all the events we take all the event strings regardless
            if (allEvents) {
                for (Event e : entry.getValue()) {
                    eventStrings.add(e.toString());
                }
            }
            // If not then we only take the events that are in the parameter (the user events)
            else {
                for (Event e : entry.getValue()) {
                    if (events.contains(e.getEventID())) {
                        eventStrings.add(e.toString());
                    }
                }
            }
            // Then, each room number has a list of events in their String form
            if (!eventStrings.isEmpty()) {
                scheduledEvents.put(entry.getKey(), eventStrings);
            }
        }
        return scheduledEvents;
    }

    // Returns whether there are already events in the event manager
    public boolean hasEvents() {
        return !eventList.isEmpty();
    }

    public boolean roomExists(String room) {
        return conferenceSchedule.containsKey(room);
    }

    /**
     * Check if the event is available to the specific user type, (for attendees, see if the event is not at full
     * capacity, and for speakers whether there are enough speakers in the event)
     * @param eventID ID of the event
     * @return true if the event is available
     */
    public boolean eventAvailable(String eventID, String userType) {
        // Event can only be accessible for the public if it has been scheduled and has speakers
        for (Map.Entry<String, List<Event>> entry : conferenceSchedule.entrySet()) {
            for (Event e : entry.getValue()) {
                // Check if event is in the schedule
                if (e.getEventID().equals(eventID)) {
                    if (userType.equals("Attendee")) {
                        return (e.getNumAttendees() < e.getCapacity()) && (e.sufficientSpeakers());

                    }
                    else if (userType.equals("Speaker")) {
                        return (!e.sufficientSpeakers());
                    }
                }
            }
        }
        return false;
    }

    /**
     * Updates the number of people attending an event for an event object. Assume the event exists and is available,
     * and that the added amount will not exceed capacity.
     * @param eventID ID of the event
     * @param amount amount of attendees being added, usually either 1 or -1 (signing up and cancelling)
     */
    public void updateCapacity(String eventID, int amount) {
        for (Event e : eventList) {
            if (e.getEventID().equals(eventID)) {
                e.setNumAttendees(e.getNumAttendees() + amount);
            }
        }
    }

    /**
     * Adds a room to the schedule.
     * @param room String value of the room
     * @return whether adding room was successful
     */
    public boolean addRoom(String room) {
        // Check if room is unique
        if (!conferenceSchedule.containsKey(room)) {
            List<Event> emptyEvents = new ArrayList<>();
            conferenceSchedule.put(room, emptyEvents);
            return true;
        }
        // Can't add room when it already exists
        else {
            return false;
        }
    }

    /**
     * Returns a list of event IDs and event names of events that can be scheduled by the organizers, mainly for
     * UI to show organizers what events can be scheduled.
     * @return list of event IDs and names of unscheduled events
     */
    public List<String> getUnscheduledEvents() {
        List<String> unscheduledEvents = new ArrayList<>();
        for (Event e : eventList) {
            if (canSchedule(e.getEventID())) {
                unscheduledEvents.add(e.getEventName() + " [" + e.getEventID() + "]");
            }
        }
        return unscheduledEvents;
    }

    /**
     * Schedules the event by adding it to the corresponding room in the conferenceSchedule map. Assume the room exists
     * and all information is valid (as checked in the controller).
     * @param eventID ID of the event
     * @param room room event is being added to
     * @param startTime event start time
     * @param endTime event end time
     * @param date date of the event
     * @param capacity event total capacity
     * @return whether scheduling of the event was successful
     */
    public boolean scheduleEvent(String eventID, String room, String startTime, String endTime, String date,
                                 int capacity) {
        // Check for time conflicts in the room
        for (Event e : conferenceSchedule.get(room)) {
            if (e.hasConflict(startTime, endTime, date)) {
                return false;
            }
        }
        // Find event object in eventList
        for (Event e : eventList) {
            // Update the event object's info
            if (eventID.equals(e.getEventID())) {
                e.setStartTime(startTime);
                e.setEndTime(endTime);
                e.setDate(date);
                e.setCapacity(capacity);
                conferenceSchedule.get(room).add(e); // Add event object to room
                return true;
            }
        }
        return false;
    }

    public boolean addSpeaker(List<String> speakerEvents, String eventID, String speakerUsername) {
        // Get a list of all the event objects
        List<Event> events = new ArrayList<>();
        Event mainEvent = new Event();
        for (Event e : eventList) {
            if (speakerEvents.contains(e.getEventID())) {
                events.add(e);
            }
            if (e.getEventID().equals(eventID)) {
                mainEvent = e;
            }
        }
        // Check for time conflicts
        for (Event e : events) {
            if (mainEvent.hasConflict(e.getStartTime(), e.getEndTime(), e.getDate())) {
                return false;
            }
        }
        // No time conflicts, add speaker username to event object instance variable
        mainEvent.addSpeaker(speakerUsername);
        return true;
    }

    /**
     *      GETTERS AND SETTERS FOR TESTING PURPOSES
     */

    public List<Event> getEventList() {
        return eventList;
    }

    public HashMap<String, List<Event>> getConferenceSchedule() {
        return conferenceSchedule;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setConferenceSchedule(HashMap<String, List<Event>> conferenceSchedule) {
        this.conferenceSchedule = conferenceSchedule;
    }

}

