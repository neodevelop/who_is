package com.makingdevs.who_is

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class CheatActivity extends Activity {

    Button mButtonShowAnswer
    TextView mAnswerText
    boolean mCorrectAnswer
    static final String EXTRA_ANSWER = "extra_answer"
    static final String EXTRA_SHOWN_ANSWER = "extra_shown_answer"

    static Intent newIntent(Context context, boolean answer){
        Intent cheatIntent = new Intent(context, CheatActivity)
        cheatIntent.putExtra(EXTRA_ANSWER, answer)
        cheatIntent
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        obtainCorrectAnswer()

        mButtonShowAnswer = findViewById(R.id.button_show_answer)
        mAnswerText = findViewById(R.id.text_answer)

        mButtonShowAnswer.onClickListener = { View view ->
            answerWasShowed()
            mAnswerText.text = mCorrectAnswer ? R.string.label_true : R.string.label_false
        } as View.OnClickListener
    }

    private void obtainCorrectAnswer() {
        mCorrectAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, false)
    }

    private void answerWasShowed(){
        Intent resultIntent = new Intent()
        resultIntent.putExtra(EXTRA_SHOWN_ANSWER, true)
        setResult(RESULT_OK, resultIntent)
    }

    @Override
    void onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED, null)
    }
}
