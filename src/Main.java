import dashboards.*;
import misc.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main extends JFrame {
    private JPanel rootPanel;
    private JPanel loginPanel;
    private JPasswordField loginPassword;
    private JTextField loginUsername;
    private JButton loginButton;
    private JPanel patientDashboardPanel;
    private JTabbedPane patientTabbedPane;
    private JPanel patientTimeslotPanel;
    private JPanel patientAppointmentsPanel;
    private JPanel medicalRecordsPanel;
    private JTextField patientTimeslotDate;
    private JTextField patientTimeslotStartTime;
    private JTextField patientTimeslotEndTime;
    private JTextField patientTimeslotDoctor;
    private JButton bookAppointmentButton;
    private JTable timeslotTable;
    private JTextField patientAppointmentDate;
    private JTextField patientAppointmentStartTime;
    private JTextField patientAppointmentEndTime;
    private JTextField patientAppointmentDoctor;
    private JButton patientCancelAppointmentButton;
    private JTable appointmentTable;
    private JTextField patientAppointmentName;
    private JTextField patientAppointmentStatus;
    private JTable medicalRecordsTable;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextArea textArea1;
    private JPanel staffDashboardPanel;
    private JTabbedPane staffTabbedPane;
    private JPanel patientHomePanel;
    private JTextField patientCurrentDate;
    private JButton patientLogoutButton;
    private JPanel staffHomePanel;
    private JTextField staffCurrentDate;
    private JButton staffLogoutButton;
    private JPanel staffTimeslotPanel;
    private JComboBox dateComboBox;
    private JComboBox doctorComboBox;
    private JButton deleteTimeslotButton;
    private JButton updateTimeslotButton;
    private JButton addTimeslotButton;
    private JTable staffTimeslotTable;
    private JButton clearTimeslotButton;
    private JComboBox aptStartComboBox;
    private JComboBox aptEndComboBox;
    private JTable todaysAppointmentTable;
    private JTextField TAendTime;
    private JTextField TAdoctorName;
    private JTextField TApatientName;
    private JTextField TAdate;
    private JTextField TAstartTime;
    private JTextField TAstatus;
    private JButton saveRecordButton;
    private JButton confirmAppointmentButton;
    private JButton staffCancelAppointmentButton;
    private JTextField PAdate;
    private JTextField PAstartTime;
    private JTextField PAendTime;
    private JTextField PAdoctorName;
    private JTextField PApatientName;
    private JTextField PAstatus;
    private JPanel staffPendingAppointmentPanel;
    private JPanel staffPatientRecordsPanel;
    private JScrollPane JScrollPane;
    private JTable pendingAppointmentTable;
    private JTable patientRecordsTable;
    private JTextArea PRrecords;
    private JTextField PRdate;
    private JTextField PRstartTime;
    private JTextField PRendTime;
    private JTextField PRdoctorName;
    private JTextField PRpatientName;
    private JTextField CAdate;
    private JTextField CAstartTime;
    private JTextField CAendTime;
    private JTextField CAdoctorName;
    private JTextField CApatientName;
    private JTextField CAstatus;
    private JButton setPendingButton;
    private JTable confirmedAppointmentTable;
    private JPanel staffConfirmedAppointmentPanel;
    private JTextField TArecords;
    private JTextArea staffHomeContent;
    private JTextArea patientHomeContent;
    private JButton toRegisterButton;
    private JPanel registerPanel;
    private JTextField registerUsername;
    private JTextField registerPassword;
    private JTextField registerConfirmPassword;
    private JTextField staffCode;
    private JButton registerButton;
    private JButton toLoginButton;
    private String status;
    private String clientUsername;

    class Dashboard {

        private String status;
        public void setStatus(String status) {
            this.status = status;
        }
        public void displayDashboard(){
            if (status.equals("patient")) {
                displayPatient();
            } else if (status.equals("staff")) {
                displayStaff();
            }
        }
        public void displayPatient() {

            // Set the current date for the text-field
            patientCurrentDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            // Display patient dashboard
            patientDashboardPanel.setVisible(true);
            patientTabbedPane.setVisible(true);

        // TIMESLOT TAB
            // Display timeslotTable
            Patient.timeslots.displayTimeslotTable(timeslotTable);

            // If timeslotTable is clicked
            timeslotTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Patient.timeslots.timeslotsTableClicked(timeslotTable, patientTimeslotDate, patientTimeslotStartTime, patientTimeslotEndTime, patientTimeslotDoctor);
                }
            });

            // If bookAppointmentButton is clicked
            bookAppointmentButton.addActionListener(e -> {

                // Book the appointment
                Patient.timeslots.bookAppointment(patientTimeslotDate, patientTimeslotStartTime, patientTimeslotEndTime, patientTimeslotDoctor, clientUsername, patientTimeslotPanel);

                // Refresh all tables
                refreshAllTables();

            });

        // APPOINTMENT TAB
            // Display appointmentTable
            Patient.appointments.displayAppointmentTable(appointmentTable);

            // If appointmentTable is clicked
            appointmentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Patient.appointments.appointmentTableClicked(appointmentTable, patientAppointmentDate, patientAppointmentStartTime, patientAppointmentEndTime, patientAppointmentDoctor, patientAppointmentName, patientAppointmentStatus);
                }
            });

            // If patientCancelAppointmentButton is clicked
            patientCancelAppointmentButton.addActionListener(e -> {
                // Remove the appointment from the database
                try {
                    Patient.appointments.cancelAppointment(patientAppointmentDate, patientAppointmentStartTime, patientAppointmentEndTime, patientAppointmentDoctor, patientAppointmentName, patientAppointmentStatus, patientAppointmentsPanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Refresh all tables
                refreshAllTables();

            });

        // MEDICAL RECORDS TAB
            // Display medicalRecordsTable
            Patient.medicalRecords.showSelfRecords(medicalRecordsTable, clientUsername);

            // If medicalRecordsTable is clicked
            medicalRecordsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Patient.medicalRecords.recordsTableClicked(medicalRecordsTable, textField12, textField13, textField14, textField15, textField16, textArea1);
                }
            });

            // Logout when patient clicks logout button
            patientLogoutButton.addActionListener(e -> logout(patientDashboardPanel));
        }

        public void displayStaff () {
            // Display current date
            staffCurrentDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            // Display staff dashboard
            staffDashboardPanel.setVisible(true);
            staffTabbedPane.setVisible(true);

        // HOME TAB
            // Display today's appointments
            Staff.home.displayTodayAppointments(todaysAppointmentTable);

            // If staff clicks today's AppointmentTable
            todaysAppointmentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Staff.appointments.appointmentTableClicked(todaysAppointmentTable, TAdate, TAstartTime, TAendTime, TAdoctorName, TApatientName, TAstatus);

                    // Clear the placeholder text in textarea
                    TArecords.setText("");
                }
            });

            // If saveRecordButton is clicked
            saveRecordButton.addActionListener(e -> {
                // Save the record to the database
                if (Staff.home.saveRecord(TAdate, TAstartTime, TAendTime, TAdoctorName, TApatientName, TArecords, staffHomePanel)) {
                    //Remove the appointment from the database after record is saved
                    try {
                        Staff.appointments.cancelAppointment(TAdate, TAstartTime, TAendTime, TAdoctorName, TApatientName, TAstatus, staffHomePanel);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // Set all text-fields to empty
                TAdate.setText("");
                TAstartTime.setText("");
                TAendTime.setText("");
                TAdoctorName.setText("");
                TApatientName.setText("");
                TAstatus.setText("");
                TArecords.setText("");

                // Refresh all tables
                refreshAllTables();
            });

        // TIMESLOT TAB
            // Display timeslotTable
            Staff.timeslots.displayTimeslotTable(staffTimeslotTable);

            // Add selections to all comboBox for staff
            comboBoxManager.addDatesToComboBox(dateComboBox, 7);
            comboBoxManager.addDoctorsToComboBox(doctorComboBox);
            comboBoxManager.addTimesToComboBox(aptStartComboBox, aptEndComboBox);

            // Clear the text-fields
            Staff.timeslots.clearTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox);

            // If addTimeslotButton is clicked
            addTimeslotButton.addActionListener(e -> {

                // Add the timeslot to the database
                Staff.timeslots.addTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox, staffTimeslotPanel);

                // Refresh all tables
                refreshAllTables();
            });

            // If updateTimeslotButton is clicked
            updateTimeslotButton.addActionListener(e -> {

                // Update the timeslot in the database
                Staff.timeslots.updateTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox, staffTimeslotPanel, staffTimeslotTable);

                // Refresh all tables
                refreshAllTables();
            });

            // If deleteTimeslotButton is clicked
            deleteTimeslotButton.addActionListener(e -> {

                // Delete the timeslot from the database
                try {
                    Staff.timeslots.deleteTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox, staffTimeslotPanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Refresh all tables
                refreshAllTables();
                // Clear the text-fields
                Staff.timeslots.clearTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox);
            });


            // If clearTimeslotButton is clicked
            clearTimeslotButton.addActionListener(e -> {

                // Clear the text-fields
                Staff.timeslots.clearTimeslot(dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox);

                // Disable the update, delete and clear buttons
                updateTimeslotButton.setEnabled(false);
                deleteTimeslotButton.setEnabled(false);
                clearTimeslotButton.setEnabled(false);

                // Enable add button
                addTimeslotButton.setEnabled(true);
            });

            //If timeslotTable is clicked
            staffTimeslotTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Staff.timeslots.timeslotsTableClicked(staffTimeslotTable, dateComboBox, aptStartComboBox, aptEndComboBox, doctorComboBox);

                    // Enable the update, delete and clear buttons
                    updateTimeslotButton.setEnabled(true);
                    deleteTimeslotButton.setEnabled(true);
                    clearTimeslotButton.setEnabled(true);

                    // Disable add button
                    addTimeslotButton.setEnabled(false);
                }
            });

        // PENDING APPOINTMENTS TAB
            // Display pendingAppointmentTable
            Staff.pendingAppointment.displayPendingAppointment(pendingAppointmentTable);

            // If confirmAppointmentButton is clicked
            confirmAppointmentButton.addActionListener(e -> {
                // Confirm the appointment in the database
                try {
                    Staff.pendingAppointment.confirmAppointment(pendingAppointmentTable, staffPendingAppointmentPanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Refresh all tables
                refreshAllTables();
            });

            // If staffCancelAppointmentButton is clicked
            staffCancelAppointmentButton.addActionListener(e -> {
                // Remove the appointment from the database
                try {
                    Staff.appointments.cancelAppointment(PAdate, PAstartTime, PAendTime, PAdoctorName, PApatientName, PAstatus, staffPendingAppointmentPanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Refresh all tables
                refreshAllTables();

            });

            // If pendingAppointmentTable is clicked
            pendingAppointmentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Staff.appointments.appointmentTableClicked(pendingAppointmentTable, PAdate, PAstartTime, PAendTime, PAdoctorName, PApatientName, PAstatus);
                }
            });

        // CONFIRMED APPOINTMENTS TAB
            // Display confirmedAppointmentTable
            Staff.confirmedAppointment.displayConfirmedAppointment(confirmedAppointmentTable);

            // If setPendingButton is clicked
            setPendingButton.addActionListener(e -> {
                // Set the appointment to pending in the database
                try {
                    Staff.confirmedAppointment.setPending(confirmedAppointmentTable, staffConfirmedAppointmentPanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Refresh all tables
                refreshAllTables();
            });

            // If confirmedAppointmentTable is clicked
            confirmedAppointmentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Staff.appointments.appointmentTableClicked(confirmedAppointmentTable, CAdate, CAstartTime, CAendTime, CAdoctorName, CApatientName, CAstatus);
                }
            });

        // PATIENT RECORDS TAB
            // Display patientRecordTable
            Staff.patientRecords.displayMedicalRecordsTable(patientRecordsTable);

            // If patientRecordsTable is clicked
            patientRecordsTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the selected row
                    Staff.patientRecords.recordsTableClicked(patientRecordsTable, PRdate, PRstartTime, PRendTime, PRdoctorName, PRpatientName, PRrecords);
                }
            });
            // Logout when staff clicks logout button
            staffLogoutButton.addActionListener(e -> {
                logout(staffDashboardPanel);
            });
        }
    }


    // Constructor for MAIN CLASS
    public Main(){

        // If user clicks register an account button
        toRegisterButton.addActionListener(e -> {
            // Hide the login panel
            loginPanel.setVisible(false);
            // Display the register panel
            registerPanel.setVisible(true);
        });

        // If user clicks back to login button
        toLoginButton.addActionListener(e -> {
            // Hide the register panel
            registerPanel.setVisible(false);
            // Display the login panel
            loginPanel.setVisible(true);
        });

        // Create an instance of dashboard class
        Dashboard dashboard = new Dashboard();

        // If user clicks login button
        loginButton.addActionListener(e -> {
            String username = (loginUsername.getText()).replaceAll(" ","");
            String password = loginPassword.getText();

            // Check if the "users.csv" file exists
            File userFile = new File("src//databases//users.csv");
            if (userFile.exists()) {
                // Create a scanner to read the file
                try (Scanner scanner = new Scanner(new File("src//databases//users.csv"))) {
                    boolean loginSuccess = false;
                    // Iterate through each line of the file
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] user = line.split(",");
                        //If username and password match, login is successful
                        if (user[0].equals(username) && user[1].equals(password)) {
                            loginSuccess = true;
                            System.out.println("Login successful");
                            // Hide the login panel
                            loginPanel.setVisible(false);
                            // Get client username and status
                            clientUsername = username;
                            status = user[2];

                            // If user is patient then display patient dashboard
                            if (status.equals("patient")) {
                                dashboard.setStatus(status);
                                dashboard.displayDashboard();

                            // If user is staff then display staff dashboard
                            }else if (status.equals("staff")) {
                                dashboard.setStatus(status);
                                dashboard.displayDashboard();

                            }else{
                                System.out.println("Error");
                            }
                            break;
                        }
                    }
                    // Login failed if the username and password were not found
                    if (!loginSuccess) {
                        JOptionPane.showMessageDialog(rootPanel, "Invalid username or password");
                        System.out.println("Login failed");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("File does not exist");
            }
        });

        // If user clicks register button
        registerButton.addActionListener(e -> {
            String username = (registerUsername.getText()).replaceAll(" ","");
            String password = registerPassword.getText();
            String confirmPassword = registerConfirmPassword.getText();
            String code = (staffCode.getText()).replaceAll(" ","");

            // Check if all fields are filled
            if (username.equals("") || password.equals("") || confirmPassword.equals("")) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill in all fields");
                return;
            }

            // Try registering the user
            if(registrationManager.register(username, password, confirmPassword, code, registerPanel)){
                // Clear all text-fields
                registerUsername.setText("");
                registerPassword.setText("");
                registerConfirmPassword.setText("");
                staffCode.setText("");
                // Hide the register panel
                registerPanel.setVisible(false);
                // Display the login panel
                loginPanel.setVisible(true);
            }
        });
    }

    // MAIN METHOD
    public static void main(String[] args) {
        // Create a new JFrame
        JFrame frame = new JFrame("ClinicMY");
        frame.setContentPane(new Main().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to refresh all staff tables
    public void refreshAllTables() {
        // Refresh the patient timeslot table
        Patient.timeslots.displayTimeslotTable(timeslotTable);
        // Refresh the patient appointment table
        Patient.appointments.displayAppointmentTable(appointmentTable);
        // Refresh the staff timeslot table
        Staff.timeslots.displayTimeslotTable(staffTimeslotTable);
        // Refresh the pending appointment table
        Staff.pendingAppointment.displayPendingAppointment(pendingAppointmentTable);
        // Refresh the confirmed appointment table
        Staff.confirmedAppointment.displayConfirmedAppointment(confirmedAppointmentTable);
        // Refresh the patient records table
        Staff.patientRecords.displayMedicalRecordsTable(patientRecordsTable);
        // Refresh today's appointment table
        Staff.home.displayTodayAppointments(todaysAppointmentTable);
    }

    // Logout Method
    public void logout(JPanel DashboardPanel) {
        // Display login screen
        loginPanel.setVisible(true);
        // Hide dashboard
        DashboardPanel.setVisible(false);
        // Clear the text fields
        loginUsername.setText("");
        loginPassword.setText("");
        status = "";
    }
}
