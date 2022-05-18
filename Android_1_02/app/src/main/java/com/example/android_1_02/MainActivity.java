package com.example.android_1_02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private boolean dotPushed = false;
    private TextView lineOne;
    private TextView lineTwo;
    private TextView lineThree;
    private TextView lineFour;
    private int calculatorStage = 0;
    CharSequence line = "";
    private static final String NameSharedPreference = "LOGIN";
    private static final String APP_THEME = "APP_THEME";
    public static int themeNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(themeNumber));
        setContentView(R.layout.activity_main);

        lineOne = findViewById(R.id.calculator_display1);
        lineTwo = findViewById(R.id.calculator_display2);
        lineThree = findViewById(R.id.calculator_display3);
        lineFour = findViewById(R.id.calculator_display4);
        initViews();

    }

    private void initViews() {
        /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/YeomanJackCondensed.otf");
        lineOne.setTypeface(tf);
        lineTwo.setTypeface(tf);
        lineThree.setTypeface(tf);
        lineFour.setTypeface(tf);*/
        Button button0 = findViewById(R.id.button_0);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button buttonDot = findViewById(R.id.button_dot);
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonDelete = findViewById(R.id.button_delete);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDevide = findViewById(R.id.button_devide);
        Button buttonPercent = findViewById(R.id.button_percent);
        Button buttonEven = findViewById(R.id.button_even);

        Button themeButton = findViewById(R.id.themeButton);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("0");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStage("number");
                stageAction("9");
            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dotPushed) {
                    checkStage("number");
                    stageAction(".");
                }
                dotPushed = true;
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorStage = 0;
                lineOne.setText("");
                lineTwo.setText("");
                lineThree.setText("");
                lineFour.setText("");
                dotPushed = false;
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage != 2) {
                    checkStage("symbol");
                    stageAction("+");
                }
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage != 2) {
                    checkStage("symbol");
                    stageAction("-");
                }
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage != 2) {
                    checkStage("symbol");
                    stageAction("x");
                }
            }
        });

        buttonDevide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage != 2) {
                    checkStage("symbol");
                    stageAction("/");
                }
            }
        });

        buttonEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage == 2) {
                    lineFour.setText(lineThree.getText());
                    lineThree.setText(lineTwo.getText());
                    lineTwo.setText(lineOne.getText());
                    lineOne.setText("");
                    if (checkDevisionByZero(String.valueOf(lineTwo.getText()), String.valueOf(lineThree.getText()))) {
                        calculatorStage = 3;
                        lineOne.setText("Деление на ноль!");
                    } else {
                        calculationSequence();
                        calculatorStage = 3;
                    }
                    dotPushed = false;
                }
            }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorStage == 1) {
                    lineOne.setText("");
                } else if (lineOne.getText().length() > 0) {
                    lineOne.setText(lineOne.getText().subSequence(0, lineOne.getText().length() - 1));
                }
            }
        });

        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTheme = new Intent(MainActivity.this, ThemeActivity.class);
                finish();
                startActivity(intentTheme);
            }
        });

    }

    private boolean checkDevisionByZero(String valueOf2, String valueOf3) {
        return (Double.valueOf(valueOf2).equals(Double.valueOf(0.0)) && valueOf3.equals("/"));
    }


    private int getAppTheme(int themeNumber) {
        return codeStyleToStyleId(getCodeStyle(themeNumber));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(APP_THEME, codeStyle);
    }

    void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(APP_THEME, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case (0):
                return R.style.Theme_Android_1_02;
            case (1):
                return R.style.DayCalculatorTheme;
            case (2):
                return R.style.NightCalculatorTheme;
            case (3):
                return R.style.MyCalculatorTheme;
            default:
                return R.style.Theme_Android_1_02;
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
                lineOne.setText(String.valueOf(argumentOne.divide(argumentTwo, 4, 6)));
                break;
            default:
                break;
        }
    }
}
