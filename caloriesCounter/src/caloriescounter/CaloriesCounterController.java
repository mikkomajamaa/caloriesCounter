/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescounter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mikko
 */
public class CaloriesCounterController implements Initializable {

    @FXML
    private Tab mainTab;
    @FXML
    private ComboBox<Integer> dayCB;
    @FXML
    private ComboBox<String> monthCB;
    @FXML
    private ComboBox<Integer> yearCB;
    @FXML
    private TextField searchFoodField;
    @FXML
    private ListView<String> foodLV;
    @FXML
    private Button bwButton;
    @FXML
    private TextField bwField;
    @FXML
    private Tab foodTab;
    @FXML
    private ListView<String> foodLVFoodTab;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addFoodButton;
    @FXML
    private TextField searchFoodFieldFoodTab;
    @FXML
    private TextField foodNameField;
    @FXML
    private TextField fatField;
    @FXML
    private TextField carbField;
    @FXML
    private TextField proteinField;
    @FXML
    private Button cancelAddFoodButton;
    @FXML
    private Tab untitledTab;
    
    private DBConnection dbc = DBConnection.getInstance();
    @FXML
    private TextField calsField;
    private ArrayList<Food> foods = new ArrayList<>();
    @FXML
    private ListView<?> foodIntakeLV;
    @FXML
    private Button addButton;
    @FXML
    private TextField amountField;
    private String pattern = "\\d*?\\.?\\d+";
    private boolean foodNameOk = false;
    private boolean calsOk = false;
    private boolean fatOk = false;
    private boolean carbsOk = false;
    private boolean proteinOk = false;
    private int foodId = 1;
    private Food food;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbc.connect();
        updateFoodLV();
        
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < 32; i++) {
            dayCB.getItems().add(i);
        }
        dayCB.setValue(day);
        
        String[] months = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "November", "October", "December"};
        int month = Calendar.getInstance().get(Calendar.MONTH);
        for (String s: months) {
            monthCB.getItems().add(s);
        }
        monthCB.setValue(months[month]);
        
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 11; i++) {
            yearCB.getItems().add(year - 5 + i);
        }
        yearCB.setValue(year);
        
        addFoodButton.setDisable(true);
        cancelAddFoodButton.setDisable(true);
        deleteButton.setDisable(true);
    }    

    @FXML
    private void addFoodButtonAction(ActionEvent event) {
        float fat = Float.valueOf(fatField.getText());
        float carbs = Float.valueOf(carbField.getText());
        float prot = Float.valueOf(proteinField.getText());
        float cals = Float.valueOf(calsField.getText());
        String foodName = foodNameField.getText();

        fatField.clear();
        carbField.clear();
        proteinField.clear();
        calsField.clear();
        foodNameField.clear();

        food = new Food(foodId++, foodName, fat, carbs, prot, cals);
        dbc.addFood(food);
        dbc.insertFood(foodName, fat, carbs, prot, cals);

        updateFoodLV();
        addFoodButton.setDisable(true);
        cancelAddFoodButton.setDisable(false);
        deleteButton.setDisable(true);
        foodNameOk = false;
        calsOk = false;
        fatOk = false;
        carbsOk = false;
        proteinOk = false;

    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        String[] foodData = foodLVFoodTab.getSelectionModel().getSelectedItem().split(",");
        String[] calsTemp = foodData[1].split(" ");

        Food food = null;
        
        for (Food f: dbc.getFoods()) {
            if ((f.getName().equals(foodData[0])) && (f.getCals() == Float.valueOf(calsTemp[1]))) {
                food = f;
                break;
            }
        }
        
        dbc.deleteFood(food);
        dbc.removeFood(food);
        
        updateFoodLV();
        
        deleteButton.setDisable(true);
        cancelAddFoodButton.setDisable(true);
        
    }
    
    private void updateFoodLV() {
        foodLVFoodTab.getItems().clear();
        foodLV.getItems().clear();
        for (Food f: dbc.getFoods()) {
            foodId = f.getfId() + 1;
            foodLVFoodTab.getItems().add(
                    f.getName() + ", "
                   +f.getCals() + " cals, "
                   +f.getFat() + " fat , "
                  + f.getCarbs() + " carbs, "
                  + f.getProt() + " protein "
            );
            foodLV.getItems().add(
                    f.getName() + ", "
                   +f.getCals() + " cals, "
                   +f.getFat() + " fat , "
                  + f.getCarbs() + " carbs, "
                  + f.getProt() + " protein "
            );
        }
    }


    @FXML
    private void searchFoodFieldFoodTabKeyReleased(KeyEvent event) {
        foodLVFoodTab.getItems().clear();
        String s = searchFoodFieldFoodTab.getText();
        for (Food f: dbc.getFoods()) {
            if (s.length() > f.getName().length()) {
                continue;
            }
            for (int i = 0; i < f.getName().length() - s.length() + 1; i++) {
                if (s.matches(f.getName().substring(i, i + s.length()))) {
                    foodLVFoodTab.getItems().add(
                        f.getName() + ", "
                       +f.getCals() + " cals, "
                       +f.getFat() + " fat , "
                      + f.getCarbs() + " carbs, "
                      + f.getProt() + " protein "
                    );
                    break;
                }
            }
        }
    }

    @FXML
    private void searchFoodKeyReleased(KeyEvent event) {
        foodLV.getItems().clear();
        String s = searchFoodField.getText();
        for (Food f: dbc.getFoods()) {
            if (s.length() > f.getName().length()) {
                continue;
            }
            for (int i = 0; i < f.getName().length() - s.length() + 1; i++) {
                if (s.matches(f.getName().substring(i, i + s.length()))) {
                    foodLV.getItems().add(
                        f.getName() + ", "
                       +f.getCals() + " cals, "
                       +f.getFat() + " fat , "
                      + f.getCarbs() + " carbs, "
                      + f.getProt() + " protein "
                    );
                    break;
                }
            }
        }
                
                
    }

    @FXML
    private void addButtonAction(ActionEvent event) {
    }

    @FXML
    private void foodNameFieldKeyReleased(KeyEvent event) {
        if (!foodNameField.getText().trim().isEmpty()) {
            foodNameOk = true;
        } else {
            foodNameOk = false;
        }
        
        if ((foodNameOk == true) && (calsOk == true) && (fatOk == true) && (carbsOk == true) && (proteinOk == true)) {
            addFoodButton.setDisable(false);
        } else {
            addFoodButton.setDisable(true);
        }
    }

    @FXML
    private void calsFieldKeyReleased(KeyEvent event) {
        if (!calsField.getText().trim().isEmpty()
                && Pattern.matches(pattern, calsField.getText())) {
            calsOk = true;
        } else {
            calsOk = false;
        }
        
        if ((foodNameOk == true) && (calsOk == true) && (fatOk == true) && (carbsOk == true) && (proteinOk == true)) {
            addFoodButton.setDisable(false);
        } else {
            addFoodButton.setDisable(true);
        }
    }

    @FXML
    private void fatFieldKeyReleased(KeyEvent event) {
        if (!fatField.getText().trim().isEmpty()
                && Pattern.matches(pattern, fatField.getText())) {
            fatOk = true;
        } else {
            fatOk = false;
        }
        
        if ((foodNameOk == true) && (calsOk == true) && (fatOk == true) && (carbsOk == true) && (proteinOk == true)) {
            addFoodButton.setDisable(false);
        } else {
            addFoodButton.setDisable(true);
        }
    }

    @FXML
    private void carbFieldKeyReleased(KeyEvent event) {
        if (!carbField.getText().trim().isEmpty()
                && Pattern.matches(pattern, carbField.getText())) {
            carbsOk = true;
        } else {
            carbsOk = false;
        }
        
        if ((foodNameOk == true) && (calsOk == true) && (fatOk == true) && (carbsOk == true) && (proteinOk == true)) {
            addFoodButton.setDisable(false);
        } else {
            addFoodButton.setDisable(true);
        }
}

    @FXML
    private void proteinFieldKeyReleased(KeyEvent event) {
        if (!proteinField.getText().trim().isEmpty()
                && Pattern.matches(pattern, proteinField.getText())) {
            proteinOk = true;
        } else {
            proteinOk = false;
        }
        
        if ((foodNameOk == true) && (calsOk == true) && (fatOk == true) && (carbsOk == true) && (proteinOk == true)) {
            addFoodButton.setDisable(false);
        } else {
            addFoodButton.setDisable(true);
        }
    }

    @FXML
    private void cancelAddFoodButtonAction(ActionEvent event) {
        dbc.removeFood(food);
        dbc.deleteFood(food);
        cancelAddFoodButton.setDisable(true);
        updateFoodLV();
    }

    @FXML
    private void foodTabLVClicked(MouseEvent event) {
        if (!foodLVFoodTab.getSelectionModel().isEmpty()) {
            deleteButton.setDisable(false);
        }
    }
    
}
