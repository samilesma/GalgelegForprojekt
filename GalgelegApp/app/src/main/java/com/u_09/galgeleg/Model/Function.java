package com.u_09.galgeleg.Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class Function {
    private static Connector con = new Connector("galgeleg.dk","galgeleg","root","ts2017");


    public static void sendMsg(String sid, String msg) throws SQLException{
        long timestamp = System.currentTimeMillis();
        con.update("INSERT INTO messages (sid,msg,timestamp) VALUES ('"+sid+"',?,'"+timestamp+"')",new String[]{"l",Long.toString(timestamp)});
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





}
