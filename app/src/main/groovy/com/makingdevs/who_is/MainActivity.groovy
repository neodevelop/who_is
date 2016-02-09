package com.makingdevs.who_is

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity extends Activity {

    static String LOG_TAG = MainActivity.class.simpleName
    static String KEY_IMAGE_INDEX =  "key_image_index"
    static String KEY_NAME_INDEX =  "key_name_index"
    static int REQUEST_CHEAT = 100

    private FloatingActionButton mTrueButton
    private FloatingActionButton mFalseButton
    private FloatingActionButton mCheatButton
    private ImageView mCharacterPhoto
    private TextView mTextPlaceholder
    private TextView mScore
    private Character[] mBankCharacter = [
            new Character(R.string.woody, R.drawable.character1),
            new Character(R.string.minion, R.drawable.character2),
            new Character(R.string.monalisa, R.drawable.character3)
    ]

    private int mCurrentCharacterImage = 0
    private int mCurrentCharacterName = 0
    private Random mRandom
    private int score = 0
    private isUserCheater

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d LOG_TAG, "onCreate"

        mFalseButton = (FloatingActionButton) findViewById(R.id.button_false)
        mTrueButton = (FloatingActionButton) findViewById(R.id.button_true)
        mCheatButton = (FloatingActionButton) findViewById(R.id.button_cheat)
        mCharacterPhoto = (ImageView) findViewById(R.id.character_photo)
        mTextPlaceholder = (TextView) findViewById(R.id.text_placeholder)
        mScore= (TextView) findViewById(R.id.text_score)
        String textScore = String.format(getString(R.string.score),0)
        mScore.setText(textScore)

        mRandom = new Random()

        if(savedInstanceState){
            mCurrentCharacterImage = savedInstanceState.getInt(KEY_IMAGE_INDEX)
            mCurrentCharacterName = savedInstanceState.getInt(KEY_NAME_INDEX)

            int nameResId = mBankCharacter[mCurrentCharacterName].nameResId
            int imageResId = mBankCharacter[mCurrentCharacterImage].imageResId

            mCharacterPhoto.imageResource = imageResId
            mTextPlaceholder.text = nameResId
        } else {
            mCurrentCharacterImage = mRandom.nextInt(mBankCharacter.length)
            mCurrentCharacterName = mRandom.nextInt(mBankCharacter.length)
        }

        mFalseButton.onClickListener = { View viewClicked ->
            characterUpdate()
            //if(viewClicked.getId() == R.id.button_false)
            //    Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        } as View.OnClickListener

        mTrueButton.onClickListener = { View viewClicked ->
            characterUpdate()
            checkAnswer(true)
        } as View.OnClickListener

        mCheatButton.onClickListener = { View viewClicked ->
            launchCheatActivity()
        } as View.OnClickListener

        characterUpdate()
    }

    private void characterUpdate(){

        int nameResId = mBankCharacter[mCurrentCharacterName].nameResId
        int imageResId = mBankCharacter[mCurrentCharacterImage].imageResId

        mCharacterPhoto.imageResource = imageResId
        mTextPlaceholder.text = nameResId
    }

    private void checkAnswer(Boolean answer){
        boolean isCorrect = answer == getCorrectAnswer()

        if(isUserCheater && isCorrect) {
            Toast.makeText(this, R.string.user_is_cheat, Toast.LENGTH_SHORT).show()
            isUserCheater = false
        } else
            Toast.makeText(this, isCorrect ? R.string.correct : R.string.incorrect, Toast.LENGTH_SHORT).show()

        if(isCorrect){
            score++
            String textScore = String.format(getString(R.string.score),score)
            mScore.text = textScore
        }

        mCurrentCharacterImage = mRandom.nextInt(mBankCharacter.length)
        mCurrentCharacterName = mRandom.nextInt(mBankCharacter.length)
    }

    private boolean getCorrectAnswer(){
        println "Check answer $mCurrentCharacterImage == $mCurrentCharacterName"
        mCurrentCharacterImage == mCurrentCharacterName
    }

    @Override
    protected void onStart() {
        super.onStart()
        Log.d LOG_TAG, "onStart"
    }

    @Override
    protected void onRestart() {
        super.onRestart()
        Log.d LOG_TAG, "onRestart"
    }

    @Override
    protected void onResume() {
        super.onResume()
        Log.d LOG_TAG, "onResume"
    }

    @Override
    protected void onPause() {
        super.onPause()
        Log.d LOG_TAG, "onPause"
    }

    @Override
    protected void onStop() {
        super.onStop()
        Log.d LOG_TAG, "onStop"
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        Log.d LOG_TAG, "onDestroy"
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_IMAGE_INDEX, mCurrentCharacterImage)
        outState.putInt(KEY_NAME_INDEX, mCurrentCharacterName)
    }

    private void launchCheatActivity(){
        Intent cheatIntent = CheatActivity.newIntent(this, getCorrectAnswer())
        startActivityForResult(cheatIntent, REQUEST_CHEAT)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CANCELED){
            Log.d LOG_TAG, "El usuario cancelo"
        }
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHEAT){
            isUserCheater = data.getBooleanExtra(CheatActivity.EXTRA_SHOWN_ANSWER, false)
        }
    }
}
