package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.u_09.galgeleg.Model.Function;
import com.u_09.galgeleg.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class AdminFragment extends Fragment {

    private Spinner dropDown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment, container, false);
        Spinner dropdown = (Spinner) view.findViewById(R.id.dropDown);
        ArrayAdapter<String> adapter;
        ArrayList<String> userList = new ArrayList<>();
/*
        try {
            userList = function.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//*/
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, userList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        return view;

    }

}
