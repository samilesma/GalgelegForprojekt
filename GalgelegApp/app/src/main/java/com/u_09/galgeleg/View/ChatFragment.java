package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//import com.u_09.galgeleg.Model.Function;
import com.u_09.galgeleg.Model.GalgelogikFunc;

import com.u_09.galgeleg.Model.Web;
import com.u_09.galgeleg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class ChatFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ListView mLvChat;
    private EditText mEtMsg;
    private Button mBtnSend;
    private GalgelogikFunc function = new GalgelogikFunc();
    private long timestamp=System.currentTimeMillis()/1000L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.chat_fragment, container, false);

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                try {
                    updatechat();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },0,5000);

        mLvChat = (ListView) mView.findViewById(R.id.lv_chat);
        mEtMsg = (EditText) mView.findViewById(R.id.et_msg);
        mBtnSend = (Button) mView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);

        return mView;
    }

    private void updatechat() throws ExecutionException, InterruptedException, JSONException {
        System.out.println(new Web().execute("http://galgeleg.dk/GalgelegWeb/AndroidServlet?type=chat&timestamp="+timestamp).get());
        //JSONObject jsonObject = new JSONObject(new Web().execute("http://localhost:8080/GalgelegWeb/ChatServlet?type=chat&timestamp="+timestamp).get());
        timestamp+=5;
/*
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
        */
    }

    @Override
    public void onClick(View v) {
        if(v==mBtnSend){
            String message = mEtMsg.getText().toString();
        }
    }
}
