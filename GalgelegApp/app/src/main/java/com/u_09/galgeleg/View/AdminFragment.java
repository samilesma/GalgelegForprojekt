package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.u_09.galgeleg.R;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class AdminFragment extends Fragment implements View.OnClickListener {

    private Spinner dropDown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment, container, false);
        Spinner dropdown = (Spinner) view.findViewById(R.id.dropDown);
        ArrayAdapter<String> users = new ArrayAdapter<String>(AdminFragment.this, android.R.layout.simple_spinner_dropdown_item,items);
        return view;

    }

    @Override
    public void onClick(View v) {


    }

}
