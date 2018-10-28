/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescounter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikko
 */
public class DBConnection {
    Connection conn = null;
    private String SQL;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private ArrayList<Food> foods = null;
    private ArrayList<Addition> additions = null;
    private static DBConnection instance = null;
    
    protected DBConnection() {
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    
    public void connect() {
        try {
            //db parameters
            String url = "jdbc:sqlite:CaloriesCounterDB";
            //create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertFood(String name, float fat, float carbs, float prot, float cals) {
        try {
            SQL = "INSERT INTO food (name, fat, carb, prot, cals) VALUES (?, ?, ?, ?, ?)";
            stmt = this.conn.prepareStatement(SQL);
            stmt.setString(1, name);
            stmt.setFloat(2, fat);
            stmt.setFloat(3, carbs);
            stmt.setFloat(4, prot);
            stmt.setFloat(5, cals);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void deleteFood(Food f) {
       try {
            SQL = "DELETE FROM food WHERE name = ? AND cals = ?";
            stmt = this.conn.prepareStatement(SQL);
            stmt.setString(1, f.getName());
            stmt.setFloat(2, f.getCals());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public ArrayList<Food> getFoods() {
        if (foods == null) {
            foods = new ArrayList<>();
            try {
                SQL = "SELECT * FROM food";
                stmt = conn.prepareStatement(SQL);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Food f = new Food(
                            rs.getInt("f_id"),
                            rs.getString("name"),
                            rs.getFloat("fat"),
                            rs.getFloat("carb"),
                            rs.getFloat("prot"),
                            rs.getFloat("cals")
                    );
                    foods.add(f);
                }
                return foods;
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            return foods;
        }
        return null;
    }
    
    public void insertDaysFoods(int day, int month, int year, int fId, float amount) {
        
    }
    
    public void addAddition(Addition a) {
        if (additions == null) {
            additions = new ArrayList<>();
        }
        additions.add(a);
    }
    
    public void insertAddition(int d, int m, int y, int fId, float amount) {
        try {
            SQL = "INSERT INTO days_food VALUES (?,?,?)";
            String date = d + "-" + m + "-" + y;
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, date);
            stmt.setFloat(2, fId);
            stmt.setFloat(3, amount);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList<Addition> getAdditions() {
        if (additions == null) {
            try {
                additions = new ArrayList<>();
                SQL = "SELECT * FROM days_food INNER JOIN food ON food.f_id = days_food.f_id;";
                stmt = conn.prepareStatement(SQL);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] date = rs.getString("day").split("-");
                    int day = Integer.valueOf(date[0]);
                    int month = Integer.valueOf(date[1]);
                    int year = Integer.valueOf(date[2]);
                    
                    Addition a = new Addition(rs.getInt("f_id"),
                                                rs.getString("name"),
                                                rs.getFloat("amount"),
                                                rs.getFloat("cals"),
                                                rs.getFloat("fat"),
                                                rs.getFloat("carb"),
                                                rs.getFloat("prot"),
                                                day,
                                                month,
                                                year
                    );
                    additions.add(a);                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return additions;
    }
    
    public void addFood(Food f) {
        this.foods.add(f);
    }
    
    public void removeFood(Food f) {
        Iterator<Food> it = foods.iterator();
        while (it.hasNext()) {
            if (it.next().getfId() == f.getfId()) {
                it.remove();
            }
        }
    }
    
    public void insertDay(int d, int m, int y, float ca, float f, float car, float prot) {
        try {
            String date = d + "-" + m + "-" + y;
            String SQLInit = "DELETE FROM days WHERE day = ?";
            stmt = conn.prepareStatement(SQLInit);
            stmt.setString(1, date);
            stmt.execute();
            
            SQL = "INSERT INTO days (day, cals, fat, carb, prot) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, date);
            stmt.setFloat(2, ca);
            stmt.setFloat(3, f);
            stmt.setFloat(4, car);
            stmt.setFloat(5, prot);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
