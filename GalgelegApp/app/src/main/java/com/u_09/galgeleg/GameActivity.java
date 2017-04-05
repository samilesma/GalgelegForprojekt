package com.u_09.galgeleg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private MainMenuFragment mMainMenuFragment;
    private GallowGameFragment mGallowGameFragment;
    private ChooseWordPopup mChooseWordPopup;
    private HighScoreFragment mHighScoreFragment;
    private HelpFragment mHelpFragment;
    private Galgelogik mGalgelogik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        mMainMenuFragment = new MainMenuFragment();
        mGallowGameFragment = new GallowGameFragment();
        mChooseWordPopup = new ChooseWordPopup();
        mHighScoreFragment = new HighScoreFragment();
        mHelpFragment = new HelpFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, mMainMenuFragment).commit();
        }

        mGalgelogik = new Galgelogik();
        hentOrd();

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    private void hentOrd() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    mGalgelogik.hentOrdFraDr();
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
                        Log.d("MULIGE ORD: ", "" + mGalgelogik.getMuligeOrd());
                    } else {
                        Log.d("MULIGE ORD FEJL: ", resultat.toString());
                    }
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_main_menu) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mMainMenuFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_game) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mChooseWordPopup).addToBackStack(null).commit();
        } else if (id == R.id.action_highscore) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHighScoreFragment).addToBackStack(null).commit();
        } else if (id == R.id.action_help) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHelpFragment).addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOrdetFromActivity(int i) {
        mGalgelogik.setOrdet(i);
    }

    public ArrayList<String> getMuligeOrdFromActivity() {
        return mGalgelogik.getMuligeOrd();
    }

    public Galgelogik getmGalgelogik() {
        return mGalgelogik;
    }

    public void setSynligtOrdFromActivity() {
        mGalgelogik.opdaterSynligtOrd();
    }
}
