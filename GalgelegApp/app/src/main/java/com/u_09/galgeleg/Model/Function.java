package com.u_09.galgeleg.Model;


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

    public ArrayList<ArrayList<String>> getAllMessages(int id){
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());

    }


}
