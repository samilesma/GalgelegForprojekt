package com.u_09.galgeleg;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class GallowGameFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private SharedPreferences mPrefs;

    private Galgelogik mGame;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private String mGuess;

    private View mView;
    private ImageSwitcher mImageSwitcher;
    private TextView mTvTitle, mTvTheWordIs, mTvTheWord, mTvGuessedLetters, mTvWrongLetters;
    private EditText mEtLetter, mEtName;
    private AlertDialog.Builder mSaveHighscoreDialog;
    private Button mBtnGuess, mBtnNewWord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.gallow_game_fragment, container, false);

        mTvTitle = (TextView) mView.findViewById(R.id.textview_title);
        mImageSwitcher = (ImageSwitcher) mView.findViewById(R.id.imageSwitcher);
        mTvTheWordIs = (TextView) mView.findViewById(R.id.textview_the_word_is);
        mTvTheWord = (TextView) mView.findViewById(R.id.textview_the_word);
        mTvGuessedLetters = (TextView) mView.findViewById(R.id.textview_guessed_letters);
        mTvWrongLetters = (TextView) mView.findViewById(R.id.textview_wrong_letters);
        mEtLetter = (EditText) mView.findViewById(R.id.editText_letter);
        mBtnGuess = (Button) mView.findViewById(R.id.button_guess);
        mBtnNewWord = (Button) mView.findViewById(R.id.button_new_word);

        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        mImageSwitcher.setInAnimation(in);
        mImageSwitcher.setOutAnimation(out);

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        mBtnGuess.setOnClickListener(this);
        mBtnNewWord.setOnClickListener(this);
        mEtLetter.setOnKeyListener(this);

        if (savedInstanceState == null) {
            mPrefs = this.getActivity().getSharedPreferences("highscorePrefs", Context.MODE_PRIVATE);
            refillImageArray();
            mGame = ((GameActivity) getActivity()).getmGalgelogik();
            newGame();
            mEtLetter.requestFocus();
        }

        return mView;
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnGuess) {
            mGuess = mEtLetter.getText().toString();
            String guessInfo;
            if (mGame.getBrugteBogstaver().contains(mGuess)) guessInfo = "Allerede brugt!";
            else if (mGame.getOrdet().contains(mGuess)) guessInfo = "Super! Gættet var korrekt!";
            else guessInfo = "Øv! Forkert gæt!";
            Snackbar.make(mView, guessInfo, Snackbar.LENGTH_SHORT).show();
            mGame.gætBogstav(mGuess);
            updateUIOnGuess();
        } else if (v == mBtnNewWord) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, new ChooseWordPopup()).addToBackStack(null).commit();
            newGame();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            mBtnGuess.callOnClick();
        }
        return false;
    }

    public void updateUIOnGuess() {
        mEtLetter.setText("");
        mEtLetter.requestFocus();
        mTvTheWord.setText(mGame.getSynligtOrd());
        mTvGuessedLetters.setText(mGame.getBrugteBogstaver().toString());
        mTvWrongLetters.setText(mGame.getAntalForkerteBogstaver() + "/7");

        if (mGame.getAntalForkerteBogstaver() != 0)
            mImageSwitcher.setImageResource(mImages.get(mGame.getAntalForkerteBogstaver() - 1));

        if (mGame.erSpilletVundet() || mGame.erSpilletTabt()) {
            String title;
            if (mGame.erSpilletVundet()) {
                title = "Tillykke! Du har vundet spillet!";
                mTvTitle.setTextColor(getResources().getColor(R.color.green));
                mImageSwitcher.setImageResource(R.drawable.vundet);
            } else {
                title = "Øv! Du har tabt spillet!";
                mTvTitle.setTextColor(getResources().getColor(R.color.red));
                mTvTheWordIs.setText(R.string.ordet_var);
                mTvTheWord.setText(mGame.getOrdet());
            }

            mTvTitle.setText(title);
            mTvTitle.setVisibility(View.VISIBLE);

            mEtName = new EditText(getContext());
            mSaveHighscoreDialog = new AlertDialog.Builder(getContext()).setTitle("Skriv dit navn for at gemme din score").setMessage("Navn:").setView(mEtName).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String playerName = mEtName.getText().toString();
                    mPrefs.edit().putInt(playerName, mGame.getAntalForkerteBogstaver()).apply();
                }
            }).setNegativeButton("Annullér", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            mSaveHighscoreDialog.show();
        }
    }

    public void newGame() {

        mImageSwitcher.setImageResource(R.drawable.galge);
        refillImageArray();
        mTvTitle.setVisibility(View.INVISIBLE);
        mTvTitle.setText("");
        mTvTheWordIs.setText(R.string.ordet_er);
        mTvTheWord.setText(mGame.getSynligtOrd());

        mTvGuessedLetters.setText("[]");
        mTvWrongLetters.setText("0/7");
        mEtLetter.setText("");
        Log.d("ORDET ER: ", "" + mGame.getOrdet());
    }

    public void refillImageArray() {

        mImages.clear();
        mImages.trimToSize();
        mImages.add(R.drawable.forkert1);
        mImages.add(R.drawable.forkert2);
        mImages.add(R.drawable.forkert3);
        mImages.add(R.drawable.forkert4);
        mImages.add(R.drawable.forkert5);
        mImages.add(R.drawable.forkert6);
        mImages.add(R.drawable.tabt);
    }
}
