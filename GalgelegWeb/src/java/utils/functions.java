/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ahmad
 */
public class functions {
    
    
    public ArrayList<String> getAllUsers(int choice) throws SQLException{
        connector c = new connector();
        ArrayList<String> userID = new ArrayList<String>();
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userSurname = new ArrayList<String>();
        
        ResultSet rUser =  c.select("SELECT sid,name,surname FROM users");
        while (rUser.next()) {
            userID.add(rUser.getString(1));
            userName.add(rUser.getString(2));
            userSurname.add(rUser.getString(3));
        }
        if(choice==1) return userID;
        else if(choice==2) return userName;
        else return userSurname;
    }
}