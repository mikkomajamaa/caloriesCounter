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
class Food {
    private String name;
    private float fat;
    private float carbs;
    private float prot;
    private float cals;
    
    public Food(String n, float fa, float ca, float pr, float cal) {
        name = n;
        fat = fa;
        carbs = ca;
        prot = pr;
        cals = cal;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getProt() {
        return prot;
    }

    public void setProt(float prot) {
        this.prot = prot;
    }

    public float getCals() {
        return cals;
    }

    public void setCals(float cals) {
        this.cals = cals;
    }
}
