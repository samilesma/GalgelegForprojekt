package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikFunc;
import com.u_09.galgeleg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Tolga on 19-04-2017.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private View mView;
    private TextView mTvUsername, mTvPassword;
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin;

    private GalgelogikFunc mGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.sign_in_fragment, container, false);

        mGame = new GalgelogikFunc();

        mTvUsername = (TextView) mView.findViewById(R.id.tv_username);
        mEtUsername = (EditText) mView.findViewById(R.id.et_username);
        mTvPassword = (TextView) mView.findViewById(R.id.tv_password);
        mEtPassword = (EditText) mView.findViewById(R.id.et_password);
        mBtnLogin = (Button) mView.findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
        mEtUsername.setOnKeyListener(this);
        mEtPassword.setOnKeyListener(this);

        return mView;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            mBtnLogin.callOnClick();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnLogin) {
            try {
                String username = mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                Snackbar.make(mView, "Logger ind...", Snackbar.LENGTH_INDEFINITE).show();
                JSONObject user = mGame.hentBruger(username, password);
                if(user != null) {
                    boolean error = user.getBoolean("error");
                    Log.d("XXXX", "" + error);
                    if (!error) {
                        String name = user.getString("fullname");
                        User.setUser(username, name);
                        Snackbar.make(mView, "Du er logget ind", Snackbar.LENGTH_SHORT).show();
                        mTvUsername.setVisibility(View.GONE);
                        mEtUsername.setVisibility(View.GONE);
                        mTvPassword.setVisibility(View.GONE);
                        mEtPassword.setVisibility(View.GONE);
                        mBtnLogin.setVisibility(View.GONE);
                        TextView tvSpiller = (TextView) getActivity().findViewById(R.id.tv_spiller);
                        tvSpiller.setText("Spiller: " + User.fullname + " " + User.sid);
                        MainMenuFragment mainMenuFragment = new MainMenuFragment();
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mainMenuFragment).addToBackStack(null).commit();
                    } else {
                        Snackbar.make(mView, "Login mislykket, forkerte oplysninger", Snackbar.LENGTH_SHORT).show();
                        mEtUsername.setText("");
                        mEtPassword.setText("");
                        mEtUsername.requestFocus();
                    }
                }
                else {
                    Snackbar.make(mView, "Login fejlet, kommunikationsfejl", Snackbar.LENGTH_SHORT).show();
                    mEtUsername.setText("");
                    mEtPassword.setText("");
                    mEtUsername.requestFocus();
                }
            } catch (NullPointerException | InterruptedException | ExecutionException | JSONException e) {
                Snackbar.make(mView, "Kommunikationsfejl: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                mEtUsername.setText("");
                mEtPassword.setText("");
                mEtUsername.requestFocus();
            }
        }
    }
}
