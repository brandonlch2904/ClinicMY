package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class medicalRecordsManager {
    public static String[] getAllRecords(){
        String medicalRecords = "";
        String medicalRecordsArray[];

        try {
            File medicalRecordFile = new File("src//databases//medicalRecords.csv");
            Scanner scanner = new Scanner(medicalRecordFile);
            while (scanner.hasNextLine()) {
                medicalRecords += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        medicalRecordsArray = medicalRecords.split("\n");
        return medicalRecordsArray;

    }

    public static String[] getSpecificRecord(String patientName){
        String personalMedicalRecords = "";
        String personalMedicalRecordsArray[];

        try {
            File medicalRecordFile = new File("src//databases//medicalRecords.csv");
            Scanner scanner = new Scanner(medicalRecordFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(patientName)){
                    personalMedicalRecords += line + "\n";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        personalMedicalRecordsArray = personalMedicalRecords.split("\n");
        return personalMedicalRecordsArray;
    }
    public boolean addRecord(String data) {

        try {
            FileWriter writer = new FileWriter("src//databases//medicalRecords.csv", true);
            writer.write(data + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
