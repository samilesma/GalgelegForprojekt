package com.u_09.galgeleg.View;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.u_09.galgeleg.Model.GalgelogikFunc;
import com.u_09.galgeleg.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GallowGameFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private SharedPreferences mPrefs;

    private GalgelogikFunc mGame;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private String mGuess;
    private View mView;
    private ImageSwitcher mImageSwitcher;
    private TextView mTvTitle, mTvTheWordIs, mTvTheWord, mTvGuessedLetters, mTvWrongLetters;
    private EditText mEtLetter, mEtName;
    private AlertDialog.Builder mSaveHighscoreDialog;
    private Button mBtnGuess, mBtnNewWord;
    private ProgressBar mProgressBar1;

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
        mProgressBar1 = (ProgressBar) mView.findViewById(R.id.progressbar1);

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
            // Snackbar.make(mView, "Henter ord...", Snackbar.LENGTH_SHORT).show();
            try {
                newGame();
            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }
            mEtLetter.requestFocus();
        }

        return mView;
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnGuess) {
            mProgressBar1.setVisibility(View.VISIBLE);
            mGuess = mEtLetter.getText().toString();
            String guessInfo;
            try {
                if (mGame.getBrugteBogstaver(User.sid).contains(mGuess)) guessInfo = "Allerede brugt!";
                else if (mGame.getOrdet(User.sid).contains(mGuess)) guessInfo = "Super! Gættet var korrekt!";
                else guessInfo = "Øv! Forkert gæt!";
                mGame.gætBogstav(mGuess, User.sid);
                updateUIOnGuess();
                mProgressBar1.setVisibility(View.INVISIBLE);
                Snackbar.make(mView, guessInfo, Snackbar.LENGTH_SHORT).show();
            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }
        } else if (v == mBtnNewWord) {
            // getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_pop, R.anim.slide_out_pop).replace(R.id.fragment_content, new ChooseWordPopupFragment()).addToBackStack(null).commit();
            try {
                newGame();
            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            mBtnGuess.callOnClick();
        }
        return false;
    }

    public void updateUIOnGuess() throws InterruptedException, ExecutionException, JSONException {
        mProgressBar1.setVisibility(View.VISIBLE);
        mEtLetter.setText("");
        mEtLetter.requestFocus();
        mTvTheWord.setText(mGame.getSynligtOrd(User.sid));
        mTvGuessedLetters.setText(mGame.getBrugteBogstaver(User.sid).toString());
        mTvWrongLetters.setText(mGame.getAntalForkerteBogstaver(User.sid) + "/7");

        if (mGame.getAntalForkerteBogstaver(User.sid) != 0)
            mImageSwitcher.setImageResource(mImages.get(mGame.getAntalForkerteBogstaver(User.sid) - 1));

        if (mGame.erSpilletVundet(User.sid) || mGame.erSpilletTabt(User.sid)) {
            String title;
            if (mGame.erSpilletVundet(User.sid)) {
                title = "Tillykke! Du har vundet spillet!";
                mTvTitle.setTextColor(getResources().getColor(R.color.green));
                mImageSwitcher.setImageResource(R.drawable.vundet);
            } else {
                title = "Øv! Du har tabt spillet!";
                mTvTitle.setTextColor(getResources().getColor(R.color.red));
                mTvTheWordIs.setText(R.string.ordet_var);
                mTvTheWord.setText(mGame.getOrdet(User.sid));
            }

            mTvTitle.setText(title);
            mTvTitle.setVisibility(View.VISIBLE);
            mProgressBar1.setVisibility(View.INVISIBLE);

            mEtName = new EditText(getContext());
            mSaveHighscoreDialog = new AlertDialog.Builder(getContext()).setTitle("Skriv dit navn for at gemme din score").setMessage("Navn:").setView(mEtName).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String playerName = mEtName.getText().toString();
                    try {
                        mPrefs.edit().putInt(playerName, mGame.getAntalForkerteBogstaver(User.sid)).apply();
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).setNegativeButton("Annullér", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            mSaveHighscoreDialog.show();
        }
    }

    public void newGame() throws InterruptedException, ExecutionException, JSONException {
        mProgressBar1.setVisibility(View.VISIBLE);
        mImageSwitcher.setImageResource(R.drawable.galge);
        refillImageArray();
        mTvTitle.setVisibility(View.INVISIBLE);
        mTvTitle.setText("");
        mTvTheWordIs.setText(R.string.ordet_er);
        mTvTheWord.setText(mGame.getSynligtOrd(User.sid));
        mGame.getBrugteBogstaver(User.sid).clear();
        // mGame.setAntalForkerteBogstaver(0);

        mTvGuessedLetters.setText("[]");
        mTvWrongLetters.setText("0/7");
        mEtLetter.setText("");
        Log.d("ORDET ER: ", "" + mGame.getOrdet(User.sid));
        mProgressBar1.setVisibility(View.INVISIBLE);
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
