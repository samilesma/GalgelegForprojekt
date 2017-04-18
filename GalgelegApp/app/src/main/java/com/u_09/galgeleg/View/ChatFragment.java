package com.u_09.galgeleg.View;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.u_09.galgeleg.R;

import java.util.ArrayList;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class ChatFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.chat_fragment, container, false);


        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
