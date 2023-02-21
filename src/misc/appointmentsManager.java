package misc;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class appointmentsManager {

    private static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

    // Method to get all appointments
    public static String[] getAppointments(){
        String appointments = "";
        String[] appointmentsArray;
        try {
            File appointmentFile = new File("src//databases//appointments.csv");
            Scanner scanner = new Scanner(appointmentFile);
            while (scanner.hasNextLine()) {
                appointments += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        appointmentsArray = appointments.split("\n");

        return appointmentsArray;
    }

    // Method to get appointment matching with date given
    public static String[] getTodayAppointment(){
        // Get current date
        String date = getCurrentDate();

        String appointments = "";
        String[] appointmentsArray;
        try {
            File appointmentFile = new File("src//databases//appointments.csv");
            Scanner scanner = new Scanner(appointmentFile);
            while (scanner.hasNextLine()) {
                String appointment = scanner.nextLine();
                String[] appointmentArray = appointment.split(",");
                if (appointmentArray[2].equals(date)){
                    appointments += appointment + "\n";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        appointmentsArray = appointments.split("\n");
        return appointmentsArray;

    }

    // Method to get appointment with specific status
    public static String[] getAppointmentStatus(String status){
        String appointments = "";
        String[] appointmentsArray;
        try {
            File appointmentFile = new File("src//databases//appointments.csv");
            Scanner scanner = new Scanner(appointmentFile);
            while (scanner.hasNextLine()) {
                String appointment = scanner.nextLine();
                String[] appointmentArray = appointment.split(",");
                if (appointmentArray[5].equals(status)){
                    appointments += appointment + "\n";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        appointmentsArray = appointments.split("\n");
        return appointmentsArray;

    }

    // Method to book an appointment
    public boolean bookAppointment(String doctor, String date, String startTime, String endTime, String username){

        boolean bookingStatus = true;
        try{
            String appointmentStatus = "Pending";
            String data = username + "," + doctor + "," + date + "," + startTime + "," + endTime + "," + appointmentStatus + "\n";

            // Checks if the appointment is already booked
            String[] appointmentsArray = getAppointments();

            // Iterate through each appointment
            for (String appointment : appointmentsArray) {
                String[] appointmentArray = appointment.split(",");

                // If there are no appointments
                if (appointment == "") {
                    FileWriter myWriter = new FileWriter("src//databases//appointments.csv");
                    myWriter.write(data);
                    myWriter.close();
                    return bookingStatus;

                // If the appointment exists returns false
                }else if (appointmentArray[1].equals(doctor) && appointmentArray[2].equals(date) && appointmentArray[3].equals(startTime) && appointmentArray[4].equals(endTime)){
                    return false;

                // Append the appointment to the file
                }else{
                    FileWriter myWriter = new FileWriter("src//databases//appointments.csv", true);
                    myWriter.write(data);
                    myWriter.close();
                    return bookingStatus;
                }
            }


        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return bookingStatus;

    }

    // Method to remove an appointment
    public boolean removeAppointment(String lineToRemove) throws IOException {

        boolean removeStatus = true;

        // Open appointments
        File appointmentsFile = new File("src//databases//appointments.csv");
        Scanner scanner = new Scanner(appointmentsFile);
        String newAppointments = "";

        try{
            // Iterate through each appointment
            while (scanner.hasNextLine()) {
                String appointment = scanner.nextLine();

                // If the appointment is not the one to be removed, add it to the new appointments
                if (appointment.equals(lineToRemove)){
                    continue;
                }
                newAppointments += appointment + "\n";
            }
            FileWriter myWriter = new FileWriter("src//databases//appointments.csv");
            myWriter.write(newAppointments);
            myWriter.close();

        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        return removeStatus;
    }

    // Method to change the status of an appointment
    public static boolean setAppointmentStatus(String oldData, String newData){
        boolean confirmStatus = true;

        // Get all appointments
        String[] appointments = getAppointments();
        String newAppointments = "";

        // Iterate through each appointment
        try{
            for (String appointment : appointments){
                // If the appointment is the one to be confirmed
                if (!appointment.equals(oldData)){
                    // Replace the appointment with the new data
                    newAppointments += appointment + "\n";
                }else{
                    // Add the appointment to the new appointments
                    newAppointments += newData + "\n";
                }
            }
            FileWriter myWriter = new FileWriter("src//databases//appointments.csv");
            myWriter.write(newAppointments);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return confirmStatus;
    }

    public static void main(String[] args) {
        String[] appointmentsArray = getAppointments();

        for (String appointment : appointmentsArray){
            if (appointment.equals("")){
                System.out.println("No appointments");
            }else{
                System.out.println("There's something");
            }
        }
    }
}
