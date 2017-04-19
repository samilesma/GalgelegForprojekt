package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.u_09.galgeleg.R;

/**
 * Created by Tolga on 19-04-2017.
 */

public class ForsideFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private EditText mEtBruger, mEtKode;
    private Button mBtnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.forside_fragment, container, false);

        mEtBruger = (EditText) mView.findViewById(R.id.et_brugernavn);
        mEtKode = (EditText) mView.findViewById(R.id.et_kode);
        mBtnLogin = (Button) mView.findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnLogin){
            
        }
    }
}
