package dashboards;

import misc.*;

import javax.swing.*;
import java.io.IOException;


public class Staff extends dashboards{
    public Staff(JTable timeslotTable, JTable pendingAppointmentTable, JTable medicalRecordsTable, JTable confirmedAppointmentTable, JTable todayAppointmentTable){
        super(timeslotTable, pendingAppointmentTable, medicalRecordsTable);
        new appointments(confirmedAppointmentTable);
        new appointments(todayAppointmentTable);
    }

    public class home extends appointments{
        public home (JTable dailyAppointmentTable){
            super(dailyAppointmentTable);
        }

        public static void displayTodayAppointments(JTable dailyAppointmentTable){
            // Get the current appointments from the appointments.csv file
            String[] appointmentArray = appointmentsManager.getTodayAppointment();
            // Specify the columns and data
            String[] columnNames = {"Username", "Doctor", "Date", "Start-time", "End-time", "Status"};
            String[][] data = new String[appointmentArray.length][6];
            // Add the appointments to the data array
            tableManager.setDataInTable(appointmentArray, columnNames, data, dailyAppointmentTable);
        }

        public static boolean saveRecord(JTextField date, JTextField startTime, JTextField endTime, JTextField doctorName, JTextField patientName, JTextField records, JPanel Home){
            String data = "";

            // Get the data from the text fields
            String TAdate = date.getText();
            String TAstartTime = startTime.getText();
            String TAendTime = endTime.getText();
            String TAdoctorName = doctorName.getText();
            String TApatientName = patientName.getText();
            String TArecords = records.getText();

            // Ensure all fields are filled
            if (TAdate.equals("") || TAstartTime.equals("") || TAendTime.equals("") || TAdoctorName.equals("") || TApatientName.equals("") || TArecords.equals("")){
                JOptionPane.showMessageDialog(Home, "Please fill all fields");
            }else{
                // Add the data to the data string
                data = TApatientName + "," + TAdoctorName + "," + TAdate + "," + TAstartTime + "," + TAendTime + "," + TArecords;

                // Add the record to the records.csv file
                boolean addStatus = new medicalRecordsManager().addRecord(data);

                // Display a message to the user
                if (addStatus){
                    JOptionPane.showMessageDialog(Home, "Record added successfully");
                    return true;
                }else{
                    JOptionPane.showMessageDialog(Home, "An error occurred");
                    return false;
                }
            }
            return false;
        }
    }

    public class timeslots extends dashboards.timeslots{
        public timeslots(JTable timeslotTable) {
            super(timeslotTable);
        }

        public static void addTimeslot(JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4, JPanel timeslotPanel) {

            String data = comboBoxManager.getDataFromCombobox(comboBox1, comboBox2, comboBox3, comboBox4, timeslotPanel);
            String[] dataArray = data.split(",");

            // Add the timeslot to the database
            boolean addStatus = new timeslotsManager().addTimeslot(dataArray[0], dataArray[1], dataArray[2], dataArray[3]);

            // If the timeslot was added successfully, add it to the table
            if (addStatus){
                JOptionPane.showMessageDialog(timeslotPanel, "Timeslot added successfully");
            }else{
                JOptionPane.showMessageDialog(timeslotPanel, "Timeslot already exists");
            }
        }

        public static void deleteTimeslot(JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4, JPanel timeslotPanel) throws IOException {
            // Get the values of the text fields
            String lineToRemove = comboBoxManager.getDataFromCombobox(comboBox1, comboBox2, comboBox3, comboBox4, timeslotPanel);

            // Remove the appointment
            boolean removeStatus = new timeslotsManager().deleteTimeslot(lineToRemove);

            // Display a message to the user
            if (removeStatus){
                JOptionPane.showMessageDialog(timeslotPanel, "Timeslot removed successfully");
            }else{
                JOptionPane.showMessageDialog(timeslotPanel, "Removal failed, an error occurred");
            }
        }

        public static void updateTimeslot(JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4, JPanel timeslotPanel, JTable timeslotTable){
            // Get the value of selected row
            String[] data =  tableManager.getDataFromTable(timeslotTable, 4);
            String oldData = String.join(",", data);

            // Get the values of the text fields
            String newData = comboBoxManager.getDataFromCombobox(comboBox1, comboBox2, comboBox3, comboBox4, timeslotPanel);

            // Update the appointment
            boolean updateStatus = new timeslotsManager().updateTimeslot(newData, oldData);

            // Display a message to the user
            if (updateStatus){
                JOptionPane.showMessageDialog(timeslotPanel, "Timeslot updated successfully");
            }else{
                JOptionPane.showMessageDialog(timeslotPanel, "Update failed, an error occurred");
            }

        }

        public static void clearTimeslot(JComboBox comboBox1, JComboBox comboBox2, JComboBox comboBox3, JComboBox comboBox4){
            // Clear the text fields
            comboBox1.setSelectedItem("");
            comboBox2.setSelectedItem("");
            comboBox3.setSelectedItem("");
            comboBox4.setSelectedItem("");
        }
    }

    public class pendingAppointment extends dashboards.appointments{
        public pendingAppointment(JTable appointmentTable){
            super(appointmentTable);
        }

        public static void displayPendingAppointment(JTable appointmentTable){
            // Get the appointments from the appointments.csv file
            String[] appointmentArray = appointmentsManager.getAppointmentStatus("Pending");
            // Specify the columns and data
            String[] columnNames = {"Username", "Doctor", "Date", "Start-time", "End-time", "Status"};
            String[][] data = new String[appointmentArray.length][6];
            // Add the appointments to the data array
            tableManager.setDataInTable(appointmentArray, columnNames, data, appointmentTable);
        }

        public static void confirmAppointment(JTable pendingAppointmentTable, JPanel pendingAppointmentPanel) throws IOException {
            // Get the value of selected row
            String[] data =  tableManager.getDataFromTable(pendingAppointmentTable, 6);
            String oldData = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
            String newData = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + "Confirmed";

            // Update the appointment
            boolean updateStatus = new appointmentsManager().setAppointmentStatus(oldData, newData);

            // Display a message to the user
            if (updateStatus){
                JOptionPane.showMessageDialog(pendingAppointmentPanel, "Appointment confirmed successfully");
            }else{
                JOptionPane.showMessageDialog(pendingAppointmentPanel, "Confirmation failed, an error occurred");
            }
        }
    }

    public class confirmedAppointment extends dashboards.appointments{
        public confirmedAppointment(JTable appointmentTable) {
            super(appointmentTable);
        }

        public static void displayConfirmedAppointment(JTable appointmentTable){
            // Get the appointments from the appointments.csv file
            String[] appointmentArray = appointmentsManager.getAppointmentStatus("Confirmed");
            // Specify the columns and data
            String[] columnNames = {"Username", "Doctor", "Date", "Start-time", "End-time", "Status"};
            String[][] data = new String[appointmentArray.length][6];
            // Add the appointments to the data array
            tableManager.setDataInTable(appointmentArray, columnNames, data, appointmentTable);
        }

        public static void setPending(JTable confirmedAppointmentTable, JPanel confirmedAppointmentPanel) throws IOException {
            // Get the value of selected row
            String[] data =  tableManager.getDataFromTable(confirmedAppointmentTable, 6);
            String oldData = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
            String newData = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + "Pending";

            // Update the appointment
            boolean updateStatus = new appointmentsManager().setAppointmentStatus(oldData, newData);

            // Display a message to the user
            if (updateStatus){
                JOptionPane.showMessageDialog(confirmedAppointmentPanel, "Appointment set to pending successfully");
            }else{
                JOptionPane.showMessageDialog(confirmedAppointmentPanel, "Setting to pending failed, an error occurred");
            }
        }
    }

    public class patientRecords extends dashboards.records{
        public patientRecords(JTable patientRecordsTable) {
            super(patientRecordsTable);
        }

    }
}
