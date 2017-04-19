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
import android.widget.TextView;

import com.u_09.galgeleg.R;

import java.util.ArrayList;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class ChatFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private View mView;
    private ListView mLvChat;
    private EditText mEtMsg;
    private Button mBtnSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.chat_fragment, container, false);

        mLvChat = (ListView) mView.findViewById(R.id.lv_chat);
        mEtMsg = (EditText) mView.findViewById(R.id.et_msg);
        mBtnSend = (Button) mView.findViewById(R.id.btn_send);

        mBtnSend.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }
}
