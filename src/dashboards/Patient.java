package dashboards;

import misc.*;

import javax.swing.*;

public class Patient extends dashboards{
    // Constructor
    public Patient(JTable timeslotTable, JTable appointmentTable, JTable medicalRecordsTable){
        super(timeslotTable, appointmentTable, medicalRecordsTable);

    }

    /* Timeslots Class */
    public class timeslots extends dashboards.timeslots{
        // Constructor
        public timeslots(JTable timeslotTable){
            super(timeslotTable);;
        }

        public static void bookAppointment(JTextField textField2, JTextField textField3, JTextField textField4, JTextField textField5, String clientUsername, JPanel timeslotPanel) {

            // Get the values of the text fields
            String date = textField2.getText();
            String startTime = textField3.getText();
            String endTime = textField4.getText();
            String doctor = textField5.getText();

            // Book the appointment
            boolean bookingStatus = new appointmentsManager().bookAppointment(doctor, date, startTime, endTime, clientUsername);

            // Display a message to the user
            if (bookingStatus){
                JOptionPane.showMessageDialog(timeslotPanel, "Appointment booked successfully");
            }else{
                JOptionPane.showMessageDialog(timeslotPanel, "Booking failed, appointment exists");
            }

        }
    }

    /* Appointment Class */
    public class appointments extends dashboards.appointments {
    // Constructor
        public appointments(JTable appointmentTable){
            super(appointmentTable);
        }

    }

    /* Medical Records Class */
    public class medicalRecords extends dashboards.records{
        // Constructor
        public medicalRecords(JTable medicalRecordsTable){
            super(medicalRecordsTable);
        }

        public static void showSelfRecords(JTable medicalRecordsTable, String clientUsername) {
            // Get the medical records
            String[] medicalRecords = medicalRecordsManager.getSpecificRecord(clientUsername);
            // Specify the columns and data
            String[] column = {"Patient Name", "Doctor", "Date", "Start-time", "End-time", "Medical Record"};
            String[][] data = new String[medicalRecords.length][6];
            // Add the medical records to the data array
            tableManager.setDataInTable(medicalRecords, column, data, medicalRecordsTable);
        }
    }
}
