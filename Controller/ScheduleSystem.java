package Controller;

import Gateway.ConferenceDataSaver;
import Presenter.ProgramPresenter;
import UseCase.AttendeeManager;
import UseCase.EventManager;
import UseCase.OrganizerManager;
import UseCase.SpeakerManager;

import java.util.*;
import java.util.regex.Pattern;
import java.time.LocalTime;
import java.time.LocalDate;

public class ScheduleSystem {

    private EventManager eventManager = new EventManager();
    private AttendeeManager attendeeManager = new AttendeeManager();
    private SpeakerManager speakerManager = new SpeakerManager();
    private OrganizerManager organizerManager = new OrganizerManager();
    private HashMap<String, String> userList = new HashMap<>();

    ProgramPresenter programPresenter = new ProgramPresenter();
    Scanner scan = new Scanner(System.in);

    public ScheduleSystem(EventManager eventManager, AttendeeManager attendeeManager, SpeakerManager speakerManager,
                          OrganizerManager organizerManager, HashMap<String, String> userList) {
        this.eventManager = eventManager;
        this.attendeeManager = attendeeManager;
        this.speakerManager = speakerManager;
        this.organizerManager = organizerManager;
        this.userList = userList;
    }

    /**
     *      ---GETTERS FOR POTENTIALLY MODIFIED USE CASES---
     */

    public EventManager getEventManager() {
        return eventManager;
    }

    public AttendeeManager getAttendeeManager() {
        return attendeeManager;
    }

    public SpeakerManager getSpeakerManager() {
        return speakerManager;
    }

    /**
     *      ---HELPER METHODS FOR MAIN SYSTEMS---
     */

    // Sign up attendee for an event
    public void attendeeAddEvent(String username) {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("SIGNING UP FOR EVENT");
            programPresenter.inputPrompt("event ID");
            String eventID = scan.nextLine();
            if (eventID.equals("-1")) {
                break;
            }
            else if (eventManager.eventAvailable(eventID, "Attendee")) {
                if (attendeeManager.signUpAttendee(username, eventID)) {
                    eventManager.updateCapacity(eventID, 1);
                    programPresenter.successMessage("You have signed up for your desired event.");
                    break;
                }
                else {
                    programPresenter.errorMessage("You are already signed up for the event.");
                }
            }
            else {
                programPresenter.errorMessage("The event is not available or doesn't exist.");
            }
        }
    }

    // Removes attendee from an event
    public void attendeeRemoveEvent(String username) {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("REMOVING EVENT");
            programPresenter.inputPrompt("event ID");
            String eventID = scan.nextLine();
            if (eventID.equals("-1")) {
                break;
            }
            if (attendeeManager.cancelEvent(username, eventID)) {
                eventManager.updateCapacity(eventID, -1);
                programPresenter.successMessage("You have removed the event from your schedule.");
                break;
            }
            else {
                programPresenter.errorMessage("Could not remove event.");
            }
        }
    }

    // Add room
    public void organizerAddRoom() {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("ADDING ROOM");
            programPresenter.inputPrompt("room");
            String room = scan.nextLine();
            if (room.equals("-1")) {
                break;
            }
            if (eventManager.addRoom(room)) {
                programPresenter.successMessage("Room successfully added.");
                break;
            }
            else {
                programPresenter.errorMessage("Room already exists.");
            }
        }
    }

    // TODO: USE REGEX TO ENSURE VALID TIME AND DATE?
    // Helper function to parse validity of input information from scheduling event
    public boolean validScheduleInfo(String eventID, String room, String startTime, String endTime,
                                     String date, int capacity) {
        String timeExpression = "(([0-1]?[0-9])|(2[0-3])):[0-5][0-9]";
        String dateExpression = "(([0-1]?[0-9])|(2[0-9])|(3[0-1])/(([0-1]?[0-9])|(2[0-9])|(3[0-1])))";
        if (!eventManager.canSchedule(eventID)) {
            programPresenter.errorMessage("Event does not exist or is already scheduled.");
            return false;
        }
        if (!eventManager.roomExists(room)) {
            programPresenter.errorMessage("Room does not exist.");
            return false;
        }
        if (LocalTime.parse(startTime).isAfter(LocalTime.parse(endTime))) {
            programPresenter.errorMessage("Entered times are not valid.");
            return false;
        }
        if (capacity <= 0) {
            programPresenter.errorMessage("Invalid capacity - the capacity must be at least 1.");
            return false;
        }
        return true;
    }

    // Schedule an event
    public void organizerScheduleEvent() {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("SCHEDULING EVENT");
            programPresenter.showUnscheduledEvents(eventManager.getUnscheduledEvents());
            programPresenter.inputPrompt("event ID");
            String eventID = scan.nextLine();
            if (eventID.equals("-1")) {
                break;
            }
            programPresenter.inputPrompt("room");
            String room = scan.nextLine();
            programPresenter.inputPrompt("start time (HH:MM)");
            String startTime = scan.nextLine();
            programPresenter.inputPrompt("end time (HH:MM)");
            String endTime = scan.nextLine();
            programPresenter.inputPrompt("date (DD/MM/YYYY)");
            String date = scan.nextLine();
            programPresenter.inputPrompt("maximum capacity");
            int capacity = scan.nextInt();
            if (!validScheduleInfo(eventID, room, startTime, endTime, date, capacity)) {
                break;
            }
            if (eventManager.scheduleEvent(eventID, room, startTime, endTime, date, capacity)) {
                programPresenter.successMessage("Event has been scheduled for " + startTime + " to "
                        + endTime + ", " + date + ".");
                break;
            }
            else {
                programPresenter.errorMessage("Event could not be added.");
            }
        }
    }

    public void organizerCreateSpeaker() {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("CREATING SPEAKER ACCOUNT");
            programPresenter.inputPrompt("speaker username");
            String username = scan.nextLine();
            if (username.equals("-1")) {
                break;
            }
            programPresenter.inputPrompt("speaker password");
            String password = scan.nextLine();
            if (!userList.containsKey(username)) {
                speakerManager.createUser(username, password);
                userList.put(username, password);
                ConferenceDataSaver conferenceDataSaver = new ConferenceDataSaver();
                conferenceDataSaver.writeUserInfo(username, password, "Speaker");
                programPresenter.successMessage("Speaker account successfully created.");
                break;
            }
            else {
                programPresenter.errorMessage("Username already exists.");
            }
        }
    }

    public void organizerAssignSpeaker() {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("ASSIGNING SPEAKER");
            programPresenter.inputPrompt("event ID");
            String eventID = scan.nextLine();
            if (eventID.equals("-1")) {
                break;
            }
            programPresenter.inputPrompt("speaker username");
            String username = scan.nextLine();
            if (eventManager.eventAvailable(eventID, "Speaker")) {
                List<String> speakerEvents = speakerManager.getSpeakerEvents(username);
                if (eventManager.addSpeaker(speakerEvents, eventID, username)) {
                    speakerManager.assignSpeaker(username, eventID);
                    programPresenter.successMessage("Speaker has been added to the event.");
                    break;
                }
                else {
                    programPresenter.errorMessage("Speaker could not be added to the event.");
                }
            }
            else {
                programPresenter.errorMessage("Speaker cannot be added to the event.");
            }
        }
    }


    /**
     *      ---MAIN SYSTEMS---
     */

    public void attendeeScheduleSystem(String username) {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.spaceBreak();
            programPresenter.title("ATTENDEE SCHEDULE MENU");
            programPresenter.scheduleOptions("Attendee");
            String scheduleOption = scan.nextLine();
            if (scheduleOption.equals("1")) {
                programPresenter.printSchedule(eventManager.getSchedule(Collections.emptyList(), true));
            }
            else if (scheduleOption.equals("2")) {
                List<String> events = attendeeManager.getAttendeeEvents(username);
                programPresenter.printSchedule(eventManager.getSchedule(events, false));
            }
            else if (scheduleOption.equals("3")) {
                attendeeAddEvent(username);
            }
            else if (scheduleOption.equals("4")) {
                attendeeRemoveEvent(username);
            }
            else if (scheduleOption.equals("-1")) {
                break;
            }
            else {
                programPresenter.errorMessage("Invalid choice.");
            }
        }
    }

    public void speakerScheduleSystem(String username) {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("SPEAKER SCHEDULE MENU");
            programPresenter.scheduleOptions("Speaker");
            String scheduleOption = scan.nextLine();
            if (scheduleOption.equals("1")) {
                programPresenter.printSchedule(eventManager.getSchedule(Collections.emptyList(), true));
            }
            else if (scheduleOption.equals("2")) {
                List<String> events = speakerManager.getSpeakerEvents(username);
                programPresenter.printSchedule(eventManager.getSchedule(events, false));
            }
            else if (scheduleOption.equals("-1")) {
                break;
            }
            else {
                programPresenter.errorMessage("Invalid choice.");
            }
        }
    }

    public void organizerScheduleSystem(String username) {
        while (true) {
            programPresenter.spaceBreak();
            programPresenter.title("ORGANIZER SCHEDULE MENU");
            programPresenter.scheduleOptions("Organizer");
            String scheduleOption = scan.nextLine();
            if (scheduleOption.equals("1")) {
                programPresenter.printSchedule(eventManager.getSchedule(Collections.emptyList(), true));
            }
            else if (scheduleOption.equals("2")) {
                organizerAddRoom();
            }
            else if (scheduleOption.equals("3")) {
                organizerScheduleEvent();
            }
            else if (scheduleOption.equals("4")) {
                organizerCreateSpeaker();
            }
            else if (scheduleOption.equals("5")) {
                organizerAssignSpeaker();
            }
            else if (scheduleOption.equals("-1")) {
                break;
            }
            else {
                programPresenter.errorMessage("Invalid choice.");
            }
        }
    }

}
