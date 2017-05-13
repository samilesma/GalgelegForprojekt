package com.u_09.galgeleg.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikCalls;
import com.u_09.galgeleg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Tolga on 19-04-2017.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private TextView mTvUsername, mTvPassword;
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin;
    private ProgressDialog mProgressDialog;

    private GalgelogikCalls mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_fragment);
        mGame = new GalgelogikCalls();

        mTvUsername = (TextView) findViewById(R.id.tv_username);
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mTvPassword = (TextView) findViewById(R.id.tv_password);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mProgressDialog = new ProgressDialog(this);

        mBtnLogin.setOnClickListener(this);
        mEtUsername.setOnKeyListener(this);
        mEtPassword.setOnKeyListener(this);
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
                JSONObject user = mGame.hentBruger(username, password);
                if (user != null) {
                    boolean error = user.getBoolean("error");
                    Log.d("XXXX", "" + error);
                    if (!error) {
                        String name = user.getString("fullname");
                        User.setUser(username, name);
                        Snackbar.make(mBtnLogin, "Du er logget ind", Snackbar.LENGTH_INDEFINITE).show();
                        finish();
                        Intent intent = new Intent(this, GameActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Snackbar.make(mBtnLogin, "Login mislykket, forkerte oplysninger", Snackbar.LENGTH_SHORT).show();
                        mEtUsername.setText("");
                        mEtPassword.setText("");
                        mEtUsername.requestFocus();
                    }
                } else {
                    Snackbar.make(mBtnLogin, "Login fejlet, kommunikationsfejl", Snackbar.LENGTH_SHORT).show();
                    mEtUsername.setText("");
                    mEtPassword.setText("");
                    mEtUsername.requestFocus();
                }
            } catch (NullPointerException | InterruptedException | ExecutionException | JSONException e) {
                Snackbar.make(mBtnLogin, "Fejl: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.d("ERROR", "" + e.getMessage());
                e.printStackTrace();
                mEtUsername.setText("");
                mEtPassword.setText("");
                mEtUsername.requestFocus();
            }
        }
    }
}
