package com.u_09.galgeleg.Model;

import java.security.Timestamp;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class Function {


    public static void sendMsg(String sid, String msg){
        long timestamp = System.currentTimeMillis();
        con.update("INSERT INTO messages (sid,msg,timestamp) VALUES ('"+sid+"',?,'"+timestamp+"')",new String[]{"l",Long.toString(timestamp)});
    }
}
