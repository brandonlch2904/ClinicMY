package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class timeslotsManager {
    public static String[] getTimeSlots(){
        String timeslots = "";
        String timeslotsArray[];

        try {
            // Get the timeslots from the database
            File timeslotFile = new File("src//databases//timeslots.csv");
            Scanner scanner = new Scanner(timeslotFile);
            while (scanner.hasNextLine()) {
                timeslots += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Split the string into an array
        timeslotsArray = timeslots.split("\n");
        return timeslotsArray;
    }

    public static List<String> getDate(int numOfDays){
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Add the number of days starting from the current date
        for (int i = 0; i < numOfDays; i++) {
            dates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    public static List<String> getTimes(){
        List<String> times = new ArrayList<>();

        try {
            File timesFile = new File("src//databases//times.csv");
            Scanner scanner = new Scanner(timesFile);
            while (scanner.hasNextLine()) {
                times.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static List<String> getDoctors(){
        List<String> doctors = new ArrayList<>();

        try {
            File doctorFile = new File("src//databases//doctors.csv");
            Scanner scanner = new Scanner(doctorFile);
            while (scanner.hasNextLine()) {
                doctors.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public static boolean addTimeslot(String doctor, String startTime, String endTime, String date){
        boolean addStatus = true;

        // Checks whether timeslot already exists
        String[] timeslotArray = getTimeSlots();

        // If timeslot already exists, return false
        for (String timeslot : timeslotArray) {
            // Split the timeslot into an array
            String[] timeslotDetails = timeslot.split(",");
            // Check if timeslot already exists by comparing all the details
            if (timeslotDetails[0].equals(doctor) && timeslotDetails[1].equals(startTime) && timeslotDetails[2].equals(endTime) && timeslotDetails[3].equals(date)) {
                return false;
            }
        }

        // If timeslot doesn't exist, add it to the database
        try {
            FileWriter writer = new FileWriter("src//databases//timeslots.csv", true);
            writer.write(doctor + "," + startTime + "," + endTime + "," + date + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addStatus;
    }

    public static boolean updateTimeslot(String newData, String oldData){
        boolean updateStatus = true;

        // Get all timeslots
        String timeslots[] = getTimeSlots();
        String newTimeslots = "";

        try {
            // Loop through timeslots[] and add all lines except the line to remove
            for (String timeslot : timeslots) {
                // If the timeslot does not match the one to be updated, add it to the newTimeslots string
                if (!timeslot.equals(oldData)) {
                    newTimeslots += timeslot + "\n";
                // If the timeslot matches the one to be updated, add the new data to the newTimeslots string
                } else {
                    newTimeslots += newData + "\n";
                }
            }
            FileWriter myWriter = new FileWriter("src//databases//timeslots.csv");
            myWriter.write(newTimeslots);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        return updateStatus;
    }

    public static boolean deleteTimeslot(String lineToRemove) throws IOException {
        boolean deleteStatus = true;

        // Open timeslots
        String timeslots[] = getTimeSlots();
        String newTimeslots = "";

        try{
            // Loop through timeslots[] and add all lines except the line to remove
            for (String timeslot : timeslots) {
                if (!timeslot.equals(lineToRemove)) {
                    newTimeslots += timeslot + "\n";
                }
            }
            FileWriter myWriter = new FileWriter("src//databases//timeslots.csv");
            myWriter.write(newTimeslots);
            myWriter.close();

        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        return deleteStatus;
    }
}
