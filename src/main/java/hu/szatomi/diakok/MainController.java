package hu.szatomi.diakok;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button allButton;
    public Button sandorButton;
    public Button kecskemetButton;
    public Button yearButton;
    public Button classButton;
    public ListView<Student> resultList;
    public Button saveButton;

    private final ArrayList<Student> students = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file = new File("diakok.csv");
        loadStudents(file);

        allButton.setOnAction(_ -> showAll());
        sandorButton.setOnAction(_ -> showSandor());
        kecskemetButton.setOnAction(_ -> showKecskemet());
        yearButton.setOnAction(_ -> show1996());
        classButton.setOnAction(_ -> show10A());

        saveButton.setOnAction(_ -> saveStudents());
    }

    private void showAll() {
        resultList.getItems().clear();
        resultList.getItems().addAll(students);
    }

    private void showSandor() {
        resultList.getItems().clear();
        resultList.getItems().addAll(students.stream().filter(s -> s.firstName().equals("Sándor")).toList());
    }

    private void showKecskemet() {
        resultList.getItems().clear();
        resultList.getItems().addAll(students.stream().filter(s -> s.city().equals("Kecskemét")).toList());
    }

    private void show1996() {
        resultList.getItems().clear();
        resultList.getItems().addAll(students.stream().filter(s -> s.birthDate().contains("1996")).toList());
    }

    private void show10A() {
        resultList.getItems().clear();
        resultList.getItems().addAll(students.stream().filter(s -> s.studentClass().equals("10/A")).toList());
    }

    private void loadStudents(File file) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.lines().forEach(line -> {

                String[] data = line.split(";");

                Integer id = Integer.valueOf(data[0]);
                String lastName = data[1];
                String firstName = data[2];
                String sex = data[3];
                String studentClass = data[4];
                String birthDate = data[5];
                String city = data[6];
                Integer height = Integer.valueOf(data[7]);

                students.add(new Student(id, lastName, firstName, sex, studentClass, birthDate, city, height));
            });

        } catch (FileNotFoundException e)  {
            showError(e);
        }
    }

    private void showError(Exception e) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    private void saveStudents() {

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")) + ".txt";
        File file = new File(filename);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            if (resultList.getItems().size() < 1) {
                showError(new Exception("Nem található diák!"));
                return;
            }

            for (int i = 0; i < resultList.getItems().size(); i++) {
                writer.write("%s%n".formatted(resultList.getItems().get(i).toString()));
            }

            writer.close();

        } catch (IOException e) {
            showError(e);
        }
    }
}
