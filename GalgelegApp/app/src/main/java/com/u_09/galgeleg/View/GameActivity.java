package com.u_09.galgeleg.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikCalls;
import com.u_09.galgeleg.R;

public class GameActivity extends AppCompatActivity {

    private MainMenuFragment mMainMenuFragment;
    private GallowGameFragment mGallowGameFragment;
    private HighScoreFragment mHighScoreFragment;
    private ChatFragment mChatFragment;
    private HelpFragment mHelpFragment;
    private GalgelogikCalls mGalgelogikCalls;
    TextView mTvSpiller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        mMainMenuFragment = new MainMenuFragment();
        mGallowGameFragment = new GallowGameFragment();
        mHighScoreFragment = new HighScoreFragment();
        mChatFragment = new ChatFragment();
        mHelpFragment = new HelpFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, mMainMenuFragment).commit();
        }

        mTvSpiller = (TextView) findViewById(R.id.tv_spiller);
        mTvSpiller.setText("Spiller: " + User.fullname + " " + User.sid);

        mGalgelogikCalls = new GalgelogikCalls();
        hentOrd();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        } else {
            super.onBackPressed();
        }


    }

    public void hentOrd() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    mGalgelogikCalls.hentOrdFraDr();
                    return "Succes: Ordene blev hentet fra DR's server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Fejl: Ordene blev ikke hentet korrekt: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                if (resultat != null) {
                    if (resultat.toString().contains("Succes")) {
                        Log.d("MULIGE ORD SUCCESS: ", resultat.toString());
                        /*try {
                            Log.d("MULIGE ORD: ", "" + mGalgelogikCalls.getMuligeOrd(User.sid));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    } else {
                        Log.d("MULIGE ORD FEJL: ", resultat.toString());
                    }
                }
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_main_menu) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mMainMenuFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_game) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mGallowGameFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_highscore) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHighScoreFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_chat) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mChatFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_help) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHelpFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_sign_out) {
            signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public GalgelogikCalls getmGalgelogikCalls() {
        return mGalgelogikCalls;
    }

    public void signOut() {
        User.setUser(null, null);
        mTvSpiller.setText(R.string.tv_spiller);
        finish();
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
