/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        ArrayList<String> adminStatus = new ArrayList <String>();
        
        ResultSet rUser =  con.select("SELECT sid,name,surname,admin FROM users");
        while (rUser.next()) {
            userID.add(rUser.getString(1));
            userName.add(rUser.getString(2));
            userSurname.add(rUser.getString(3));
            adminStatus.add(rUser.getString(4));
        }
        if(choice==1) return userID;
        else if(choice==2) return userName;
        else return userSurname;
    }
    
    public void sendMsg(String sid, String msg) throws SQLException{
        Long timestamp = System.currentTimeMillis()/1000L;
        con.update("INSERT INTO chat (sid,msg,timestamp) VALUES ('"+sid+"',?,'"+timestamp+"')",msg);
    }
    
    public ArrayList<ArrayList<String>> getMessages(String sid, long timestamp) throws SQLException{
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());
        ResultSet rs = con.select("SELECT users.name, users.surname, chat.msg, chat.timestamp FROM users RIGHT JOIN chat ON chat.sid=users.sid WHERE chat.deleted=0 AND chat.sid!='"+sid+"' AND chat.timestamp>='"+timestamp+"'");
        while(rs.next()){
            msg.get(0).add(rs.getString("name")+" "+rs.getString("surname"));
            msg.get(1).add(rs.getString("msg"));
            msg.get(2).add(rs.getString("timestamp"));
        }

        return msg;
    }
    
    public JSONObject getMessagesJson(String sid, long timestamp) throws SQLException, JSONException{
        ResultSet rs = con.select("SELECT users.name, users.surname, chat.msg, chat.timestamp FROM users RIGHT JOIN chat ON chat.sid=users.sid WHERE chat.deleted=0 AND chat.sid!='"+sid+"' AND chat.timestamp>='"+timestamp+"'");
        JSONArray ja=new JSONArray();
        while(rs.next()) ja.put(new JSONObject().put("name", rs.getString("name")+" "+rs.getString("surname")).put("msg", rs.getString("msg")).put("timestamp", rs.getString("timestamp")));
        return new JSONObject().put("messages",ja);
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
    
    public void makeAdmin (String id) throws SQLException{
        con.update("UPDATE users SET admin='1' WHERE sid='"+id+"'");
    }
    public void removeAdmin (String id) throws SQLException{
        con.update("UPDATE users SET admin='0' WHERE sid='"+id+"'");
    }
    
    
    public void banUser(String sid, long timestamp) throws SQLException{
        con.update("UPDATE users SET banned="+timestamp+" WHERE sid='"+sid+"'");
    }
    
    public void challengeFriend(String sidOne, String sidTwo, String word) throws SQLException{
        long unixTime = System.currentTimeMillis() / 1000L;
        int id = con.updateLastInsertedId("INSERT INTO challenges (p1,p2,timestamp,word) VALUES ('"+sidOne+"','"+sidTwo+"',"+unixTime+",'"+word+"')");
        con.update("INSERT INTO multiplayer (cid) VALUES ("+id+")");
    }
    
    public void rejectChallenge(int challengeID) throws SQLException {
        con.update("DELETE FROM challenges WHERE id="+challengeID);
    }
    
}