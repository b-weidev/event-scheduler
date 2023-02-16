package UseCase;

import Entity.Speaker;

import java.io.Serializable;
import java.util.*;

public class SpeakerManager implements UserAccountManager, Serializable {

    private List<Speaker> speakerList = new ArrayList<>();
    private HashMap<String, List<String>> speakerMap = new HashMap<>(); // Mapping of speaker usernames to eventIDs
    private static final long serialVersionUID = 8L;

    // Creates and adds speaker to list
    public void createUser(String username, String password) {
        Speaker s = new Speaker(username, password);
        speakerList.add(s);
    }

    // Checks if user exists as a speaker
    public boolean checkUserExistence(String username) {
        for (Speaker s : speakerList) {
            if (s.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Returns all usernames of speakers
    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        for (Speaker s : speakerList) {
            users.add(s.getUsername());
        }
        return users;
    }

    // Returns a list of eventIDs that speaker is speaking at
    public List<String> getSpeakerEvents(String username) {
        if (speakerMap.containsKey(username)) {
            return speakerMap.get(username);
        }
        return Collections.emptyList(); // Empty list
    }

    // Updates speakerMap to add eventID to list pertaining to speaker
    public boolean assignSpeaker(String username, String eventID) {
        // Check for existing mapping to see if eventID is already there, if not just add eventID
        if (speakerMap.containsKey(username)) {
            if (speakerMap.get(username).contains(eventID)) {
                return false;
            }
            else {
                speakerMap.get(username).add(eventID);
                return true;
            }
        }
        // If there is no speaker mapping, create a new mapping
        List<String> events = new ArrayList<>();
        events.add(eventID);
        speakerMap.put(username, events);
        return true;
    }

}
