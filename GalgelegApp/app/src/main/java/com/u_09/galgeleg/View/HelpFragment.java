package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u_09.galgeleg.R;

public class HelpFragment extends Fragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.help_fragment, container, false);

        return mView;
    }

}
