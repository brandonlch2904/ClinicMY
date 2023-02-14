package misc;

import javax.swing.*;
import java.util.List;

public class comboBoxManager {

    public static void addDatesToComboBox(JComboBox comboBox1, int numOfDays) {
        // Get the dates
        List<String> dates = new timeslotsManager().getDate(numOfDays);

        // Loop through dates based on numOfDays starting from today
        for (int i = 0; i < numOfDays; i++) {
            // Add the date to the combo box
            comboBox1.addItem(dates.get(i));
        }
    }

    public static void addTimesToComboBox(JComboBox comboBox2, JComboBox comboBox3) {
        // Get the times
        List<String> times = new timeslotsManager().getTimes();

        // Loop through the times and add them to the combo box
        for (int i = 0; i < times.size(); i++) {
            comboBox2.addItem(times.get(i));
            comboBox3.addItem(times.get(i));
        }
    }

    public static void addDoctorsToComboBox(JComboBox comboBox4){
        // Get the doctors
        List<String> doctors = new timeslotsManager().getDoctors();

        // Loop through the doctors and add them to the combo box
        for (int i = 0; i < doctors.size(); i++) {
            comboBox4.addItem(doctors.get(i));
        }

    }

    public static String getDataFromCombobox(JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4, JPanel timeslotPanel) {
        String data = "";

        // Get the values from the text fields
        Object date = comboBox1.getSelectedItem();
        Object startTime = comboBox2.getSelectedItem();
        Object endTime = comboBox3.getSelectedItem();
        Object doctor = comboBox4.getSelectedItem();

        // Ensures no null value is returned
        if (doctor == null || startTime == null || endTime == null || date == null){
            JOptionPane.showMessageDialog(timeslotPanel, "Please fill in all fields");
            return null;

        }else{
            String dateString = date.toString();
            String startTimeString = startTime.toString();
            String endTimeString = endTime.toString();
            String doctorString = doctor.toString();

            if (doctorString.equals("") || startTimeString.equals("") || endTimeString.equals("") || dateString.equals("")){
                JOptionPane.showMessageDialog(timeslotPanel, "Please fill in all fields");
                return null;
            }else{
                data = doctorString + "," + startTimeString + "," + endTimeString + "," + dateString;
            }
        }
        return data;
    }
}
