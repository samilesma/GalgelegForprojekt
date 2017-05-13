package com.u_09.galgeleg.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.u_09.galgeleg.Model.GalgelogikFunc;
import com.u_09.galgeleg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HighScoreFragment extends Fragment implements View.OnClickListener {

    SharedPreferences myPrefs;
    ArrayAdapter mAdapter;
    String[] mNames = {};
    Integer[] mWrongs = {};
    Integer[] mTime = {};

    ListView mListView;
    Button mBtnClearAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.highscore_fragment, container, false);

        mListView = (ListView) rod.findViewById(R.id.highscore_list);
        mBtnClearAll = (Button) rod.findViewById(R.id.button_clear_all);

        mBtnClearAll.setOnClickListener(this);

        // myPrefs = this.getActivity().getSharedPreferences("highscorePrefs", Context.MODE_PRIVATE);

        try {
            fillHighscoreList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rod;
    }

    private void fillHighscoreList() throws JSONException {

        JSONArray array = null;
        try {
            array = new GalgelogikFunc().getHighscores();
        } catch (JSONException | InterruptedException| ExecutionException e) {
            e.printStackTrace();
        }
        if(array.length() > 0) {
            mNames = new String[array.length()];
            mWrongs = new Integer[array.length()];
            mTime = new Integer[array.length()];
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                mNames[i] = object.getString("fullname");
                mWrongs[i] = object.getInt("wrong");
                mTime[i] = object.getInt("time");
                Log.d("Array", "Index: " + i + array.getJSONObject(i));
                Log.d("Array", "Value fullname: " + object.getString("fullname"));
                Log.d("Array", "Value wrong: " + object.getInt("wrong"));
                Log.d("Array", "Value time: " + object.getInt("time"));
                Log.d("Array", "Value date: " + object.getString("date"));
            }
        }
        mAdapter = new ArrayAdapter(getContext(), R.layout.highscore_element, R.id.highscore_element_wrong, mWrongs) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView highscoreElementName = (TextView) view.findViewById(R.id.highscore_element_name);
                TextView highscoreElementTime = (TextView) view.findViewById(R.id.highscore_element_time);

                highscoreElementName.setText(mNames[position].toString());
                highscoreElementTime.setText(mTime[position].toString());

                ImageView highscoreIcon = (ImageView) view.findViewById(R.id.highscore_icon);
                if (position == 0) {
                    highscoreIcon.setImageResource(R.drawable.gold);
                } else if (position == 1) {
                    highscoreIcon.setImageResource(R.drawable.silver);
                } else if (position == 2) {
                    highscoreIcon.setImageResource(R.drawable.bronze);
                } else highscoreIcon.setImageResource(R.drawable.empty);

                return view;
            }
        };

        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnClearAll) {

            Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.button_clear_all), "Er du sikker?", Snackbar.LENGTH_LONG).setAction("Ja", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar1 = Snackbar.make(getView().findViewById(R.id.button_clear_all), "Ryddet!", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    // myPrefs.edit().clear().apply();
                    // fillHighscoreList();
                }
            });
            snackbar.show();

        }
    }
}
