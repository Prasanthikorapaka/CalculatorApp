package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = Double.NaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        setButtonListeners();
    }

    private void setButtonListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();

                switch (buttonText) {
                    case "C":
                        reset();
                        break;
                    case "⌫":
                        deleteLast();
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        handleOperator(buttonText);
                        break;
                    case "=":
                        calculateResult();
                        break;
                    default:
                        handleNumber(buttonText);
                        break;
                }
            }
        };

        // Set listeners for all buttons
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnEquals, R.id.btnClear, R.id.btnDelete, R.id.btnDecimal
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void reset() {
        currentInput = "";
        operator = "";
        firstOperand = Double.NaN;
        tvDisplay.setText("0");
    }

    private void deleteLast() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            tvDisplay.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
    }

    private void handleOperator(String op) {
        if (!Double.isNaN(firstOperand)) {
            if (!currentInput.isEmpty()) {
                calculateResult();
            }
        }
        operator = op;
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
        }
        currentInput = "";
    }

    private void handleNumber(String number) {
        currentInput += number;
        tvDisplay.setText(currentInput);
    }

    private void calculateResult() {
        if (Double.isNaN(firstOperand) || currentInput.isEmpty()) {
            return;
        }

        double secondOperand = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    tvDisplay.setText("Error");
                    return;
                }
                break;
        }

        tvDisplay.setText(String.valueOf(result));
        firstOperand = result; // Update the first operand to allow for further calculations
        currentInput = "";
        operator = "";
    }
}
