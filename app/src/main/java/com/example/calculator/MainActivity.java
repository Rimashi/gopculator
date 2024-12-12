package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.FlingAnimation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn_plus;
    private Button btn_minus;
    private Button btn_multiply;
    private Button btn_divide;
    private Button btn_clear;
    private Button btn_result;
    private Button btn_open;
    private Button btn_close;
    private Button btn_del;
    private Button point;

    private double num1 = 0;
    private double num2 = 0;
    private double res = 0;
    private boolean f = true;
    private String op = "";
    private String showy = "";
    String result = "";
/*
   private boolean in(String st, String c) {
        for (int i = 0; i < st.length(); i++) {
            if (st[i] == c)
                return true;
        }
        return false;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);
        btn_clear = findViewById(R.id.btn_clear_all);
        btn_result = findViewById(R.id.btn_result);
        btn_open = findViewById(R.id.btn_open);
        btn_close = findViewById(R.id.btn_close);
        btn_del = findViewById(R.id.btn_backspace);
        point = findViewById(R.id.btn_point);
        text.setText("0");
        Button[] buttons_num = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        Button[] buttons_operand = {btn_plus, btn_minus, btn_multiply, btn_divide, btn_open, btn_close};
        for (Button i : buttons_num) {
            i.setOnClickListener(show);
        }
        for (Button i : buttons_operand) {
            i.setOnClickListener(show_operator);
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("0");
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st = text.getText().toString();
                if (text.getText().toString().charAt(text.getText().toString().length() - 1) == '0' || text.getText().toString().equals("0.0") || text.getText().toString().length() <= 1) {
                    st = st;
                } else if (isnum(Character.toString(text.getText().toString().charAt(text.getText().toString().length() - 1)))) {
                    st = st.substring(0, st.length() - 1);
                } else {
                    st = st.substring(0, st.length() - 2);
                }
                text.setText(st);
            }
        });

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPost(text.getText().toString().split(" "));
                System.out.print(text);
            }
        });

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st = text.getText().toString();
                if (isnum(Character.toString(text.getText().toString().charAt(text.getText().toString().length() - 1))) || text.getText().toString().charAt(text.getText().toString().length() - 1) == '0') {
                    st += ((Button) view).getText().toString();
                    text.setText(st);
                } else {
                    text.setText("ошибка ввода");
                }
            }
        });
    }


    private View.OnClickListener show = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String st = text.getText().toString();
            if (text.getText().toString().equals("0") || text.getText().toString().equals("0.0") || text.getText().toString().equals("ошибка ввода")) {
                text.setText("");
                st = "";
                st += ((Button) view).getText().toString();
            } else {
                if (isnum(Character.toString(text.getText().toString().charAt(text.getText().toString().length() - 1))) || text.getText().toString().charAt(text.getText().toString().length() - 1) == '.') {
                    //text.setText("");
                    st += ((Button) view).getText().toString();
                    //System.out.println(st);
                } else if (st.charAt(st.length() - 1) != ' ') {
                    st += " " + ((Button) view).getText().toString();
                } else st += ((Button) view).getText().toString();
            }
            text.setText(st);
            System.out.println(st);
        }

    };


    private View.OnClickListener show_operator = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String st = text.getText().toString();
            if (text.getText().toString().equals("0") || text.getText().toString().equals("0.0") || text.getText().toString().equals("ошибка ввода")) {
                if (((Button) view).getText().toString().equals("/") || ((Button) view).getText().toString().equals("-") || ((Button) view).getText().toString().equals("+") || ((Button) view).getText().toString().equals("*")) {
                    st += " " + ((Button) view).getText().toString() + " ";
                    System.out.println("it works?");
                }
            } else if (st.charAt(st.length() - 1) != ' ') {
                st += " " + ((Button) view).getText().toString() + " ";
            }//внимание, костыли
            else if ((st.charAt(st.length() - 2) == '-' || st.charAt(st.length() - 2) == '*' || st.charAt(st.length() - 2) == '+' || st.charAt(st.length() - 2) == '/') && !((Button) view).getText().toString().equals("(") && !((Button) view).getText().toString().equals(")")) {
                st = st.substring(0, st.length() - 2);
                st += ((Button) view).getText().toString() + " ";
            }//костыли закончились
            else {
                st += ((Button) view).getText().toString() + " ";
            }
            text.setText(st);
            System.out.println(st);
        }
    };


    public boolean isnum(String st) {
        /*for (int i = 0; i < st.length(); i++) {
            if (!Character.isDigit(st.charAt(i)) || st.charAt(i) == '(' || st.charAt(i) == ')') {
                return false;
            }
        }
        return true;*/
        try {
            float num = Float.parseFloat(st);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void toPost(String[] st) {
        //for (String i : st)
        //  System.out.println(i);
        List<String> post = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        HashMap<String, Integer> operands = new HashMap<String, Integer>();
        operands.put("*", 2);
        operands.put("/", 2);
        operands.put("+", 1);
        operands.put("-", 1);

        for (int i = 0; i < st.length; i++) {
            String c = st[i];
            //System.out.println(c);
            if (isnum(c)) {
                //System.out.println(post);
                post.add(c);
            } else if (operands.containsKey(c)) {
                //System.out.println(post);
                while (!stack.isEmpty() && !stack.peek().equals("(") && operands.get(c) <= operands.get(stack.peek())) {
                    post.add(stack.peek());
                    stack.pop();
                    //System.out.println(post);
                }
                stack.push(c);
                //System.out.println(stack);
                //System.out.println(operands.get(c) + operands.get(stack.peek()));
            } else if (c.equals("(")) {
                stack.push(c);
                System.out.println(post);
                //System.out.println(stack);
            } else if (c.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    //System.out.println(post);
                    post.add(stack.peek());
                    stack.pop();
                    //System.out.println(post);
                }
                stack.pop();
                //System.out.println(stack);
            }
        }
        //System.out.println(stack);
        while (!stack.isEmpty()) {
            post.add(stack.peek());
            stack.pop();
        }
        System.out.println(post);
        //post.remove(0);
        //System.out.println(post);
        solve(post);
    }

    private void solve(List<String> post) {
        Stack<Float> stack = new Stack<Float>();
        for (String i : post) {
            if (isnum(i) && !i.equals("")) {
                float c = Float.parseFloat(i);
                stack.push(c);
            } else {
                char c = i.charAt(0);
                if (c == '-') {
                    float num1 = stack.peek();
                    stack.pop();
                    float num2 = stack.peek();
                    stack.pop();
                    stack.push(num2 - num1);
                }
                if (c == '+') {
                    float num1 = stack.peek();
                    stack.pop();
                    float num2 = stack.peek();
                    stack.pop();
                    stack.push(num2 + num1);
                    //stack.push(stack.peek() + stack.peek());
                }
                if (c == '/') {
                    float num1 = stack.peek();
                    stack.pop();
                    float num2 = stack.peek();
                    stack.pop();
                    if (num2 == 0 || num1 == 0 || num1 == 0.0 || num2 == 0.0) {
                        num2 = 0;
                        stack.push(num2);
                    } else stack.push(num2 / num1);
                }
                if (c == '*') {
                    float num1 = stack.peek();
                    stack.pop();
                    float num2 = stack.peek();
                    stack.pop();
                    stack.push(num2 * num1);
                }
            }
        }
        int num = 0;
        System.out.println(stack);
        String[] str = Float.toString(stack.peek()).split("\\.");
        //for (String i : str)
        //  System.out.println(i);
        String str1 = str[1];
        //System.out.println(str1);
        try {
            num = Integer.parseInt(str1);
        } catch (NumberFormatException e) {
            text.setText(Float.toString(stack.peek()));
        }
        if (num == 0) {
            text.setText(str[0]);
        } else {
            text.setText(Float.toString(stack.peek()));
        }
        //text.setText(Float.toString(stack.peek()));
        System.out.println(stack.peek());
    }
    /*Не обязательно подписывать релизным ключом для передачи на тесты.
Зашли в каталог проекта, далее в каталог out, потом production, затем каталог с названием вашего проекта и внутри лежит ваш apk.*/
}