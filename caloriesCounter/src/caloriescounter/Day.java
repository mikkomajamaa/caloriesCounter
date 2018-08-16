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
    private Date date;
    private float cals;
    private float fat;
    private float carbs;
    private float protein;
    
    public Day(Date d, float cal, float f, float car, float p) {
        date = d;
        cals = cal;
        fat = f;
        carbs = car;
        protein = p;
    }
}
