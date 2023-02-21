package dashboards;

import misc.appointmentsManager;
import misc.medicalRecordsManager;
import misc.tableManager;
import misc.timeslotsManager;

import javax.swing.*;
import java.io.IOException;

public class dashboards {
    public dashboards(JTable timeslotTable, JTable appointmentTable, JTable medicalRecordsTable){
        new timeslots(timeslotTable);
        new appointments(appointmentTable);
        new records(medicalRecordsTable);
    }

    public class timeslots {
        // Constructor
        public timeslots(JTable timeslotTable) {

            // Set the table to be non-editable
            timeslotTable.setDefaultEditor(Object.class, null);

            // Set the selection mode to single selection
            timeslotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        }

        // Overloading Method
        public static void timeslotsTableClicked(JTable timeslotTable, JTextField textField2, JTextField textField3, JTextField textField4, JTextField textField5) {

            String data[] = tableManager.getDataFromTable(timeslotTable, 4);

            // Set the text fields to the values of the selected row
            textField2.setText(data[3]);
            textField3.setText(data[1]);
            textField4.setText(data[2]);
            textField5.setText(data[0]);

            // Deselect the row after getting the values
            timeslotTable.clearSelection();
            timeslotTable.getSelectionModel().clearSelection();
        }

        // Overloading Method
        public static void timeslotsTableClicked(JTable timeslotTable, JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4) {

            String[] data = tableManager.getDataFromTable(timeslotTable, 4);

            // Set the text fields to the values of the selected row
            comboBox1.getModel().setSelectedItem(data[3]);
            comboBox2.getModel().setSelectedItem(data[1]);
            comboBox3.getModel().setSelectedItem(data[2]);
            comboBox4.getModel().setSelectedItem(data[0]);

        }

        public static void displayTimeslotTable(JTable timeslotTable){

            // Get the timeslots from the timetable class
            String[] timeslotArray = timeslotsManager.getTimeSlots();
            // Specify the columns and data
            String[] columnNames = {"Doctor", "Start-time", "End-time", "Date"};
            String[][] data = new String[timeslotArray.length][4];
            // Add the timeslots to the data array
            tableManager.setDataInTable(timeslotArray, columnNames, data, timeslotTable);
        }
    }

    public class appointments {
        // Constructor
        public appointments(JTable appointmentTable){

            //Make the appointment table uneditable
            appointmentTable.setDefaultEditor(Object.class, null);

            //Set Selection Mode to Single Selection
            appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        public static void appointmentTableClicked(JTable appointmentTable, JTextField date, JTextField startTime, JTextField endTime, JTextField doctorName, JTextField patientName, JTextField status){

            String[] data = tableManager.getDataFromTable(appointmentTable, 6);

            // Set the text fields to the values of the selected row
            date.setText(data[2]);
            startTime.setText(data[3]);
            endTime.setText(data[4]);
            doctorName.setText(data[1]);
            patientName.setText(data[0]);
            status.setText(data[5]);

        }

        public static void cancelAppointment(JTextField dateTextField, JTextField startTextField, JTextField endTextField, JTextField doctorTextField, JTextField patientTextField, JTextField statusTextField, JPanel appointmentsPanel) throws IOException {

            // Get the values of the text fields
            String doctor = doctorTextField.getText();
            String startTime = startTextField.getText();
            String endTime = endTextField.getText();
            String date = dateTextField.getText();
            String patientName = patientTextField.getText();
            String status = statusTextField.getText();
            String lineToRemove = patientName + "," + doctor + "," + date + "," + startTime + "," + endTime + "," + status;

            // Remove the appointment
            boolean removeStatus = new appointmentsManager().removeAppointment(lineToRemove);

            // Display a message to the user
            if (removeStatus) {
                JOptionPane.showMessageDialog(appointmentsPanel, "Appointment removed successfully");
                // Clear all the text fields
                dateTextField.setText("");
                startTextField.setText("");
                endTextField.setText("");
                doctorTextField.setText("");
                patientTextField.setText("");
                statusTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(appointmentsPanel, "Cancel failed, an error occurred");
            }
        }

        public static void displayAppointmentTable(JTable appointmentTable){
            // Get the appointments from the appointments.csv file
            String[] appointmentArray = appointmentsManager.getAppointments();
            // Specify the columns and data
            String[] columnNames = {"Username", "Doctor", "Date", "Start-time", "End-time", "Status"};
            String[][] data = new String[appointmentArray.length][6];
            // Add the appointments to the data array
            tableManager.setDataInTable(appointmentArray, columnNames, data, appointmentTable);
        }

    }

    public class records{
        public records(JTable recordsTable){
            //Make the appointment table uneditable
            recordsTable.setDefaultEditor(Object.class, null);

            //Set Selection Mode to Single Selection
            recordsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        public static void recordsTableClicked(JTable medicalRecordsTable, JTextField date, JTextField startTime, JTextField endTime, JTextField doctorName, JTextField patientName, JTextArea records){

            String data[] = tableManager.getDataFromTable(medicalRecordsTable, 6);

            // Set the text fields to the values of the selected row
            date.setText(data[0]);
            startTime.setText(data[1]);
            endTime.setText(data[2]);
            doctorName.setText(data[3]);
            patientName.setText(data[4]);
            records.setText(data[5]);

        }

        public static void displayMedicalRecordsTable(JTable medicalRecordsTable){
            // Get medical records from the csv file
            String[] medicalRecords = medicalRecordsManager.getAllRecords();
            // Specify the columns and data
            String[] column = {"Patient Name", "Doctor", "Date", "Start-time", "End-time", "Medical Record"};
            String[][] data = new String[medicalRecords.length][6];
            // Add the medical records to the data array
            tableManager.setDataInTable(medicalRecords, column, data, medicalRecordsTable);
        }
    }
}
