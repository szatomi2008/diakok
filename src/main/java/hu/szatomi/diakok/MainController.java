package hu.szatomi.diakok;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button allButton;
    public Button sandorButton;
    public Button musketeerButton;
    public Button yearButton;
    public Button classButton;
    public ListView<Student> resultList;
    public Button saveButton;

    private final ArrayList<Student> students = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file = new File("diakok.csv");
        loadStudents(file);

        resultList.getItems().addAll(students);
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

}
