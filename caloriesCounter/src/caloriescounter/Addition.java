/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescounter;

/**
 *
 * @author mikko
 */
public class Addition {
    
    public int getYear() {
        return year;
    }
    private int foodId;
    private String foodName;
    private float amount;
    private float cal;
    private float fat;
    private float carbs;
    private float protein;
    private int day;
    private int month;
    private int year;
    
    public Addition(int fId, String fN, float a, float ca, float f, float car, float p, int d, int m, int y) {
        foodId = fId;
        foodName = fN;
        amount = a;
        cal = ca;
        fat = f;
        carbs = car;
        protein = p;
        day = d;
        month = m;
        year = y;
    }
    
    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getAmount() {
        return amount;
    }

    public float getCal() {
        return cal;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }
    
}
