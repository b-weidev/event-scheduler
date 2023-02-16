package Controller;

import Gateway.ConferenceDataSaver;
import Presenter.ProgramPresenter;
import UseCase.AttendeeManager;
import UseCase.OrganizerManager;
import UseCase.SpeakerManager;

import java.util.HashMap;
import java.util.Scanner;

public class LoginSystem {

    private String currentUser = "";
    private String currentUserType = "";
    private AttendeeManager attendeeManager = new AttendeeManager();
    private OrganizerManager organizerManager = new OrganizerManager();
    private SpeakerManager speakerManager = new SpeakerManager();
    private HashMap<String, String> userList = new HashMap<>();

    ProgramPresenter loginPresenter = new ProgramPresenter();
    Scanner scan = new Scanner(System.in);

    public LoginSystem(AttendeeManager attendeeManager, SpeakerManager speakerManager,
                       OrganizerManager organizerManager, HashMap<String, String> userList) {
        this.attendeeManager = attendeeManager;
        this.speakerManager = speakerManager;
        this.organizerManager = organizerManager;
        this.userList = userList;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserType() {
        return currentUserType;
    }

    public AttendeeManager getAttendeeManager() {
        return attendeeManager;
    }

    public OrganizerManager getOrganizerManager() {
        return organizerManager;
    }

    public SpeakerManager getSpeakerManager() {
        return speakerManager;
    }

    public HashMap<String, String> getUserList() {
        return userList;
    }

    public void signUp () {
        ConferenceDataSaver conferenceDataSaver = new ConferenceDataSaver();
        loginPresenter.signUpWelcome();
        // Loop until username is chosen
        String username = "";
        while (true) {
            loginPresenter.inputPrompt("a unique username");
            String u = scan.nextLine();
            if (userList.containsKey(u)) {
                loginPresenter.errorMessage("Username is already taken.");
            }
            else {
                loginPresenter.successMessage("Username accepted.");
                username = u;
                break;
            }
        }
        // Set password
        loginPresenter.inputPrompt("a new password");
        String password = scan.nextLine();
        loginPresenter.successMessage("Password accepted.");
        // Set user type
        while (true) {
            loginPresenter.promptUserType();
            int userTypeChoice = scan.nextInt();
            if (userTypeChoice == 1) {
                organizerManager.createUser(username, password); // Creates a new user in the corresponding use case
                userList.put(username, password); // Add to user list
                currentUserType = "Organizer"; // Set user type
                break;
            } else if (userTypeChoice == 2) {
                speakerManager.createUser(username, password);
                userList.put(username, password);
                currentUserType = "Speaker";
                break;
            } else if (userTypeChoice == 3) {
                attendeeManager.createUser(username, password);
                userList.put(username, password);
                currentUserType = "Attendee";
                break;
            } else {
                loginPresenter.errorMessage("Invalid choice.");
            }
        }
        loginPresenter.successMessage("You have created a new account.");
        currentUser = username;
        conferenceDataSaver.writeUserInfo(username, password, currentUserType);
    }

    public void logIn() {
        loginPresenter.loginWelcome();
        String logInUsername = "";
        String logInPassword = "";
        boolean canLogIn = true;
        // Loop for checking if user exists
        while (true) {
            loginPresenter.inputPrompt("your username");
            String uName = scan.nextLine();
            if (userList.containsKey(uName)) {
                loginPresenter.successMessage("Your username exists.");
                logInUsername = uName;
                break;
            } else {
                loginPresenter.errorMessage("User does not exist.");
            }
        }
        // Loop for checking if password matches user
        while (true) {
            loginPresenter.inputPrompt("your password");
            String pWord = scan.nextLine();
            if (userList.get(logInUsername).equals(pWord)) {
                loginPresenter.successMessage("You are now logged in.");
                break;
            } else {
                loginPresenter.errorMessage("Wrong password.");
            }
        }
        if (attendeeManager.checkUserExistence(logInUsername)) {
            currentUserType = "Attendee";
        }
        else if (organizerManager.checkUserExistence(logInUsername)) {
            currentUserType = "Organizer";
        }
        else if (speakerManager.checkUserExistence(logInUsername)) {
            currentUserType = "Speaker";
        }
        currentUser = logInUsername; // Now tracking logged in user
    }

    /**
     *      ---MAIN SYSTEMS---
     */

     public void loginMainSystem() {
         while (true) {
             loginPresenter.spaceBreak();
             loginPresenter.loginOptions();
             String loginChoice = scan.nextLine();
             if (loginChoice.equals("1")) {
                 signUp();
                 break;
             }
             else if (loginChoice.equals("2")) {
                 logIn();
                 break;
             }
             else if (loginChoice.equals("3")) {
                 System.exit(0);
             }
             else {
                 loginPresenter.errorMessage("Invalid input");
             }
         }
     }

}
