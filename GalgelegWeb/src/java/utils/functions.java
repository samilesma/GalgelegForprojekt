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
    private connector con;
    
    public functions(){
        con=new connector();
    }
    
    public ArrayList<String> getAllUsers(int choice) throws SQLException{
        //connector c = new connector();
        ArrayList<String> userID = new ArrayList<String>();
        ArrayList<String> userName = new ArrayList<String>();
        ArrayList<String> userSurname = new ArrayList<String>();
        
        ResultSet rUser =  con.select("SELECT sid,name,surname FROM users");
        while (rUser.next()) {
            userID.add(rUser.getString(1));
            userName.add(rUser.getString(2));
            userSurname.add(rUser.getString(3));
        }
        if(choice==1) return userID;
        else if(choice==2) return userName;
        else return userSurname;
    }
    
    public void sendMsg(String sid, String msg) throws SQLException{
        Long timestamp = System.currentTimeMillis();
        con.update("INSERT INTO messages (sid,msg,timestamp) VALUES ('"+sid+"',?,'"+timestamp+"')",new String[][]{new String[]{"l",Long.toString(timestamp)}});
    }
    
    public ArrayList<ArrayList<String>> getMessages(long timestamp) throws SQLException{
        timestamp = System.currentTimeMillis();
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        ResultSet rs = con.select("SELECT sid,msg,timestamp FROM chat WHERE deleted=0 AND timestamp>"+timestamp);
        while(rs.next()){
            msg.get(0).add(rs.getString("sid"));
            msg.get(1).add(rs.getString("msg"));
            msg.get(2).add(rs.getString("timestamp"));
        }

        return msg;
    }
    
    
    public ArrayList<ArrayList<String>> getAllMessages() throws SQLException{
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        ResultSet rs = con.select("SELECT sid,msg,timestamp FROM chat WHERE deleted=0");
        while(rs.next()){
            msg.get(0).add(rs.getString("sid"));
            msg.get(1).add(rs.getString("msg"));
            msg.get(2).add(rs.getString("timestamp"));
        }

        return msg;
    }
    
    public void deleteMessage(int id) throws SQLException{
        con.update("UPDATE chat SET deleted=1 WHERE id="+id);
    }
    
    public void banUser(String sid, long timestamp) throws SQLException{
        timestamp = System.currentTimeMillis();
        con.update("UPDATE users SET ban="+timestamp+" WHERE sid="+sid);
    }
    
    public void challengeFriend(String sidOne, String sidTwo, String word, long timestamp) throws SQLException{
        timestamp = System.currentTimeMillis();
        con.update("INSERT INTO challenges (p1,p2,timestamp,word) VALUES ('"+sidOne+"','"+sidTwo+"',"+timestamp+",'"+word+"')",new String[]{"l",Long.toString(timestamp)});
        /*
        MULTIPLAYER OG CHALLENGES skal måske samles til en tabel, da andet ikke giver mening.
        */
    }
    
    public void rejectChallenge(int challengeID) throws SQLException {
        con.update("DELETE FROM challenges WHERE id="+challengeID);
    }
    
}