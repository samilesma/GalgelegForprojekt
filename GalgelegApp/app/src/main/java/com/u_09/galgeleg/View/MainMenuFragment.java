package com.u_09.galgeleg.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.u_09.galgeleg.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextView mTvTitle;
    private Button mBtnPlayGame, mBtnHighScore, mBtnChat, mBtnHelp;
    private GallowGameFragment mGallowGameFragment;
    private HighScoreFragment mHighScoreFragment;
    private ChatFragment mChatFragment;
    private AdminFragment mAdminFragment;
    private HelpFragment mHelpFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        mGallowGameFragment = new GallowGameFragment();
        mHighScoreFragment = new HighScoreFragment();
        mChatFragment = new ChatFragment();
        mHelpFragment = new HelpFragment();
        mAdminFragment = new AdminFragment();

        mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
        mTvTitle.setText("Velkommen til Galgespillet \n" + User.fullname);
        mBtnPlayGame = (Button) mView.findViewById(R.id.button_play_game);
        mBtnHighScore = (Button) mView.findViewById(R.id.button_highscore);
        mBtnChat = (Button) mView.findViewById(R.id.button_chat);
        mBtnHelp = (Button) mView.findViewById(R.id.button_help);

        mBtnPlayGame.setOnClickListener(this);
        mBtnHighScore.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);
        mBtnHelp.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {

        if (v == mBtnPlayGame) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mGallowGameFragment).addToBackStack(null).commit();
        } else if (v == mBtnHighScore) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHighScoreFragment).addToBackStack(null).commit();
        } else if (v == mBtnChat) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mChatFragment).addToBackStack(null).commit();
        } else if (v == mBtnHelp) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, mHelpFragment).addToBackStack(null).commit();
        }
    }

}
