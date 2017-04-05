package com.u_09.galgeleg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HighScoreFragment extends Fragment implements View.OnClickListener {

    SharedPreferences myPrefs;
    ArrayAdapter adapter;
    String[] names = {};
    Integer[] scores = {};

    ListView listView;
    Button buttonClearAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.highscore_fragment, container, false);

        listView = (ListView) rod.findViewById(R.id.highscore_list);
        buttonClearAll = (Button) rod.findViewById(R.id.button_clear_all);

        buttonClearAll.setOnClickListener(this);

        myPrefs = this.getActivity().getSharedPreferences("highscorePrefs", Context.MODE_PRIVATE);

        fillHighscoreList();

        return rod;
    }

    private void fillHighscoreList() {
        Map map = myPrefs.getAll();
        Map sortedMap = sortByValue(map);

        Object[] namesArray = sortedMap.keySet().toArray();
        Object[] scoresArray = sortedMap.values().toArray();
        names = Arrays.copyOf(namesArray, namesArray.length, String[].class);
        scores = Arrays.copyOf(scoresArray, scoresArray.length, Integer[].class);

        adapter = new ArrayAdapter(getContext(), R.layout.highscore_element, R.id.highscore_element_score, scores) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView highscoreElementName = (TextView) view.findViewById(R.id.highscore_element_name);
                highscoreElementName.setText(names[position].toString());

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

        listView.setAdapter(adapter);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonClearAll) {

            Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.button_clear_all), "Er du sikker?", Snackbar.LENGTH_LONG).setAction("Ja", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar1 = Snackbar.make(getView().findViewById(R.id.button_clear_all), "Ryddet!", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    myPrefs.edit().clear().apply();
                    fillHighscoreList();
                }
            });
            snackbar.show();

        }
    }
}
