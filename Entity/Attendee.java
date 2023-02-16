package Entity;

/**
 * Attendee entity, subclass of User class.
 */
public class Attendee extends User {

    /**
     * Super constructor inherited from User.
     * @param username username of the attendee
     * @param password password of the attendee
     */
    public Attendee(String username, String password) {
        super(username, password);
    }

    /**
     * Overrides method in superclass, returns if the user is an attendee, always true.
     * @return true
     */
    public boolean isAttendee() {
        return true;
    }

}
