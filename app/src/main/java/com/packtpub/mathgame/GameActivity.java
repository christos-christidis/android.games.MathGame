package com.packtpub.mathgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener {

    private final Button[] buttons = new Button[3];
    private int correctAnswer;
    private int currentScore = 0;
    private int currentLevel = 1;

    private interface Function {
        int apply(int a, int b);

        String getSymbol();
    }

    private final static Function[] functions = {
            new Function() {
                @Override
                public String getSymbol() {
                    return "+";
                }

                @Override
                public int apply(int a, int b) {
                    return a + b;
                }
            },
            new Function() {
                @Override
                public String getSymbol() {
                    return "x";
                }

                @Override
                public int apply(int a, int b) {
                    return a * b;
                }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        VisibilityManager.hideSystemUI(this);

        buttons[0] = findViewById(R.id.buttonChoice1);
        buttons[1] = findViewById(R.id.buttonChoice2);
        buttons[2] = findViewById(R.id.buttonChoice3);

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }

        setQuestion();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            VisibilityManager.hideSystemUI(this);
        }
    }

    private void setQuestion() {
        int numberRange = currentLevel * 3;
        Random random = new Random();

        int partA = random.nextInt(numberRange) + 1;
        int partB = random.nextInt(numberRange) + 1;

        TextView textPartA = findViewById(R.id.textPartA);
        TextView textPartB = findViewById(R.id.textPartB);
        textPartA.setText(String.valueOf(partA));
        textPartB.setText(String.valueOf(partB));

        applyRandomFunction(partA, partB);

        assignAnswersToButtons();
    }

    private void applyRandomFunction(int partA, int partB) {
        Random random = new Random();
        int index = random.nextInt(functions.length);

        Function function = functions[index];
        correctAnswer = function.apply(partA, partB);

        TextView textOperator = findViewById(R.id.textOperator);
        textOperator.setText(function.getSymbol());
    }

    private void assignAnswersToButtons() {
        int wrongAnswer1 = correctAnswer - 2;
        int wrongAnswer2 = correctAnswer + 2;

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < buttons.length; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        // First random index is set to correct answer, the rest to the wrong ones
        int buttonIndex = indexes.get(0);
        buttons[buttonIndex].setText(String.valueOf(correctAnswer));

        buttonIndex = indexes.get(1);
        buttons[buttonIndex].setText(String.valueOf(wrongAnswer1));

        buttonIndex = indexes.get(2);
        buttons[buttonIndex].setText(String.valueOf(wrongAnswer2));
    }

    @Override
    public void onClick(View v) {
        int buttonNumber = 0;

        switch (v.getId()) {
            case R.id.buttonChoice1:
                buttonNumber = Integer.parseInt(buttons[0].getText().toString());
                break;
            case R.id.buttonChoice2:
                buttonNumber = Integer.parseInt(buttons[1].getText().toString());
                break;
            case R.id.buttonChoice3:
                buttonNumber = Integer.parseInt(buttons[2].getText().toString());
                break;
        }

        updateScoreAndLevel(buttonNumber);
    }

    private void updateScoreAndLevel(int answerGiven) {
        if (isCorrect(answerGiven)) {
            for (int i = 0; i <= currentLevel; i++) {
                currentScore += i;
            }

            currentLevel++;
        } else {
            currentScore = 0;
            currentLevel = 1;
        }

        TextView textScore = findViewById(R.id.textScore);
        TextView textLevel = findViewById(R.id.textLevel);

        textScore.setText(getString(R.string.text_score, currentScore));
        textLevel.setText(getString(R.string.text_level, currentLevel));

        String toastText = isCorrect(answerGiven) ?
                getString(R.string.toast_success) : getString(R.string.toast_failure);
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

        setQuestion();
    }

    private boolean isCorrect(int answerGiven) {
        return answerGiven == correctAnswer;
    }
}
