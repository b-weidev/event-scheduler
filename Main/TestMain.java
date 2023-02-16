package Main;

import Controller.ConferenceSystem;
import Entity.Attendee;
import Entity.Message;
import Entity.Organizer;
import Gateway.ConferenceDataReceiver;
import UseCase.AttendeeManager;
import UseCase.MessageManager;
import UseCase.OrganizerManager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *      TESTING METHODS, NOT IMPORTANT
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
        LocalTime time1 = LocalTime.parse("01:20");
        LocalTime time2 = LocalTime.parse("03:20");
        System.out.println(time1.isBefore(time2));
    }

    public static void testRun() {
        ConferenceDataReceiver conferenceDataReceiver = new ConferenceDataReceiver();
        MessageManager messageManager = conferenceDataReceiver.readMessageManager();
        for (Map.Entry<String, List<Message>> entry : messageManager.getMessageMap().entrySet()) {
            System.out.println(entry.getKey());
            for (Message m : entry.getValue()) {
                System.out.println(m);
            }
        }
    }

}
