package Presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramPresenter {

    public void welcomeMessage() {
        System.out.println("Welcome to the Conference App!");
    }

    public void loginOptions() {
        System.out.println("Do you have an existing account?");
        System.out.println("[1] Sign Up \n" + "[2] Log In \n" + "[3] Exit Program");
    }

    public void title(String s) {
        System.out.println("---" + s + "---");
    }

    public void signUpWelcome() {
        System.out.println("You are signing up for a new account.");
    }

    public void promptUserType() {
        System.out.println("What would you like to sign up as?");
        System.out.println("[1] ORGANIZER \n" + "[2] SPEAKER \n" + "[3] ATTENDEE");
    }

    public void loginWelcome() {
        System.out.println("You are logging in.");
    }

    public void welcomeUser(String s) {
        System.out.println("Welcome to your account, " + s + "!");
    }

    public void systemOptions() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Access Scheduling System \n" + "[2] Access Messaging System \n" + "[3] Log Out");
    }

    public void scheduleOptions(String userType) {
        if (userType.equals("Attendee")) {
            System.out.println("[1] View Conference Schedule \n" + "[2] View Your Schedule \n" +
                    "[3] Sign Up For Event \n" + "[4] Remove Event");
        }
        else if (userType.equals("Speaker")) {
            System.out.println("[1] View Conference Schedule \n" + "[2] View Your Schedule");
        }
        else if (userType.equals("Organizer")) {
            System.out.println("[1] View Conference Schedule \n" + "[2] Add Room \n" + "[3] Schedule Event \n"
                    + "[4] Create Speaker \n" + "[5] Assign Speaker");
        }
    }

    public void messageOptions(String userType) {
        if (userType.equals("Attendee")) {
            System.out.println("[1] View Messages \n" + "[2] Send Message \n" + "[3] View Contacts \n" +
                    "[4] Add Contact \n" + "[5] Remove Contact");
        }
        else if (userType.equals("Speaker")) {
            System.out.println("[1] View Messages \n" + "[2] Send Message \n" + "[3] View Contacts \n" +
                    "[4] Add Contact \n" + "[5] Remove Contact \n" + "[6] Message Event Attendees");
        }
        else if (userType.equals("Organizer")) {
            System.out.println("[1] View Messages \n" + "[2] Send Message \n" + "[3] View Contacts \n" +
                    "[4] Add Contact \n" + "[5] Remove Contact \n" + "[6] Message All Speakers \n" +
                    "[7] Message All Attendees");
        }
    }

    public void showMessagedUsers(List<String> messagedUsers) {
        System.out.println("Here are all the users you have pending conversations with:");
        for (String s : messagedUsers) {
            System.out.println("- " + s);
        }
    }

    public void showConversation(List<String> conversation, String otherUser) {
        spaceBreak();
        System.out.println("Now showing messages from " + otherUser + ":");
        for (String s : conversation) {
            System.out.println(s);
        }
    }

    public void showContacts(List<String> contacts, String username) {
        System.out.println("Now showing contacts for " + username + ":");
        for (String s : contacts) {
            System.out.println("- " + s);
        }
    }

    public void printSchedule(HashMap<String, List<String>> schedule) {
        if (schedule.isEmpty()) {
            System.out.println("The schedule is empty.");
        }
        for (Map.Entry<String, List<String>> entry : schedule.entrySet()) {
            spaceBreak();
            System.out.println("Room: " + "[" + entry.getKey() + "]");
            System.out.println("---");
            for (String s : entry.getValue()) {
                System.out.println(s);
            }
        }
    }

    public void showUnscheduledEvents(List<String> unscheduledEvents) {
        System.out.println("***");
        System.out.println("Events to be scheduled:");
        for (String e : unscheduledEvents) {
            System.out.println(e);
        }
        System.out.println("***");
    }

    public void unorderedListItems(List<String> strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public void spaceBreak() {
        System.out.println("");
    }

    public void inputPrompt(String s) {
        System.out.println("Please enter " + s + ":");
    }

    public void errorMessage(String s) {
        System.out.println("Oops! There's been an error. " + s + " Please try again.");
    }

    public void successMessage(String s) {
        System.out.println("Success! " + s);
    }

    public void goBack() {
        System.out.println("To go back, please input '-1'");
    }

    public void logOut(String s) {
        System.out.println("Logging out " + s + "...");
    }

}
