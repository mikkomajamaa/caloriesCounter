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
    }    

    @FXML
    private void addFoodButtonAction(ActionEvent event) {
        String pattern = "\\d*?\\.?\\d+";
        if (!fatField.getText().trim().isEmpty()
                && !carbField.getText().trim().isEmpty()
                && !proteinField.getText().trim().isEmpty()
                && !foodNameField.getText().trim().isEmpty()
                && !calsField.getText().trim().isEmpty()) {
            if (Pattern.matches(pattern, fatField.getText())
                    && Pattern.matches(pattern, carbField.getText())
                    && Pattern.matches(pattern, proteinField.getText())
                    && Pattern.matches(pattern, calsField.getText())) {
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
                
                Food food = new Food(foodName, fat, carbs, prot, cals);
                dbc.addFood(food);
                dbc.insertFood(foodName, fat, carbs, prot, cals);
                
                updateFoodLV();
            }
        }
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        String[] foodData = foodLVFoodTab.getSelectionModel().getSelectedItem().split(",");
        String[] calsTemp = foodData[1].split(" ");
        //System.out.println(foodData[0] + calsTemp[1]);
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
        
    }
    
    private void updateFoodLV() {
        foodLVFoodTab.getItems().clear();
        for (Food f: dbc.getFoods()) {
            foodLVFoodTab.getItems().add(
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
    }

    @FXML
    private void addButtonAction(ActionEvent event) {
    }
    
}
