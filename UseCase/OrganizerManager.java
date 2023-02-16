package UseCase;

import Entity.Organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrganizerManager implements UserAccountManager, Serializable {

    private List<Organizer> organizerList = new ArrayList<>();
    private static final long serialVersionUID = 7L;

    // Creates and adds organizer to list
    public void createUser(String username, String password) {
        Organizer o = new Organizer(username, password);
        organizerList.add(o);
    }

    // Checks if user exists as an organizer
    public boolean checkUserExistence(String username) {
        for (Organizer o : organizerList) {
            if (o.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Returns all usernames of organizers
    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        for (Organizer o : organizerList) {
            users.add(o.getUsername());
        }
        return users;
    }

    public List<Organizer> getOrganizerList() {
        return organizerList;
    }

}
