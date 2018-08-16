/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescounter;

import java.util.Date;

/**
 *
 * @author mikko
 */
public class Day {
    private int day;
    private int month;
    private int year;
    private float cals;
    private float fat;
    private float carbs;
    private float protein;
    
    public Day(int d, int m, int y, float cal, float f, float car, float p) {
        day = d;
        month = m;
        year = y;
        cals = cal;
        fat = f;
        carbs = car;
        protein = p;
    }
    
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public float getCals() {
        return cals;
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
}
