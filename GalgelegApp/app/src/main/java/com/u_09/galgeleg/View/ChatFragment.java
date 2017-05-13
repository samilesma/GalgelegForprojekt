package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikCalls;
import com.u_09.galgeleg.Model.Web;
import com.u_09.galgeleg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

//import com.u_09.galgeleg.Model.Function;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class ChatFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private View mView;
    private ListView mLvChat;
    private ArrayAdapter mAdapter;
    private String[] messages, names, times;
    private EditText mEtMsg;
    private Button mBtnSend;
    private GalgelogikCalls mGalgelogikCalls;
    private GalgelogikCalls function = new GalgelogikCalls();
    private long timestamp = System.currentTimeMillis() / 1000L;
    private Timer mTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.chat_fragment, container, false);

        mGalgelogikCalls = new GalgelogikCalls();

        mLvChat = (ListView) mView.findViewById(R.id.lv_chat);
        mEtMsg = (EditText) mView.findViewById(R.id.et_msg);
        mEtMsg.setOnKeyListener(this);
        mBtnSend = (Button) mView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updatechat();
            }
        }, 0, 2000);

        return mView;
    }

    private void updatechat() {

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonObject = new JSONObject(new Web().execute("http://galgeleg.dk/GalgelegWeb/AndroidServlet?type=chat&timestamp=" + timestamp).get());
            jsonArray = jsonObject.getJSONArray("messages");
        } catch (ExecutionException | InterruptedException | JSONException e) {

        }

        messages = new String[jsonArray.length()];
        names = new String[jsonArray.length()];
        times = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
                names[i] = object.getString("name");
                messages[i] = object.getString("msg");
                Timestamp stamp = new Timestamp(object.getInt("timestamp"));
                Date date = new Date(stamp.getTime());
                times[i] = date.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        timestamp += 2;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new ArrayAdapter(getContext(), R.layout.chat_element, R.id.chat_element_name, names) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView chatElementMsg = (TextView) view.findViewById(R.id.chat_element_msg);
                        TextView chatElementTime = (TextView) view.findViewById(R.id.chat_element_time);

                        chatElementMsg.setText(messages[position]);
                        chatElementTime.setText(times[position]);

                        return view;
                    }
                };
                mLvChat.setAdapter(mAdapter);
                mLvChat.setSelection(mLvChat.getCount() - 1);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.cancel();
        mTimer.purge();
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSend) {
            String msg = mEtMsg.getText().toString();
            try {
                mGalgelogikCalls.sendMsg(User.sid, msg);
            } catch (SQLException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mEtMsg.setText("");
            mEtMsg.requestFocus();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            mBtnSend.callOnClick();
        }
        return false;
    }
}
