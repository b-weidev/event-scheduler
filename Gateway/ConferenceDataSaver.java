package Gateway;

import UseCase.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ConferenceDataSaver {

    public void saveAttendeeManager(AttendeeManager attendeeManager) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/AttendeeData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(attendeeManager);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveSpeakerManager(SpeakerManager speakerManager) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/SpeakerData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(speakerManager);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveOrganizerManager(OrganizerManager organizerManager) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/OrganizerData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(organizerManager);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveEventManager(EventManager eventManager) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/EventData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(eventManager);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveMessageManager(MessageManager messageManager) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/MessageData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(messageManager);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveUserList(HashMap<String, String> userList) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/Data/UserData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userList);
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void writeUserInfo(String username, String password, String userType) {
        String outputFile = "src/Data/UserInfo.csv";
        FileWriter csvwriter = null;
        try {
            csvwriter = new FileWriter(outputFile, true);
            csvwriter.append(username);
            csvwriter.append(", ");
            csvwriter.append(password);
            csvwriter.append(", ");
            csvwriter.append(userType);
            csvwriter.append("\n");
            csvwriter.flush();
            csvwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
