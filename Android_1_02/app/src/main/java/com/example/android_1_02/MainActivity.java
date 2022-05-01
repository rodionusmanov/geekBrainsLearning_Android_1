package com.example.android_1_02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private boolean dotPushed = false;
    private TextView lineOne;
    private TextView lineTwo;
    private TextView lineThree;
    private TextView lineFour;
    private int calculatorStage = 0;
    CharSequence line = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineOne = findViewById(R.id.calculator_display1);
        lineTwo = findViewById(R.id.calculator_display2);
        lineThree = findViewById(R.id.calculator_display3);
        lineFour = findViewById(R.id.calculator_display4);

    }

    public void button0_onClick(View view) {
        checkStage("number");
        stageAction("0");
    }

    public void button1_onClick(View view) {
        checkStage("number");
        stageAction("1");
    }

    public void button2_onClick(View view) {
        checkStage("number");
        stageAction("2");
    }

    public void button3_onClick(View view) {
        checkStage("number");
        stageAction("3");
    }

    public void button4_onClick(View view) {
        checkStage("number");
        stageAction("4");
    }

    public void button5_onClick(View view) {
        checkStage("number");
        stageAction("5");
    }

    public void button6_onClick(View view) {
        checkStage("number");
        stageAction("6");
    }

    public void button7_onClick(View view) {
        checkStage("number");
        stageAction("7");
    }

    public void button8_onClick(View view) {
        checkStage("number");
        stageAction("8");
    }

    public void button9_onClick(View view) {
        checkStage("number");
        stageAction("9");
    }

    public void buttonDot_onClick(View view) {
        if (!dotPushed) {
            checkStage("number");
            stageAction(".");
        }
        dotPushed = true;
    }

    public void buttonClear_onClick(View view) {
        calculatorStage = 0;
        lineOne.setText("");
        lineTwo.setText("");
        lineThree.setText("");
        lineFour.setText("");
        dotPushed = false;
    }

    public void buttonDelete_onClick(View view) {
        if (calculatorStage == 1) {
            lineOne.setText("");
        } else if (lineOne.getText().length() > 0) {
            lineOne.setText(lineOne.getText().subSequence(0, lineOne.getText().length() - 1));
        }
    }

    public void buttonPlus_onClick(View view) {
        checkStage("symbol");
        stageAction("+");
    }

    public void buttonMinus_onClick(View view) {
        checkStage("symbol");
        stageAction("-");
    }

    public void buttonMultiply_onClick(View view) {
        checkStage("symbol");
        stageAction("x");
    }

    public void buttonDevide_onClick(View view) {
        checkStage("symbol");
        stageAction("/");
    }

    public void buttonEven_onClick(View view) {
        if (calculatorStage == 2) {
            lineFour.setText(lineThree.getText());
            lineThree.setText(lineTwo.getText());
            lineTwo.setText(lineOne.getText());
            lineOne.setText("");
            calculationSequence();
            calculatorStage = 3;
            dotPushed = false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void buttonPercent_onClick(View view) {
        BigDecimal argumentForPercent;
        switch (calculatorStage) {
            case (0):
                if (0 != lineOne.getText().length()) {
                    lineTwo.setText(lineOne.getText());
                    argumentForPercent = BigDecimal.valueOf(Double.parseDouble(lineTwo.getText().toString()) / 100);
                    lineOne.setText(String.valueOf(argumentForPercent) + "%");
                    calculatorStage = 3;
                }
                break;
            case (1):
                lineOne.setText("");
                argumentForPercent = BigDecimal.valueOf(Double.parseDouble(lineTwo.getText().toString()) / 100);
                lineOne.setText(String.valueOf(argumentForPercent) + "%");
                calculatorStage = 3;
                break;
            default:
                break;
        }
    }


    private void stageAction(String argument) {
        switch (calculatorStage) {
            case (0):
            case (2):
                if (argument.equals(".") && lineOne.getText().length() == 0) {
                    line = "0" + argument;
                } else {
                    line = lineOne.getText() + argument;
                }
                lineOne.setText(line);
                break;
            case (1):
                if (lineTwo.getText().length() != 0) {
                    lineOne.setText(argument);
                } else {
                    calculatorStage = 0;
                }
                break;
            default:
                break;

        }
    }

    private void checkStage(String argument) {
        if (argument.equals("number") && calculatorStage % 2 == 1) {
            switch (calculatorStage) {
                case (1):
                    lineThree.setText(lineTwo.getText());
                    lineTwo.setText(lineOne.getText());
                    lineOne.setText("");
                    break;
                case (3):
                    lineOne.setText("");
                    lineTwo.setText("");
                    lineThree.setText("");
                    lineFour.setText("");
                    break;
                default:
                    break;
            }
            calculatorStage = (calculatorStage + 1) % 4;
            dotPushed = false;
        }
        if (argument.equals("symbol") && calculatorStage % 2 == 0) {
            /*switch (calculatorStage) {
                case (0):
                    lineTwo.setText(lineOne.getText());
                    lineOne.setText("");
                    break;
                case (2):
                    lineFour.setText(lineThree.getText());
                    lineThree.setText(lineTwo.getText());
                    lineTwo.setText(lineOne.getText());
                    lineOne.setText("");
                    break;
                default:
                    break;
            }*/
            lineTwo.setText(lineOne.getText());
            lineOne.setText("");
            calculatorStage = (calculatorStage + 1) % 4;
            dotPushed = false;
        }
    }

    private void calculationSequence() {
        BigDecimal argumentOne = BigDecimal.valueOf(Double.parseDouble(lineFour.getText().toString()));
        BigDecimal argumentTwo = BigDecimal.valueOf(Double.parseDouble(lineTwo.getText().toString()));
        switch (lineThree.getText().toString()) {
            case ("+"):
                lineOne.setText(String.valueOf(argumentOne.add(argumentTwo)));
                break;
            case ("-"):
                lineOne.setText(String.valueOf(argumentOne.subtract(argumentTwo)));
                break;
            case ("x"):
                lineOne.setText(String.valueOf(argumentOne.multiply(argumentTwo)));
                break;
            case ("/"):
                lineOne.setText(String.valueOf(argumentOne.divide(argumentTwo,4,6)));
                break;
            default:
                break;
        }
    }
}
