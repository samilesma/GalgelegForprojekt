package com.u_09.galgeleg.Model;

import java.util.ArrayList;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class Chat {

    private String name;
    private String chatMessage;
    private

    public Chat(){
        this.name="";
        this.chatMessage="";
    }

    public void sendMessage(String msg, String studieID){
        con.update("INSERT INTO messages (sid,msg) VALUES (?,'" + getName()+"," +msg);

    }

    public ArrayList<ArrayList<String>> getAllMessages(int id){
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(new ArrayList<String>());
        msg.add(new ArrayList<String>());

    }

    public String getName(){
        return this.name;
    }

    public String getChatMessage(){
        return this.chatMessage;
    }
}
