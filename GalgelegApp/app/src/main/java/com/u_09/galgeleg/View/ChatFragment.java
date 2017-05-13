package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikFunc;
import com.u_09.galgeleg.Model.Web;
import com.u_09.galgeleg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

//import com.u_09.galgeleg.Model.Function;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class ChatFragment extends Fragment implements View.OnClickListener {

    String[] messages;
    String[] names;
    private View mView;
    private ListView mLvChat;
    private ArrayAdapter mAdapter;
    private EditText mEtMsg;
    private Button mBtnSend;
    private GalgelogikFunc mGalgelogikFunc;
    private GalgelogikFunc function = new GalgelogikFunc();
    private long timestamp = System.currentTimeMillis() / 1000L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.chat_fragment, container, false);

        mGalgelogikFunc = new GalgelogikFunc();

        mLvChat = (ListView) mView.findViewById(R.id.lv_chat);
        mEtMsg = (EditText) mView.findViewById(R.id.et_msg);
        mBtnSend = (Button) mView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    updatechat();
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000);

        return mView;
    }

    private void updatechat() throws ExecutionException, InterruptedException, JSONException {

        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonObject = new JSONObject(new Web().execute("http://galgeleg.dk/GalgelegWeb/AndroidServlet?type=chat&timestamp=" + timestamp).get());
        jsonArray = jsonObject.getJSONArray("messages");

        messages = new String[jsonArray.length()];
        names = new String[jsonArray.length()];
        for (int i = 0; i > jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            messages[i] = object.getString("msg");
            names[i] = object.getString("name");
        }

        timestamp += 5;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new ArrayAdapter(getContext(), R.layout.chat_element, R.id.chat_element_name, names) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView chatElementMsg = (TextView) view.findViewById(R.id.chat_element_msg);
                        chatElementMsg.setText(messages[position]);

                        return view;
                    }
                };
                mLvChat.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSend) {
            String message = mEtMsg.getText().toString();
        }
    }
}
