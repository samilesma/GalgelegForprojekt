package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u_09.galgeleg.R;

/**
 * Created by ibsenb on 18/04/2017.
 */

public class Admin_Fragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.highscore_fragment, container, false);

        return view;

    }

    @Override
    public void onClick(View v) {

        
    }

}
