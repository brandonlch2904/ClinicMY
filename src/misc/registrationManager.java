package misc;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class registrationManager {
    private static String accountDetails = "";
    private static String status = "";


    public static boolean register(String username, String password, String confirmPassword, String staffCode, JPanel registerPanel) {
        // Check if the "users.csv" file exists
        File userFile = new File("src//databases//users.csv");
        if (userFile.exists()) {
            // Create a scanner to read the file
            try (Scanner scanner = new Scanner(new File("src//databases//users.csv"))) {
                boolean usernameExists = false;
                // Iterate through each line of the file
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] user = line.split(",");
                    // If username already exists, set usernameExists to true
                    if (user[0].equals(username)) {
                        usernameExists = true;
                        break;
                    }
                }
                // If username already exists, display error message
                if (usernameExists) {
                    JOptionPane.showMessageDialog(registerPanel, "Username already exists");
                    // If username does not exist, create a new user
                } else {
                    // Check if password and confirm password match
                    if (password.equals(confirmPassword)) {
                        // Check if staff code is correct
                        String code = "1234";
                        if (staffCode.equals(code)) {
                            // Create a staff account
                            status = "staff";
                        } else if (staffCode.equals("")) {
                            // Create a patient account
                            status = "patient";
                        } else {
                            JOptionPane.showMessageDialog(registerPanel, "Staff code is incorrect");
                            return false;
                        }

                        // Write the new user to the file
                        accountDetails = username + "," + password + "," + status;
                        FileWriter writer = new FileWriter("src//databases//users.csv", true);
                        writer.write("\n" + accountDetails);
                        writer.close();
                        // Display success message
                        JOptionPane.showMessageDialog(registerPanel, "Account created successfully");
                        return true;

                        // If password and confirm password do not match, display error message
                    } else {
                        JOptionPane.showMessageDialog(registerPanel, "Password does not match");
                        return false;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("File does not exist");
        }
        return true;
    }

}
