package com.u_09.galgeleg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Button mBtnPlayGame, mBtnHighScore, mBtnHelp, mBtnTest;
    private ChooseWordPopup mChooseWordPopup;
    private HighScoreFragment mHighScoreFragment;
    private HelpFragment mHelpFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        mChooseWordPopup = new ChooseWordPopup();
        mHighScoreFragment = new HighScoreFragment();
        mHelpFragment = new HelpFragment();

        mBtnPlayGame = (Button) mView.findViewById(R.id.button_play_game);
        mBtnHighScore = (Button) mView.findViewById(R.id.button_highscore);
        mBtnHelp = (Button) mView.findViewById(R.id.button_help);

        mBtnPlayGame.setOnClickListener(this);
        mBtnHighScore.setOnClickListener(this);
        mBtnHelp.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {

        if (v == mBtnPlayGame) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mChooseWordPopup).addToBackStack(null).commit();
        } else if (v == mBtnHighScore) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHighScoreFragment).addToBackStack(null).commit();
        } else if (v == mBtnHelp) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHelpFragment).addToBackStack(null).commit();
        }
    }

}
