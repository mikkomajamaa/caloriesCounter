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
            String url = "jdbc:sqlite:C:\\Users\\mikko\\sqlite-tools-win32-x86-3240000\\cal";
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
    
    
}
