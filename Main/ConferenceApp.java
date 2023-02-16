package Main;

import Controller.ConferenceSystem;

import java.io.File;
import java.io.IOException;

public class ConferenceApp {

    public static void main(String[] args) throws IOException {

        ConferenceSystem conferenceSystem = new ConferenceSystem();
        conferenceSystem.run();
    }

}
